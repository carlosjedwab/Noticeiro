package noticeiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		String username = usuario.getUsername();
		
		if(usuarioService.usernameJaRegistrado(username)) {
			return new RedirectView("signup?username_error", true);
		}
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuarioService.insertUsuario(usuario);
		return new RedirectView("login", true);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/feed/newlink")
    public RedirectView insertLink(Link link) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        
        if (usuarioService.urlJaRegistrado(link.getUrl(), username)) {
        	return new RedirectView("/feed?url_error", true);
        }
        
        usuarioService.insertLink(link, username);
        return new RedirectView("/feed", true);
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "api/usuario/{username}")
	public Usuario getUsuarioByUsername(@PathVariable("username") String username) {
		return usuarioService.getUsuarioByUsername(username);
	}
	
	@ModelAttribute("links")
    @RequestMapping(method = RequestMethod.GET, path = "/feed")
    public List<Link> getListaLinks(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return usuarioService.getUsuarioByUsername(username).getLinks();
    }
	
	// DELETE methods
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/usuario/{id}")
	public void deleteUsuarioById(@PathVariable("id") String id) {
		usuarioService.deleteUsuarioById(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/feed/delete")
	public RedirectView deleteLinkDoUsuario(String url) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
		usuarioService.deleteUrlDoUsuario(url, username);
		return new RedirectView("/feed", true);
	}
	
	// PUT methods
	
	
}
