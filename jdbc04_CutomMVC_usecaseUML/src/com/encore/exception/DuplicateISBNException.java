package com.encore.exception;

public class DuplicateISBNException extends Exception{
	public DuplicateISBNException() {
		this("중복되는 도서입니다..");
	}
	public DuplicateISBNException(String message) {
		super(message);
	}
}
