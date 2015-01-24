package org.knard.tools.autoSwing.parser;

import org.knard.tools.autoSwing.model.GuiContainerElement;
import org.knard.tools.autoSwing.model.GuiElement;
import org.knard.tools.autoSwing.model.GuiElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HierarchyWildCardSelector implements TreeSelector {

	private static final Logger log = LoggerFactory.getLogger(HierarchyWildCardSelector.class);
	
	@Override
	public GuiElementList select(GuiElementList elements) {
		if( log.isTraceEnabled() ) {
			log.trace("apply " + this.getClass().getName() + " to " + elements);
		}
		return doSelect(elements);
	}

	private GuiElementList doSelect(GuiElementList elements) {
		GuiElementList result = new GuiElementList();
		if( log.isTraceEnabled())  {
			log.trace("adding to result : " + elements);
		}
		result.addAll(elements);
		for (GuiElement guiElement : elements) {
			if (guiElement instanceof GuiContainerElement) {
				GuiContainerElement container = (GuiContainerElement) guiElement;
				GuiElementList childrens = select(container.getChildrenElements());
				if( log.isTraceEnabled())  {
					log.trace("adding children to result : " + elements);
				}
				result.addAll(childrens);
			}
		}
		return result;
	}

}
