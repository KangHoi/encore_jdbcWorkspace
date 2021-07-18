package com.encore.exception;

public class InvalidInputException extends Exception{
	public InvalidInputException() {
		this("max 가격이 min 가격보다 커야합니다..");
	}
	public InvalidInputException(String message) {
		super(message);
	}
}
