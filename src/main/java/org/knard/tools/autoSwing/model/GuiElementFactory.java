package org.knard.tools.autoSwing.model;

import java.awt.Component;
import java.awt.Container;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * factory class used to instanciate new {@link GuiElement} from a swing
 * {@link Component}.
 * 
 * @author knard
 *
 */
public class GuiElementFactory {

	private final static Logger log = LoggerFactory
			.getLogger(GuiElementFactory.class);

	/**
	 * instanciate a new {@link GuiElement}
	 * 
	 * @param c
	 *            {@link Component} to be wrapped
	 * @return the new {@link GuiElement} used to wrap the swing
	 *         {@link Component}
	 */
	public static GuiElement get(final Component c) {
		if (log.isDebugEnabled()) {
			log.debug("create gui component for " + c);
		}
		GuiElement result = null;
		if (c instanceof JButton) {
			result = new GuiButtonElement((JButton) c);
		} else if (c instanceof JFrame) {
			result = new GuiJFrameElement((JFrame) c);
		} else if (c instanceof Window) {
			result = new GuiWindowElement((Window) c);
		} else if (c instanceof Container) {
			result = new DefaultGuiContainerElement<Container>((Container) c);
		} else {
			result = new DefaultGuiElement<Component>(c);
		}
		if (log.isTraceEnabled()) {
			log.trace("element created of type : "
					+ result.getClass().getName());
			log.trace("element created : " + result);
		}
		return result;
	}
}
