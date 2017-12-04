package com.kchan.project.beyond.notes.dao;

import java.util.List;

import com.kchan.project.beyond.notes.dto.Note;

public interface NotesDao {
	
	// Defined interface
	Note create(String body);
	Note read(int id);
	List<Note> read();
	List<Note> read(String query);
	
	// Freedom to implement behavior/response
	Note update(Note note);
	void delete(int id);
	
	int getNextId();
}
