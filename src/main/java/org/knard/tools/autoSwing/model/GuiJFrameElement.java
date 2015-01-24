package org.knard.tools.autoSwing.model;

import javax.swing.JFrame;

import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessorMapBuilder;

/**
 * gui element representing a {@link JFrame}.
 * 
 * @author knard
 *
 */
public class GuiJFrameElement extends GuiAbstractWindowElement<JFrame> {
	public GuiJFrameElement(final JFrame f) {
		super(f);
		PropertyAccessorMapBuilder
				.create(javax.swing.JFrame.class)
				.addProperty("defaultCloseOperation", int.class)
				.addProperty("transferHandler",
						javax.swing.TransferHandler.class)
				.addProperty("jMenuBar", javax.swing.JMenuBar.class)
				.addProperty("rootPane", javax.swing.JRootPane.class)
				.addProperty("contentPane", java.awt.Container.class)
				.addProperty("layeredPane", javax.swing.JLayeredPane.class)
				.addProperty("glassPane", java.awt.Component.class)
				.addProperty("graphics", java.awt.Graphics.class)
				.addProperty("defaultLookAndFeelDecorated", boolean.class)
				.addProperty("accessibleContext",
						javax.accessibility.AccessibleContext.class)
				.set(getPropertyMap());
	}

}
