package com.kchan.project.beyond.notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kchan.project.beyond.notes.domain.Note;
import com.kchan.project.beyond.notes.domain.NoteRepository;

@SpringBootApplication
public class RunApp {
	
	public static void main(String[] args) {
		SpringApplication.run(RunApp.class, args);
	}
	
	/*
	 * Bean to inject data prior to SpringApplication.run runs :)
	 */
	@Bean
	protected CommandLineRunner demo(NoteRepository repo) {
		return (args) -> {
			repo.save(new Note("Buy milk before going home."));
			repo.save(new Note("Tell Luke about his father."));
			repo.save(new Note("Review Beyond app."));
			repo.save(new Note("Got milk?"));
		};
	}
}
