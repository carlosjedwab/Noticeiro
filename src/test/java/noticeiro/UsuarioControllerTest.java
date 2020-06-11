package noticeiro;

import org.hamcrest.Matchers;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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
import noticeiro.model.Link;
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
	
	final String url_do_site1 = "https://g1.globo.com/rss/g1/";
	final String url_do_site2 = "https://www.wired.com/feed/category/business/latest/rss";
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
	
	@SuppressWarnings("unchecked")
	@Test
	@WithMockUser(username_do_user)
	void testGetLinks() throws Exception {
		mvc.perform(MockMvcRequestBuilders
			.post("/feed/links/add")
			.param("url", url_do_site1));
		MvcResult result = mvc.perform(MockMvcRequestBuilders
			.get("/feed"))
				.andReturn();
		Link content = ((List<Link>) result.getModelAndView().getModel().get("links")).get(0);
		System.out.println("---------------------------------------------------");
		System.out.println(content.getUrl());
		System.out.println("---------------------------------------------------");
		assertTrue(content.getUrl().equals(url_do_site1));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@WithMockUser(username_do_user)
	void testGetPublicacoes() throws Exception {
		mvc.perform(MockMvcRequestBuilders
			.post("/feed/links/add")
			.param("url", url_do_site1));
		MvcResult result = mvc.perform(MockMvcRequestBuilders
			.get("/feed"))
				.andReturn();
		List<Link> content = ((List<Link>) result.getModelAndView().getModel().get("publicacoes"));
		assertTrue(!content.isEmpty());
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