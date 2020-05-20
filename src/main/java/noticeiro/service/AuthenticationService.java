package noticeiro.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import noticeiro.model.Usuario;

@Service
public class AuthenticationService implements UserDetailsService{

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> listGrantAuthority = new ArrayList<GrantedAuthority>();
		listGrantAuthority.add(new GrantedAuthorityImpl("ROLE_USER"));
		Usuario usuario = usuarioService.getUsuarioByUsername(username);
		UserDetails userDetails = validateUsuario(username, listGrantAuthority, usuario);
		return userDetails;
		
	}
	
	private UserDetails validateUsuario(String username,List<GrantedAuthority> listGrantAuthority, Usuario usuario) {
		UserDetails userDetails= null;
		if(usuario != null){
			boolean accountNonLocked=true;
			boolean enabledUser=true;
			boolean accountNonExpired=true;
			boolean credentialsNonExpired=true;
			userDetails = new  org.springframework.security.core.userdetails.User(username,
					usuario.getPassword(),
					enabledUser,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					listGrantAuthority);
		}	
		return userDetails;
	}

}
