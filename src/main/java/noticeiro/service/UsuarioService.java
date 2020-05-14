package noticeiro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import noticeiro.dao.UsuarioRepository;
import noticeiro.model.Usuario;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository repository;
	
	public void insertUsuario(Usuario usuario) {
		repository.save(new Usuario(usuario.getUsername()));
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
	
	
}
