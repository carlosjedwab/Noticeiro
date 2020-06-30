package noticeiro.controller;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import noticeiro.LeitorXML;
import noticeiro.PublicacaoComparator;
import noticeiro.model.Datas;
import noticeiro.model.Link;
import noticeiro.model.Publicacao;
import noticeiro.model.ListaDeTags;
import noticeiro.model.Usuario;
import noticeiro.service.UsuarioService;

@RestController
public class UsuarioController {
	
	PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	@Autowired
	UsuarioService usuarioService;
	
	// POST methods ---------------------------------------------------------------------
	
	@RequestMapping(method = RequestMethod.POST, path="/signup/try")
	public RedirectView insertUsuarioPeloForms(@Valid @NotBlank Usuario usuario, BindingResult result) {
		if(result.hasErrors()) {
			return new RedirectView("/signup?invalid", true);
		}
		
		String username = usuario.getUsername();
		
		if(usuarioService.usernameJaRegistrado(username)) {
			return new RedirectView("/signup?username_error", true);
		}
		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuarioService.insertUsuario(usuario);
		return new RedirectView("/login", true);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/feed/links/add")
    public RedirectView insertLink(@Valid @NotNull Link link, BindingResult result) {
		if(result.hasErrors() || !LeitorXML.checarValidadeDoRSS(link.getUrl())) {
			return new RedirectView("/feed?invalid", true);
		}
		
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        
        if (usuarioService.urlJaRegistrado(link.getUrl(), username)) {
        	return new RedirectView("/feed?url_error", true);
        }
        
        usuarioService.insertLink(link, username);
        return new RedirectView("/feed", true);
    }
	
	@RequestMapping(method = RequestMethod.POST, path = "/feed/links/delete")
	public RedirectView deleteLink(Link link) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
		usuarioService.deleteLink(link.getUrl(), username);
		return new RedirectView("/feed", true);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/feed/tag/add")
    public RedirectView insertTag(@Valid @NotNull ListaDeTags tags, BindingResult result) {
		if(result.hasErrors()) {
			return new RedirectView("/feed?invalid_tag", true);
		}		
		
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        
        if (usuarioService.tagJaRegistrado(tags.getString(), username)) {
            return new RedirectView("/feed?tag_error", true);
        }
        System.out.println(tags.getString());
        usuarioService.inserirTag(tags.getString(), username);
		
        return new RedirectView("/feed", true);   
    }
	
	@RequestMapping(method = RequestMethod.POST, path = "/feed/tag/delete")
	public RedirectView deleteTag(ListaDeTags tag) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
		usuarioService.deleteTag(tag.getString(), username);
		return new RedirectView("/feed", true);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/feed/data")
	public RedirectView linkData (Datas datas) {
		
		String url = "/feed" + "/" + datas.getMinDate() + "/" + datas.getMaxDate() 
					+ "/" + datas.getMinTime() + "/" + datas.getMaxTime();
        
		return new RedirectView(url, true);
	}
	
	
	
	// GET methods ---------------------------------------------------------------------
	
	@RequestMapping(method = RequestMethod.GET, path = "/feed")
	public ModelAndView getFeed() {
		
		ModelAndView mv = new ModelAndView("feed");

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
    
        List<Link> listaLinks = usuarioService.getLinksByUsername(username); 
        List<String> listaTags = usuarioService.getTagsByUsername(username);
        List<Publicacao> listaPub = usuarioService.getPublicationsByUsernameTags(username);
        listaPub.sort(new PublicacaoComparator());
        
		mv.addObject("links", listaLinks);
		mv.addObject("tags", listaTags);
		mv.addObject("publicacoes", listaPub);
		
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/feed/{minDate}/{maxDate}/{minTime}/{maxTime}")
	public ModelAndView getFeedWithFilter(
			@PathVariable("minDate") String minDate, @PathVariable("maxDate") String maxDate,
			@PathVariable("minTime") String minTime, @PathVariable("maxTime") String maxTime) throws ParseException {
		
		ModelAndView mv = new ModelAndView("feed");

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
    
        List<Link> listaLinks = usuarioService.getLinksByUsername(username);
        List<String> listaTags = usuarioService.getTagsByUsername(username);
        List<Publicacao> listaPub = usuarioService.filterPubsByTime(
        		usuarioService.filterPubsByDate (
        				usuarioService.getPublicationsByUsernameTags(username),
        				minDate, maxDate
        		),
        		minTime, maxTime
        );
        listaPub.sort(new PublicacaoComparator());
		
		mv.addObject("links", listaLinks);
		mv.addObject("tags", listaTags);
		mv.addObject("publicacoes", listaPub);
		return mv;
	}
 
	// DELETE methods ---------------------------------------------------------------------
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/delete")
	public void deleteAllUsuarios() {
		usuarioService.clearRepository();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/api/usuario/{id}/delete")
	public void deleteUsuarioById(@PathVariable("id") String id) {
		usuarioService.deleteUsuarioById(id);
	}
	
}
