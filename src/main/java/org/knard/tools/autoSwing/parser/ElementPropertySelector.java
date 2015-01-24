package org.knard.tools.autoSwing.parser;

import org.knard.tools.autoSwing.model.GuiElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElementPropertySelector extends AbtractListSelector implements
		TreeSelector {

	private static final Logger log = LoggerFactory.getLogger(ElementPropertySelector.class);
	
	@Override
	public GuiElementList select(GuiElementList elements) {
		if( log.isTraceEnabled() ) {
			log.trace("apply " + this.getClass().getName() + " to " + elements);
		}
		GuiElementList result = elements;
		for (int i = getSize() - 1; i >= 0; i--) {
			TreeSelector selector = getSelector(i);
			if( log.isDebugEnabled() ) {
				log.debug("apply selector " + selector);
			}
			result = selector.select(result);
			if( log.isTraceEnabled()) {
				log.trace("intermediate result : " + result);
			}
		}
		if( log.isTraceEnabled()) {
			log.trace("final result : " + result);
		}
		return result;
	}

}
