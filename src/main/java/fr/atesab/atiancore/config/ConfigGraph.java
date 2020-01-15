package fr.atesab.atiancore.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigGraph {
	private String path;
	private Map<String, ConfigGraph> childrens = new HashMap<>();
	private List<ConfigField> fields = new ArrayList<>();

	public ConfigGraph(String path) {
		this.path = path;
	}

	public ConfigGraph getOrCreateChildren(String categorie) {
		return childrens.computeIfAbsent(categorie, s -> new ConfigGraph(path + "." + categorie));
	}

	public void addField(ConfigField field) {
		fields.add(field);
	}

	public List<ConfigField> getFields() {
		return Collections.unmodifiableList(fields);
	}

	public Map<String, ConfigGraph> getChildrens() {
		return Collections.unmodifiableMap(childrens);
	}

}
