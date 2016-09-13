// Se definen los includes
%{
package Object;

import java.awt.List;
import java.io.*;
import javax.swing.JTable;
import java.util.Vector;
import java.util.Hashtable;

%}

// Se definen los Tokens

%token PUNTO_COMA INTEGER DOUBLE IDENTIFICADOR COMA ASIG MAS MENOS MULT DIV NUMEROENTERO NUMERODOUBLE LLAVE_ABRE LLAVE_CIERRA  PARENTESIS_ABRE PARENTESIS_CIERRA MENOR MAYOR IGUAL MAYOR_IGUAL MENOR_IGUAL DISTINTO IF ELSE WHILE PRINT CADENA PUNTO FUNCTION INTTODOUBLE MENOS_MENOS RETURN INTTODOUBLE TO ALLOW ANOTACION
%%


programa: converimplicita declaraciones bloque {System.out.println("programa: declaraciones bloque");}
        | declaraciones bloque {System.out.println("programa: declaraciones bloque");}
        ;

converimplicita : INTEGER TO DOUBLE ALLOW PUNTO_COMA {System.out.println("Conversion implicita INTEGER TO DOUBLE HABILITADA");mostrarSentencia("Conversion implicita Habilitada");}
                | INTEGER TO DOUBLE ALLOW { System.out.println("Conversion Implicita:"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ';'." ); }
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


tipo: INTEGER {System.out.println("tipo: INTEGER");mostrarSentencia("Declaraci�n de entero");}
	| DOUBLE {System.out.println("tipo: DOUBLE");mostrarSentencia("Declaraci�n de double");}
    ;
	
lista_variables: IDENTIFICADOR {System.out.println("lista_variables: IDENTIFICADOR");}
               | lista_variables COMA IDENTIFICADOR {System.out.println("lista_variables: lista_variables COMA IDENTIFICADOR");}
               | lista_variables IDENTIFICADOR 	{ System.out.println("lista_variables: lista_variables IDENTIFICADOR"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ','." ); }
	         ;

ejecutables: sentencia {System.out.println("ejecutables: sentencia");}
	     | ejecutables sentencia {System.out.println("ejecutables: ejecutables sentencia");}
	     | ejecutables declaracion {System.out.println("ejecutables: ejecutables declaracion");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". No se puede realizar declaraciones luego de sentencias ejecutables. No se permiten �mbitos.");}
           ;

sentencia: asignacion {System.out.println("sentencia: asignacion");mostrarSentencia("Sentencia de asignaci�n");}
		 | seleccion {System.out.println("sentencia: seleccion");mostrarSentencia("Sentencia de selecci�n");}
		 | iteracion {System.out.println("sentencia: iteracion");mostrarSentencia("Sentencia de iteraci�n");}
             | conversion {System.out.println("sentencia: impresion");mostrarSentencia("Sentencia de conversi�n");}
		 | impresion {System.out.println("sentencia: impresion");mostrarSentencia("Sentencia de impresi�n");}
	;

asignacion: IDENTIFICADOR ASIG expresion PUNTO_COMA anotacion {System.out.println("asignacion: asign_izq ASIG expresion PUNTO_COMA");}
          | ASIG expresion PUNTO_COMA { System.out.println("asignacion: ASIG expresion PUNTO_COMA"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte izquierda de la asignaci�n."); }
	    | ASIG expresion { System.out.println("asignacion: ASIG expresion"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte izquierda de la asignaci�n y el ; al final."); }
	    | ASIG PUNTO_COMA { System.out.println("asignacion: ASIG PUNTO_COMA"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte izquierda y la parte derecha de la asignaci�n."); }
	    | IDENTIFICADOR ASIG PUNTO_COMA { System.out.println("asignacion: asign_izq ASIG PUNTO_COMA"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba la parte derecha de la asignaci�n."); }
	    | IDENTIFICADOR error { System.out.println("asignacion: asign_izq error"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ':='."); }
	    | IDENTIFICADOR ASIG expresion error { System.out.println("asignacion: asign_izq ASIG expresion error"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ';'."); }
	    | IDENTIFICADOR ASIG error PUNTO_COMA{ System.out.println("asignacion: asign_izq ASIG error"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Asignaci�n incorrecta."); }
	    | IDENTIFICADOR ASIG error { System.out.println("asignacion: asign_izq ASIG error"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Asignaci�n incorrecta."); }
	    ;

anotacion : /* vacio */
          | ANOTACION {System.out.println("ANOTACION");mostrarSentencia("Anotacion");} 
          ;

	
expresion:	expresion MAS termino {System.out.println("expresion: expresion MAS termin");mostrarSentencia("Suma de expresiones");}
         |	expresion MENOS termino {System.out.println("expresion: expresion MENOS termino");mostrarSentencia("Resta de expresiones");}
	   |	termino {System.out.println("expresion: termino");}
         |	expresion MAS error {System.out.println("expresion: expresion MAS error");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba expresi�n luego de la suma.");mostrarSentencia("Suma de expresiones");}
	   |	expresion MENOS error {System.out.println("expresion: expresion MENOS error");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba expresi�n luego de la resta.");mostrarSentencia("Resta de expresiones");}
         ;

termino:   termino MULT factor {System.out.println("termino: termino MULT factor");mostrarSentencia("Multiplicaci�n de expresiones");}
	   | termino DIV factor  {System.out.println("termino: termino DIV factor");mostrarSentencia("Divisi�n de expresiones");}
	   | factor              {System.out.println("termino: factor");}
         | termino MULT error  { System.out.println("termino: termino MULT error"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba termino a derecha de la multiplicaci�n.");mostrarSentencia("Multiplicaci�n de expresiones");}
	   | termino DIV error   { System.out.println("termino: termino DIV error"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba termino a derecha de la divisi�n.");mostrarSentencia("Divisi�n de expresiones");}
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
       | IF  error    { System.out.println("seleccion: IF ");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ( o ) en la condicion.");mostrarSentencia("Sentencia de Seleccion");}
	 ;

parteelse: ELSE { System.out.println("seleccion: ELSE");}
         ;

condicion: PARENTESIS_ABRE expresion_logica PARENTESIS_CIERRA {System.out.println("condicion: PARENTESIS_ABRE expresion_logica PARENTESIS_CIERRA");mostrarSentencia("Condici�n l�gica");}
         | PARENTESIS_ABRE expresion_logica {System.out.println("condicion: PARENTESIS_ABRE expresion_logica");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ')' en condici�n.");mostrarSentencia("Condici�n l�gica");}
         | expresion_logica PARENTESIS_CIERRA {System.out.println("condicion: expresion_logica PARENTESIS_CIERRA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' en condici�n.");mostrarSentencia("Condici�n l�gica");}
         | expresion_logica {System.out.println("condicion: expresion_logica");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' y ')' en condici�n.");mostrarSentencia("Condici�n l�gica");}
         | PARENTESIS_ABRE error PARENTESIS_CIERRA {System.out.println("condicion: PARENTESIS_ABRE error PARENTESIS_CIERRA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en condici�n.");mostrarSentencia("Condici�n l�gica");}
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
        | LLAVE_ABRE ejecutables error {System.out.println("bloque: { ejecutables error");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '}' en bloque.");}	  
	  | LLAVE_ABRE error LLAVE_CIERRA {System.out.println("bloque: { error }");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en bloque.");}
	  | ejecutables LLAVE_CIERRA{System.out.println("bloque: ejecutables }");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Se esperaba { del bloque.");}
	  | ejecutables error{System.out.println("bloque: ejecutables error");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Se esperaban delimitadores del bloque.");}
	;

bloqueF: /* vacio */  {System.out.println("bloque: { ejecutables } de funcion");}
       | ejecutables  {System.out.println("bloque: { ejecutables } de funcion");}
;


iteracion: WHILE condicion bloque PUNTO_COMA {System.out.println("iteracion: WHILE condicion  bloque");}
         | WHILE condicion error {System.out.println("iteracion: WHILE condicion  error");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en bloque .");}
         | WHILE error  bloque {System.out.println("iteracion: WHILE error  bloque"); mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en condici�n del WHILE.");}
	  ;


impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA PUNTO_COMA");}
	   | PRINT PARENTESIS_ABRE PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE PARENTESIS_CIERRA PUNTO_COMA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba cadena en impresi�n.");}
	   | PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA PARENTESIS_CIERRA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ';' al final de la impresi�n.");}
	   | PRINT PARENTESIS_ABRE CADENA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA PUNTO_COMA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ')' en impresi�n.");}
	   | PRINT CADENA PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT CADENA PARENTESIS_CIERRA PUNTO_COMA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' en impresi�n.");}
	   | PRINT error CADENA PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT error CADENA PARENTESIS_CIERRA PUNTO_COMA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba '(' en impresi�n.");}
         | PRINT PARENTESIS_ABRE error PARENTESIS_CIERRA PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE error PARENTESIS_CIERRA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba cadena v�lida en impresi�n.");}
	   | PRINT PARENTESIS_ABRE CADENA error PUNTO_COMA {System.out.println("impresion: PRINT PARENTESIS_ABRE CADENA error PUNTO_COMA");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico. Se esperaba ')' en impresi�n.");}
	   | PRINT PARENTESIS_ABRE error{System.out.println("impresion: PRINT PARENTESIS_ABRE error");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en impresi�n.");}
	   | PRINT PARENTESIS_ABRE IDENTIFICADOR error {System.out.println("impresion: PRINT error");mostrarError("L�nea " + this.archivo.obtenerLineaActual() + ". Error Sintactico en impresi�n.");}
;

		
func: IDENTIFICADOR PARENTESIS_ABRE parametrosf PARENTESIS_CIERRA {System.out.println("func => IDENTIFICADOR PARENTESIS_ABRE parametrosf PARENTESIS_CIERRA ");mostrarSentencia("Sentencia de funci�n");}
    | IDENTIFICADOR PARENTESIS_ABRE PARENTESIS_CIERRA {System.out.println("func => IDENTIFICADOR PARENTESIS_ABRE PARENTESIS_CIERRA  ");mostrarSentencia("Sentencia de funci�n");}
    | INTTODOUBLE PARENTESIS_ABRE factor PARENTESIS_CIERRA  {System.out.println("func => Conversion explicita INTODUBLE");mostrarSentencia("Conversion explicita");}
    ;


parametrosf : factor  {System.out.println("Parametrof => factor ");}
            | parametrosf COMA factor {System.out.println("Parametrof => parametrosf COMA factor ");}
            ;


conversion:  INTTODOUBLE PARENTESIS_ABRE expresion PARENTESIS_CIERRA PUNTO_COMA {System.out.println("Conversion ");}
          ;

%%

private Lexico lexico;
private Archivo archivo;
private Matriz matriz;
private JTable jTableTokens;
private List listErrores;
private JTable jTableTS;
private TablaSimbolos ts;
private JTable jTableSentenciasSint;
private List listErroresSint;
private String mensaje = "";
private Vector<String> estructuras = new Vector();

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
    this.mensaje = "";
    this.estructuras = new Vector();
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
    if (t.obtenerNombre().equals("Asignaci�n"))
        return this.ASIG;
    if (t.obtenerNombre().equals("Anotaci�n"))
        return this.ANOTACION;
    if (t.obtenerNombre().equals("Cadena"))
        return this.CADENA;
    if (t.obtenerNombre().equals("Coma"))
        return this.COMA;
    if (t.obtenerNombre().equals("Mayor"))
        return this.MAYOR;
    if (t.obtenerNombre().equals("Menor"))
        return this.MENOR;
    if (t.obtenerNombre().equals("MayorIgual"))
        return this.MAYOR_IGUAL;
    if (t.obtenerNombre().equals("MenorIgual"))
        return this.MENOR_IGUAL;
    if (t.obtenerNombre().equals("Igual"))
        return this.IGUAL;
    if (t.obtenerNombre().equals("Distinto"))
        return this.DISTINTO;
    if (t.obtenerNombre().equals("Divisi�n"))
        return this.DIV;
    if (t.obtenerNombre().equals("Double"))
        return this.NUMERODOUBLE;
    if (t.obtenerNombre().equals("Entero"))
        return this.NUMEROENTERO;
    if (t.obtenerNombre().equals("Identificador")){
            return this.IDENTIFICADOR;
    }
    if (t.obtenerNombre().equals("LlaveA"))
        return this.LLAVE_ABRE;
    if (t.obtenerNombre().equals("LlaveC"))
        return this.LLAVE_CIERRA;
    if (t.obtenerNombre().equals("Producto"))
        return this.MULT;
    if (t.obtenerNombre().equals("ParentesisA"))
        return this.PARENTESIS_ABRE;
    if (t.obtenerNombre().equals("ParentesisC"))
        return this.PARENTESIS_CIERRA;
    if (t.obtenerNombre().equals("PuntoComa"))
        return this.PUNTO_COMA;
    if (t.obtenerNombre().equals("Punto"))
        return this.PUNTO;
    if (t.obtenerNombre().equals("Resta"))
        return this.MENOS;
    if (t.obtenerNombre().equals("MenosMenos"))
        return this.MENOS_MENOS;    
    if (t.obtenerNombre().equals("Suma"))
        return this.MAS;
    if (t.obtenerNombre().equals("P. Reservada")){
        if (t.obtenerLexema().equalsIgnoreCase("IF"))
            return this.IF;
        if (t.obtenerLexema().equalsIgnoreCase("ELSE"))
            return this.ELSE;
        if (t.obtenerLexema().equalsIgnoreCase("FUNCTION"))
            return this.FUNCTION;
        if (t.obtenerLexema().equalsIgnoreCase("WHILE"))
            return this.WHILE;
        if (t.obtenerLexema().equalsIgnoreCase("INTTODOUBLE"))
            return this.INTTODOUBLE;
        if (t.obtenerLexema().equalsIgnoreCase("PRINT"))
            return this.PRINT;
        if (t.obtenerLexema().equalsIgnoreCase("INTEGER"))
            return this.INTEGER;
        if (t.obtenerLexema().equalsIgnoreCase("DOUBLE"))
            return this.DOUBLE;
        if (t.obtenerLexema().equalsIgnoreCase("RETURN"))
            return this.RETURN;  
       if (t.obtenerLexema().equalsIgnoreCase("TO"))
            return this.TO; 
       if (t.obtenerLexema().equalsIgnoreCase("ALLOW"))
            return this.ALLOW; 
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