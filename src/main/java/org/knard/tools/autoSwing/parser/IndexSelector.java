package org.knard.tools.autoSwing.parser;

import org.knard.tools.autoSwing.model.GuiContainerElement;
import org.knard.tools.autoSwing.model.GuiElement;
import org.knard.tools.autoSwing.model.GuiElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexSelector implements TreeSelector {

	private static final Logger log = LoggerFactory.getLogger(IndexSelector.class);
	
	private int index;

	public IndexSelector(int index) {
		this.index = index;
	}

	@Override
	public GuiElementList select(GuiElementList elements) {
		if( log.isTraceEnabled() ) {
			log.trace("apply " + this.getClass().getName() + " to " + elements);
		}
		GuiElementList result = new GuiElementList();
		for (GuiElement guiElement : elements) {
			GuiContainerElement parent = guiElement.getParent();
			if( parent.getChildrenElementCount() > index && parent.getChildrenElement(index).equals(guiElement)) {
				result.add(guiElement);
			}
		}
		return result;
	}

}
