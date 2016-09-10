// Se definen los includes
%{
package Objetos;

import java.awt.List;
import java.io.*;
import javax.swing.JTable;
import java.util.Vector;

%}

// Se definen los Tokens

%token PUNTO_COMA INTEGER DOUBLE ESTRUCTURA IDENTIFICADOR COMA ASIG MAS MENOS MULT DIV NUMEROENTERO NUMERODOUBLE STRUCT LLAVE_ABRE LLAVE_CIERRA  PARENTESIS_ABRE PARENTESIS_CIERRA MENOR MAYOR IGUAL MAYOR_IGUAL MENOR_IGUAL DISTINTO IF ELSE WHILE DO PRINT CADENA PUNTO FUNCTION INTTODOUBLE
%%

programa: declaraciones ejecutables {System.out.println("programa: estructuras declaraciones  ejecutables");}
	;

	   
declaraciones: /*declaracion vacias*/ {System.out.println("declaraciones: /*declaracion vacias*/");}
		 | declaraciones declaracion {System.out.println("declaraciones: declaraciones declaracion");}			 
    ;  
	   
declaracion: declaracionV
           | declaracionF
           ;

declaracionV: tipo lista_variables PUNTO_COMA {System.out.println("declaracion: tipo lista_variables PUNTO_COMA");}
           ;

declaracionF: tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametros PARENTESIS_CIERRA declaraciones LLAVE_ABRE bloqueF LLAVE_CIERRA PUNTO_COMA
            ;

parametros: /* vacio */
          | parametro
          | parametros COMA parametro
          ;

parametro: tipo IDENTIFICADOR
         ;

tipo: INTEGER {System.out.println("tipo: INTEGER");mostrarSentencia("Declaración de entero");}
	| DOUBLE {System.out.println("tipo: DOUBLE");mostrarSentencia("Declaración de double");}
    ;
	
lista_variables: IDENTIFICADOR {System.out.println("lista_variables: IDENTIFICADOR");}
               | lista_variables COMA IDENTIFICADOR {System.out.println("lista_variables: lista_variables COMA IDENTIFICADOR");}
	         ;

ejecutables: sentencia {System.out.println("ejecutables: sentencia");}
		   | ejecutables sentencia {System.out.println("ejecutables: ejecutables sentencia");}
               ;

sentencia: asignacion {System.out.println("sentencia: asignacion");mostrarSentencia("Sentencia de asignación");}
		 | seleccion {System.out.println("sentencia: seleccion");mostrarSentencia("Sentencia de selección");}
		 | iteracion {System.out.println("sentencia: iteracion");mostrarSentencia("Sentencia de iteración");}
             | func      {System.out.println("sentencia: iteracion");mostrarSentencia("Sentencia de función");}
             | conversion {System.out.println("sentencia: impresion");mostrarSentencia("Sentencia de conversión");}
		 | impresion {System.out.println("sentencia: impresion");mostrarSentencia("Sentencia de impresión");}
	;

asignacion: IDENTIFICADOR ASIG expresion PUNTO_COMA {System.out.println("asignacion: asign_izq ASIG expresion PUNTO_COMA");}
          ;

	
expresion:	expresion MAS termino {System.out.println("expresion: expresion MAS termin");mostrarSentencia("Suma de expresiones");}
         |	expresion MENOS termino {System.out.println("expresion: expresion MENOS termino");mostrarSentencia("Resta de expresiones");}
		 |	termino {System.out.println("expresion: termino");}
           ;

termino: termino MULT factor {System.out.println("termino: termino MULT factor");mostrarSentencia("Multiplicación de expresiones");}
	   | termino DIV factor {System.out.println("termino: termino DIV factor");mostrarSentencia("División de expresiones");}
	   | factor {System.out.println("termino: factor");}
         ;

factor: IDENTIFICADOR PUNTO IDENTIFICADOR {System.out.println("factor: IDENTIFICADOR PUNTO IDENTIFICADOR");}
      | IDENTIFICADOR {System.out.println("factor: IDENTIFICADOR");}
	  | NUMEROENTERO {System.out.println("factor: NUMEROENTERO");tratarEnteroPositivo($1.sval);}
	  | MENOS NUMEROENTERO {System.out.println("factor: MENOS NUMEROENTERO");mostrarSentencia("Entero negativo");tratarEnteroNegativo($2.sval);}
	  | NUMERODOUBLE {System.out.println("factor: NUMERODOUBLE");}
	  | MENOS NUMERODOUBLE {System.out.println("factor: MENOS NUMERODOUBLE");mostrarSentencia("Double negativo");tratarDoubleNegativo($2.sval);}
	;

seleccion: IF condicion bloque ELSE bloque {System.out.println("seleccion: IF condicion bloque ELSE bloque");}
;

condicion: PARENTESIS_ABRE expresion_logica PARENTESIS_CIERRA {System.out.println("condicion: PARENTESIS_ABRE expresion_logica PARENTESIS_CIERRA");mostrarSentencia("Condición lógica");}
;

expresion_logica: expresion operador_logico expresion {System.out.println("expresion_logica: expresion operador_logico expresion");}
	;

operador_logico: MENOR {System.out.println("operador_logico: MENOR");}
               | MAYOR {System.out.println("operador_logico: MAYOR");}
               | IGUAL {System.out.println("operador_logico: IGUAL");}
			   | MAYOR_IGUAL {System.out.println("operador_logico: MAYOR_IGUAL");}
               | MENOR_IGUAL {System.out.println("operador_logico: MENOR_IGUAL");}
	           | DISTINTO {System.out.println("operador_logico: DISTINTO");}
	;   
	
bloque:LLAVE_ABRE ejecutables LLAVE_CIERRA {System.out.println("bloque: BEGIN ejecutables END");}
;

bloqueF: ejecutables  {System.out.println("bloque: BEGIN ejecutables END");}
;


iteracion: WHILE condicion DO bloque {System.out.println("iteracion: WHILE condicion DO bloque");}
;


impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA PUNTO_COMA");}
;

		
func: IDENTIFICADOR PARENTESIS_ABRE lista_variables PARENTESIS_CIERRA;
    ;

conversion: IDENTIFICADOR ASIG INTTODOUBLE PARENTESIS_ABRE expresion PARENTESIS_CIERRA PUNTO_COMA {System.out.println("Conversion y asignacion");}
          ;
