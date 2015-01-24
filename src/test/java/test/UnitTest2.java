package test;

import java.awt.Component;
import java.awt.Window;

import javax.swing.JFrame;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knard.tools.autoSwing.junit.ApplicationClass;
import org.knard.tools.autoSwing.junit.GuiTestRunner;
import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessorFactory;
import org.knard.tools.autoSwing.model.propertyAccessor.PropertyAccessor;

import test.app.FrameDemo2;

@RunWith(GuiTestRunner.class)
@ApplicationClass(FrameDemo2.class)
public class UnitTest2 {

	@Test
	public void test1() throws Exception {
		PropertyAccessorFactory factory = new PropertyAccessorFactory();
		PropertyAccessor<Component, Boolean> accessor =  factory.getAccessor(Component.class, "visible", boolean.class);
		Thread.sleep(1000);
		Window[] windows = Window.getWindows();
		JFrame frame = (JFrame) windows[0];
		accessor.setProperty(frame, false);
		Thread.sleep(5000);
		accessor.setProperty(frame, true);
		Thread.sleep(5000);
		accessor.setProperty(frame, false);
		Thread.sleep(5000);
		accessor.setProperty(frame, true);
		Thread.sleep(5000);
		accessor.setProperty(frame, false);
		Thread.sleep(5000);
		accessor.setProperty(frame, true);
		Thread.sleep(5000);
	}
}
