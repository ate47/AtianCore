package fr.atesab.atiancore;

public class FileAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1666642526567558747L;

	public FileAlreadyExistsException() {
	}

	public FileAlreadyExistsException(String message) {
		super(message);
	}

	public FileAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FileAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}
