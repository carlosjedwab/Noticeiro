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
	
	@BeforeEach
	void init() {
		repository.deleteAll();
		usuarioService.insertUsuario(new Usuario("some_user", "123"));
	}
	
	@Test
	void testGetUsuarioByUsername() {
		assertTrue(usuarioService.getUsuarioByUsername("some_user").getUsername().equals("some_user"));
	}
	
	@Test
	void testUsernameJaRegistrado() {
		assertTrue(usuarioService.usernameJaRegistrado("some_user"));
	}
	
	@Test
	void testDeleteUsuarioById() {
		usuarioService.deleteUsuarioById(usuarioService.getUsuarioByUsername("some_user").getId());
		assertTrue(usuarioService.getUsuarioByUsername("some_user") == null);
	}
	
	@Test
	void testInserirLink() {
		usuarioService.insertLink(new Link("some_link"), "some_user");
		assertTrue(usuarioService.getUsuarioByUsername("some_user").getLinks().get(0).getUrl().equals("some_link"));
	}
	
	@Test
	void testDeleteUrlDoUsuario() {
		usuarioService.insertLink(new Link("some_link"), "some_user");
		usuarioService.deleteUrlDoUsuario("some_link", "some_user");
		assertTrue(usuarioService.getUsuarioByUsername("some_user").getLinks().isEmpty());
	}

	@Test
	void testUrlJaRegistrado() {
		usuarioService.insertLink(new Link("some_link"), "some_user");
		assertTrue(usuarioService.urlJaRegistrado("some_link", "some_user"));
	}
	
	
}
