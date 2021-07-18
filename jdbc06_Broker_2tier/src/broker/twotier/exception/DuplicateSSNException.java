package broker.twotier.exception;

public class DuplicateSSNException extends Exception {
	public DuplicateSSNException() {
		this("이미 존재하는 고객입니다..");
	}
	public DuplicateSSNException(String message) {
		super(message);
	}
}
