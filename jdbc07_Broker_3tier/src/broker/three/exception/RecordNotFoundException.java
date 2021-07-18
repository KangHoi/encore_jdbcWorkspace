package broker.three.exception;

public class RecordNotFoundException extends Exception {
	public RecordNotFoundException() {
		this("보유 주식이 없습니다..");
	}
	public RecordNotFoundException(String message) {
		super(message);
	}
}
