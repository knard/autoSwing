/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
lexer grammar ExpressionTreeSelectorLexer;

 NODE_SEPARATOR
 :
 	'/'
 ;

 NODE_TYPE_NAME
 :
 	[a-zA-Z] [a-zA-Z0-9\._$]* [a-zA-Z0-9]
 ;

 NODE_INDEX_OPEN_BRACKET
 :
 	'[' -> pushMode ( index )
 ;

 NODE_ATTRIBUTE_LIST_OPEN_BRACKET
 :
 	'{' -> pushMode ( attribute )
 ;

 mode index;

 NODE_INDEX_CLOSE_BRACKET
 :
 	']' -> popMode
 ;

 NODE_INDEX
 :
 	[0-9]+
 ;

 mode attribute;

 NODE_ATTRIBUTE_LIST_CLOSE_BRACKET
 :
 	'}' -> popMode
 ;

 NODE_ATTRIBUTE_SEPARATOR
 :
 	','
 ;

 NODE_ATTRIBUTE_VALUE_ASSIGNATION
 :
 	'='
 ;

 NODE_ATTRIBUTE_NAME
 :
 	[a-zA-Z0-9_]+
 ;

 NODE_ATTRIBUTE_VALUE_DELIMITER_START
 :
 	'\'' -> pushMode ( attributeValue )
 ;

 mode attributeValue;

 NODE_ATTRIBUTE_VALUE
 :
 	(
 		~'\''
 		| '\\\''
 	)*
 ;

 NODE_ATTRIBUTE_VALUE_DELIMITER_END
 :
 	'\'' -> popMode
 ;
