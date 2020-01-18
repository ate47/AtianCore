package fr.atesab.atiancore.news;

import java.util.Collections;
import java.util.List;

public class ModObject {
	private String author;
	private String name;
	private String icon;
	private String desc;
	private String pageLink;
	private List<ModVersionObject> versions;

	public String getAuthor() {
		return author != null ? author : "???";
	}

	public String getDesc() {
		return desc != null ? desc : "???";
	}

	public String getIcon() {
		return icon;
	}

	public String getName() {
		return name != null ? name : "???";
	}

	public String getPageLink() {
		return pageLink;
	}
	
	public List<ModVersionObject> getVersions() {
		return versions != null ? Collections.unmodifiableList(versions) : Collections.emptyList();
	}
}
