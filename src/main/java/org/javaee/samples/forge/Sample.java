package org.javaee.samples.forge;

import java.util.Date;
import java.util.List;

public class Sample {

	String id;
	String name;
	String description;
	String html_url;
	String category;
	Date last_updated;
	List<String> usage;
	
	@Override
	public String toString() {
		return "Sample [id=" + id + ", name=" + name + ", description="
				+ description + ", html_url=" + html_url + ", category="
				+ category + ", last_updated=" + last_updated + ", usage="
				+ usage + "]";
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getHtmlURL() {
		return html_url;
	}

	public String getCategory() {
		return category;
	}

	public Date getLastUpdated() {
		return last_updated;
	}

	public List<String> getUsage() {
		return usage;
	}
}
