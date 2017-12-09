package com.kchan.project.beyond.notes.web;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kchan.project.beyond.notes.domain.Note;
import com.kchan.project.beyond.notes.domain.NoteRepository;
import com.kchan.project.beyond.notes.exception.runtimes.NotFoundException;

@RestController
public class NoteController {

	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private NoteRepository repo;
	
	@RequestMapping(method=RequestMethod.POST, value="/notes", consumes="application/json")
	public Note createNote (@RequestBody Note input) {
		
		logger.debug(String.format("Creating note containing [%s]", input));
		return repo.save(new Note(input.getBody()));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/notes/{id}")
	public @ResponseBody Note readNote(@PathVariable Integer id) {
		
		boolean exists = this.repo.exists(id);
		
		if(!exists) {
			throw new NotFoundException(String.format("Could not find id[%s]", id.toString()));
		}
		
		Note result = this.repo.findOne(id);
		logger.debug(String.format("Found note: \n%s", result.toString()));
		return result;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/notes")
	public @ResponseBody List<Note> readNotes(@RequestParam(value="query", required=false) Optional<String> query) {
		
		logger.debug(query.isPresent() ? 
					String.format("Querying [%s]", query.get()) : "Fetch all notes");
		
		return query.isPresent() ?
					this.repo.findByBodyContainingIgnoreCase(query.get()) :
					(List<Note>) this.repo.findAll();
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/notes", consumes="application/json")
	public ResponseEntity<String> updateNote(@RequestBody Note note) {
		
		boolean noteFound = this.repo.exists(note.getId());
		this.repo.save(note);
		
		logger.debug(String.format("\n\nUPDATE NOTE: %s", note.toString()));
		
		return noteFound ? 
				new ResponseEntity<>("Update successful", HttpStatus.OK) : 
				new ResponseEntity<>("Note does not exist; saved note with a different ID", HttpStatus.CREATED);
	}
	
	/*
	 * Synchronized to prevent possibility of Exception thrown during delete operation
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="/notes/{id}")
	public synchronized ResponseEntity<String> deleteNote(@PathVariable("id") Integer id) {
		
		boolean noteFound = this.repo.exists(id);
		
		if(!noteFound) {
			throw new NotFoundException("Note does not exist");
		}
		
		this.repo.delete(id);
		
		return new ResponseEntity<>("Delete successful", HttpStatus.OK); 
	}
}
