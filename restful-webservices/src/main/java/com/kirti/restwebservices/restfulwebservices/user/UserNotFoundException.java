package com.kirti.restwebservices.restfulwebservices.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException { //By default class extends super class "Exception", then exception will become a checked exception.
	//To avoid checked exceptions, go with "RuntimeException"

	public UserNotFoundException(String message) {
		super(message);
	}

	
}
