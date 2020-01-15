package fr.atesab.atiancore.config;

import java.lang.reflect.Field;

import javax.annotation.Nullable;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;

public class ConfigField {
	private ConfigField parent;
	private Config config;
	private Field field;
	private String path;

	public ConfigField(Config config, Field field) {
		this(null, config, field);
	}

	public ConfigField(@Nullable ConfigField parent, Config config, Field field) {
		this.parent = parent;
		this.config = config;
		this.field = field;
		this.path = (parent == null ? "" : parent.getPath()) + (config.path().isEmpty() ? "" : (config.path() + "."))
				+ (config.name().isEmpty() ? field.getName() : config.name());


		field.setAccessible(true);

	}

	public Object get(Object data) throws IllegalAccessException {
		return field.get(getParentObject(data));
	}

	public Config getConfig() {
		return config;
	}

	public void getConfig(CommentedFileConfig config, Object data) throws IllegalAccessException {
		if (!this.config.comment().isEmpty())
			config.setComment(getPath(), this.config.comment());

		Object value = get(data);
		Object parent = getParentObject(data);

		if (value == null) {
			field.set(parent, config.get(path));
		} else {
			field.set(parent, config.getOrElse(path, value));
		}
	}

	public Field getField() {
		return field;
	}

	public Object getParentObject(Object data) throws IllegalAccessException {
		return parent == null ? data : parent.get(data);
	}

	public String getPath() {
		return path;
	}

	public void set(Object data, Object value) throws IllegalAccessException {
		field.set(getParentObject(data), value);
	}

	public void setConfig(CommentedFileConfig config, Object data) throws IllegalAccessException {
		config.set(getPath(), get(data));
	}

}
