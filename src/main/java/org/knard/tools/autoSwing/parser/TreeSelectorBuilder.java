package org.knard.tools.autoSwing.parser;

import org.knard.tools.autoSwing.parser.ExpressionTreeSelectorParserParser.AttributeContext;
import org.knard.tools.autoSwing.parser.ExpressionTreeSelectorParserParser.Attribute_nameContext;
import org.knard.tools.autoSwing.parser.ExpressionTreeSelectorParserParser.Attribute_valueContext;
import org.knard.tools.autoSwing.parser.ExpressionTreeSelectorParserParser.ExpressionContext;
import org.knard.tools.autoSwing.parser.ExpressionTreeSelectorParserParser.Node_descriptionContext;
import org.knard.tools.autoSwing.parser.ExpressionTreeSelectorParserParser.Node_indexContext;
import org.knard.tools.autoSwing.parser.ExpressionTreeSelectorParserParser.Node_typeContext;

public class TreeSelectorBuilder extends
		ExpressionTreeSelectorParserBaseListener {

	private static final String attributeValueWildCardRegexp4 = "(?<!\\\\)[\\*]";
	private static final String attributeValueWildCardRegexp1 = "\\\\\\*";
	private static final String attributeValueWildCardRegexp2 = "\\.";
	private static final String attributeValueWildCardRegexp3 = "\\$";

	private static final String attributeValueWildCardReplacement4 = ".*";
	private static final String attributeValueWildCardReplacement1 = "\\\\*";
	private static final String attributeValueWildCardReplacement2 = "\\\\.";
	private static final String attributeValueWildCardReplacement3 = "\\\\\\$";

	public static void main(String[] args) {
		TreeSelectorBuilder b = new TreeSelectorBuilder();
		System.out.println(b.format("test.app.Frame\\*Demo2$MyFr*ame"));
	}

	
	private HierarchySelector selector;
	private String attributeName;
	private String attributeValue;
	private ElementPropertySelector elementPropertySelector;

	@Override
	public void enterExpression(ExpressionContext ctx) {
		selector = new HierarchySelector();
	}

	@Override
	public void exitAttribute_name(Attribute_nameContext ctx) {
		attributeName = format(ctx.getText());
	}

	/**
	 * transform as valid regexp
	 * 
	 * @param text
	 * @return
	 */
	private String format(String text) {
		return text
				.replaceAll(attributeValueWildCardRegexp1,
						attributeValueWildCardReplacement1)
				.replaceAll(attributeValueWildCardRegexp2,
						attributeValueWildCardReplacement2)
				.replaceAll(attributeValueWildCardRegexp3,
						attributeValueWildCardReplacement3)
				.replaceAll(attributeValueWildCardRegexp4,
						attributeValueWildCardReplacement4);
	}
	

	@Override
	public void exitAttribute_value(Attribute_valueContext ctx) {
		attributeValue = format(ctx.getText());
	}

	@Override
	public void exitAttribute(AttributeContext ctx) {
		if (attributeName != null && attributeValue != null) {
			elementPropertySelector.add(new PropertyBasedSelector(
					attributeName, attributeValue));
		}
	}

	@Override
	public void enterAttribute(AttributeContext ctx) {
		attributeName = null;
		attributeValue = null;
	}

	@Override
	public void exitNode_index(Node_indexContext ctx) {
		elementPropertySelector.add(new IndexSelector(Integer.parseInt(ctx
				.getText())));
	}

	@Override
	public void exitNode_type(Node_typeContext ctx) {
		elementPropertySelector
				.add(new NodeTypeSelector(format(ctx.getText())));
	}

	@Override
	public void enterNode_description(Node_descriptionContext ctx) {
		elementPropertySelector = new ElementPropertySelector();
	}

	@Override
	public void exitNode_description(Node_descriptionContext ctx) {
		if (elementPropertySelector.hasSelector()) {
			selector.add(elementPropertySelector);
		} else if (!(selector.getLastSelector() instanceof HierarchyWildCardSelector)) {
			selector.add(new HierarchyWildCardSelector());
		}
		elementPropertySelector = null;
	}

	public TreeSelector getSelector() {
		return selector;
	}

}
