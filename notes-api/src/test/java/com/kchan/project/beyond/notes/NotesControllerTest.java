package com.kchan.project.beyond.notes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.kchan.project.beyond.notes.dao.NotesDao;
import com.kchan.project.beyond.notes.run.RunApplicationLocally;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RunApplicationLocally.class)
@WebAppConfiguration
public class NotesControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private NotesDao dao;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	/*
	 * Happy path, note created
	 */
	@Test
	public void testCreateNote() throws Exception {
		
		int expectedId = dao.getNextId();
		String expectedBody = "Note created!";
		this.doCreateNote(expectedBody)
			.andExpect(status().isOk())
			.andExpect(content().contentType(this.contentType))
			.andExpect(jsonPath("$.id", is(expectedId)))
			.andExpect(jsonPath("$.body", is(expectedBody)));
	}
	
	/*
	 * Can we find that note we just created?
	 */
	@Test
	public void testReadNote() throws Exception {
		
		int expectedId = dao.getNextId();
		String expectedBody = "Spring REST!";
		this.doCreateNote(expectedBody);
		
		mockMvc.perform(get(String.format("/notes/%d", expectedId)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(this.contentType))
				.andExpect(jsonPath("$.id", is(expectedId)))
				.andExpect(jsonPath("$.body", is(expectedBody)));
	}
	
	/*
	 * Reusable note creation by only passing the note body contents
	 */
	private ResultActions doCreateNote(String noteBody) throws Exception {
		String noteRequest = String.format("{\"body\" : \"%s\"}", noteBody);
		
		System.out.println(noteRequest);

		return mockMvc.perform(post("/notes")
				.contentType(this.contentType)
				.content(noteRequest));

	}
}
