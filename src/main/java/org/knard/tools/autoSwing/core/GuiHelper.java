package org.knard.tools.autoSwing.core;

import org.knard.tools.autoSwing.model.GuiElement;
import org.knard.tools.autoSwing.model.GuiElementList;
import org.knard.tools.autoSwing.parser.ExpressionTreeParser;
import org.knard.tools.autoSwing.parser.TreeSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper used to access information from the gui. pattern used to find gui
 * element has to follow rule defined by the {@link ExpressionTreeParser} class.
 * 
 * @see ExpressionTreeParser
 * 
 * @author knard
 *
 */
public class GuiHelper {

	private final static Logger log = LoggerFactory.getLogger(GuiHelper.class);

	/**
	 * find a elements respecting the pattern passed as parameter
	 * 
	 * @param pattern
	 *            the pattern used to select gui component
	 * @return a list of element respecting the pattern passed as parameter.
	 */
	public static final GuiElementList find(final String pattern) {
		if (log.isTraceEnabled()) {
			log.trace("search for element with following pattern : " + pattern);
		}
		ExpressionTreeParser parser = new ExpressionTreeParser();
		TreeSelector selector = parser.parse(pattern);
		return selector.select(null);
	}

	/**
	 * find a elements respecting the pattern passed as parameter.
	 * 
	 * @param pattern
	 *            the pattern used to select gui component
	 * @param root
	 *            the root of the search
	 * @return a list of element respecting the pattern passed as parameter.
	 */
	public static final GuiElementList find(final String pattern,
			GuiElement root) {
		if (log.isTraceEnabled()) {
			log.trace("search for element with following pattern : " + pattern);
		}
		ExpressionTreeParser parser = new ExpressionTreeParser();
		TreeSelector selector = parser.parse(pattern);
		GuiElementList list = new GuiElementList();
		list.add(root);
		return selector.select(list);
	}

	/**
	 * find a elements respecting the pattern passed as parameter.
	 * 
	 * @param pattern
	 *            the pattern used to select gui component
	 * @param roots
	 *            the roots of the search
	 * @return a list of element respecting the pattern passed as parameter.
	 */
	public static final GuiElementList find(final String pattern,
			GuiElementList roots) {
		if (log.isTraceEnabled()) {
			log.trace("search for element with following pattern : " + pattern);
		}
		ExpressionTreeParser parser = new ExpressionTreeParser();
		TreeSelector selector = parser.parse(pattern);
		return selector.select(roots);
	}

	/**
	 * this method is used to wait a predefine amount of time after an action as
	 * been done on the gui.
	 */
	public static void guiActionWait() {
		try {
			Thread.sleep(Constants.DEFAULT_GUI_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
