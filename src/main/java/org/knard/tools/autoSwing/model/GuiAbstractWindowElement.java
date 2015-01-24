package org.knard.tools.autoSwing.model;

import java.awt.Window;

import org.knard.tools.autoSwing.core.GuiHelper;
import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessorMapBuilder;

/**
 * abstract default implementation of windows component. All {@link Window}
 * element or all element extending {@link Window} should extends this
 * component.
 * 
 * @author knard
 *
 * @param <Type> the type of the window
 */
public abstract class GuiAbstractWindowElement<Type extends Window> extends
		DefaultGuiContainerElement<Type> {

	public GuiAbstractWindowElement(final Type w) {
		super(w);
		PropertyAccessorMapBuilder
				.create(java.awt.Window.class)
				.addProperty("iconImages", java.util.List.class)
				.addProperty("toolkit", java.awt.Toolkit.class)
				.addProperty("warningString", java.lang.String.class)
				.addProperty("locale", java.util.Locale.class)
				.addProperty("inputContext", java.awt.im.InputContext.class)
				.addProperty("ownedWindows", java.awt.Window[].class)
				.addProperty("windows", java.awt.Window[].class)
				.addProperty("ownerlessWindows", java.awt.Window[].class)
				.addProperty("modalExclusionType",
						java.awt.Dialog.ModalExclusionType.class)
				.addProperty("windowListeners",
						java.awt.event.WindowListener[].class)
				.addProperty("windowFocusListeners",
						java.awt.event.WindowFocusListener[].class)
				.addProperty("windowStateListeners",
						java.awt.event.WindowStateListener[].class)
				.addProperty("alwaysOnTopSupported", boolean.class)
				.addProperty("alwaysOnTop", boolean.class)
				.addProperty("focusOwner", java.awt.Component.class)
				.addProperty("mostRecentFocusOwner", java.awt.Component.class)
				.addProperty("active", boolean.class)
				.addProperty("focused", boolean.class)
				.addProperty("focusCycleRoot", boolean.class)
				.addProperty("focusCycleRootAncestor", java.awt.Container.class)
				.addProperty("focusableWindow", boolean.class)
				.addProperty("focusableWindowState", boolean.class)
				.addProperty("autoRequestFocus", boolean.class)
				.addProperty("validateRoot", boolean.class)
				.addProperty("showing", boolean.class)
				.addProperty("accessibleContext",
						javax.accessibility.AccessibleContext.class)
				.addProperty("bufferStrategy",
						java.awt.image.BufferStrategy.class)
				.addProperty("locationByPlatform", boolean.class)
				.addProperty("opacity", float.class)
				.addProperty("shape", java.awt.Shape.class)
				.addProperty("background", java.awt.Color.class)
				.addProperty("type", java.awt.Window.Type.class)
				.addProperty("owner", java.awt.Window.class)
				.addProperty("opaque", boolean.class).set(getPropertyMap());
	}

	public void toFront() {
		getComponent().toFront();
		GuiHelper.guiActionWait();
	}

}
