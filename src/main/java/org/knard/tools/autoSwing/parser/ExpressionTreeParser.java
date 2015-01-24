package org.knard.tools.autoSwing.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionTreeParser {

	private static final Logger log = LoggerFactory
			.getLogger(ExpressionTreeParser.class);

	public TreeSelector parse(String expressionString) {
		if (log.isDebugEnabled()) {
			log.debug("try to parse " + expressionString);
		}
		ExpressionTreeSelectorLexer lexer = new ExpressionTreeSelectorLexer(
				new ANTLRInputStream(expressionString));
		ExpressionTreeSelectorParserParser parser = new ExpressionTreeSelectorParserParser(
				new CommonTokenStream(lexer));
		TreeSelectorBuilder builder = new TreeSelectorBuilder();
		parser.addParseListener(builder);
		parser.expression();
		return builder.getSelector();
	}

}
