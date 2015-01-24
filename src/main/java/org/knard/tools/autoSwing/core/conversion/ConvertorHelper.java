package org.knard.tools.autoSwing.core.conversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * helper used to convert value from a string representation to a target type.
 * 
 * @author knard
 *
 */
public class ConvertorHelper {

	private static final Logger log = LoggerFactory
			.getLogger(ConvertorHelper.class);

	/**
	 * convert a string representation to a specific type.
	 * 
	 * @param attributeValue
	 *            the string representation of the value
	 * @param valueType
	 *            the value type that is expected
	 * @return an instance of the object created from the string representation
	 */
	@SuppressWarnings("unchecked")
	public static <ValueTye> ValueTye fromString(String attributeValue,
			Class<ValueTye> valueType) {
		if (log.isTraceEnabled()) {
			log.trace("try to convert : " + attributeValue);
			log.trace("try to convert to type : " + valueType.getName());
		}
		if (String.class.equals(valueType)
				|| CharSequence.class.equals(valueType)) {
			return (ValueTye) attributeValue;
		}
		if (Boolean.class.equals(valueType)) {
			return (ValueTye) new Boolean(Boolean.parseBoolean(attributeValue));
		}
		throw new ConversionException(
				"don't know how to convert from string to "
						+ valueType.getName());
	}

}
