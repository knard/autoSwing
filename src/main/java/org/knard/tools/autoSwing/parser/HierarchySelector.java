package org.knard.tools.autoSwing.parser;

import java.awt.Window;

import org.knard.tools.autoSwing.model.GuiContainerElement;
import org.knard.tools.autoSwing.model.GuiElement;
import org.knard.tools.autoSwing.model.GuiElementFactory;
import org.knard.tools.autoSwing.model.GuiElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HierarchySelector extends AbtractListSelector implements
		TreeSelector {

	private static final Logger log = LoggerFactory.getLogger(HierarchySelector.class);
	
	@Override
	public GuiElementList select(GuiElementList elements) {
		if( log.isTraceEnabled() ) {
			log.trace("apply " + this.getClass().getName() + " to " + elements);
		}
		if( elements == null ) {
			if( log.isTraceEnabled() ) {
				log.trace("no element passed as parameter, using root windows.");
			}
			elements = new GuiElementList();
			Window[] windows = Window.getWindows();
			for (Window window : windows) {
				elements.add(GuiElementFactory.get(window));
			}
		}
		GuiElementList result = elements;
		for(int i = 0 ; i < getSize() ; i++ ) {
			if( log.isTraceEnabled()) {
				log.trace("actual set of element " + result);
			}
			TreeSelector selector = getSelector(i);
			result = selector.select(result);
			if( i < getSize() -1  ) {
				GuiElementList oldResult = result;
				result = new GuiElementList();
				if( log.isTraceEnabled()) {
					log.trace("retrieve children of filtered elements");
				}
				for (GuiElement guiElement : oldResult) {
					if (guiElement instanceof GuiContainerElement) {
						GuiContainerElement container = (GuiContainerElement) guiElement;
						result.addAll(container.getChildrenElements());
					}
				}
			}
		}
		return result;
	}

}
