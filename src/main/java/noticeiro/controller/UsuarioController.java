package noticeiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import noticeiro.model.Usuario;
import noticeiro.service.UsuarioService;

@RequestMapping("/usuario")
@RestController
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@PostMapping
	public void insertUsuario(@RequestBody Usuario usuario) {
		usuarioService.insertUsuario(usuario);
	}
	
	@GetMapping()
	public List<Usuario> getTodosUsuarios(){
		return usuarioService.getTodosUsuarios();
	}
	
	@GetMapping(path = "{username}")
	public Usuario getUsuarioByUsername(@PathVariable("username") String username) {
		return usuarioService.getUsuarioByUsername(username);
	}
	
	@DeleteMapping(path = "{id}")
	public void deleteUsuarioById(@PathVariable("id") String id) {
		usuarioService.deleteUsuarioById(id);
	}
}
