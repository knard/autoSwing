package org.knard.tools.autoSwing.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this list is design to store only {@link GuiElement}. it provides all
 * features from an {@link ArrayList} plus some extra method adapted to
 * {@link GuiElement}.
 * 
 * @author knard
 *
 */
public class GuiElementList extends ArrayList<GuiElement> {

	/**
	 * used to hold a reference to an object.
	 * 
	 * @author knard
	 *
	 * @param <T>
	 *            the type of the referenced object.
	 */
	private final static class Holder<T> {

		/**
		 * reference to the object.
		 */
		public T reference;

	}

	private final static Logger log = LoggerFactory
			.getLogger(GuiElementList.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 5185070805838732977L;

	/**
	 * default selector that always return true.
	 */
	private final static GuiElementSelector allSelector = new GuiElementSelector() {

		@Override
		public boolean select(GuiElement e) {
			return true;
		}
	};

	/**
	 * apply an action to all selected element in this list.
	 * 
	 * @param action
	 *            the action to be applied.
	 * @param selector
	 *            the selector used to select element to which the action should
	 *            be applied.
	 */
	public void foreach(GuiElementAction action, GuiElementSelector selector) {
		for (final GuiElement e : this) {
			if (selector.select(e)) {
				action.execute(e);
			}
		}
	}

	/**
	 * apply an action to all element in this list.
	 * 
	 * @param action
	 *            the action to be applied.
	 */
	public void foreach(GuiElementAction action) {
		foreach(action, allSelector);
	}

	/**
	 * assert that the list size is equal to an expected size
	 * 
	 * @param expectedSize
	 *            the expected size of the list.
	 */
	public void assertListSize(int expectedSize) {
		assertEquals("expect a different count of element", expectedSize,
				size());
	}

	/**
	 * 
	 * @return all children of all element.
	 */
	public GuiElementList getChildren() {
		return getChildren(GuiElementList.allSelector);
	}

	/**
	 * return all children of all element that pass the condition define by the
	 * selector
	 * 
	 * @param selector
	 *            {@link GuiElementSelector} used to filter child element.
	 * @return a list of child element filtered by the
	 *         {@link GuiElementSelector}
	 */
	public GuiElementList getChildren(final GuiElementSelector selector) {
		final GuiElementList list = new GuiElementList();
		for (final GuiElement element : this) {
			if (element instanceof GuiContainerElement) {
				final GuiContainerElement container = (GuiContainerElement) element;
				container.getChildrenElements().foreach(new GuiElementAction() {

					@Override
					public void execute(GuiElement e) {
						if (selector.select(e)) {
							list.add(e);
						}
					}
				});
			}
		}
		return list;
	}

	/**
	 * check that all element of the list have a specific property set to a
	 * specific value
	 * 
	 * @param propertyName
	 *            the property that should be checked
	 * @param value
	 *            the value that should have the property
	 * @return <code>true</code> if all element have the property set to the
	 *         specified value, otherwise return <code>false</code>
	 */
	public boolean allHasProperty(final String propertyName, final Object value) {
		final Holder<Boolean> allHasValueHolder = new Holder<Boolean>();
		allHasValueHolder.reference = true;
		foreach(new GuiElementAction() {

			@Override
			public void execute(final GuiElement e) {
				Object propertyValue = e.getProperty(propertyName);
				if ((value == null && propertyValue != null)
						|| (value != null && !value.equals(propertyValue))) {
					allHasValueHolder.reference = false;
				}
			}
		});
		return allHasValueHolder.reference;
	}

	/**
	 * check that all element of the list have a specific property not set to a
	 * specific value
	 * 
	 * @param propertyName
	 *            the property that should be checked
	 * @param value
	 *            the value that should not have the property
	 * @return <code>true</code> if all element have the property not set to the
	 *         specified value, otherwise return <code>false</code>
	 */
	public boolean noneHasProperty(final String propertyName, final Object value) {
		final Holder<Boolean> allHasValueHolder = new Holder<Boolean>();
		allHasValueHolder.reference = true;
		foreach(new GuiElementAction() {

			@Override
			public void execute(final GuiElement e) {
				Object propertyValue = e.getProperty(propertyName);
				if ((value == null && propertyValue == null)
						|| (value != null && value.equals(propertyValue))) {
					allHasValueHolder.reference = false;
				}
			}
		});
		return allHasValueHolder.reference;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		boolean first = true;
		for (GuiElement element : this) {
			if (first) {
				first = false;
				if (size() > 1) {
					builder.append("\n\t");
				}
			} else {
				builder.append(",\n\t");
			}
			builder.append(element.toString());
		}
		if (size() > 1) {
			builder.append("\n");
		}
		builder.append("]");
		return builder.toString();
	}
}
