package com.kchan.project.beyond.notes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		logger.debug(String.format("Found note: %s", result.toString()));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/notes")
	public @ResponseBody List<Note> readNotes(@RequestParam(required=false, value="query") String query) {
		
		if(query != null) logger.info(String.format("Query: [%s]", query));
		return (query ==  null) ? (List<Note>) this.repo.findAll() : this.repo.findByBodyContainingIgnoreCase(query); 
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/notes")
	public @ResponseBody String updateNote(@RequestBody Note note) {
		
		boolean noteFound = this.repo.exists(note.getId());
		this.repo.save(note);
		
		return noteFound ? "Successful update" : "Note does not exist; saved note with a different ID";
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/notes/{id}")
	public @ResponseBody String deleteNote(@PathVariable int id) {
		
		boolean noteFound = this.repo.exists(id);
		this.repo.delete(id);
		
		return noteFound ? "Successful delete" : "Note does not exist";
	}
}
