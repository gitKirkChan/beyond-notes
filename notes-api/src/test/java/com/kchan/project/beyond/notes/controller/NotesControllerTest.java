package com.kchan.project.beyond.notes.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kchan.project.beyond.notes.dao.NotesRepository;
import com.kchan.project.beyond.notes.dto.Note;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NotesControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private NotesRepository repo;
	
	@Before
	public void resetData() throws Exception {
		this.repo.deleteAll();
	}

	/*
	 * Happy path, note created
	 */
	@Test
	public void testCreateNote() throws Exception {
		
		String expectedBody = "Note created!";
		this.doHttpCreateNote(expectedBody)
			.andExpect(status().isOk())
			.andExpect(content().contentType(this.contentType))
			.andExpect(jsonPath("$.id").exists())
			.andExpect(jsonPath("$.body", is(expectedBody)));
	}
	
	/*
	 * Can we read the latest note created?
	 */
	@Test
	public void testReadNote() throws Exception {
		
		String expectedBody = "Spring REST!";
		Note createdNote = this.doHttpCreateNoteAndReturnNote(expectedBody);
		
		this.mockMvc.perform(get(String.format("/notes/%d", createdNote.getId())))
				.andExpect(status().isOk())
				.andExpect(content().contentType(this.contentType))
				.andExpect(jsonPath("$.id", is(createdNote.getId())))
				.andExpect(jsonPath("$.body", is(expectedBody)));
	}
	
	/*
	 * Can we read all the notes?
	 */
	@Test
	public void testReadAllNotes() throws Exception {
		
		String expectedBody = "Generic note %d";
		
		for(int i=0; i<5; i++) {
			this.doHttpCreateNote(String.format(expectedBody, i));
		}
		
		this.mockMvc.perform(get("/notes"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(this.contentType))
				.andExpect(jsonPath("$.*", hasSize(5)));
	}
	
	@Test
	public void testReadAllNotesContainingQuery() throws Exception {
		
		String query = "Generic no";

		this.doHttpCreateNote("Note you should not get");
		this.doMassCreateNotes();

		this.mockMvc.perform(get(String.format("/notes?query=%s", query))
				.contentType(this.contentType)
				.content(query))
				.andExpect(jsonPath("$.*", hasSize(5)))
				.andExpect(jsonPath("$[0].body", containsString("Generic")));
	}
	
	@Test
	public void testReadAllNotesContainingCaseSensitiveQuery() throws Exception {
		
		String noteBody = "Mississippi";
		String weirdCaseQuery = "mIssIssIppI";
		
		this.doHttpCreateNote(noteBody);
		this.doHttpCreateNote(noteBody + "State");
		this.doMassCreateNotes();
		
		this.mockMvc.perform(get(String.format("/notes?query=%s", weirdCaseQuery))
				.contentType(this.contentType)
				.content(weirdCaseQuery))
				.andExpect(jsonPath("$.*", hasSize(2)))
				.andExpect(jsonPath("$[0].body", containsString(noteBody)));
	}
	
	/*
	 * Reusable note creation
	 * 
	 * Creates note and returns Note object
	 */
	private Note doHttpCreateNoteAndReturnNote(String noteBody) throws Exception {
		
		String rawResponse = this.doHttpCreateNote(noteBody).andReturn().getResponse().getContentAsString();
		return new ObjectMapper().readValue(rawResponse, Note.class);
	}
	
	/*
	 * Reusable note creation
	 * 
	 * Creates note and returns ResultActions from HTTP response
	 */
	private ResultActions doHttpCreateNote(String noteBody) throws Exception {
		
		String noteRequest = String.format("{\"body\" : \"%s\"}", noteBody);
		
		return this.mockMvc.perform(post("/notes")
				.contentType(this.contentType)
				.content(noteRequest));
	}
	
	/*
	 * Mass note creation
	 */
	private void doMassCreateNotes() throws Exception {
		
		String genericBody = "Generic note %d";

		for(int i=0; i<5; i++) {
			this.doHttpCreateNote(String.format(genericBody, i));
		}
	}
}
