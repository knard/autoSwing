package org.knard.tools.autoSwing.model;

import java.awt.Component;
import java.util.Set;

/**
 * Interface representing a gui component. All component wrapper must implement
 * this interface.
 * 
 * @author knard
 *
 */
public interface GuiElement {

	/**
	 * 
	 * @return a set of property names that this element has
	 */
	public Set<String> getPropertyNames();

	/**
	 * retrieve a property value
	 * 
	 * @param propertyName
	 *            name of the desired property value wanted.
	 * @return the value of the property. if the property doesn't exist if
	 *         method return <code>null</code>
	 */
	public Object getProperty(String propertyName);

	/**
	 * set the property if the property can be set. If the property is read only
	 * the method do nothing.
	 * 
	 * @param propertyName
	 *            the property name to be set.
	 * @param value
	 *            the value to be set.
	 */
	public void setProperty(String propertyName, Object value);

	/**
	 * retrieve the property type
	 * 
	 * @param propertyName
	 *            the name of the property for which the type has to be
	 *            retrieved
	 * @return the class representing the property type or <code>null</code> if
	 *         the property doesn't exist.
	 */
	public Class<?> getPropertyType(String propertyName);

	/**
	 * check if the element has a specific property
	 * 
	 * @param propertyName
	 *            the name of the property to be checked.
	 * @return <code>true</code> if the element has this property otherwise
	 *         return <code>false</code>
	 */
	public boolean hasProperty(String propertyName);

	/**
	 * retrieve the parent element.
	 * 
	 * @return the parent container or <code>null</code> if the element is the
	 *         root element.
	 */
	public GuiContainerElement getParent();

	/**
	 * @return the type of the swing component wrapped by this element.
	 */
	public Class<? extends Component> getType();

	/**
	 * generate a mouse click on this component.
	 */
	public void click();
}
