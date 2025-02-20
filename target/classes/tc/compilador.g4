grammar compilador;

@header {
package tc.dec;
}

tipo : 'int' | 'float' | 'char' | 'void' ;
L_LLAVE : '{' ;
R_LLAVE : '}' ;
NUMERO : [0-9]+ ('.' [0-9]+)? ;
ID : [a-zA-Z_][a-zA-Z0-9_]* ;
WS : [ \t\r\n]+ -> skip ;
NUMBER: [0-9]+ ('.' [0-9]+)?;
STRING : '"' (~["\r\n])* '"' ;
COMENTARIO : '//' ~[\r\n]* -> skip ;
BLOCK_COMMENT: '/*' .*? '*/' -> skip; 
incrementoDecremento : ID ('++' | '--') ;

programa : (instruccion | funcionDecl)* EOF ;

instruccion : bloque
            | declaracion
            | asignacion
            | expr ';'
            | ifStmt
            | forStmt
            | whileStmt
            | returnStmt
            | funcionCall
            ;

bloque : L_LLAVE (instruccion*) R_LLAVE ;

declaracion : tipo ID ('=' expr)? ';' ;
asignacion  : ID '=' expr ';' ;

expr : expr op=('*' | '/' | '%') expr
     | expr op=('+' | '-') expr
     | expr op=('<' | '<=' | '>' | '>=') expr
     | expr op=('==' | '!=') expr
     | expr op=('&&' | '||') expr
     | '(' expr ')'
     | ID
     | NUMERO
     | STRING
     | funcionCall
     ;

ifStmt : 'if' '(' expr ')' bloque ( 'else' bloque )? ;

forStmt : 'for' '(' declaracion? expr? ';' incrementoDecremento? ')' bloque ;

whileStmt : 'while' '(' expr ')' bloque ;

returnStmt : 'return' expr? ';' ;

funcionDecl : tipo ID '(' parametros? ')' bloque ;
funcionCall : ID '(' argumentos? ')';

parametros : parametro (',' parametro)* ;
parametro  : tipo ID ;
argumentos : expr (',' expr)* ;

