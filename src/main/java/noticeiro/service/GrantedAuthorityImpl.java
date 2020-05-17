package noticeiro.service;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
public class GrantedAuthorityImpl implements GrantedAuthority{
	String role;

	public GrantedAuthorityImpl(String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}
	
}
