package noticeiro.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Usuario {
	@Id
	public String id;
	@NotBlank
	public String username;
	@NotBlank
	public String password;
	public List<Link> links;

	public Usuario(@JsonProperty("username") String username,
				   @JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
		this.links = new ArrayList<>();
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
	
	
}
