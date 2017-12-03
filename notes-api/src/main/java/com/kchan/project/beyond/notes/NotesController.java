package com.kchan.project.beyond.notes;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * Do we need to specify /application-json content?
 */
@RestController
public class NotesController {
	
	@RequestMapping(path="/notes", method=RequestMethod.GET)
	public String hello (@RequestParam(value="body", defaultValue="World") String body) {
		return String.format("Hello %s", body);
	}
}
