package fr.atesab.atiancore.command;

import com.mojang.brigadier.Command;

import fr.atesab.atiancore.command.ModdedCommandHelp.CommandClickOption;
import net.minecraft.command.CommandSource;

public class SimpleModdedCommand extends ModdedCommand {
	private Command<CommandSource> noArgument;

	public SimpleModdedCommand(String name, Command<CommandSource> noArgument) {
		super(name);
		this.noArgument = noArgument;
	}

	public SimpleModdedCommand(String name, String description, CommandClickOption clickOption, boolean displayInHelp,
			Command<CommandSource> noArgument) {
		super(name, description, clickOption, displayInHelp);
		this.noArgument = noArgument;
	}

	public SimpleModdedCommand(String name, String description, CommandClickOption clickOption,
			Command<CommandSource> noArgument) {
		super(name, description, clickOption);
		this.noArgument = noArgument;
	}
	
	@Override
	protected Command<CommandSource> onNoArgument() {
		return noArgument;
	}
}
