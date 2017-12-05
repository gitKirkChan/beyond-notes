package com.kchan.project.beyond.notes.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kchan.project.beyond.notes.dto.Note;

public interface NotesRepository extends CrudRepository<Note, Integer> {

	Note save(Note note);
	
	List<Note> findAll();
	List<Note> findByBody(String body);

}
