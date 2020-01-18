package fr.atesab.atiancore.config;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

import net.minecraft.client.resources.I18n;

public class ConfigField {
	private ConfigurationLoader loader;
	private Object defaultValue;
	private ConfigField parent;
	private Config config;
	private Field field;
	private String path;

	public ConfigField(ConfigurationLoader loader, Config config, Field field) {
		this(loader, null, config, field);
	}

	public ConfigField(ConfigurationLoader loader, @Nullable ConfigField parent, Config config, Field field) {
		this.parent = parent;
		this.config = config;
		this.field = field;
		this.loader = loader;
		this.path = (parent == null ? "" : parent.getPath()) + (config.path().isEmpty() ? "" : (config.path() + "."))
				+ (config.name().isEmpty() ? field.getName() : config.name());

		field.setAccessible(true);
		defaultValue = get();
	}

	/**
	 * @return the value of this field
	 */
	public Object get() {
		if (loader.getConfigObject() == null)
			return null;
		try {
			return field.get(getParentObject());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the config data
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * get this config into a {@link CommentedFileConfig}
	 * 
	 * @param config
	 *            the file config
	 */
	public void getConfig(CommentedFileConfig config) {
		if (!this.config.comment().isEmpty())
			config.setComment(getPath(), this.config.comment());

		Object value = get();
		Object parent = getParentObject();

		try {
			if (value == null) {
				field.set(parent, config.get(path));
			} else {
				field.set(parent, config.getOrElse(path, value));
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the default value
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	public Field getField() {
		return field;
	}

	public ConfigurationLoader getLoader() {
		return loader;
	}

	public Object getParentObject() {
		return parent == null ? loader.getConfigObject() : parent.get();
	}

	public String getPath() {
		return path;
	}

	public void set(Object value) {
		try {
			field.set(getParentObject(), value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void setConfig(CommentedFileConfig config) {
		config.set(getPath(), get());
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getTranslatedName() {
		return config.nameTranslated().isEmpty() ? config.name().isEmpty() ? field.getName() : config.name()
				: I18n.format(config.nameTranslated());
	}

}
