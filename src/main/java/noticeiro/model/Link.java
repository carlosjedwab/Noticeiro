package noticeiro.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
	String url;
	
	public Link (@JsonProperty("url") String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
}
