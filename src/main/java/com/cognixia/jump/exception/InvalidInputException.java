package com.cognixia.jump.exception;

public class InvalidInputException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public InvalidInputException(int user_id) {
		super("The user_id that you inputed is not on the server");
	}
}
