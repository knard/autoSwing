package org.knard.tools.autoSwing.model.propertyAccessor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * convenient class to set a map in which property name are associated to they
 * PropertyAccessor instance.
 * 
 * @author knard
 *
 * @param <T>
 */
public class PropertyAccessorMapBuilder<T> {

	/**
	 * factory used to create the PropertyAccessor.
	 */
	private static final PropertyAccessorFactory factory = new PropertyAccessorFactory();

	/**
	 * 
	 * internal class used to store information about the PropertyAccessor that
	 * should be generated.
	 * 
	 * @author knard
	 *
	 */
	private static final class PropertyDescriptor {

		/**
		 * property name of the property we want to access
		 */
		public String propertyName;

		/**
		 * type of the property we want to access
		 */
		public Class<?> valueType;
	}

	/**
	 * class of the object that hold properties that we want to set the map for.
	 */
	private Class<T> objectType;

	/**
	 * list of descriptor that will be used to set the map of PropertyAccessor
	 */
	private Set<PropertyDescriptor> descriptors = new HashSet<PropertyDescriptor>();

	/**
	 * private constructor used to enforce usage of the static method as entry
	 * point.
	 */
	private PropertyAccessorMapBuilder() {
	}

	/**
	 * instanciate a new PropertyAccessorMapBuilder
	 * 
	 * @param objectType
	 *            object type owning the property we want to access.
	 * @return a new PropertyAccessorMapBuilder
	 */
	public static <T> PropertyAccessorMapBuilder<? extends T> create(
			Class<T> objectType) {
		PropertyAccessorMapBuilder<T> builder = new PropertyAccessorMapBuilder<T>();
		builder.objectType = objectType;
		return builder;
	}

	/**
	 * add a property to the list of property that should be set to the map.
	 * 
	 * @param propertyName
	 *            the property name that we want to add
	 * @param valueType
	 *            the property type
	 * @return return the PropertyAccessorMapBuilder that as been used to call
	 *         this method. Useful to simplify code and link call.
	 */
	public PropertyAccessorMapBuilder<T> addProperty(String propertyName,
			Class<?> valueType) {
		PropertyDescriptor desc = new PropertyDescriptor();
		desc.propertyName = propertyName;
		desc.valueType = valueType;
		descriptors.add(desc);
		return this;
	}

	/**
	 * set the map with all property names as key and PropertyAccessor as value.
	 * 
	 * @param map
	 *            the map that should be set
	 * @return return the PropertyAccessorMapBuilder that as been used to call
	 *         this method. Useful to simplify code and link call.
	 */
	@SuppressWarnings("unchecked")
	public PropertyAccessorMapBuilder<T> set(
			@SuppressWarnings("rawtypes") Map map) {
		for (PropertyDescriptor desc : descriptors) {
			map.put(desc.propertyName, factory.getAccessor(objectType,
					desc.propertyName, desc.valueType));
		}
		return this;
	}

}
