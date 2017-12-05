package com.kchan.project.beyond.notes.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kchan.project.beyond.notes.dto.Note;

/*Yay JUnit4, no annotations needed*/
@RunWith(SpringRunner.class)
@DataJpaTest
public class NotesRepositoryTest {

	@Autowired
	NotesRepository repo;

	/*
	 * Happy path of new note created
	 */
	@Test
	public void createNoteAtDao() {
		
		String newNoteBody = "First note!";
		
		Note savedNote = repo.save(new Note(newNoteBody));
		
		assertNotNull(savedNote);
		assertNotNull(savedNote.getId());
		assertEquals(newNoteBody, savedNote.getBody());
	}
}
