package com.kchan.project.beyond.notes.dao;

import java.util.HashMap;
import java.util.Map;

import com.kchan.project.beyond.notes.dto.Note;

public class NotesDaoImpl implements NotesDao {

	private int nextId = 0;
	
	Map<Integer, Note> notes = new HashMap<>();
	
	@Override
	public Note create(String body) {
		Note newNote = new Note(getNextId(), body);
		
		/*
		 * Possible case of overwrite in maps
		 * TODO Change to log4j
		 */
		if(notes.containsKey(newNote.getId())) {			
			System.out.println(String.format("WARN: Note at ID[%d] overwritten!", newNote.getId()));
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
	
	private void incrementNextId() {
		if(this.getNextId() > Integer.MAX_VALUE) {
			this.nextId = 0;
			System.out.println("WARN: Next Note ID pointer reset! Caution of note overwrite.");
		}
		
		this.nextId++;
	}

	@Override
	public int getNextId() {
		return this.nextId+1;
	}
}
