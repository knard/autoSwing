package org.knard.tools.autoSwing.model.propertyAccessor;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * Used to generate PropertyAccessor class. It's avoid to use reflection and to
 * have to code every PropertyAcessor manually. New GuiElement implementation
 * just have to describe properties and the corresponding PropertyAccessor can
 * be generated.
 * 
 * @author knard
 *
 */
public class PropertyAccessorFactory {

	private static final Logger log = LoggerFactory
			.getLogger(PropertyAccessorFactory.class);

	/**
	 * package suffix used during class generation.
	 */
	private static final String GENERATION_PACKAGE = ".gen.Accessor";

	/**
	 * id of the next generated class
	 */
	private static volatile long implementationId = 0;

	/**
	 * cached variable of the javassist ClassPool
	 */
	private ClassPool pool;

	/**
	 * cached variable of the package used to generate class.
	 */
	private String packageName;

	public PropertyAccessorFactory() {
		pool = ClassPool.getDefault();
		packageName = getClass().getPackage().getName() + GENERATION_PACKAGE;
		if (log.isTraceEnabled()) {
			log.trace("package used for generating accessor : " + packageName);
		}
	}

	/**
	 * method used to generate a PropertyAccessor
	 * 
	 * @param objectToaccess
	 *            the object class against which we want to generate the
	 *            PropertyAccessor
	 * @param properyToAccess
	 *            the property name
	 * @param propertyType
	 *            the expected type of the property
	 * @return
	 */
	public <ObjectType, ValueType> PropertyAccessor<ObjectType, ValueType> getAccessor(
			Class<ObjectType> objectToaccess, String properyToAccess,
			Class<ValueType> propertyType) {
		if (log.isDebugEnabled()) {
			log.debug("generating accessor to property " + properyToAccess
					+ "for object " + objectToaccess.getName()
					+ " and expecting " + propertyType.getName()
					+ " as return type.");
		}
		String getterName = findGetter(objectToaccess, properyToAccess,
				propertyType);
		if (getterName == null) {
			throw new PropertyAccessorFactoryException(
					"can't find anyway to access property " + properyToAccess);
		}
		String setterName = findSetter(objectToaccess, properyToAccess,
				propertyType);
		String className = getClassName();
		String objectToAccessClassName = objectToaccess.getName();
		if (log.isTraceEnabled()) {
			log.trace("getter found : " + getterName);
			log.trace("setter found : " + setterName);
			log.trace("generated class name : " + className);
		}
		CtClass ctClass = createImplementation(getterName, setterName,
				className, objectToAccessClassName, propertyType);
		try {
			@SuppressWarnings("unchecked")
			PropertyAccessor<ObjectType, ValueType> newInstance = (PropertyAccessor<ObjectType, ValueType>) ctClass.toClass()
					.newInstance();
			return newInstance;
		} catch (InstantiationException e) {
			throw new PropertyAccessorFactoryException(e);
		} catch (IllegalAccessException e) {
			throw new PropertyAccessorFactoryException(e);
		} catch (CannotCompileException e) {
			throw new PropertyAccessorFactoryException(e);
		}
	}

	/**
	 * find the getter name that should be used in the PropertyAccessor
	 * implementation.
	 * 
	 * @param objectToaccess
	 *            class representing the object type holding the property.
	 * @param propertyToAccess
	 *            name of the property.
	 * @param propertyType
	 *            the expected type of the property.
	 * @return the name of the getter to use.
	 */
	private String findGetter(Class<?> objectToaccess, String propertyToAccess,
			Class<?> propertyType) {
		String[] possibleGetterNames = getPossibleGetterName(propertyToAccess);
		for (String possibleGetterName : possibleGetterNames) {
			Method m;
			try {
				m = objectToaccess.getMethod(possibleGetterName);
			} catch (NoSuchMethodException e) {
				if (log.isTraceEnabled()) {
					log.trace(possibleGetterName
							+ " is not the getter name for property "
							+ propertyToAccess);
				}
				// no method found -> try next name
				continue;
			} catch (SecurityException e) {
				throw new PropertyAccessorFactoryException(e);
			}
			if (m.getReturnType().equals(propertyType)) {
				if (log.isTraceEnabled()) {
					log.trace(possibleGetterName
							+ " is the getter for property " + propertyToAccess);
				}
				return possibleGetterName;
			}
			if (log.isTraceEnabled()) {
				log.trace(possibleGetterName
						+ " doesn't have the coorect return type. Expecting : "
						+ propertyType.getName() + " . Found : "
						+ m.getReturnType().getName());
			}
		}
		return null;
	}

	/**
	 * return all possible getter name for a property
	 * 
	 * @param propertyToAccess
	 *            the property for which we want to generate getter name
	 * @return an array containing all possible getter names
	 */
	private String[] getPossibleGetterName(String propertyToAccess) {
		String preparedPropertyName = propertyToAccess.substring(0, 1)
				.toUpperCase() + propertyToAccess.substring(1);
		return new String[] { "get" + preparedPropertyName,
				"is" + preparedPropertyName, "has" + preparedPropertyName };
	}

	/**
	 * find the setter name that should be used in the PropertyAccessor
	 * implementation.
	 * 
	 * @param objectToaccess
	 *            class representing the object type holding the property.
	 * @param propertyToAccess
	 *            name of the property.
	 * @param propertyType
	 *            the expected type of the property.
	 * @return the name of the setter to use.
	 */
	private String findSetter(Class<?> objectToaccess, String propertyToAccess,
			Class<?> propertyType) {
		String[] possibleSetterNames = getPossibleSetterName(propertyToAccess);
		for (String possibleSetterName : possibleSetterNames) {
			Method m;
			try {
				m = objectToaccess.getMethod(possibleSetterName, propertyType);
			} catch (NoSuchMethodException e) {
				if (log.isTraceEnabled()) {
					log.trace(possibleSetterName
							+ " is not the setter name for property "
							+ propertyToAccess);
				}
				// no method found -> try next name
				continue;
			} catch (SecurityException e) {
				throw new PropertyAccessorFactoryException(e);
			}
			Class<?>[] paramTypes = m.getParameterTypes();
			if (paramTypes.length == 1 && paramTypes[0].equals(propertyType)) {
				if (log.isTraceEnabled()) {
					log.trace(possibleSetterName
							+ " is the setter for property " + propertyToAccess);
				}
				return possibleSetterName;
			}
			if (log.isTraceEnabled()) {
				log.trace(possibleSetterName
						+ " doesn't have the correct parameter type or count");
			}
		}
		return null;
	}

	/**
	 * return all possible setter name for a property
	 * 
	 * @param propertyToAccess
	 *            the property for which we want to generate setter name
	 * @return an array containing all possible setter names
	 */
	private String[] getPossibleSetterName(String propertyToAccess) {
		String preparedPropertyName = propertyToAccess.substring(0, 1)
				.toUpperCase() + propertyToAccess.substring(1);
		return new String[] { "set" + preparedPropertyName };
	}

	/**
	 * generate a unique class name that can be used to generate the
	 * PropertyAccessor
	 * 
	 * @return the class name
	 */
	private String getClassName() {
		return packageName + getNextId();
	}

	/**
	 * get the next id that can be used to generate class name. this method is
	 * used to synchronize the access to the implementationId field.
	 * 
	 * @return the next id.
	 */
	private synchronized long getNextId() {
		return implementationId++;
	}

	/**
	 * create a new class representing the PropertyAccessor
	 * 
	 * @param getterName
	 *            the getter name that should be used to access the property
	 * @param setterName
	 *            the setter name in case the property has a setter, otherwise
	 *            <code>null</code>
	 * @param className
	 *            the class name that can be used to generate the
	 *            PropertyAccessor
	 * @param objectToAccessClassName
	 *            the class name of the target object.
	 * @param propertyType
	 *            the type of the property that we want to access
	 * @return a class representing the PropertyAccessor implementation
	 */
	private CtClass createImplementation(String getterName, String setterName,
			String className, String objectToAccessClassName,
			Class<?> propertyType) {
		try {
			CtClass ctClass = pool.makeClass(className);
			ctClass.addInterface(pool.get(PropertyAccessor.class.getName()));
			ctClass.addMethod(CtNewMethod.make(
					getGetterSourceCode(getterName, objectToAccessClassName,
							propertyType), ctClass));
			ctClass.addMethod(CtNewMethod.make(
					getSetterSourceCode(setterName, objectToAccessClassName,
							propertyType), ctClass));
			ctClass.addMethod(CtNewMethod.make(
					getGetTypeSourceCode(propertyType), ctClass));
			return ctClass;
		} catch (NotFoundException e) {
			throw new PropertyAccessorFactoryException(e);
		} catch (CannotCompileException e) {
			throw new PropertyAccessorFactoryException(e);
		}
	}

	/**
	 * generate the java source code of the getPropertyValue method that should
	 * be implemented by the PropertyAccessor
	 * 
	 * @param getterName
	 *            the getter name used to access the property
	 * @param objectToAccessClassName
	 *            the type of the object holding the property
	 * @param propertyType
	 *            the type of the property
	 * @return the source code of the getPropertyValue method
	 */
	private String getGetterSourceCode(String getterName,
			String objectToAccessClassName, Class<?> propertyType) {
		String code = "public java.lang.Object getPropertyValue(java.lang.Object object) { return "
				+ castReturn(propertyType,
						getValueRetrieval(objectToAccessClassName, getterName))
				+ ";}";
		if (log.isDebugEnabled()) {
			log.debug("generated code for getter :");
			log.debug(code);
		}
		return code;
	}

	/**
	 * generate the piece of code used to access the property
	 * 
	 * @param objectToAccessClassName
	 *            the type of the object holding the property
	 * @param getterName
	 *            the getter name used to access the property
	 * @return
	 */
	private String getValueRetrieval(String objectToAccessClassName,
			String getterName) {
		return "((" + objectToAccessClassName + ") object)." + getterName
				+ "()";
	}

	/**
	 * correctly casting the property to be able to return it. As the interface
	 * use generics the method return type to implement is define as
	 * <code>Object</code> and have to be explicitly cast.
	 * 
	 * @param returnType
	 *            the return type
	 * @param valueRetrievalCode
	 *            the piece of code used to retrieve the property value.
	 * @return the piece of code used to retrieve the property correctly casted.
	 */
	private String castReturn(Class<?> returnType, String valueRetrievalCode) {
		if (boolean.class.equals(returnType)) {
			return "new java.lang.Boolean(" + valueRetrievalCode + ")";
		}
		if (int.class.equals(returnType)) {
			return "new java.lang.Integer(" + valueRetrievalCode + ")";
		}
		if (long.class.equals(returnType)) {
			return "new java.lang.Long(" + valueRetrievalCode + ")";
		}
		if (char.class.equals(returnType)) {
			return "new java.lang.Character(" + valueRetrievalCode + ")";
		}
		if (float.class.equals(returnType)) {
			return "new java.lang.Float(" + valueRetrievalCode + ")";
		}
		if (double.class.equals(returnType)) {
			return "new java.lang.Double(" + valueRetrievalCode + ")";
		}
		if (byte.class.equals(returnType)) {
			return "new java.lang.Byte(" + valueRetrievalCode + ")";
		}
		if (short.class.equals(returnType)) {
			return "new java.lang.Short(" + valueRetrievalCode + ")";
		}
		return valueRetrievalCode;
	}

	/**
	 * generate the <code>setProperty</code> method. If the setterName parameter
	 * is null, the method body will be empty.
	 * 
	 * @param setterName
	 *            the setter name used to access the property
	 * @param objectToAccessClassName
	 *            the type of the object holding the property
	 * @param valueType
	 *            the type of the property
	 * @return the java code of the <code>setProperty</code> method
	 *         implementation.
	 */
	private String getSetterSourceCode(String setterName,
			String objectToAccessClassName, Class<?> valueType) {
		String sourceCode = null;
		if (setterName == null) {
			sourceCode = "public void setProperty(java.lang.Object object, java.lang.Object value) {}";
		} else {
			sourceCode = "public void setProperty(java.lang.Object object, java.lang.Object value) {(("
					+ objectToAccessClassName
					+ ")object)."
					+ setterName
					+ "("
					+ castValueToSet(valueType) + ");}";
		}
		if (log.isDebugEnabled()) {
			log.debug("generated code for setter :");
			log.debug(sourceCode);
		}
		return sourceCode;
	}

	/**
	 * cast the value to the type expected by the setter.
	 * 
	 * @param valueType
	 *            the expected type.
	 * @return the code used to cast the value passed as argument of the setter.
	 */
	private String castValueToSet(Class<?> valueType) {
		if (boolean.class.equals(valueType)) {
			return "((java.lang.Boolean)value).booleanValue()";
		}
		if (int.class.equals(valueType)) {
			return "((java.lang.Integer)value).intValue()";
		}
		if (long.class.equals(valueType)) {
			return "((java.lang.Long)value).longValue()";
		}
		if (char.class.equals(valueType)) {
			return "((java.lang.Character)value).charValue()";
		}
		if (float.class.equals(valueType)) {
			return "((java.lang.Float)value).floatValue()";
		}
		if (double.class.equals(valueType)) {
			return "((java.lang.Double)value).doubleValue()";
		}
		if (byte.class.equals(valueType)) {
			return "((java.lang.Byte)value).byteValue()";
		}
		if (short.class.equals(valueType)) {
			return "((java.lang.Short)value).shortValue()";
		}
		return "(" + getJavaForm(valueType) + ")value";
	}

	/**
	 * generate the <code>getPropertyType</code> method implementation.
	 * 
	 * @param returnType
	 *            the return type.
	 * @return the java code of the implemented method.
	 */
	private String getGetTypeSourceCode(Class<?> returnType) {
		String sourceCode = "public Class getPropertyType() {return "
				+ getJavaForm(returnType) + ".class;}";
		if (log.isDebugEnabled()) {
			log.debug("generated code for property type method :");
			log.debug(sourceCode);
		}
		return sourceCode;
	}

	/**
	 * generate the type name in a form respecting the java syntax.
	 * 
	 * @param valueType
	 *            the value type
	 * @return the piece of code representing the <code>valueType</code> in a
	 *         form respecting the java syntax.
	 */
	private String getJavaForm(Class<?> valueType) {
		if (valueType.isArray()) {
			return getJavaForm(valueType.getComponentType()) + "[]";
		} else {
			return valueType.getName().replace('$', '.');
		}
	}

}
