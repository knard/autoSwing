package org.knard.tools.autoSwing.model;

import java.awt.Component;
import java.awt.Container;

import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessorMapBuilder;

/**
 * default implementation of a {@link GuiContainerElement}. This class can be
 * instanciated to represent a container that doesn't have a specific
 * representation. This class is also used a base for other container.
 * 
 * @author knard
 *
 * @param <ContainerType> the swing container that will be wrapped
 */
public class DefaultGuiContainerElement<ContainerType extends Container>
		extends DefaultGuiElement<ContainerType> implements GuiContainerElement {

	public DefaultGuiContainerElement(final ContainerType container) {
		super(container);
		PropertyAccessorMapBuilder
				.create(java.awt.Container.class)
				.addProperty("componentCount", int.class)
				.addProperty("components", java.awt.Component[].class)
				.addProperty("insets", java.awt.Insets.class)
				.addProperty("layout", java.awt.LayoutManager.class)
				.addProperty("validateRoot", boolean.class)
				.addProperty("preferredSize", java.awt.Dimension.class)
				.addProperty("minimumSize", java.awt.Dimension.class)
				.addProperty("maximumSize", java.awt.Dimension.class)
				.addProperty("alignmentX", float.class)
				.addProperty("alignmentY", float.class)
				.addProperty("containerListeners",
						java.awt.event.ContainerListener[].class)
				.addProperty("focusCycleRoot", boolean.class)
				.addProperty("focusTraversalPolicy",
						java.awt.FocusTraversalPolicy.class)
				.addProperty("focusTraversalPolicySet", boolean.class)
				.addProperty("focusTraversalPolicyProvider", boolean.class)
				.set(getPropertyMap());

	}

	@Override
	public GuiElementList getChildrenElements() {
		final GuiElementList result = new GuiElementList();
		final Component[] components = getComponent().getComponents();
		for (final Component component : components) {
			result.add(GuiElementFactory.get(component));
		}
		return result;
	}

	@Override
	public int getChildrenElementCount() {
		return getComponent().getComponentCount();
	}

	@Override
	public GuiElement getChildrenElement(final int index) {
		return GuiElementFactory.get(getComponent().getComponent(index));
	}

}
