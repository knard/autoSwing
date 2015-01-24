package org.knard.tools.autoSwing.model;

/**
 * used to provide condition while fitering {@link GuiElement}
 * 
 * @author knard
 *
 */
public interface GuiElementSelector {

	/**
	 * 
	 * @param e
	 *            the {@link GuiElement} that should be analyzed.
	 * @return <code>true</code> if the selected correspond to the criteria
	 *         otherwise return <code>false</code>
	 */
	boolean select(GuiElement e);

}
