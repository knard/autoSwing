package org.knard.tools.autoSwing.parser;

import java.util.regex.Pattern;

import org.knard.tools.autoSwing.core.conversion.ConvertorHelper;
import org.knard.tools.autoSwing.model.GuiElement;
import org.knard.tools.autoSwing.model.GuiElementList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyBasedSelector implements TreeSelector {

	private final static Logger log = LoggerFactory
			.getLogger(PropertyBasedSelector.class);

	private Pattern attributeNamePattern;
	private Pattern attributeValuePattern;

	public PropertyBasedSelector(String attributeNameRegEx,
			String attributeValue) {
		if (log.isDebugEnabled()) {
			log.debug("PropertyBasedSelector (" + attributeNameRegEx + ", "
					+ attributeValue + ")");
		}
		attributeNamePattern = Pattern.compile(attributeNameRegEx);
		attributeValuePattern = Pattern.compile(attributeValue);
	}

	@Override
	public GuiElementList select(GuiElementList elements) {
		if (log.isTraceEnabled()) {
			log.trace("apply " + this.getClass().getName() + " to " + elements);
		}
		GuiElementList selectedComponent = new GuiElementList();
		for (GuiElement element : elements) {
			for (String propertyName : element.getPropertyNames()) {
				if (log.isTraceEnabled()) {
					log.trace("try to match property " + propertyName
							+ " with pattern " + attributeNamePattern.pattern());
				}
				if (attributeNamePattern.matcher(propertyName).matches()) {
					Class<?> valueType = element.getPropertyType(propertyName);
					Object value = element.getProperty(propertyName);
					if (log.isTraceEnabled()) {
						log.trace("try to match value " + value + " of type "
								+ valueType.getName() + " with pattern "
								+ attributeValuePattern.pattern());
					}
					if (value != null) {
						if (CharSequence.class.isAssignableFrom(valueType)) {
							if (attributeValuePattern.matcher(
									(CharSequence) value).matches()) {
								if (log.isTraceEnabled()) {
									log.trace("matched");
								}
								selectedComponent.add(element);
							}
						} else {
							Object expectedValue = ConvertorHelper.fromString(
									attributeValuePattern.pattern(), valueType);
							if (expectedValue.equals(value)) {
								if (log.isTraceEnabled()) {
									log.trace("matched");
								}
								selectedComponent.add(element);
							}
						}
					}
				}
			}
		}
		return selectedComponent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropertyBasedSelector [attributeNamePattern="
				+ attributeNamePattern + ", attributeValuePattern="
				+ attributeValuePattern + "]";
	}

}
