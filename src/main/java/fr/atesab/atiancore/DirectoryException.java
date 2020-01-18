package fr.atesab.atiancore;

public class DirectoryException extends RuntimeException {

	private static final long serialVersionUID = 8492234128647412537L;

	public DirectoryException() {
	}

	public DirectoryException(String message) {
		super(message);
	}

	public DirectoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public DirectoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DirectoryException(Throwable cause) {
		super(cause);
	}

}
