package com.kchan.project.beyond.notes.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.kchan.project.beyond.notes.dto.Note;

public class NotesDaoImpl implements NotesDao {

	private static Logger logger = LogManager.getLogger();
	private int nextId = 0;
	
	Map<Integer, Note> notes = new HashMap<>();
	
	@Override
	public Note create(String body) {
		Note newNote = new Note(getNextId(), body);
		
		/*
		 * Possible case of overwrite in maps
		 */
		if(notes.containsKey(newNote.getId())) {			
			logger.warn(String.format("Note at ID[%d] overwritten!", newNote.getId()));
		}

		notes.put(newNote.getId(), newNote);
		logger.info(String.format("Note at ID[%d] created.", newNote.getId()));
		this.incrementNextId();
		
		return notes.get(newNote.getId());
	}

	@Override
	public Note read(int id) {
		return notes.get(id);
	}

	@Override
	public Note[] read() {
		return (Note[]) notes.values().toArray();
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
	 * Is there a way to avoid this manual upkeep? Java Proxy?...
	 */
	private void incrementNextId() {
		if(this.getNextId() > Integer.MAX_VALUE) {
			this.nextId = 0;
			logger.warn("Next Note ID pointer reset! Caution of note overwrite.");
		}
		
		this.nextId++;
	}
}
