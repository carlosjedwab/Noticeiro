package noticeiro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import noticeiro.dao.UsuarioRepository;
import noticeiro.model.Link;
import noticeiro.model.Usuario;
import noticeiro.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
class UsuarioServiceTest {
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository repository;
	
	final String url_do_site1 = "some_url";
	final String url_do_site2 = "some_other_url";
	final String username_do_user = "some_user";
	final String password_do_user = "some_password";
	
	@BeforeEach
	void init() {
		repository.deleteAll();
		usuarioService.insertUsuario(new Usuario(username_do_user, password_do_user));
	}
	
	@Test
	void testGetUsuarioByUsername() {
		assertTrue(usuarioService.getUsuarioByUsername(username_do_user).getUsername().equals(username_do_user));
	}
	
	@Test
	void testUsernameJaRegistrado() {
		assertTrue(usuarioService.usernameJaRegistrado(username_do_user));
	}
	
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
		usuarioService.deleteUrlDoUsuario(url_do_site1, username_do_user);
		assertTrue(!usuarioService.urlJaRegistrado(url_do_site1, username_do_user));
	}

	@Test
	void testUrlJaRegistrado() {
		usuarioService.insertLink(new Link(url_do_site1), username_do_user);
		assertTrue(usuarioService.urlJaRegistrado(url_do_site1, username_do_user));
	}
	
	
}