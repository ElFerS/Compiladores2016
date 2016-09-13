package org.unicen.compiladores.sintactico;

import java.awt.List;
import java.util.Enumeration;

import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.Matriz;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class LexicalDecoder implements Enumeration{//TODO implementar enumeration para que vaya pidiendo a un token y corra
	
	private static final int LETRA = 0;
    private static final int NUEMRO = 1;
    private static final int GUION_BAJO = 2;
    private static final int I_MINUSCULA = 3;
    private static final int D_MINUSCULA = 4;
    private static final int D_MAYUSCULA = 5;
    private static final int SUMA = 6;
    private static final int MENOS = 7;
    private static final int PUNTO = 8;
    private static final int DOS_PUNTOS = 9;
    private static final int IGUAL = 10;
    private static final int PARENTESIS_ABRE = 11;
    private static final int PARENTESIS_CIERRA = 12;
    private static final int LLAVE_ABRE = 13;
    private static final int LLAVE_CIERRA = 14;
    private static final int PUNTO_Y_COMA = 15;
    private static final int COMA = 16;
    private static final int MENOR = 17;
    private static final int MAYOR = 18;
    private static final int CAT = 19;
    private static final int MULTIPLICACION = 20;
    private static final int DIVISION = 21;
    private static final int COMILLA_SIMPLE = 22;
    private static final int LETRA_N = 23;
    private static final int LETRA_C = 24;
    private static final int ARROBA = 25;
    private static final int NUMERAL = 26;
    private static final int ENTER = 27;
    private static final int EOF = 28;
    private static final int TAB = 29;
    private static final int SPACE = 30;    
    
    public Token obtenerToken(Archivo a,Matriz m,JTable jTableTokens,List listErrores, JTable jTableTS, TablaSimbolos ts){
        Token t = new Token();
        String caracter = "";
        int estado = 0;
        int entrada;
        while (estado >= 0){
            caracter = a.obtenerCaracter();
            entrada = this.obtenerColumna(caracter);
            m.obtenerCelda(estado,entrada).ObtenerAccion().ejecutar(caracter, t, a, jTableTokens, listErrores, jTableTS, ts);
            estado = m.obtenerCelda(estado,entrada).ObtenerEstadoSiguiente();
        }  
        //TODO cual es el ultimo token ????
        return t;
    }

    private int obtenerColumna(String caracter) {
    	
    	switch (caracter){

    	case  "_":
    		return LexicalDecoder.GUION_BAJO;

    	case "i":
    		return LexicalDecoder.I_MINUSCULA;

    	case "d":
    		return LexicalDecoder.D_MINUSCULA;

    	case "D":
    		return LexicalDecoder.D_MAYUSCULA;

    	case "+":
    		return LexicalDecoder.SUMA;

    	case "*":
    		return LexicalDecoder.MULTIPLICACION;

    	case "-":
    		return LexicalDecoder.MENOS;

    	case ".":
    		return LexicalDecoder.PUNTO;

    	case ":":
    		return LexicalDecoder.DOS_PUNTOS;

    	case "=":
    		return LexicalDecoder.IGUAL;

    	case "(":
    		return LexicalDecoder.PARENTESIS_ABRE;

    	case ")":
    		return LexicalDecoder.PARENTESIS_CIERRA;

    	case "{":
    		return LexicalDecoder.LLAVE_ABRE;

    	case "}":
    		return LexicalDecoder.LLAVE_CIERRA;

    	case ";":
    		return LexicalDecoder.PUNTO_Y_COMA;

    	case ",":
    		return LexicalDecoder.COMA;

    	case "<":
    		return LexicalDecoder.MENOR;

    	case ">":
    		return LexicalDecoder.MAYOR;

    	case "!":
    		return LexicalDecoder.CAT;

    	case "/":
    		return LexicalDecoder.DIVISION;

    	case "\'":
    		return LexicalDecoder.COMILLA_SIMPLE;

    	case "N":
    		return LexicalDecoder.LETRA_N;

    	case "C":
    		return LexicalDecoder.LETRA_C;

    	case "@":
    		return LexicalDecoder.ARROBA;

    	case "#":
    		return LexicalDecoder.NUMERAL;

    	case  "\n": 
    		return LexicalDecoder.ENTER;

    	case "\t":
    		return LexicalDecoder.TAB;

    	case "\b":
    		return LexicalDecoder.SPACE;

    	default: 
    		if (esEOF(caracter)) 
    			return LexicalDecoder.EOF;
    		else if (esDigito(caracter))
    			return LexicalDecoder.NUEMRO;
    		else return LexicalDecoder.LETRA;
    	}

    }
    private boolean esEOF(String c){
    	return (c==null);
    }
    private boolean esDigito(String c){
    	return (   c.compareToIgnoreCase("0")>=0 
    			&& c.compareToIgnoreCase("9")<=0
    			);
    }

	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object nextElement() {
		// TODO Auto-generated method stub
		return null;
	}
}