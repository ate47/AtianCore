package fr.atesab.atiancore.config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import fr.atesab.atiancore.reflection.ReflectionUtils;

/**
 * 
 * a loader for a configuration object
 * 
 * @author ATE47
 *
 */
public class ConfigurationLoader {
	private CommentedFileConfig config;
	private Object data;
	private List<ConfigField> fields = new ArrayList<>();

	public ConfigurationLoader(File saveFile, Object data) {
		// load config
		config = CommentedFileConfig.builder(saveFile).sync().writingMode(WritingMode.REPLACE).build();
		this.data = data;

		loadFields(null, data.getClass());
	}

	/**
	 * @return a non mutable list of the fields
	 */
	public List<ConfigField> getFields() {
		return Collections.unmodifiableList(fields);
	}

	private void loadField(ConfigField parent, Config cfg, Field field) {
		ConfigField current = new ConfigField(this, parent, cfg, field);
		Class<?> type = field.getType();
		Configurable configurable = type.getAnnotation(Configurable.class);
		if (configurable != null) {
			loadFields(current, type);
		} else {
			fields.add(current);
			current.setDefaultValue(current.get());
		}
	}

	private void loadFields(ConfigField parent, Class<?> type) {
		ReflectionUtils.forEachFieldWithAnnotation(Config.class, type, (cfg, field) -> loadField(parent, cfg, field));
	}

	/**
	 * save the current configuration state
	 */
	public void save() {
		for (ConfigField field : fields)
			field.setConfig(config);
		config.save();
	}

	/**
	 * sync the configuration with the current state
	 */
	public void sync() {
		config.load();

		for (ConfigField field : fields)
			field.getConfig(config);
		save();
	}

	/**
	 * @return the graph of this config
	 */
	public ConfigGraph buildGraph() {
		ConfigGraph g = new ConfigGraph("", "");
		String path;
		int sec;
		ConfigGraph current;
		for (ConfigField field : fields) {
			path = field.getPath();
			current = g;
			while ((sec = path.indexOf(".")) != -1) {
				current = current.getOrCreateChildren(path.substring(0, sec));
				path = path.substring(sec + 1, path.length());
			}
			current.addField(field);
		}
		return g;
	}

	public Object getConfigObject() {
		return data;
	}
}
