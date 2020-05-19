package noticeiro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import noticeiro.dao.UsuarioRepository;
import noticeiro.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	MockMvc mvc;
	
	@BeforeEach
	void init() {
		repository.deleteAll();
	}
	
	@Test
	void testCadastrarUsuario() throws Exception{
		mvc.perform(MockMvcRequestBuilders
				.post("/forms")
				.content("some_user,123")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
		assertTrue(usuarioService.getUsuarioByUsername("some_user") != null);
	}
}
