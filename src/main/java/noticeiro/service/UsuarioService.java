package noticeiro.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import noticeiro.DateUtils;
import noticeiro.LeitorXML;
import noticeiro.dao.UsuarioRepository;
import noticeiro.model.Link;
import noticeiro.model.Publicacao;
import noticeiro.model.Usuario;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepository repository;
	
	// NOT USED BY THE USER CONTROLLER methods ---------------------------------------------------------------------
	
	public void setRepository(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public void clearRepository() {
		this.repository.deleteAll();
	}
	
	// "EDIT repository" methods (return void) ---------------------------------------------------------------------

	public void insertUsuario(Usuario usuario) {
		repository.save(new Usuario(usuario.getUsername(), usuario.getPassword()));
	}
	
	public void insertLink(Link link, String usernameDoUsuario) {
		Usuario usuario = getUsuarioByUsername(usernameDoUsuario);
		usuario.insertLink(new Link(link.getUrl()));
		repository.save(usuario);
	}
	
	public void deleteLink(String url, String usernameDoUsuario) {
		Usuario usuario = getUsuarioByUsername(usernameDoUsuario);
		for(Link link: usuario.getLinks()) {
			if(link.getUrl().contentEquals(url)) {
				usuario.getLinks().remove(link);
				break;
			}
		}
		repository.save(usuario);
	}
	
	public void deleteUsuarioById(String id) {
		repository.deleteById(id);
	}
	
	public void inserirTag(String tags, String usernameDoUsuario) {
		Usuario usuario = getUsuarioByUsername(usernameDoUsuario);
		List<String> listTags = LeitorXML.dividirTag(tags);
		for(String tag : listTags) {
			usuario.insertTag(tag);
		}
		repository.save(usuario);
	}	
	
	public void deleteTag(String tag, String usernameDoUsuario) {
		Usuario usuario = getUsuarioByUsername(usernameDoUsuario);
		for(int i = 0; i < usuario.getTag().size(); i++) {
			if(usuario.getTag().get(i).contentEquals(tag)){
				usuario.getTag().remove(i);
				break;
			}
		}
		repository.save(usuario);
	}
	
	// "CHECK from repository" methods (return boolean) ---------------------------------------------------------------------
	
	public boolean usernameJaRegistrado(String username) {
		for(Usuario usuario: repository.findAll()) {
			if(usuario.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean urlJaRegistrado(String url, String username) {
		for (Link link : getUsuarioByUsername(username).getLinks()) {
			if(url.equals(link.getUrl())) {
     			return true;
     		}
		}
		return false;
	}
	
	public boolean tagJaRegistrado(@Valid @NotNull String tags, String username) {
		List<String> listaTag = LeitorXML.dividirTag(tags);
		for (String tagExistente : getUsuarioByUsername(username).getTag()) {
			for(String tag: listaTag) {
				if(tag.equals(tagExistente)) {
	     			return true;
	     		}
			}
		}
		return false;
	}
	
	// "GET form repository" methods (return Object) ---------------------------------------------------------------------
	
	public Usuario getUsuarioByUsername(String username) {
		return repository.findByUsername(username);
	}
	
	public List<Link> getLinksByUsername(String username) {
		return repository.findByUsername(username).getLinks();
	}
	
	public List<String> getTagsByUsername(String username){
		return repository.findByUsername(username).getTag();
	}


	
	public List<Publicacao> getPublicationsByUsername(String username) {
		List<Publicacao> listaPub = new ArrayList<>();
		for(Link link:getLinksByUsername(username)) {
			for(Publicacao pub:LeitorXML.lerRSS(link.getUrl())) {
				listaPub.add(pub);
			}
		}
		return listaPub;
	}
	
	public List<Publicacao> getPublicationsByUsernameTags(String username) {
		List<Publicacao> listaPub = new ArrayList<>();
		List<String> listaTags = getTagsByUsername(username);
		
		for(Link link:getLinksByUsername(username)) {
			for(Publicacao pub: LeitorXML.lerRSS(link.getUrl(), listaTags)) {
					listaPub.add(pub);
			}
		}
		return listaPub;
	}
	
	public List<Publicacao> filterPubsByDate(List<Publicacao> listaPub, String minDate, String maxDate) throws ParseException {
		List<Publicacao> filteredListaPub = new ArrayList<>();
		for(Publicacao pub:listaPub) {
			if (DateUtils.sameDateInterval(pub.getDate(), minDate, maxDate)) {
				filteredListaPub.add(pub);
			}
		}
		return filteredListaPub;
	}
	
	public List<Publicacao> filterPubsByTime(List<Publicacao> listaPub, String minTime, String maxTime) throws ParseException {
		List<Publicacao> filteredListaPub = new ArrayList<>();
		for(Publicacao pub:listaPub) {
			if (DateUtils.sameTimeInterval(pub.getDate(), minTime, maxTime)) {
				filteredListaPub.add(pub);
			}
		}
		return filteredListaPub;
	}
}