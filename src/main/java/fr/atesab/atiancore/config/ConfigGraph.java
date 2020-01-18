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
	private String categorie;

	public ConfigGraph(String path, String categorie) {
		this.path = path;
		this.categorie = categorie;
	}

	public ConfigGraph getOrCreateChildren(String categorie) {
		return childrens.computeIfAbsent(categorie, s -> new ConfigGraph(path + "." + categorie, categorie));
	}

	public void addField(ConfigField field) {
		fields.add(field);
	}

	public String getCategorie() {
		return categorie;
	}

	public List<ConfigField> getFields() {
		return Collections.unmodifiableList(fields);
	}

	public Map<String, ConfigGraph> getChildrens() {
		return Collections.unmodifiableMap(childrens);
	}

}
