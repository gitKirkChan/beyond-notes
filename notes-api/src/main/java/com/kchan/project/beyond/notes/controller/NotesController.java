package com.kchan.project.beyond.notes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kchan.project.beyond.notes.dao.NotesRepository;
import com.kchan.project.beyond.notes.dto.Note;

/*
 * Do we need to specify /application-json content?
 * 
 * TODO Add runtime error(s)
 */
@RestController
public class NotesController {
	
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private NotesRepository repo;
	
	@RequestMapping(method=RequestMethod.POST, value="/notes")
	public Note createNote (@RequestBody Note input) {
		
		logger.info(input);
		return repo.save(new Note(input.getBody()));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/notes/{id}")
	public @ResponseBody Note readNote(@PathVariable int id) {
		
		Note result = this.repo.findOne(new Integer(id));
		logger.info(String.format("Found note: %s", result.toString()));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/notes")
	public @ResponseBody List<Note> readNotes(@PathVariable(required=false, value="query") String query) {
		
		return (query ==  null) ? this.repo.findAll() : this.repo.findByBody(query); 
	}
}
