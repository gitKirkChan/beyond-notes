package com.kchan.project.beyond.notes.domain;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kchan.project.beyond.notes.domain.Note;
import com.kchan.project.beyond.notes.domain.NoteRepository;

/*Yay JUnit4, no annotations needed*/
@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRepositoryTest {

	@Autowired
	NoteRepository repo;

	@Before
	public void resetData() throws Exception {
		
		this.repo.deleteAll();
	}
	
	/*
	 * Happy path of new note created
	 */
	@Test
	public void createNote() {
		
		String newNoteBody = "A fresh note!";
		
		Note savedNote = this.repo.save(new Note(newNoteBody));
		
		assertNotNull(savedNote);
		assertNotNull(savedNote.getId());
		assertEquals(newNoteBody, savedNote.getBody());
	}
	
	@Test
	public void readNote() {
		
		Note expectedNote = this.repo.save(new Note("Drafted note"));
		Note noteFound = this.repo.findOne(expectedNote.getId());
		
		assertEquals(expectedNote.getId(), noteFound.getId());
		assertEquals(expectedNote.getBody(), noteFound.getBody());
	}
	
	@Test
	public void readAllNotes() {
		
		this.doPopulateDatabase();
		List<Note> notesFound = (List<Note>) this.repo.findAll();
		
		assertTrue(notesFound.size() == 5);
	}
	
	@Test
	public void readNotesContaining() {
		
		String query = "Note num";
		
		this.repo.save(new Note("Distraction"));
		this.doPopulateDatabase();
		
		List<Note> notesFound = (List<Note>) this.repo.findByBodyContainingIgnoreCase(query);
		
		assertTrue(notesFound.size() == 5);
		for(int i=0; i<5; i++) {
			assertTrue(notesFound.get(i).getBody().contains("Note numero"));
		}
	}
	
	@Test
	public void readNotesContainingIgnoreCase() {

		String query = "NoTe NuM";
		
		this.repo.save(new Note("Distraction"));
		this.doPopulateDatabase();
		
		List<Note> notesFound = (List<Note>) this.repo.findByBodyContainingIgnoreCase(query);
		
		assertTrue(notesFound.size() == 5);
	}
	
	@Test
	public void updateNote() {
		
		Note note = this.repo.save(new Note("Drafted note"));
		note.setBody("Finalized note");
		
		Note updatedNote = this.repo.save(note);
		assertEquals(note.getId(), updatedNote.getId());
		assertEquals(note.getBody(), updatedNote.getBody());
	}
	
	@Test
	public synchronized void deleteNote() {
		
		Note note = this.repo.save(new Note("Note to trash"));
		
		this.repo.delete(note.getId());
		assertNull(this.repo.findOne(note.getId()));
	}
	
	private void doPopulateDatabase() {

		this.repo.save(new Note("Note numero uno"));
		this.repo.save(new Note("Note numero dos"));
		this.repo.save(new Note("Note numero tres"));
		this.repo.save(new Note("Note numero quatro"));
		this.repo.save(new Note("Note numero cinco"));
	}
}
