package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {

	private static final long serialVersionUID = -43847260482209169L;

	public NotEnoughStockException() {
		super();
	}

	public NotEnoughStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughStockException(String message) {
		super(message);
	}

	public NotEnoughStockException(Throwable cause) {
		super(cause);
	}

}
