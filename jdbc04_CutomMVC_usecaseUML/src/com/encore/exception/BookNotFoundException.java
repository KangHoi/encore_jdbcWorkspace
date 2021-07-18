package com.encore.exception;

public class BookNotFoundException extends Exception{
		public BookNotFoundException() {
			this("해당하는 책이 없습니다..");
		}
		public BookNotFoundException(String message) {
			super(message);
		}
}
