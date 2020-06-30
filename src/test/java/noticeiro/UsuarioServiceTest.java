package noticeiro;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import noticeiro.dao.UsuarioRepository;
import noticeiro.model.Link;
import noticeiro.model.Usuario;
import noticeiro.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
class UsuarioServiceTest {
	//@InjectMocks
	@Autowired
	UsuarioService usuarioService;
	
	@Mock
	UsuarioRepository repository;
	
	final String url_do_site1 = "https://g1.globo.com/rss/g1/";
	final String url_do_site2 = "https://www.wired.com/feed/category/business/latest/rss";
	final String username_do_user = "some_user";
	final String password_do_user = "some_password";
	
	@BeforeEach
	void init() {
		usuarioService.clearRepository(); // Clear not mocked repository. That is, erases real Data Base
		//repository.deleteAll(); // Clear mocked repository, but tests don't work
		usuarioService.insertUsuario(new Usuario(username_do_user, password_do_user));
		
		//when(usuarioService.getUsuarioByUsername(username_do_user)).thenReturn(new Usuario(username_do_user, password_do_user));
		// This with "@InjectMocks UsuarioService usuarioService;" works for most test, but doen't actually adds a new user to the repository, so you can't delete or check its existence.
		// No need to insertUsuario with this activated
	}
	
	// "EDIT repository" methods (return void) ---------------------------------------------------------------------
	
	@Test
	void testDeleteUsuarioById() {
		usuarioService.deleteUsuarioById(usuarioService.getUsuarioByUsername(username_do_user).getId());
		assertTrue(usuarioService.getUsuarioByUsername(username_do_user) == null);
	}
	
	@Test
	void testInserirLink() {
		usuarioService.insertLink(new Link(url_do_site1), username_do_user);
		assertTrue(usuarioService.getUsuarioByUsername(username_do_user).getLinks().get(0).getUrl().equals(url_do_site1));
	}
	
	@Test
	void testDeleteUrlDoUsuario() {
		usuarioService.insertLink(new Link(url_do_site1), username_do_user);
		usuarioService.deleteLink(url_do_site1, username_do_user);
		assertTrue(!usuarioService.urlJaRegistrado(url_do_site1, username_do_user));
	}
	
	// "CHECK from repository" methods (return boolean) ---------------------------------------------------------------------
	
	@Test
	void testUsernameJaRegistrado() {
		assertTrue(usuarioService.usernameJaRegistrado(username_do_user));
	}
	
	@Test
	void testLinkJaRegistrado() {
		usuarioService.insertLink(new Link(url_do_site1), username_do_user);
		assertTrue(usuarioService.urlJaRegistrado(url_do_site1, username_do_user));
	}
	
	// "GET form repository" methods (return Object) ---------------------------------------------------------------------
	
	@Test
	void testGetUsuarioByUsername() {
		assertTrue(usuarioService.getUsuarioByUsername(username_do_user).getUsername().equals(username_do_user));
	}
	
	@Test
	void testGetLinksByUsername() {
		usuarioService.insertLink(new Link(url_do_site1), username_do_user);	
		assertTrue(usuarioService.getLinksByUsername(username_do_user).get(0).getUrl().equals(url_do_site1));
	}
	
	@Test
	void testGetPublicationsByUsername() {
		usuarioService.insertLink(new Link(url_do_site1), username_do_user);
		assertTrue(usuarioService.getPublicationsByUsername(username_do_user) != null);
	}
	
}