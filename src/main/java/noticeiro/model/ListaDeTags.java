package noticeiro.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListaDeTags {
	@NotBlank
	String string;
	
	public ListaDeTags (@JsonProperty("string") String string) {
		this.string = string.trim();
	}
	
	public String getString() {
		return string;
	}
}