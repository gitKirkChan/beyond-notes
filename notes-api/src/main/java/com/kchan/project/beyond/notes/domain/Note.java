package com.kchan.project.beyond.notes.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Yay Lombok
 */
@Entity
@Getter @Setter @NoArgsConstructor
@ToString @EqualsAndHashCode
public class Note {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String body;
	
	@JsonCreator
	public Note(@JsonProperty("body") String body) {
		this.body = body;
	}
}
