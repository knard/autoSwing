package org.knard.tools.autoSwing.model;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.knard.tools.autoSwing.core.UserInteractionHelper;
import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessor;
import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessorMapBuilder;

/**
 *default implementation of a {@link GuiElement}. This class can be
 * instanciated to represent a {@link Component} that doesn't have a specific
 * representation. This class is also used a base for other component.
 * 
 * @author knard
 *
 * @param <ComponentType> the swing component that will be wrapped
 */
public class DefaultGuiElement<ComponentType extends Component> implements
		GuiElement {

	private final ComponentType component;
	private final Map<String, PropertyAccessor<ComponentType, ?>> propertyMap = new HashMap<String, PropertyAccessor<ComponentType, ?>>();

	public DefaultGuiElement(final ComponentType component) {
		this.component = component;
		PropertyAccessorMapBuilder
				.create(java.awt.Component.class)
				.addProperty("peer", java.awt.peer.ComponentPeer.class)
				.addProperty("dropTarget", java.awt.dnd.DropTarget.class)
				.addProperty("graphicsConfiguration",
						java.awt.GraphicsConfiguration.class)
				.addProperty("treeLock", java.lang.Object.class)
				.addProperty("toolkit", java.awt.Toolkit.class)
				.addProperty("valid", boolean.class)
				.addProperty("displayable", boolean.class)
				.addProperty("visible", boolean.class)
				.addProperty("mousePosition", java.awt.Point.class)
				.addProperty("showing", boolean.class)
				.addProperty("enabled", boolean.class)
				.addProperty("doubleBuffered", boolean.class)
				.addProperty("foreground", java.awt.Color.class)
				.addProperty("foregroundSet", boolean.class)
				.addProperty("background", java.awt.Color.class)
				.addProperty("backgroundSet", boolean.class)
				.addProperty("font", java.awt.Font.class)
				.addProperty("fontSet", boolean.class)
				.addProperty("locale", java.util.Locale.class)
				.addProperty("colorModel", java.awt.image.ColorModel.class)
				.addProperty("locationOnScreen", java.awt.Point.class)
				.addProperty("bounds", java.awt.Rectangle.class)
				.addProperty("x", int.class)
				.addProperty("y", int.class)
				.addProperty("width", int.class)
				.addProperty("height", int.class)
				.addProperty("lightweight", boolean.class)
				.addProperty("preferredSizeSet", boolean.class)
				.addProperty("preferredSize", java.awt.Dimension.class)
				.addProperty("minimumSizeSet", boolean.class)
				.addProperty("minimumSize", java.awt.Dimension.class)
				.addProperty("maximumSizeSet", boolean.class)
				.addProperty("maximumSize", java.awt.Dimension.class)
				.addProperty("alignmentX", float.class)
				.addProperty("alignmentY", float.class)
				.addProperty("baselineResizeBehavior",
						java.awt.Component.BaselineResizeBehavior.class)
				.addProperty("graphics", java.awt.Graphics.class)
				.addProperty("cursor", java.awt.Cursor.class)
				.addProperty("cursorSet", boolean.class)
				.addProperty("ignoreRepaint", boolean.class)
				.addProperty("componentListeners",
						java.awt.event.ComponentListener[].class)
				.addProperty("focusListeners",
						java.awt.event.FocusListener[].class)
				.addProperty("hierarchyListeners",
						java.awt.event.HierarchyListener[].class)
				.addProperty("hierarchyBoundsListeners",
						java.awt.event.HierarchyBoundsListener[].class)
				.addProperty("keyListeners", java.awt.event.KeyListener[].class)
				.addProperty("mouseListeners",
						java.awt.event.MouseListener[].class)
				.addProperty("mouseMotionListeners",
						java.awt.event.MouseMotionListener[].class)
				.addProperty("mouseWheelListeners",
						java.awt.event.MouseWheelListener[].class)
				.addProperty("inputMethodListeners",
						java.awt.event.InputMethodListener[].class)
				.addProperty("inputMethodRequests",
						java.awt.im.InputMethodRequests.class)
				.addProperty("inputContext", java.awt.im.InputContext.class)
				.addProperty("focusTraversable", boolean.class)
				.addProperty("focusable", boolean.class)
				.addProperty("focusTraversalKeysEnabled", boolean.class)
				.addProperty("focusCycleRootAncestor", java.awt.Container.class)
				.addProperty("focus", boolean.class)
				.addProperty("focusOwner", boolean.class)
				.addProperty("propertyChangeListeners",
						java.beans.PropertyChangeListener[].class)
				.addProperty("componentOrientation",
						java.awt.ComponentOrientation.class)
				.addProperty("accessibleContext",
						javax.accessibility.AccessibleContext.class)
				.addProperty("name", java.lang.String.class)
				.addProperty("parent", java.awt.Container.class)
				.addProperty("location", java.awt.Point.class)
				.addProperty("size", java.awt.Dimension.class)
				.addProperty("opaque", boolean.class)
				.addProperty("class", java.lang.Class.class)
				.set(getPropertyMap());

	}

	@Override
	public GuiContainerElement getParent() {
		return (GuiContainerElement) GuiElementFactory.get(this.component
				.getParent());
	}

	protected ComponentType getComponent() {
		return this.component;
	}

	@Override
	public Class<ComponentType> getType() {
		@SuppressWarnings("unchecked")
		Class<ComponentType> c = (Class<ComponentType>) this.component.getClass();
		return c;
	}

	@Override
	public void click() {
		final Rectangle r = this.component.getBounds();
		final Point p = this.component.getLocationOnScreen();
		final int x = p.x + (r.width / 2);
		final int y = p.y + (r.height / 2);
		UserInteractionHelper.mouseClick(x, y);
	}

	/**
	 * get for the {@link PropertyAccessor} map.
	 * @return the {@link PropertyAccessor} map.
	 */
	protected Map<String, PropertyAccessor<ComponentType, ?>> getPropertyMap() {
		return propertyMap;
	}

	@Override
	public Set<String> getPropertyNames() {
		return getPropertyMap().keySet();
	}

	@Override
	public Object getProperty(String propertyName) {
		PropertyAccessor<ComponentType, ?> accessor = getPropertyMap().get(
				propertyName);
		if (accessor == null) {
			return null;
		}
		return accessor.getPropertyValue(component);
	}

	@Override
	public boolean hasProperty(String propertyName) {
		PropertyAccessor<ComponentType, ?> accessor = getPropertyMap().get(
				propertyName);
		return accessor != null;
	}

	@Override
	public Class<?> getPropertyType(String propertyName) {
		PropertyAccessor<ComponentType, ?> accessor = getPropertyMap().get(
				propertyName);
		if (accessor == null) {
			return null;
		}
		return accessor.getPropertyType();
	}

	@Override
	public void setProperty(String propertyName, Object value) {
		@SuppressWarnings("unchecked")
		PropertyAccessor<ComponentType, Object> accessor = (PropertyAccessor<ComponentType, Object>) getPropertyMap()
				.get(propertyName);
		if (accessor != null) {
			accessor.setProperty(component, value);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((component == null) ? 0 : component.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		DefaultGuiElement other = (DefaultGuiElement) obj;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + ":" + component.toString();
	}
}
