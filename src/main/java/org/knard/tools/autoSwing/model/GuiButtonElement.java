package org.knard.tools.autoSwing.model;

import javax.swing.JButton;

import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessorMapBuilder;

/**
 * Gui element representing a {@link JButton}.
 * 
 * @author knard
 *
 */
public class GuiButtonElement extends DefaultGuiElement<JButton> implements
		GuiElement {

	public GuiButtonElement(final JButton c) {
		super(c);
		PropertyAccessorMapBuilder
				.create(javax.swing.JButton.class)
				.addProperty("uIClassID", java.lang.String.class)
				.addProperty("defaultButton", boolean.class)
				.addProperty("defaultCapable", boolean.class)
				.addProperty("accessibleContext",
						javax.accessibility.AccessibleContext.class)
				.set(getPropertyMap());
	}

}
