package com.kchan.project.beyond.notes.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.kchan.project.beyond.notes.dto.Note;

/*Yay JUnit4, no annotations needed*/
public class NotesDaoImplTest {

	/*
	 * Happy path of new note created
	 */
	@Test
	public void createNoteAtDao() {
		
		NotesDao dao = new NotesDaoImpl();
		String newNoteBody = "First note!";
		
		dao.create(newNoteBody);
		// Should be ID 1... can it be guaranteed?
		Note noteFound = dao.read(1);
		
		assertNotNull(noteFound);
		assertEquals(newNoteBody, noteFound.getBody());
		assertEquals(2, dao.getNextId());
	}
}
