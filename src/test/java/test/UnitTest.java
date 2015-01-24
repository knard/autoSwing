package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knard.tools.autoSwing.core.GuiHelper;
import org.knard.tools.autoSwing.junit.ApplicationClass;
import org.knard.tools.autoSwing.junit.GuiTestRunner;
import org.knard.tools.autoSwing.model.GuiAbstractWindowElement;
import org.knard.tools.autoSwing.model.GuiElement;
import org.knard.tools.autoSwing.model.GuiElementAction;
import org.knard.tools.autoSwing.model.GuiElementList;

import test.app.FrameDemo2;

@RunWith(GuiTestRunner.class)
@ApplicationClass(FrameDemo2.class)
public class UnitTest {

	@Test
	public void test1() throws Exception {
		Thread.sleep(1000);
		GuiElementList frames = GuiHelper.find("/javax.swing.JFrame");
		frames.assertListSize(1);
		assertTrue("not all are visible.",
				frames.allHasProperty("visible", true));
		GuiElementList buttons = GuiHelper
				.find("//javax.swing.JButton{text='New window'}");
		buttons.foreach(new GuiElementAction() {

			@Override
			public void execute(GuiElement e) {
				System.out.println(e.getProperty("text"));
				e.setProperty("text", "New Windows Button");
				System.out.println(e.getProperty("text"));
				e.click();
				e.click();
				e.click();
				e.click();
				e.click();
				e.click();
			}
		});
		System.out.println("==========================================");
		frames = GuiHelper
				.find("/test.app.FrameDemo2$MyFrame{title='A window'}");
		System.out.println(buttons.size());
		frames.foreach(new GuiElementAction() {

			@Override
			public void execute(GuiElement e) {
				if (e instanceof GuiAbstractWindowElement) {
					GuiAbstractWindowElement<?> window = (GuiAbstractWindowElement<?>) e;
					window.toFront();
					GuiElementList buttons2 = GuiHelper
							.find("//javax.swing.JButton{text='Close*'}", e);
					buttons2.foreach(new GuiElementAction() {
						
						@Override
						public void execute(GuiElement e2) {
							e2.click();
						}
					});
				}
			}
		});
		Thread.sleep(5000);
	}
}
