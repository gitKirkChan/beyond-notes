package com.kchan.project.beyond.notes.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.kchan.project.beyond.notes.dto.Note;

@Repository
public class NotesDaoImpl implements NotesDao {

	private static Logger logger = LogManager.getLogger();
	private int nextId = 0;
	
	Map<Integer, Note> notes = new HashMap<>();
	
	@Override
	public Note create(String body) {
		Note newNote = new Note();
		newNote.setId(getNextId());
		newNote.setBody(body);
		
		/*
		 * Possible case of overwrite in maps
		 */
		if(notes.containsKey(newNote.getId())) {			
			logger.warn(String.format("Note at ID[%d] overwritten!", newNote.getId()));
		}

		notes.put(newNote.getId(), newNote);
		this.incrementNextId();
		
		return notes.get(newNote.getId());
	}

	@Override
	public Note read(int id) {
		return notes.get(id);
	}

	@Override
	public List<Note> read() {
		return new ArrayList<Note>(notes.values());
	}
	
	/*
	 * No longer viable to use Map. Find appropriate database.
	 */
	@Override
	public List<Note> read(String query) {
		return new ArrayList<Note>(notes.values());
	}

	@Override
	public Note update(Note note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getNextId() {
		return this.nextId+1;
	}
	
	/*
	 * TODO Is there a way to avoid this manual upkeep? Java Proxy?...
	 */
	private void incrementNextId() {
		if(this.getNextId() > Integer.MAX_VALUE) {
			this.nextId = 0;
			logger.warn("Next Note ID pointer reset! Caution of note overwrite.");
		}
		
		this.nextId++;
	}
}
