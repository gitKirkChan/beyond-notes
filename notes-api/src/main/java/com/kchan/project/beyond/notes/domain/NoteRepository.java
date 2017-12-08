package com.kchan.project.beyond.notes.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/*
 * Through Spring Data, it generates the operations defined by the CrudRepository:
 * https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
 * 
 * Accepted repository query keywords for custom repository operations:
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords
 */
public interface NoteRepository extends CrudRepository<Note, Integer> {
	
	List<Note> findByBodyContainingIgnoreCase(String body);
}
