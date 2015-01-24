package org.knard.tools.autoSwing.model;

/**
 * Interface representing a container. All container wrapper must implement this
 * interface.
 * 
 * @author knard
 *
 */
public interface GuiContainerElement extends GuiElement {

	/**
	 * @return a list of child gui elements.
	 */
	public GuiElementList getChildrenElements();

	/**
	 * 
	 * @return the count of children element.
	 */
	public int getChildrenElementCount();

	/**
	 * retrieve a child at a specific index.
	 * 
	 * @param index
	 *            index of the child
	 * @return the child at the specific index.
	 */
	public GuiElement getChildrenElement(int index);

}
