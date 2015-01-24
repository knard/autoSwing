package org.knard.tools.autoSwing.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this helper provide a set of predefine action that user can do on a gui.
 * 
 * @author knard
 *
 */
public class UserInteractionHelper {

	private static final Logger log = LoggerFactory
			.getLogger(UserInteractionHelper.class);

	/**
	 * the java {@link Robot} used to interact with the gui.
	 */
	private static Robot r;

	static {
		try {
			UserInteractionHelper.r = new Robot();
		} catch (final AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * do a mouse click on the coordinate passed as parameter
	 * 
	 * @param x
	 *            the x coordinate where the mouse click should happen
	 * @param y
	 *            the y coordinate where the mouse click should happen
	 */
	public static void mouseClick(int x, int y) {
		if (log.isTraceEnabled()) {
			log.trace("click on position (" + x + "," + y + ")");
		}
		mouseMove(x, y);
		UserInteractionHelper.r.mousePress(InputEvent.BUTTON1_MASK);
		UserInteractionHelper.r.mouseRelease(InputEvent.BUTTON1_MASK);
		GuiHelper.guiActionWait();
	}

	/**
	 * move the mouse to the specify coordinate
	 * 
	 * @param x
	 *            the x coordinate where the mouse should be moved
	 * @param y
	 *            the y coordinate where the mouse should be moved
	 */
	public static void mouseMove(int x, int y) {
		UserInteractionHelper.r.mouseMove(x, y);
	}

}
