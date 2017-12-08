package com.kchan.project.beyond.notes.exception.runtimes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7997734896724456976L;

	public NotFoundException(String message) {
		super(message);
	}
}
