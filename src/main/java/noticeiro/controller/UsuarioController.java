package noticeiro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import noticeiro.model.Link;
import noticeiro.model.Usuario;
import noticeiro.service.UsuarioService;

@RestController
public class UsuarioController {
	
	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Autowired
	UsuarioService usuarioService;
	
	// POST methods
	@RequestMapping(method = RequestMethod.POST, path="api/usuario")
	public void insertUsuario(@RequestBody Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuarioService.insertUsuario(usuario);
	}
	
	@RequestMapping(method = RequestMethod.POST, path="/forms")
	public RedirectView insertUsuarioPeloForms(Usuario usuario) {
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuarioService.insertUsuario(usuario);
		return new RedirectView("login", true);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/{username}/links")
	public RedirectView insertLink(Link link, 
						   @PathVariable("username") String usernameDoUsuario) {
		usuarioService.insertLink(link, usernameDoUsuario);
		return new RedirectView("/feed", true);
	}
	
	// GET methods
	@RequestMapping(method = RequestMethod.GET, path = "api/usuario")
	public List<Usuario> getTodosUsuarios(){
		return usuarioService.getTodosUsuarios();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "api/usuario/{username}")
	public Usuario getUsuarioByUsername(@PathVariable("username") String username) {
		return usuarioService.getUsuarioByUsername(username);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{username}/links")
	public List<String> getUrlsDoUsuario(@PathVariable("username") String username){
		List<Link> links = usuarioService.getUsuarioByUsername(username).getLinks();
		List<String> urls = new ArrayList<>();
		for(Link link: links) {
			urls.add(link.getUrl());
		}
		return urls;
	}
	
	// DELETE methods
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/usuario/{id}")
	public void deleteUsuarioById(@PathVariable("id") String id) {
		usuarioService.deleteUsuarioById(id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "{username}/links")
	public void deleteLinkDoUsuario(@RequestParam(value = "url", defaultValue = "") String url,
									@PathVariable("username") String username) {
		usuarioService.deleteUrlDoUsuario(url, username);
	}
	
	// PUT methods
	
	
}
