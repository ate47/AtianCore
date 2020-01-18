package fr.atesab.atiancore;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;

import fr.atesab.atiancore.command.ModdedCommand;
import fr.atesab.atiancore.config.Config;
import fr.atesab.atiancore.config.ConfigurationLoader;
import fr.atesab.atiancore.reflection.ReflectionUtils;
import fr.atesab.atiancore.ui.Ui;
import fr.atesab.atiancore.ui.Ui.BuildUiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.client.gui.GuiModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.moddiscovery.ModInfo;

@Mod(AtianCore.MOD_ID)
public class AtianCore {
	public static final String MOD_ID = "atiancore";
	public static final Logger LOGGER = Logger.getLogger(MOD_ID);
	private static CommandDispatcher<CommandSource> dispatcher = new CommandDispatcher<>();
	private static Set<String> commandSet = new HashSet<>();
	private static CommandDispatcher<ISuggestionProvider> suggestionProvider;

	/**
	 * create a {@link ConfigurationLoader} for a mod with an ID
	 * 
	 * @param modID
	 *            the mod ID
	 * @param configObject
	 *            the config object to parse
	 * @return the {@link ConfigurationLoader}
	 */
	public static ConfigurationLoader createConfigForMod(String modID, Object configObject) {
		return createConfigToFile(FMLPaths.CONFIGDIR.get().resolve(modID + ".toml").toFile(), configObject);
	}

	/**
	 * create a {@link ConfigurationLoader} for an object on a file
	 * 
	 * @param file
	 *            the file
	 * @param configObject
	 *            the config object to parse
	 * @return the {@link ConfigurationLoader}
	 */
	public static ConfigurationLoader createConfigToFile(File file, Object configObject) {
		return new ConfigurationLoader(file, configObject);
	}

	/**
	 * @return the command dispatcher of the core
	 */
	public static CommandDispatcher<CommandSource> getCommandDispatcher() {
		return dispatcher;
	}

	/**
	 * @return a non mutable set of the commands registered
	 */
	public static Set<String> getCommandSet() {
		return Collections.unmodifiableSet(commandSet);
	}

	/**
	 * register a creative command to the act command dispatcher (unless we can't
	 * create client command)
	 * 
	 * @param command
	 *            the command
	 * @since 2.0
	 */
	public static void registerCommand(ModdedCommand command) {
		commandSet.addAll(command.getAliases());
		command.register(dispatcher);
	}

	/**
	 * register a config ui for a mod
	 * 
	 * @param modID
	 *            the modID
	 * @param builder
	 *            the UI builder
	 */
	public static void registerConfigUi(String modID, BuildUiConsumer<?> builder) {
		ModList.get().getModContainerById(modID).ifPresent(mc -> {
			mc.registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
					() -> (m, s) -> builder.build(Ui.createFakeUi(s)).getMcScreen());
		});
	}

	private ConfigurationLoader config;

	@Config(name = "infoMessage", comment = "Super useless config")
	private boolean uselessConfig = true;

	public AtianCore() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void checkModList(GuiScreen screen) {
		// enabling the config button
		if (screen != null && screen instanceof GuiModList) {
			ModInfo info = ReflectionUtils.getFirstFieldOfTypeInto(ModInfo.class, screen);
			if (info != null) {
				Optional<? extends ModContainer> op = ModList.get().getModContainerById(info.getModId());
				if (op.isPresent()) {
					boolean value = op.get().getCustomExtension(ExtensionPoint.CONFIGGUIFACTORY).isPresent();
					String config = I18n.format("fml.menu.mods.config");
					for (IGuiEventListener b : screen.getChildren())
						if (b instanceof GuiButton && ((GuiButton) b).displayString.equals(config))
							((GuiButton) b).enabled = value;
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createSuggestion(CommandNode<CommandSource> dispatcher,
			CommandNode<ISuggestionProvider> rootCommandNode, CommandSource player,
			Map<CommandNode<CommandSource>, CommandNode<ISuggestionProvider>> suggestions) {
		for (CommandNode<CommandSource> child : dispatcher.getChildren()) {
			ArgumentBuilder<ISuggestionProvider, ?> argumentbuilder = (ArgumentBuilder) child.createBuilder();
			argumentbuilder.requires((ctx) -> true);
			if (argumentbuilder.getCommand() != null)
				argumentbuilder.executes((ctx) -> 0);

			if (argumentbuilder instanceof RequiredArgumentBuilder) {
				RequiredArgumentBuilder<ISuggestionProvider, ?> requiredargumentbuilder = (RequiredArgumentBuilder) argumentbuilder;
				if (requiredargumentbuilder.getSuggestionsProvider() != null) {
					requiredargumentbuilder.suggests(
							SuggestionProviders.func_197496_b(requiredargumentbuilder.getSuggestionsProvider()));
				}
			}

			if (argumentbuilder.getRedirect() != null) {
				argumentbuilder.redirect(suggestions.get(argumentbuilder.getRedirect()));
			}

			CommandNode<ISuggestionProvider> commandnode1 = argumentbuilder.build();
			suggestions.put(child, commandnode1);
			rootCommandNode.addChild(commandnode1);
			if (!child.getChildren().isEmpty()) {
				this.createSuggestion(child, commandnode1, player, suggestions);
			}
		}

	}

	private void injectSuggestions() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null && mc.player.connection != null) {
			CommandDispatcher<ISuggestionProvider> current = mc.player.connection.func_195515_i(); // getSuggestionProvider()
			if (current != suggestionProvider) {
				suggestionProvider = current;
				if (current != null) {
					Map<CommandNode<CommandSource>, CommandNode<ISuggestionProvider>> map = Maps.newHashMap();
					RootCommandNode<ISuggestionProvider> root = suggestionProvider.getRoot();
					map.put(dispatcher.getRoot(), root);
					createSuggestion(dispatcher.getRoot(), root, mc.player.getCommandSource(), map);
				}
			}
		}
	}

	@SubscribeEvent
	public void onChatMessage(ClientChatEvent ev) {
		String msg = ev.getMessage();
		// check if it is a command
		if (!msg.startsWith("/"))
			return;

		final String command = msg.substring(1);

		// check if we know it
		if (!commandSet.contains(command.split(" ", 2)[0]))
			return;

		// we know it, we remove it
		ev.setCanceled(true);
		Minecraft.getInstance().ingameGUI.getChatGUI().addToSentMessages(msg);

		StringReader reader = new StringReader(msg);
		if (reader.canRead())
			reader.skip(); // remove the '/'
		CommandSource source = Minecraft.getInstance().player.getCommandSource();

		try {
			ParseResults<CommandSource> parse = dispatcher.parse(reader, source);
			dispatcher.execute(parse);
		} catch (CommandException e) {
			source.sendErrorMessage(e.getComponent());
		} catch (CommandSyntaxException e) {
			source.sendErrorMessage(TextComponentUtils.toTextComponent(e.getRawMessage()));
			if (e.getInput() != null && e.getCursor() >= 0) {
				int messageSize = Math.min(e.getInput().length(), e.getCursor());
				ITextComponent error = (new TextComponentString("")).applyTextStyle(TextFormatting.GRAY)
						.applyTextStyle((style) -> {
							style.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, msg));
						});
				if (messageSize > 10) {
					error.appendText("...");
				}

				error.appendText(e.getInput().substring(Math.max(0, messageSize - 10), messageSize));
				if (messageSize < e.getInput().length()) {
					ITextComponent itextcomponent2 = (new TextComponentString(e.getInput().substring(messageSize)))
							.applyTextStyles(new TextFormatting[] { TextFormatting.RED, TextFormatting.UNDERLINE });
					error.appendSibling(itextcomponent2);
				}

				error.appendSibling((new TextComponentTranslation("command.context.here"))
						.applyTextStyles(new TextFormatting[] { TextFormatting.RED, TextFormatting.ITALIC }));
				source.sendErrorMessage(error);
			}
		} catch (Exception e) {
			ITextComponent error = new TextComponentString(
					e.getMessage() == null ? e.getClass().getName() : e.getMessage());
			if (LOGGER.isLoggable(Level.WARNING))
				e.printStackTrace();

			source.sendErrorMessage((new TextComponentTranslation("command.failed")).applyTextStyle((style) -> {
				style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, error));
			}));
		}
	}

	@SubscribeEvent
	public void onClientTickClient(TickEvent.ClientTickEvent ev) {
		if (ev.phase == Phase.END)
			checkModList(Minecraft.getInstance().currentScreen);
	}

	@SubscribeEvent
	public void onInitGui(InitGuiEvent.Post ev) {
		injectSuggestions();
	}

	private void setup(FMLLoadCompleteEvent ev) {
		config = createConfigForMod(MOD_ID, this);
		config.sync();

		if (uselessConfig) {
			LOGGER.info("Super user config enabled");
		}
	}
}
