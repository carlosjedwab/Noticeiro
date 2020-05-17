package noticeiro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import noticeiro.dao.UsuarioRepository;
import noticeiro.model.Link;
import noticeiro.model.Usuario;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository repository;
	
	public void insertUsuario(Usuario usuario) {
		repository.save(new Usuario(usuario.getUsername(), usuario.getPassword()));
	}
	
	public Usuario getUsuarioByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public List<Usuario> getTodosUsuarios(){
		return repository.findAll();
	}
	
	public void deleteUsuarioById(String id) {
		repository.deleteById(id);
	}
	
	public void deleteUrlDoUsuario(String url, String usernameDoUsuario) {
		Usuario usuario = getUsuarioByUsername(usernameDoUsuario);
		for(Link link: usuario.getLinks()) {
			if(link.getUrl().contentEquals(url)) {
				usuario.getLinks().remove(link);
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
