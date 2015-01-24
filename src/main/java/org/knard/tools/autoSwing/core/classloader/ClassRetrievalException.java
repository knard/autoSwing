package org.knard.tools.autoSwing.core.classloader;

public class ClassRetrievalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7202626667794839855L;

	public ClassRetrievalException() {
		super();
	}

	public ClassRetrievalException(String message) {
		super(message);
	}

	public ClassRetrievalException(Throwable cause) {
		super(cause);
	}

	public ClassRetrievalException(String message, Throwable cause) {
		super(message, cause);
	}

}
