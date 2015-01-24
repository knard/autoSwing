package org.knard.tools.autoSwing.core.conversion;

/**
 * exception thrown by the <code>ConvertorHelper</code> class when conversion can't be done.
 * 
 * @see ConvertorHelper
 * @author knard
 *
 */
public class ConversionException extends RuntimeException {

	private static final long serialVersionUID = 8932808766486518110L;

	public ConversionException() {
		super();
	}

	public ConversionException(String message) {
		super(message);
	}

	public ConversionException(Throwable cause) {
		super(cause);
	}

	public ConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConversionException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
