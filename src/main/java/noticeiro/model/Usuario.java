package noticeiro.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Usuario {
	@Id
	public String id;
	
	@NotBlank
	@Pattern(regexp = "[^\\s]\\S*[^\\s]\\S*[^\\s]")
	String username;
	
	@NotBlank
	@Pattern(regexp = "[^\\s]\\S*[^\\s]\\S*[^\\s]")
	String password;
	List<Link> links;
	List<String> tag;

	public Usuario(@JsonProperty("username") String username,
				   @JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
		this.links = new ArrayList<>();
		this.tag = new ArrayList<>();
	}
	
	public void insertLink(Link link) {
		links.add(link);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public List<String> getTag() {
		return tag;
	}

	public void setTag(List<String> tag) {
		this.tag = tag;
	}
	public void insertTag(String tag) {
		this.tag.add(tag);
	}
	
}