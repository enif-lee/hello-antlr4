grammar Expr;
prog: expr EOF;
expr:
	expr (TIMES | DIV) expr
	| expr (PLUS | MINUS) expr
	| INT
	| '(' expr ')'
	;


PLUS: '+';
MINUS: '-';
TIMES: '*';
DIV: '/';



NEWLINE: [\r\n]+ -> skip;
INT: [0-9]+ ;
WS      : [ \t\r\n]+ -> skip ;