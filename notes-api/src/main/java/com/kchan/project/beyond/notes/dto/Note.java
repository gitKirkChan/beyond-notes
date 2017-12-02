package com.kchan.project.beyond.notes.dto;

/*
 * Main data transfer object for the note taking application
 * 
 * TODO Integrate Lombak to remove boilerplate code.
 */
public class Note {
	
	private int id;
	private String body;
	
	public Note(int id, String body) {
		this.id = id;
		this.body = body;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
}
