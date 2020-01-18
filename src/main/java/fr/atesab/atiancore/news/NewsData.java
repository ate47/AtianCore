package fr.atesab.atiancore.news;

import java.util.Collections;
import java.util.List;

public class NewsData {
	private String message;
	private List<ModObject> mods;

	public String getMessage() {
		return message != null ? message : "";
	}

	public List<ModObject> getMods() {
		return mods != null ? Collections.unmodifiableList(mods) : Collections.emptyList();
	}
}
