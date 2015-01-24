package org.knard.tools.autoSwing.model.propertyAccessor;

/**
 * Exception thrown in case of problem during PropertyAccessor class generation
 * 
 * @author knard
 *
 */
public class PropertyAccessorFactoryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3558892911505726313L;

	public PropertyAccessorFactoryException(Throwable cause) {
		super(cause);
	}

	public PropertyAccessorFactoryException(String msg) {
		super(msg);
	}

}