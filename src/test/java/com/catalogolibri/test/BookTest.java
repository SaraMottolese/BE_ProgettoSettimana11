package com.catalogolibri.test;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;

import com.catalogolibri.model.Author;
import com.catalogolibri.model.Book;
import com.catalogolibri.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BookTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@WithMockUser
	final void testGetAll() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/libro")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser
	final void testGetById() throws Exception {
		this.mockMvc.perform(get("http://localhost:8080/api/libro/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testDeleteById() throws Exception {
		this.mockMvc.perform(delete("http://localhost:8080/api/libro/1")).andDo(print()).andExpect(status().isOk());	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPost() throws Exception {
		Category c1 = new Category();
		c1.setName("categoria1");
		Author a1 = new Author();
		a1.setName("nome1");
		a1.setSurname("cognome1");
		Book l1 = new Book();
		l1.setTitle("titolo1");
		l1.setPrice(30.5);
		l1.setPublicationDate("2022/6/12");
		l1.setCategories((List<Category>)c1);
		l1.setAuthors((List<Author>)a1);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String json = objectMapper.writeValueAsString(l1);
	    
	    MvcResult result = mockMvc.perform(
	            post("/api/libro")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isOk())
	            .andExpect((ResultMatcher) content().json("{'titolo':'titolo1'}"))
	            .andExpect((ResultMatcher) content().json("{'prezzo': 30.5}"))
	            .andReturn();
	}
	
	@Test
	@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
	final void testPut() throws Exception {
		Category c1 = new Category();
		c1.setName("Horror");
		Author a1 = new Author();
		a1.setName("Stephen");
		a1.setSurname("King");
		Book l1 = new Book();
		l1.setTitle("titolo1");
		l1.setPrice(30.5);
		l1.setPublicationDate("1989/8/13");
		l1.setCategories((List<Category>)c1);
		l1.setAuthors((List<Author>)a1);
		
		ObjectMapper objectMapper = new ObjectMapper();
	    String json = objectMapper.writeValueAsString(l1);
	    
	    MvcResult result = mockMvc.perform(
	            put("/api/libro/1")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(json))
	            .andExpect(status().isOk())
	            .andExpect((ResultMatcher) content().json("{'titolo':'titolo1'}"))
	            .andExpect((ResultMatcher) content().json("{'prezzo': 30.5}"))
	            .andReturn();
	}
}
