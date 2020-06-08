package noticeiro;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import noticeiro.dao.UsuarioRepository;
import noticeiro.service.AuthenticationService;
import noticeiro.service.UsuarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {
	@Autowired
	UsuarioService usuarioService;
	
	@Mock
	UsuarioRepository repository;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	final String url_do_site1 = "some_url";
	final String url_do_site2 = "some_other_url";
	final String username_do_user = "some_user";
	final String username_do_admin = "some_admin";
	final String password_do_user = "some_password";
	final String password_do_admin = "some_password";
	
	@BeforeEach
	void init() throws Exception {
		repository.deleteAll();
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		mvc.perform(MockMvcRequestBuilders
			.post("/signup/try")
			.param("username", username_do_user)
			.param("password", password_do_user));
	}
	
	@Test
	void testCadastrarUsuario() throws Exception {
		assertTrue(usuarioService.getUsuarioByUsername(username_do_user) != null);
	}
	
	@Test
	@WithMockUser(username_do_user)
	void testNewLink() throws Exception {
		mvc.perform(MockMvcRequestBuilders
			.post("/feed/links/add")
			.param("url", url_do_site1));
		
		assertTrue(usuarioService.urlJaRegistrado(url_do_site1, username_do_user));
	}
	
	@Test
	@WithMockUser(username_do_user)
	void testGetLinks() throws Exception {
		mvc.perform(MockMvcRequestBuilders
			.post("/feed/links/add")
			.param("url", url_do_site1));
		MvcResult result = mvc.perform(MockMvcRequestBuilders
			.get("links"))
		.andReturn();
		//.andExpect(status().isOk())
		//.andExpect(content().string(containsString(url_do_site1)));
		
		String content = result.getResponse().getContentAsString();
		// arrumar (Carlos)
		
		//assertTrue(usuarioService.urlJaRegistrado(url_do_site1, username_do_user));
	}
	
	@Test
	@WithMockUser(username_do_user)
	void testDeleteLink() throws Exception {
		mvc.perform(MockMvcRequestBuilders
			.post("/feed/links/add")
			.param("url", url_do_site1));
		mvc.perform(MockMvcRequestBuilders
			.post("/feed/links/delete")
			.param("url", url_do_site1));
		
		assertTrue(!usuarioService.urlJaRegistrado(url_do_site1, username_do_user));
	}
	
	// Falta o teste do getter da lista de links
	
	// Falta o teste para excluir usuário por id, mas precisa ser admin pra isso, então o @WithMockUser nn funciona
}