/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
grammar ExpressionTreeSelectorParser;

options {
	tokenVocab = ExpressionTreeSelectorLexer;
}

expression
:
	(
		NODE_SEPARATOR node_description?
	)*
;

node_description
:
	node_type?
	(
		NODE_INDEX_OPEN_BRACKET node_index NODE_INDEX_CLOSE_BRACKET
	)?
	(
		NODE_ATTRIBUTE_LIST_OPEN_BRACKET attribute
		(
			NODE_ATTRIBUTE_SEPARATOR attribute
		)* NODE_ATTRIBUTE_LIST_CLOSE_BRACKET
	)?
;

attribute
:
	attribute_name NODE_ATTRIBUTE_VALUE_ASSIGNATION NODE_ATTRIBUTE_VALUE_DELIMITER_START
	attribute_value NODE_ATTRIBUTE_VALUE_DELIMITER_END
;

attribute_value
:
	NODE_ATTRIBUTE_VALUE
;

attribute_name
:
	NODE_ATTRIBUTE_NAME
;

node_index
:
	NODE_INDEX
;

node_type
:
	NODE_TYPE_NAME
;

