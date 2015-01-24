package org.knard.tools.autoSwing.parser;

import java.util.regex.Pattern;

import org.knard.tools.autoSwing.model.GuiElement;
import org.knard.tools.autoSwing.model.GuiElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeTypeSelector implements TreeSelector {

	private final static Logger log = LoggerFactory.getLogger(NodeTypeSelector.class);
	
	private Pattern nodeTypePattern;

	public NodeTypeSelector(String format) {
		nodeTypePattern = Pattern.compile(format);
	}

	@Override
	public GuiElementList select(GuiElementList elements) {
		if( log.isTraceEnabled() ) {
			log.trace("apply " + this.getClass().getName() + " to " + elements);
		}
		GuiElementList result  = new GuiElementList();
		for (GuiElement guiElement : elements) {
			if( nodeTypePattern.matcher(guiElement.getType().getName()).matches()) {
				result.add(guiElement);
			}
		}
		return result;
	}

}
