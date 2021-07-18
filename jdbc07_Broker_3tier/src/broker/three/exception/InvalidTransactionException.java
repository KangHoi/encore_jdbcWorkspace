package broker.three.exception;

public class InvalidTransactionException extends Exception {
	public InvalidTransactionException() {
		this("존재하지 않는 거래입니다.. ");
	}
	public InvalidTransactionException(String message) {
		super(message);
	}
}
