package org.knard.tools.autoSwing.model;

/**
 * call back that can be applied to a {@link GuiElement}
 * 
 * @author knard
 *
 */
public interface GuiElementAction {

	/**
	 * code that should be executed again a {@link GuiElement}
	 * 
	 * @param e
	 *            the element that will be used to execute the action.
	 */
	void execute(GuiElement e);

}
