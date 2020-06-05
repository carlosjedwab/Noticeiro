package noticeiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import noticeiro.dao.UsuarioRepository;
import noticeiro.model.Link;
import noticeiro.model.Usuario;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository repository;
	
	public void setRepository(UsuarioRepository repository) {
		this.repository = repository;
	}

	public void insertUsuario(Usuario usuario) {
		repository.save(new Usuario(usuario.getUsername(), usuario.getPassword()));
	}
	
	public Usuario getUsuarioByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	// Possivel problema de performance
	public boolean usernameJaRegistrado(String username) {
		for(Usuario usuario: repository.findAll()) {
			if(usuario.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	// Possivel problema de performance
	public boolean urlJaRegistrado(String url, String username) {
		for (Link link : getUsuarioByUsername(username).getLinks()) {
			if(url.equals(link.getUrl())) {
     			return true;
     		}
		}
		return false;
	}
	
	public void deleteUsuarioById(String id) {
		repository.deleteById(id);
	}
	
	public void deleteUrlDoUsuario(String url, String usernameDoUsuario) {
		Usuario usuario = getUsuarioByUsername(usernameDoUsuario);
		for(Link link: usuario.getLinks()) {
			if(link.getUrl().contentEquals(url)) {
				usuario.getLinks().remove(link);
				break;
			}
		}
		repository.save(usuario);
	}
	
	public void insertLink(Link link, String usernameDoUsuario) {
		Usuario usuario = getUsuarioByUsername(usernameDoUsuario);
		usuario.insertLink(new Link(link.getUrl()));
		repository.save(usuario);
	}
}