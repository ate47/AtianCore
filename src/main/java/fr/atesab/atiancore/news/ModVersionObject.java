package fr.atesab.atiancore.news;

public class ModVersionObject {
	private String version;
	private String link;

	public String getLink() {
		return link;
	}
	
	public String getVersion() {
		return version == null ? "" : version;
	}
}
