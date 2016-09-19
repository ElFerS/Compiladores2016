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
%}

// Se definen los Tokens

%token PUNTO_COMA INTEGER DOUBLE IDENTIFICADOR COMA ASIG MAS MENOS MULT DIV NUMEROENTERO NUMERODOUBLE LLAVE_ABRE LLAVE_CIERRA  PARENTESIS_ABRE PARENTESIS_CIERRA MENOR MAYOR IGUAL MAYOR_IGUAL MENOR_IGUAL DISTINTO IF ELSE WHILE PRINT CADENA PUNTO FUNCTION INTTODOUBLE MENOS_MENOS RETURN INTTODOUBLE TO ALLOW ANOTACION
%%


programa: converimplicita declarativas bloqueeje {System.out.println("PROGRAMA");}
        ;

sentencias : sentencias sentencia  {System.out.println("sentencias : sentencias sentencia");} 
           | sentencia ;           {System.out.println("sentencias : sentencia");}

sentencia : declarativa            {System.out.println("sentencia : declarativa");}
          | ejecutable ;           {System.out.println("sentencia : ejecutable");} 

converimplicita : ALLOW INTEGER TO DOUBLE PUNTO_COMA { System.out.println("converimplicita : ALLOW INTEGER TO DOUBLE PUNTO_COMA");mostrarSentencia("Conversion implicita Habilitada");}
                | ALLOW INTEGER TO DOUBLE            { System.out.println("converimplicita : ALLOW INTEGER TO DOUBLE"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Conversion implicita falta ';'." ); }
                | ALLOW INTEGER TO error             { System.out.println("converimplicita : ALLOW INTEGER TO error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Conversion implicita esperada INTEGER TO DOUBLE." ); }
                | ALLOW INTEGER error                { System.out.println("converimplicita : ALLOW INTEGER error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Conversion implicita esperada INTEGER TO DOUBLE ." ); }
                | ALLOW error                        { System.out.println("converimplicita : ALLOW error"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Conversion implicita esperada INTEGER TO DOUBLE ." ); }
                | /* Sin conversion */               { System.out.println("converimplicita : sin conversion implicita");}
                ;

declarativas : declarativa {System.out.println("declarativas:  declarativa");}
		 | declarativas declarativa {System.out.println("declarativas:  declarativas declarativa");}			 
             ; 
	   
declarativa : tipo lista_variables PUNTO_COMA {System.out.println("declarativa: tipo lista_variables PUNTO_COMA");}
            | error lista_variables PUNTO_COMA {System.out.println("declarativa: error lista_variables PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Declaracion sin definicion de tipo ';'." ); }
            | tipo lista_variables error      {System.out.println("declarativa: tipo lista_variables PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Declaracion se esperaba ';'." ); }
            | tipo  error PUNTO_COMA          {System.out.println("declarativa: tipo  error PUNTO_COMA ");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Declaracion con errores ';'." ); }
            | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA declarativas LLAVE_ABRE ejecutables RETURN PARENTESIS_ABRE expresion PARENTESIS_CIERRA LLAVE_CIERRA PUNTO_COMA {System.out.println("declaracion: Function");}
            | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA declarativas LLAVE_ABRE ejecutables RETURN PARENTESIS_ABRE expresion PARENTESIS_CIERRA LLAVE_CIERRA  {System.out.println("declaracion: tipo lista_variables PUNTO_COMA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Declaracion function se esperaba ';'." ); }
            | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA declarativas LLAVE_ABRE ejecutables RETURN PARENTESIS_ABRE expresion PARENTESIS_CIERRA error  {System.out.println("declarativa: funcion sin }");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Declaracion function se esperaba '}'." ); }
            | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA declarativas LLAVE_ABRE ejecutables RETURN PARENTESIS_ABRE expresion  error  {System.out.println("declarativa: funcion sin )");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Declaracion function se esperaba ')'." ); }
            | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA declarativas LLAVE_ABRE ejecutables RETURN PARENTESIS_ABRE error  {System.out.println("declarativa: funcion expresion error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. expresion incorrecta." ); }
            | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA declarativas LLAVE_ABRE ejecutables RETURN  error  {System.out.println("declarativa: funcion (");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '('." ); }
            | tipo FUNCTION IDENTIFICADOR PARENTESIS_ABRE parametro PARENTESIS_CIERRA error        LLAVE_ABRE ejecutables RETURN PARENTESIS_ABRE expresion PARENTESIS_CIERRA LLAVE_CIERRA PUNTO_COMA   {System.out.println("declarativa: funcion  error declarativas");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Declaracion function en declarativas '('." ); }
            ;

parametro : /* vacio */ {System.out.println("parametro => vacio");}
          | tipo IDENTIFICADOR {System.out.println("parametro => tipo IDENTIFICADOR");}
          | FUNCTION IDENTIFICADOR PARENTESIS_ABRE PARENTESIS_CIERRA {System.out.println("parametro => FUNCTION IDENTIFICADOR");}
          | tipo  {System.out.println("parametro => tipo");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba un identificador." ); }
          | IDENTIFICADOR {System.out.println("parametro => IDENTIFICADOR");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Sin definicion del tipo en el parametro." ); }
          | FUNCTION IDENTIFICADOR PARENTESIS_ABRE error {System.out.println("parametro :FUNCTION IDENTIFICADOR PARENTESIS_ABRE error ");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ')'." ); }
          | FUNCTION IDENTIFICADOR  error {System.out.println("parametro :FUNCTION IDENTIFICADOR PARENTESIS_ABRE error ");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '('." ); }
          | FUNCTION error {System.out.println("parametro :FUNCTION  error ");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. El nombre de la funcion." ); }
          ;


tipo: INTEGER {System.out.println("tipo: INTEGER");mostrarSentencia("Declaración de entero");}
	| DOUBLE {System.out.println("tipo: DOUBLE");mostrarSentencia("Declaración de double");}
    ;
	
lista_variables: IDENTIFICADOR {System.out.println("lista_variables: IDENTIFICADOR");}
               | lista_variables COMA IDENTIFICADOR {System.out.println("lista_variables: lista_variables COMA IDENTIFICADOR");}
               | lista_variables IDENTIFICADOR 	{ System.out.println("lista_variables: lista_variables IDENTIFICADOR"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ','." ); }
	         ;

ejecutables: ejecutable {System.out.println("ejecutables: sentencia");}
	     | ejecutables ejecutable {System.out.println("ejecutables: ejecutables sentencia");}
	     | ejecutables declarativa {System.out.println("ejecutables: ejecutables declaracion");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". No se puede realizar declaraciones luego de sentencias ejecutables. No se permiten ámbitos.");}
           ;

ejecutable   : asignacion {System.out.println("sentencia: asignacion");}
		 | seleccion {System.out.println("sentencia: seleccion");}
		 | iteracion {System.out.println("sentencia: iteracion");}
		 | impresion {System.out.println("sentencia: impresion");mostrarSentencia("Sentencia de impresión");}
            ;


asignacion: IDENTIFICADOR ASIG expresion PUNTO_COMA anotacion {System.out.println("asignacion: asign_izq ASIG expresion PUNTO_COMA");mostrarSentenciados( (ParserVal) val_peek(3).obj,"Sentencia de asignación");}
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
          | ANOTACION {System.out.println("ANOTACION");mostrarSentenciados( (ParserVal) $1.obj,"Anotacion en Asignacion");} 
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



seleccion: parteif bloquesen PUNTO_COMA {System.out.println("seleccion: IF ");}
         | parteif bloquesen{System.out.println("2seleccion: IF ");}
         | parteif bloquesen parteelse bloquesen PUNTO_COMA{System.out.println("seleccion 1: IF condicion bloque ELSE bloque");}
         | parteif bloquesen PUNTO_COMA parteelse bloquesen PUNTO_COMA{System.out.println("seleccion2: IF condicion bloque ELSE bloque");}
         | parteif bloquesen PUNTO_COMA parteelse bloquesen {System.out.println("seleccion3: IF condicion bloque ELSE bloque");}
         | parteif bloquesen parteelse bloquesen {System.out.println("seleccion 1: IF condicion bloque ELSE bloque");}
         | parteif error  parteelse error {System.out.println("seleccion: error en ambos bloques ");}
         ;

parteif: IF condicion { System.out.println("parteif: IF ");mostrarSentenciados( (ParserVal) $1.obj,"Sentencia de selección");}
       | IF  error    { System.out.println("parteif: IF ");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ( o ) en la condicion.");mostrarSentencia("Sentencia de Seleccion");}
	 ;

parteelse: ELSE { System.out.println("parteElse: ELSE");}
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
	
bloqueeje: LLAVE_ABRE ejecutables LLAVE_CIERRA  {System.out.println("bloque: { ejecutables }");}
        | LLAVE_ABRE ejecutables error {System.out.println("bloque: { ejecutables error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '}' o ';' en bloque.");}	  
	  | LLAVE_ABRE error LLAVE_CIERRA {System.out.println("bloque: { error }");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en bloque.");}
	  | ejecutables LLAVE_CIERRA{System.out.println("bloque: ejecutables }");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Se esperaba { del bloque.");}
	  | ejecutables error{System.out.println("bloque: ejecutables error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Se esperaban delimitadores del bloque.");}
	;

bloquesen: LLAVE_ABRE sentencias LLAVE_CIERRA  {System.out.println("bloque: { ejecutables }");}
         ;
        
iteracion: WHILE condicion bloquesen PUNTO_COMA {System.out.println("iteracion: WHILE condicion  bloque");mostrarSentenciados( (ParserVal) $1.obj,"Sentencia de iteración");}
         | WHILE condicion error {System.out.println("iteracion: WHILE condicion  error");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en bloque .");}
         | WHILE error  bloquesen {System.out.println("iteracion: WHILE error  bloque"); mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en condición del WHILE.");}
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
    | INTTODOUBLE PARENTESIS_ABRE  PARENTESIS_CIERRA {System.out.println("func: INTTODOUBLE PARENTESIS_ABRE  PARENTESIS_CIERRA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Se esperaba algo a convertir.");}
    | INTTODOUBLE PARENTESIS_ABRE factor error {System.out.println("func: INTTODOUBLE PARENTESIS_ABRE  PARENTESIS_CIERRA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Se esperaba ')'.");}
    | INTTODOUBLE factor  PARENTESIS_CIERRA {System.out.println("func: INTTODOUBLE factor PARENTESIS_CIERRA");mostrarError("Línea " + this.archivo.obtenerLineaActual() + ". Se esperaba '('.");}
    ;


parametrosf: IDENTIFICADOR {System.out.println("factor: IDENTIFICADOR");}
	     | NUMEROENTERO {System.out.println("factor: NUMEROENTERO");tratarEnteroPositivo($1.sval);}  
	     | MENOS NUMEROENTERO {System.out.println("factor: MENOS NUMEROENTERO");mostrarSentencia("Entero negativo");tratarEnteroNegativo($2.sval);}  
	     | NUMERODOUBLE {System.out.println("factor: NUMERODOUBLE");}
	     | MENOS NUMERODOUBLE {System.out.println("factor: MENOS NUMERODOUBLE");mostrarSentencia("Double negativo");tratarDoubleNegativo($2.sval);}
           | IDENTIFICADOR PARENTESIS_ABRE  PARENTESIS_CIERRA
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


public Parser(LexicalDecoder l, Archivo a, Matriz m, JTable jTableTokens, List listErrores, JTable jTableTS, TablaSimbolos ts,JTable JTableSentenciasSinc,List listErroresSint){
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
    yylval.obj = new ParserVal(t); 
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
  }
    return 0;
}



public void mostrarSentenciados(ParserVal pv, String s){
       Token t =  (Token) pv.obj;
       ((javax.swing.table.DefaultTableModel)(this.jTableSentenciasSint.getModel())).addRow(new String[] {String.valueOf(t.getlinea()),s});
}

public void mostrarSentencia(String s){

    ((javax.swing.table.DefaultTableModel)(this.jTableSentenciasSint.getModel())).addRow(new String[] {String.valueOf(this.archivo.obtenerLineaActual()),s});
}


public void tratarDoubleNegativo(String d){
    this.ts.tratarDoubleNegativo(d);
}

public void tratarEnteroNegativo(String d){
    this.ts.tratarEnteroNegativo(Integer.valueOf("-"+d));
}

public void tratarEnteroPositivo(String d){
    this.ts.tratarEnteroPositivo(Integer.valueOf(d),this.listErroresSint,this.archivo.obtenerLineaActual(),this.jTableTS);
}