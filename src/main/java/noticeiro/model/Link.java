package noticeiro.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {
	@NotBlank
	@Pattern(regexp = "\\S*[^\\s]\\S*")
	String url;
	
	public Link (@JsonProperty("url") String url) {
		this.url = url.trim();
	}
	
	public String getUrl() {
		return url;
	}
}
