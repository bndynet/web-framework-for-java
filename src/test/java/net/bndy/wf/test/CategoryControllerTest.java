package net.bndy.wf.test;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.swagger.models.HttpMethod;
import net.bndy.wf.domain.Category;
import net.bndy.wf.repository.CategoryRepository;

import static org.junit.Assert.*;

public class CategoryControllerTest extends  _Test {

	MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	@Autowired
	private TestRestTemplate restTempalte;

	static Category category;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@BeforeClass
	static public void init() {
		category = new Category();
		category.setName("category test");
		category.setCreatedAt(new Date());
	}
	
	@Test
	public void testHttpPost() {
		Category response = this.restTempalte.postForObject("/api/categories", category, Category.class);
		assertTrue(response.getId() > 0);
		category.setId(response.getId());;
	}
	
	@Test
	public void testHttpPut() {
		category.setName("changed");
		this.restTempalte.put("/api/categories/" + category.getId(), category);
	}
	
	@Test
	public void testHttpGet() {
		Category response = this.restTempalte.getForObject("/api/categories/" + category.getId(), Category.class);
//		Category response = this.restTempalte.execute(url, method, requestCallback, responseExtractor)("/api/categories/" + category.getId(), Category.class);
		assertTrue(response.getId() > 0);
	}
	
//	@Test
//	public void testHttpPatch() {
//		HttpEntity<Category> request = new HttpEntity<>(category);
//		Category response = this.restTempalte.("/api/categories/" + category.getId(), HttpMethod.PATCH, request, Category.class);
//		assertTrue(response.getId() > 0);
//	}
	
	@Test
	public void testHttpGetAll() {
		@SuppressWarnings("rawtypes")
		ArrayList response = this.restTempalte.getForObject("/api/categories", ArrayList.class);
		assertTrue(response.size() > 0);
	}
}
