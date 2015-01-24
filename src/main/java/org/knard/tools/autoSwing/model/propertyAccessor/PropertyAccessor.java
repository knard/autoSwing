package org.knard.tools.autoSwing.model.propertyAccessor;

/**
 * represent a property accessor. It's to have a normalized way to access object
 * property.
 * 
 * @author knard
 *
 * @param <ObjectType>
 *            the type of object that own the property we want to access
 * @param <ValueType>
 *            the type of the property we want to access
 */
public interface PropertyAccessor<ObjectType, ValueType extends Object> {

	/**
	 * retrieve the property from the object passed as parameter
	 * 
	 * @param object
	 *            the object from which we want to access the property
	 * @return the property value
	 */
	ValueType getPropertyValue(ObjectType object);

	/**
	 * return the property type. This is a convenient method as extracting type
	 * from generics can be tricky
	 * 
	 * @return the type of the property
	 */
	Class<ValueType> getPropertyType();

	/**
	 * if the property have a setter this method is used to set the property
	 * value. If the property can't be set the method do nothing.
	 * 
	 * @param object
	 *            the object from which we want to access the property
	 * @param value
	 *            the value that has to be set
	 */
	void setProperty(ObjectType object, ValueType value);

}
