grammar Expr;
prog: expr EOF;
expr: expr op=(TIMES | DIV) expr # timesAndDiv
	| expr op=(PLUS | MINUS) expr # plusMinus
	| '(' expr ')' # wrappedExp
	| func # function
	| atomic # number
	;
atomic: NUMERIC | VARIABLES;

func: 'ROUND_OFF(' expr ')' # off
    | 'ROUND_UP(' expr ')' # up
    | 'ROUND_DOWN(' expr ')' # down
    | 'MIN(' expr ',' expr ')' # MIN
    | 'MAX(' expr ',' expr ')' # MIN
    ;

// operator
PLUS        : '+';
MINUS       : '-';
TIMES       : '*';
DIV         : '/';
VARIABLES   : ADD_FEE_RATE
    | ADD_FEE_AMOUNT
    | DISCOUNT_SHARE_RATIO
    | CHANNEL_FEE_RATE
    | ORDER_AMOUNT
    | PAYMENT_AMOUNT
    ;

ADD_FEE_RATE: 'ADD_FEE_RATE' | '추가수수료_정률';
ADD_FEE_AMOUNT: 'ADD_FEE_AMOUNT' | '추가수수료_정액';
DISCOUNT_SHARE_RATIO: 'DISCOUNT_SHARE_RATIO' | '할인분담률';
CHANNEL_FEE_RATE: 'CHANNEL_FEE_RATE' | '채널수수료_정률';
CHANNEL_FEE_AMOUNT: 'CHANNEL_FEE_AMOUNT' | '채널수수료_정액';

ORDER_AMOUNT: 'ORDER_AMOUNT' | '주문금액';
PAYMENT_AMOUNT: 'PAYMENT_AMOUNT' | '결제금액';



// setting
NEWLINE     : [\r\n]+ -> skip;
NUMERIC     : [0-9]+(.[0-9]+)? ;
WS          : [ \t\r\n]+ -> skip ;