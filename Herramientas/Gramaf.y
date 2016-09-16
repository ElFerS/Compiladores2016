// Se definen los includes
%{
package org.unicen.compiladores.sintactico;

import java.awt.List;
import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.Matriz;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.LexicalDecoder;
import org.unicen.compiladores.lexico.Token;

import java.util.Vector;
%}

// Se definen los Tokens

%token PUNTO_COMA INTEGER DOUBLE IDENTIFICADOR COMA ASIG MAS MENOS MULT DIV NUMEROENTERO NUMERODOUBLE LLAVE_ABRE LLAVE_CIERRA  PARENTESIS_ABRE PARENTESIS_CIERRA MENOR MAYOR IGUAL MAYOR_IGUAL MENOR_IGUAL DISTINTO IF ELSE WHILE PRINT CADENA PUNTO FUNCTION INTTODOUBLE MENOS_MENOS RETURN INTTODOUBLE TO ALLOW ANOTACION
%%


programa: converimplicita declaraciones bloque {System.out.println("programa: declaraciones bloque");}
        | declaraciones bloque {System.out.println("programa: declaraciones bloque");}
        ;

converimplicita : INTEGER TO DOUBLE ALLOW PUNTO_COMA {System.out.println("Conversion implicita INTEGER TO DOUBLE HABILITADA");mostrarSentencia("Conversion implicita Habilitada");}
                | INTEGER TO DOUBLE ALLOW { System.out.println("Conversion Implicita:"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ';'." ); }
                ;

declaraciones: declaracion {System.out.println("declaraciones:  declaracion");}
		 | declaraciones declaracion {System.out.println("declaraciones: declaraciones declaracion");}			 
             ; 
	   
declaracion: tipo lista_variables PUNTO_COMA {System.out.println("declaracion: tipo lista_variables PUNTO_COMA");}
           | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA declaraciones LLAVE_ABRE bloqueF RETURN PARENTESIS_ABRE expresion PARENTESIS_CIERRA LLAVE_CIERRA PUNTO_COMA {System.out.println("declaracion: tipo lista_variables PUNTO_COMA");}


parametro : /* vacio */ {System.out.println("parametro => vacio");}
          | tipo IDENTIFICADOR {System.out.println("parametro => tipo IDENTIFICADOR");}
          | FUNCTION IDENTIFICADOR {System.out.println("parametro => FUNCTION IDENTIFICADOR");}
          ;


tipo: INTEGER {System.out.println("tipo: INTEGER");mostrarSentencia("Declaración de entero");}
	| DOUBLE {System.out.println("tipo: DOUBLE");mostrarSentencia("Declaración de double");}
    ;
	
lista_variables: IDENTIFICADOR {System.out.println("lista_variables: IDENTIFICADOR");}
               | lista_variables COMA IDENTIFICADOR {System.out.println("lista_variables: lista_variables COMA IDENTIFICADOR");}
               | lista_variables IDENTIFICADOR 	{ System.out.println("lista_variables: lista_variables IDENTIFICADOR"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ','." ); }
	         ;

ejecutables: sentencia {System.out.println("ejecutables: sentencia");}
	     | ejecutables sentencia {System.out.println("ejecutables: ejecutables sentencia");}
	     | ejecutables declaracion {System.out.println("ejecutables: ejecutables declaracion");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". No se puede realizar declaraciones luego de sentencias ejecutables. No se permiten ámbitos.");}
           ;

sentencia: asignacion {System.out.println("sentencia: asignacion");mostrarSentencia("Sentencia de asignación");}
		 | seleccion {System.out.println("sentencia: seleccion");mostrarSentencia("Sentencia de selección");}
		 | iteracion {System.out.println("sentencia: iteracion");mostrarSentencia("Sentencia de iteración");}
             | conversion {System.out.println("sentencia: impresion");mostrarSentencia("Sentencia de conversión");}
		 | impresion {System.out.println("sentencia: impresion");mostrarSentencia("Sentencia de impresión");}
	;

asignacion: IDENTIFICADOR ASIG expresion PUNTO_COMA anotacion {System.out.println("asignacion: asign_izq ASIG expresion PUNTO_COMA");}
          | ASIG expresion PUNTO_COMA { System.out.println("asignacion: ASIG expresion PUNTO_COMA"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte izquierda de la asignación."); }
	    | ASIG expresion { System.out.println("asignacion: ASIG expresion"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte izquierda de la asignación y el ; al final."); }
	    | ASIG PUNTO_COMA { System.out.println("asignacion: ASIG PUNTO_COMA"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte izquierda y la parte derecha de la asignación."); }
	    | IDENTIFICADOR ASIG PUNTO_COMA { System.out.println("asignacion: asign_izq ASIG PUNTO_COMA"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte derecha de la asignación."); }
	    | IDENTIFICADOR error { System.out.println("asignacion: asign_izq error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ':='."); }
	    | IDENTIFICADOR ASIG expresion error { System.out.println("asignacion: asign_izq ASIG expresion error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ';'."); }
	    | IDENTIFICADOR ASIG error PUNTO_COMA{ System.out.println("asignacion: asign_izq ASIG error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Asignación incorrecta."); }
	    | IDENTIFICADOR ASIG error { System.out.println("asignacion: asign_izq ASIG error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Asignación incorrecta."); }
	    ;

anotacion : /* vacio */
          | ANOTACION {System.out.println("ANOTACION");mostrarSentencia("Anotacion");} 
          ;

	
expresion:	expresion MAS termino {System.out.println("expresion: expresion MAS termin");mostrarSentencia("Suma de expresiones");}
         |	expresion MENOS termino {System.out.println("expresion: expresion MENOS termino");mostrarSentencia("Resta de expresiones");}
	   |	termino {System.out.println("expresion: termino");}
         |	expresion MAS error {System.out.println("expresion: expresion MAS error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba expresión luego de la suma.");mostrarSentencia("Suma de expresiones");}
	   |	expresion MENOS error {System.out.println("expresion: expresion MENOS error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba expresión luego de la resta.");mostrarSentencia("Resta de expresiones");}
         ;

termino:   termino MULT factor {System.out.println("termino: termino MULT factor");mostrarSentencia("Multiplicación de expresiones");}
	   | termino DIV factor  {System.out.println("termino: termino DIV factor");mostrarSentencia("División de expresiones");}
	   | factor              {System.out.println("termino: factor");}
         | termino MULT error  { System.out.println("termino: termino MULT error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba termino a derecha de la multiplicación.");mostrarSentencia("Multiplicación de expresiones");}
	   | termino DIV error   { System.out.println("termino: termino DIV error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba termino a derecha de la división.");mostrarSentencia("División de expresiones");}
	;

factor:  IDENTIFICADOR {System.out.println("factor: IDENTIFICADOR");}
        | IDENTIFICADOR MENOS_MENOS {System.out.println("factor: IDENTIFICADOR MENOS_MENOS");}
	  | NUMEROENTERO {System.out.println("factor: NUMEROENTERO");tratarEnteroPositivo($1.sval);}
	  | MENOS NUMEROENTERO {System.out.println("factor: MENOS NUMEROENTERO");mostrarSentencia("Entero negativo");tratarEnteroNegativo($2.sval);}
	  | NUMERODOUBLE {System.out.println("factor: NUMERODOUBLE");}
	  | MENOS NUMERODOUBLE {System.out.println("factor: MENOS NUMERODOUBLE");mostrarSentencia("Double negativo");tratarDoubleNegativo($2.sval);}
        | func         {System.out.println("funcion");}
;



seleccion: parteif bloque PUNTO_COMA {System.out.println("seleccion: IF ");}
         | parteif bloque parteelse bloque PUNTO_COMA{System.out.println("seleccion: IF condicion bloque ELSE bloque");}
         ;

parteif: IF condicion { System.out.println("seleccion: IF ");}
       | IF  error    { System.out.println("seleccion: IF ");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ( o ) en la condicion.");mostrarSentencia("Sentencia de Seleccion");}
	 ;

parteelse: ELSE { System.out.println("seleccion: ELSE");}
         ;

condicion: PARENTESIS_ABRE expresion_logica PARENTESIS_CIERRA {System.out.println("condicion: PARENTESIS_ABRE expresion_logica PARENTESIS_CIERRA");mostrarSentencia("Condición lógica");}
         | PARENTESIS_ABRE expresion_logica {System.out.println("condicion: PARENTESIS_ABRE expresion_logica");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ')' en condición.");mostrarSentencia("Condición lógica");}
         | expresion_logica PARENTESIS_CIERRA {System.out.println("condicion: expresion_logica PARENTESIS_CIERRA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' en condición.");mostrarSentencia("Condición lógica");}
         | expresion_logica {System.out.println("condicion: expresion_logica");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' y ')' en condición.");mostrarSentencia("Condición lógica");}
         | PARENTESIS_ABRE error PARENTESIS_CIERRA {System.out.println("condicion: PARENTESIS_ABRE error PARENTESIS_CIERRA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en condición.");mostrarSentencia("Condición lógica");}
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
	
bloque:   LLAVE_ABRE ejecutables LLAVE_CIERRA  {System.out.println("bloque: { ejecutables }");}
        | LLAVE_ABRE ejecutables error {System.out.println("bloque: { ejecutables error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '}' en bloque.");}	  
	  | LLAVE_ABRE error LLAVE_CIERRA {System.out.println("bloque: { error }");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en bloque.");}
	  | ejecutables LLAVE_CIERRA{System.out.println("bloque: ejecutables }");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Se esperaba { del bloque.");}
	  | ejecutables error{System.out.println("bloque: ejecutables error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Se esperaban delimitadores del bloque.");}
	;

bloqueF: /* vacio */  {System.out.println("bloque: { ejecutables } de funcion");}
       | ejecutables  {System.out.println("bloque: { ejecutables } de funcion");}
;


iteracion: WHILE condicion bloque PUNTO_COMA {System.out.println("iteracion: WHILE condicion  bloque");}
         | WHILE condicion error {System.out.println("iteracion: WHILE condicion  error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en bloque .");}
         | WHILE error  bloque {System.out.println("iteracion: WHILE error  bloque"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en condición del WHILE.");}
	  ;


impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA PUNTO_COMA");}
	   | PRINT PARENTESIS_ABRE PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE PARENTESIS_CIERRA PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba cadena en impresión.");}
	   | PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ';' al final de la impresión.");}
	   | PRINT PARENTESIS_ABRE CADENA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ')' en impresión.");}
	   | PRINT CADENA PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT CADENA PARENTESIS_CIERRA PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' en impresión.");}
	   | PRINT error CADENA PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT error CADENA PARENTESIS_CIERRA PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' en impresión.");}
         | PRINT PARENTESIS_ABRE error PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE error PARENTESIS_CIERRA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba cadena válida en impresión.");}
	   | PRINT PARENTESIS_ABRE CADENA error PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA error PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ')' en impresión.");}
	   | PRINT PARENTESIS_ABRE error{System.out.println("impresion: PRINT PARENTESIS_ABRE error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en impresión.");}
	   | PRINT PARENTESIS_ABRE IDENTIFICADOR error {System.out.println("impresion: PRINT error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en impresión.");}
;

		
func: IDENTIFICADOR PARENTESIS_ABRE parametrosf PARENTESIS_CIERRA {System.out.println("func => IDENTIFICADOR PARENTESIS_ABRE parametrosf PARENTESIS_CIERRA ");mostrarSentencia("Sentencia de función");}
    | IDENTIFICADOR PARENTESIS_ABRE PARENTESIS_CIERRA {System.out.println("func => IDENTIFICADOR PARENTESIS_ABRE PARENTESIS_CIERRA  ");mostrarSentencia("Sentencia de función");}
    | INTTODOUBLE PARENTESIS_ABRE factor PARENTESIS_CIERRA  {System.out.println("func => Conversion explicita INTODUBLE");mostrarSentencia("Conversion explicita");}
    ;


parametrosf : factor  {System.out.println("Parametrof => factor ");}
            | parametrosf COMA factor {System.out.println("Parametrof => parametrosf COMA factor ");}
            ;


conversion:  INTTODOUBLE PARENTESIS_ABRE expresion PARENTESIS_CIERRA PUNTO_COMA {System.out.println("Conversion ");}
          ;

%%

private LexicalDecoder lexico;
private Archivo archivo;
private Matriz matriz;
private JTable jTableTokens;
private List listErrores;
private JTable jTableTS;
private TablaSimbolos ts;
private JTable jTableSentenciasSint;
private List listErroresSint;

public Parser(Lexico l, Archivo a, Matriz m, JTable jTableTokens, List listErrores, JTable jTableTS, TablaSimbolos ts,JTable JTableSentenciasSinc,List listErroresSint){
    this.lexico = l;
    this.archivo = a;
    this.matriz = m;
    this.jTableTokens = jTableTokens;
    this.listErrores = listErrores;
    this.jTableTS = jTableTS;
    this.ts = ts;
    this.ts.limpiar();
    this.jTableSentenciasSint = JTableSentenciasSinc;
    this.listErroresSint = listErroresSint;
}

private void mostrarError(String e) {
    this.listErroresSint.add(e);
}

public void yyerror(String e){
}

private int yylex(){
    Token t = this.lexico.obtenerToken(this.archivo, this.matriz, this.jTableTokens, this.listErrores, this.jTableTS, this.ts);
    while ((t.obtenerNombre().equals("")))
        t = this.lexico.obtenerToken(this.archivo, this.matriz, this.jTableTokens, this.listErrores, this.jTableTS, this.ts);
    yylval = new ParserVal(t.obtenerLexema());
    return decodificarToken(t);
}

public int decodificarToken(Token t){
    switch (t.obtenerNombre()){
	
		case "Asignación": return Parser.ASIG;
		case "Anotación": return Parser.ANOTACION;
		case "Cadena": return Parser.CADENA;
		case "Coma": return Parser.COMA;
		case "Mayor": return Parser.MAYOR;
		case "Menor": return Parser.MENOR;
		case "MayorIgual": return Parser.MAYOR_IGUAL;
		case "MenorIgual": return Parser.MENOR_IGUAL;
		case "Igual": return Parser.IGUAL;
		case "Distinto": return Parser.DISTINTO;
		case "División": return Parser.DIV;
		case "Double": return Parser.NUMERODOUBLE;
		case "Entero": return Parser.NUMEROENTERO;
		case "Identificador": return Parser.IDENTIFICADOR;
		case "LlaveA": return Parser.LLAVE_ABRE;
		case "LlaveC": return Parser.LLAVE_CIERRA;
		case "Producto": return Parser.MULT;
		case "ParentesisA": return Parser.PARENTESIS_ABRE;
		case "ParentesisC": return Parser.PARENTESIS_CIERRA;
		case "PuntoComa": return Parser.PUNTO_COMA;
		case "Punto": return Parser.PUNTO;
		case "Resta": return Parser.MENOS;
		case "MenosMenos": return Parser.MENOS_MENOS;
		case "Suma": return Parser.MAS;
		case "P. Reservada": 
			switch(t.obtenerLexema()){
			case "IF": return Parser.IF;
			case "ELSE": return Parser.ELSE;
			case "FUNCTION": return Parser.FUNCTION;
			case "WHILE": return Parser.WHILE;
			case "INTTODOUBLE": return Parser.INTTODOUBLE;
			case "PRINT": return Parser.PRINT;
			case "INTEGER": return Parser.INTEGER;
			case "DOUBLE": return Parser.DOUBLE;
			case "RETURN": return Parser.RETURN;  
			case "TO": return Parser.TO; 
			case "ALLOW": return Parser.ALLOW; 
  }
    return 0;
}


public void mostrarSentencia(String s){
    ((javax.swing.table.DefaultTableModel)(this.jTableSentenciasSint.getModel())).addRow(new String[] {String.valueOf(this.archivo.obtenerLineaActual()),s});
}

public void tratarDoubleNegativo(String d){
    this.ts.tratarDoubleNegativo(d);
}

public void tratarEnteroNegativo(String d){
    this.ts.tratarEnteroNegativo(Integer.valueOf("-"+d.substring(0,d.length()-1)));
}

public void tratarEnteroPositivo(String d){
    this.ts.tratarEnteroPositivo(Integer.valueOf(d.substring(0,d.length()-1)),this.listErroresSint,this.archivo.obtenerLineaActual(),this.jTableTS);
}