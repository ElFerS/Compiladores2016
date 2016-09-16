package org.unicen.compiladores.estructuras;

import java.io.IOException;

import org.unicen.compiladores.acciones.AccionAcumular;
import org.unicen.compiladores.acciones.AccionIgnorar;
import org.unicen.compiladores.acciones.AccionInicioAnotacion;
import org.unicen.compiladores.acciones.AccionInicioComentario;
import org.unicen.compiladores.acciones.AccionRetonarParentesisAbre;
import org.unicen.compiladores.acciones.AccionRetornarAnotacion;
import org.unicen.compiladores.acciones.AccionRetornarAsignacion;
import org.unicen.compiladores.acciones.AccionRetornarCadena;
import org.unicen.compiladores.acciones.AccionRetornarComa;
import org.unicen.compiladores.acciones.AccionRetornarDistinto;
import org.unicen.compiladores.acciones.AccionRetornarDivision;
import org.unicen.compiladores.acciones.AccionRetornarDouble;
import org.unicen.compiladores.acciones.AccionRetornarEOF;
import org.unicen.compiladores.acciones.AccionRetornarEntero;
import org.unicen.compiladores.acciones.AccionRetornarIdentificador;
import org.unicen.compiladores.acciones.AccionRetornarIgual;
import org.unicen.compiladores.acciones.AccionRetornarLlaveAbre;
import org.unicen.compiladores.acciones.AccionRetornarLlaveCierra;
import org.unicen.compiladores.acciones.AccionRetornarMayor;
import org.unicen.compiladores.acciones.AccionRetornarMayorIgual;
import org.unicen.compiladores.acciones.AccionRetornarMenor;
import org.unicen.compiladores.acciones.AccionRetornarMenorIgual;
import org.unicen.compiladores.acciones.AccionRetornarMenos;
import org.unicen.compiladores.acciones.AccionRetornarMenosMenos;
import org.unicen.compiladores.acciones.AccionRetornarMultipliacion;
import org.unicen.compiladores.acciones.AccionRetornarParentesisCierra;
import org.unicen.compiladores.acciones.AccionRetornarPuntoComa;
import org.unicen.compiladores.acciones.AccionRetornarSuma;
import org.unicen.compiladores.acciones.error.AccionErrorCaracterInvalido;
import org.unicen.compiladores.acciones.error.AccionErrorDistinto;
import org.unicen.compiladores.acciones.error.AccionErrorDoubleDigito;
import org.unicen.compiladores.acciones.error.AccionErrorDoubleExponente;
import org.unicen.compiladores.acciones.error.AccionErrorDoublePrefijo;
import org.unicen.compiladores.acciones.error.AccionErrorDoublePunto;
import org.unicen.compiladores.acciones.error.AccionErrorDoublePuntoD;
import org.unicen.compiladores.acciones.error.AccionErrorDoubleSignoExponente;
import org.unicen.compiladores.acciones.error.AccionErrorPrefijo;
import org.unicen.compiladores.acciones.error.AccionErrorPrefijoSolo;
import org.unicen.compiladores.acciones.error.AccionErrorSignoNumero;
import org.unicen.compiladores.acciones.error.AccionWarningComentario;

public class Matriz {
	
	private PalabrasReservadas palRes;
	private static Celda[][] matriz; 
	private static final int RETORNAR = -1;
    private static final int ERROR = -2;
	
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
    private static final int CARACTER_ERROR = 31;
    
    public Matriz(){
    //inicializo palabras reservadas
    try {
		palRes = new PalabrasReservadas();
	} catch (IOException e) {
		e.printStackTrace();
	}
    	
    // Seteo la cantidad d
    Matriz.matriz = new Celda[27][31];  // 27 Estados y 29 Valores reconocibles

    //  >= proximo estado ;  ERROR -2 ; RETORNAR -1
    	
    Matriz.matriz[0][LETRA]  = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[0][NUEMRO]  = new Celda(ERROR , new AccionErrorPrefijo());            // Valor numerico sin prefijo ??????????? siempre con prefijo 
    Matriz.matriz[0][GUION_BAJO]  = new Celda(2 ,  new AccionAcumular());
    Matriz.matriz[0][I_MINUSCULA]  = new Celda(1  , new AccionAcumular());
    Matriz.matriz[0][D_MINUSCULA]  = new Celda(1  , new AccionAcumular());
    Matriz.matriz[0][D_MAYUSCULA]  = new Celda(1  , new AccionAcumular());
    Matriz.matriz[0][SUMA]  = new Celda(RETORNAR , new AccionRetornarSuma());
    Matriz.matriz[0][MENOS]  = new Celda(15 , new AccionAcumular());
    Matriz.matriz[0][PUNTO]  = new Celda(ERROR , new AccionErrorCaracterInvalido());
    Matriz.matriz[0][DOS_PUNTOS]  = new Celda(13 , new AccionAcumular());
    Matriz.matriz[0][IGUAL] = new Celda(RETORNAR , new AccionRetornarIgual());
    Matriz.matriz[0][PARENTESIS_ABRE] = new Celda(RETORNAR , new AccionRetonarParentesisAbre());
    Matriz.matriz[0][PARENTESIS_CIERRA] = new Celda(RETORNAR , new AccionRetornarParentesisCierra());
    Matriz.matriz[0][LLAVE_ABRE] = new Celda(RETORNAR , new AccionRetornarLlaveAbre());
    Matriz.matriz[0][LLAVE_CIERRA] = new Celda(RETORNAR , new AccionRetornarLlaveCierra());
    Matriz.matriz[0][PUNTO_Y_COMA] = new Celda(RETORNAR , new AccionRetornarPuntoComa());
    Matriz.matriz[0][COMA] = new Celda(RETORNAR , new AccionRetornarComa());
    Matriz.matriz[0][MENOR] = new Celda(14 , new AccionAcumular());
    Matriz.matriz[0][MAYOR] = new Celda(25 , new AccionAcumular());
    Matriz.matriz[0][CAT] = new Celda(26 , new AccionAcumular());
    Matriz.matriz[0][MULTIPLICACION] = new Celda(RETORNAR , new AccionRetornarMultipliacion());
    Matriz.matriz[0][DIVISION] = new Celda(16 , new AccionAcumular());
    Matriz.matriz[0][COMILLA_SIMPLE] = new Celda(23 , new AccionAcumular());
    Matriz.matriz[0][LETRA_N] = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[0][LETRA_C] = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[0][ARROBA] = new Celda(ERROR , new AccionErrorCaracterInvalido());
    Matriz.matriz[0][NUMERAL] = new Celda(ERROR , new AccionErrorCaracterInvalido());
    Matriz.matriz[0][ENTER] = new Celda(0 ,  new AccionIgnorar());
    Matriz.matriz[0][EOF] = new Celda(RETORNAR , new AccionRetornarEOF());
    Matriz.matriz[0][TAB] = new Celda(0 ,  new AccionIgnorar());
    Matriz.matriz[0][SPACE] = new Celda(0 ,  new AccionIgnorar());
    Matriz.matriz[0][CARACTER_ERROR] = new Celda(-2, new AccionErrorCaracterInvalido()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado 1  --   IDENTIFICARDOR
    Matriz.matriz[1][LETRA]  = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[1][NUEMRO]  = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[1][GUION_BAJO]  = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[1][I_MINUSCULA]  = new Celda(1  , new AccionAcumular());
    Matriz.matriz[1][D_MINUSCULA]  = new Celda(1  , new AccionAcumular());
    Matriz.matriz[1][D_MAYUSCULA]  = new Celda(1  , new AccionAcumular());
    Matriz.matriz[1][SUMA]  = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][MENOS]  = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][PUNTO]  = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][DOS_PUNTOS]  = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][IGUAL] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][PARENTESIS_ABRE] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][PARENTESIS_CIERRA] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][LLAVE_ABRE] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][LLAVE_CIERRA] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][PUNTO_Y_COMA] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][COMA] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][MENOR] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][MAYOR] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][CAT] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][MULTIPLICACION] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][DIVISION] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][COMILLA_SIMPLE] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][LETRA_N] = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[1][LETRA_C] = new Celda(1 ,  new AccionAcumular());
    Matriz.matriz[1][ARROBA] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][NUMERAL] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes)); 
    Matriz.matriz[1][ENTER] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][EOF] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][TAB] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][SPACE] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));
    Matriz.matriz[1][CARACTER_ERROR] = new Celda(RETORNAR , new AccionRetornarIdentificador(palRes));

    /////////////////////////////////////////////////////////////////////
    /////   Estado 2  --   INICIO CONSTANTE
    Matriz.matriz[2][LETRA]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][NUEMRO]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][I_MINUSCULA]  = new Celda(3  ,  new AccionAcumular());
    Matriz.matriz[2][D_MINUSCULA]  = new Celda(6  ,  new AccionAcumular());
    Matriz.matriz[2][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][SUMA]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][MENOS]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][PUNTO]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][IGUAL] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][COMA] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][MENOR] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][MAYOR] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][CAT] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][DIVISION] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][LETRA_N] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][LETRA_C] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][ARROBA] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][NUMERAL] = new Celda(ERROR ,  new AccionErrorPrefijoSolo()); 
    Matriz.matriz[2][ENTER] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][EOF] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][TAB] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][SPACE] = new Celda(ERROR ,  new AccionErrorPrefijoSolo());
    Matriz.matriz[2][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorPrefijoSolo()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado 3  --   CONSTANTE ENTERA
    Matriz.matriz[3][LETRA]  = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][NUEMRO]  = new Celda(4 ,  new AccionAcumular());
    Matriz.matriz[3][GUION_BAJO]  = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][I_MINUSCULA]  = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][D_MINUSCULA]  = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][D_MAYUSCULA]  = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][SUMA]  = new Celda(5 ,  new AccionAcumular());
    Matriz.matriz[3][MENOS]  = new Celda(5 ,  new AccionAcumular());
    Matriz.matriz[3][PUNTO]  = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][DOS_PUNTOS]  = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][IGUAL] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][PARENTESIS_ABRE] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][PARENTESIS_CIERRA] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][LLAVE_ABRE] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][LLAVE_CIERRA] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][PUNTO_Y_COMA] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][COMA] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][MENOR] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][MAYOR] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][CAT] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][MULTIPLICACION] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][DIVISION] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][COMILLA_SIMPLE] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][LETRA_N] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][LETRA_C] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][ARROBA] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][NUMERAL] = new Celda(RETORNAR , new AccionErrorSignoNumero()); 
    Matriz.matriz[3][ENTER] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][EOF] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][TAB] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][SPACE] = new Celda(RETORNAR , new AccionErrorSignoNumero());
    Matriz.matriz[3][CARACTER_ERROR] = new Celda(RETORNAR , new AccionErrorSignoNumero()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado 4 --   CONSTANTE ENTERA
    Matriz.matriz[4][LETRA]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][NUEMRO]  = new Celda(4  ,  new AccionAcumular());
    Matriz.matriz[4][GUION_BAJO]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][I_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][D_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][D_MAYUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][SUMA]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][MENOS]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][PUNTO]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][DOS_PUNTOS]  = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][PARENTESIS_ABRE] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][PARENTESIS_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][LLAVE_ABRE] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][LLAVE_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][PUNTO_Y_COMA] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][COMA] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][MENOR] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][MAYOR] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][CAT] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][MULTIPLICACION] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][DIVISION] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][LETRA_N] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][LETRA_C] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][ARROBA] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][NUMERAL] = new Celda(RETORNAR ,  new AccionRetornarEntero()); 
    Matriz.matriz[4][ENTER] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][EOF] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][TAB] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][SPACE] = new Celda(RETORNAR ,  new AccionRetornarEntero());
    Matriz.matriz[4][CARACTER_ERROR] = new Celda(RETORNAR ,  new AccionRetornarEntero());

    /////////////////////////////////////////////////////////////////////
    /////   Estado 5 --   CONSTANTE ENTERA
    Matriz.matriz[5][LETRA]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][NUEMRO]  = new Celda(4  ,  new AccionAcumular());
    Matriz.matriz[5][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][SUMA]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][MENOS]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][PUNTO]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][IGUAL] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][COMA] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][MENOR] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][MAYOR] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][CAT] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][DIVISION] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][LETRA_N] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][LETRA_C] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][ARROBA] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][NUMERAL] = new Celda(ERROR ,  new AccionErrorSignoNumero()); 
    Matriz.matriz[5][ENTER] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][EOF] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][TAB] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][SPACE] = new Celda(ERROR ,  new AccionErrorSignoNumero());
    Matriz.matriz[5][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorSignoNumero());

    /////////////////////////////////////////////////////////////////////
    /////   Estado6 --   CONSTANTE DOUBLE
    Matriz.matriz[6][LETRA]  = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][NUEMRO]  = new Celda(8  ,  new AccionAcumular());
    Matriz.matriz[6][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][SUMA]  = new Celda(7  ,  new AccionAcumular());
    Matriz.matriz[6][MENOS]  = new Celda(7  ,  new AccionAcumular());
    Matriz.matriz[6][PUNTO]  = new Celda(24 ,  new AccionAcumular());
    Matriz.matriz[6][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][IGUAL] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][COMA] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][MENOR] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][MAYOR] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][CAT] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][DIVISION] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][LETRA_N] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][LETRA_C] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][ARROBA] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][NUMERAL] = new Celda(ERROR ,  new AccionErrorDoublePrefijo()); 
    Matriz.matriz[6][ENTER] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][EOF] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][TAB] = new Celda(ERROR ,  new AccionErrorDoublePrefijo());
    Matriz.matriz[6][SPACE] = new Celda(ERROR ,  new AccionErrorDoublePrefijo()); 
    Matriz.matriz[6][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorDoublePrefijo());

    /////////////////////////////////////////////////////////////////////
    /////   Estado7 --   CONSTANTE DOUBLE
    Matriz.matriz[7][LETRA]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][NUEMRO]  = new Celda(8  ,  new AccionAcumular());
    Matriz.matriz[7][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][SUMA]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][MENOS]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][PUNTO]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][IGUAL] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][COMA] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][MENOR] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][MAYOR] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][CAT] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][DIVISION] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][LETRA_N] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][LETRA_C] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][ARROBA] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][NUMERAL] = new Celda(ERROR ,  new AccionErrorDoubleDigito()); 
    Matriz.matriz[7][ENTER] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][EOF] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][TAB] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][SPACE] = new Celda(ERROR ,  new AccionErrorDoubleDigito());
    Matriz.matriz[7][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorDoubleDigito()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado8 --   CONSTANTE DOUBLE
    Matriz.matriz[8][LETRA]  = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][NUEMRO]  = new Celda(8  ,  new AccionAcumular());
    Matriz.matriz[8][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][D_MINUSCULA]  = new Celda(10  , new AccionAcumular());
    Matriz.matriz[8][D_MAYUSCULA]  = new Celda(10  , new AccionAcumular());
    Matriz.matriz[8][SUMA]  = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][MENOS]  = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][PUNTO]  = new Celda(9  ,  new AccionAcumular());
    Matriz.matriz[8][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][IGUAL] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][COMA] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][MENOR] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][MAYOR] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][CAT] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][DIVISION] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][LETRA_N] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][LETRA_C] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][ARROBA] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][NUMERAL] = new Celda(ERROR ,  new AccionErrorDoublePuntoD()); 
    Matriz.matriz[8][ENTER] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][EOF] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][TAB] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][SPACE] = new Celda(ERROR ,  new AccionErrorDoublePuntoD());
    Matriz.matriz[8][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorDoublePuntoD()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado9 --   CONSTANTE DOUBLE
    Matriz.matriz[9][LETRA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][NUEMRO]  = new Celda(9  ,  new AccionAcumular());
    Matriz.matriz[9][GUION_BAJO]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][I_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][D_MINUSCULA]  = new Celda(10  , new AccionAcumular());
    Matriz.matriz[9][D_MAYUSCULA]  = new Celda(10  , new AccionAcumular());
    Matriz.matriz[9][SUMA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][MENOS]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][PUNTO]  = new Celda(24 ,  new AccionAcumular());
    Matriz.matriz[9][DOS_PUNTOS]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][PARENTESIS_ABRE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][PARENTESIS_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][LLAVE_ABRE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][LLAVE_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][PUNTO_Y_COMA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][COMA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][MENOR] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][MAYOR] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][CAT] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][MULTIPLICACION] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][DIVISION] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][LETRA_N] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][LETRA_C] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][ARROBA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][NUMERAL] = new Celda(RETORNAR ,  new AccionRetornarDouble()); 
    Matriz.matriz[9][ENTER] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][EOF] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][TAB] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][SPACE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[9][CARACTER_ERROR] = new Celda(RETORNAR ,  new AccionRetornarDouble());

    /////////////////////////////////////////////////////////////////////
    /////   Estado10 --   CONSTANTE DOUBLE
    Matriz.matriz[10][LETRA]  = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][NUEMRO]  = new Celda(12 ,  new AccionAcumular());
    Matriz.matriz[10][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][SUMA]  = new Celda(11 ,  new AccionAcumular());
    Matriz.matriz[10][MENOS]  = new Celda(11,   new AccionAcumular());
    Matriz.matriz[10][PUNTO]  = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][IGUAL] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][COMA] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][MENOR] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][MAYOR] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][CAT] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][DIVISION] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][LETRA_N] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][LETRA_C] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][ARROBA] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][NUMERAL] = new Celda(ERROR ,  new AccionErrorDoubleExponente()); 
    Matriz.matriz[10][ENTER] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][EOF] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][TAB] = new Celda(ERROR ,  new AccionErrorDoubleExponente());
    Matriz.matriz[10][SPACE] = new Celda(ERROR ,  new AccionErrorDoubleExponente());            
    Matriz.matriz[10][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorDoubleExponente()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado11 --   CONSTANTE DOUBLE
    Matriz.matriz[11][LETRA]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][NUEMRO]  = new Celda(12 ,  new AccionAcumular());
    Matriz.matriz[11][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][SUMA]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][MENOS]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][PUNTO]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][IGUAL] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][COMA] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][MENOR] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][MAYOR] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][CAT] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][DIVISION] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][LETRA_N] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][LETRA_C] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][ARROBA] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][NUMERAL] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente()); 
    Matriz.matriz[11][ENTER] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][EOF] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][TAB] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());
    Matriz.matriz[11][SPACE] = new Celda(ERROR ,  new AccionErrorDoubleSignoExponente());            
    Matriz.matriz[11][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorDoubleSignoExponente());

    /////////////////////////////////////////////////////////////////////
    /////   Estado12 --   CONSTANTE DOUBLE
    Matriz.matriz[12][LETRA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][NUEMRO]  = new Celda(12 ,  new AccionAcumular());
    Matriz.matriz[12][GUION_BAJO]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][I_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][D_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][D_MAYUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][SUMA]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][MENOS]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][PUNTO]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][DOS_PUNTOS]  = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][PARENTESIS_ABRE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][PARENTESIS_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][LLAVE_ABRE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][LLAVE_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][PUNTO_Y_COMA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][COMA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][MENOR] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][MAYOR] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][CAT] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][MULTIPLICACION] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][DIVISION] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][LETRA_N] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][LETRA_C] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][ARROBA] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][NUMERAL] = new Celda(RETORNAR ,  new AccionRetornarDouble()); 
    Matriz.matriz[12][ENTER] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][EOF] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][TAB] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][SPACE] = new Celda(RETORNAR ,  new AccionRetornarDouble());
    Matriz.matriz[12][CARACTER_ERROR] = new Celda(RETORNAR ,  new AccionRetornarDouble()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado13 --   CAMINO ASIGNACION
    Matriz.matriz[13][LETRA]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][NUEMRO]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][SUMA]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][MENOS]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][PUNTO]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarAsignacion());
    Matriz.matriz[13][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][COMA] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][MENOR] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][MAYOR] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][CAT] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][DIVISION] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][LETRA_N] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][LETRA_C] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][ARROBA] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][NUMERAL] = new Celda(ERROR ,  new AccionErrorCaracterInvalido()); 
    Matriz.matriz[13][ENTER] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][EOF] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][TAB] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());
    Matriz.matriz[13][SPACE] = new Celda(ERROR ,  new AccionErrorCaracterInvalido());  
    Matriz.matriz[13][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorCaracterInvalido()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado14 --   CAMINO MENOR ,  MENOR IGUAL
    Matriz.matriz[14][LETRA]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][NUEMRO]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][GUION_BAJO]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][I_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][D_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][D_MAYUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][SUMA]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][MENOS]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][PUNTO]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][DOS_PUNTOS]  = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarMenorIgual());
    Matriz.matriz[14][PARENTESIS_ABRE] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][PARENTESIS_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][LLAVE_ABRE] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][LLAVE_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][PUNTO_Y_COMA] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][COMA] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][MENOR] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][MAYOR] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][CAT] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][MULTIPLICACION] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][DIVISION] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][LETRA_N] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][LETRA_C] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][ARROBA] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][NUMERAL] = new Celda(RETORNAR ,  new AccionRetornarMenor()); 
    Matriz.matriz[14][ENTER] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][EOF] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][TAB] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][SPACE] = new Celda(RETORNAR ,  new AccionRetornarMenor());
    Matriz.matriz[14][CARACTER_ERROR] = new Celda(RETORNAR ,  new AccionRetornarMenor());

    /////////////////////////////////////////////////////////////////////
    /////   Estado15 --   CAMINO MENOS ,  MENOS MENOS
    Matriz.matriz[15][LETRA]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][NUEMRO]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][GUION_BAJO]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][I_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][D_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][D_MAYUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][SUMA]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][MENOS]  = new Celda(RETORNAR ,  new AccionRetornarMenosMenos());
    Matriz.matriz[15][PUNTO]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][DOS_PUNTOS]  = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][PARENTESIS_ABRE] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][PARENTESIS_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][LLAVE_ABRE] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][LLAVE_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][PUNTO_Y_COMA] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][COMA] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][MENOR] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][MAYOR] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][CAT] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][MULTIPLICACION] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][DIVISION] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][LETRA_N] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][LETRA_C] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][ARROBA] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][NUMERAL] = new Celda(RETORNAR ,  new AccionRetornarMenos()); 
    Matriz.matriz[15][ENTER] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][EOF] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][TAB] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][SPACE] = new Celda(RETORNAR ,  new AccionRetornarMenos());
    Matriz.matriz[15][CARACTER_ERROR] = new Celda(RETORNAR ,  new AccionRetornarMenos()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado16 --   INICIO COMENTARIO ; DIVISION
    Matriz.matriz[16][LETRA]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][NUEMRO]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][GUION_BAJO]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][I_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][D_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][D_MAYUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][SUMA]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][MENOS]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][PUNTO]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][DOS_PUNTOS]  = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][PARENTESIS_ABRE] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][PARENTESIS_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][LLAVE_ABRE] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][LLAVE_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][PUNTO_Y_COMA] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][COMA] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][MENOR] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][MAYOR] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][CAT] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][MULTIPLICACION] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][DIVISION] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][LETRA_N] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][LETRA_C] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][ARROBA] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][NUMERAL] = new Celda(17 ,  new AccionAcumular()); 
    Matriz.matriz[16][ENTER] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][EOF] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][TAB] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][SPACE] = new Celda(RETORNAR ,  new AccionRetornarDivision());
    Matriz.matriz[16][CARACTER_ERROR] = new Celda(RETORNAR ,  new AccionRetornarDivision()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado17 --   INICIO COMENTARIO
    Matriz.matriz[17][LETRA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][NUEMRO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][GUION_BAJO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][I_MINUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][D_MINUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][D_MAYUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][SUMA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][MENOS]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][PUNTO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][DOS_PUNTOS]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][IGUAL] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][PARENTESIS_ABRE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][PARENTESIS_CIERRA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][LLAVE_ABRE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][LLAVE_CIERRA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][PUNTO_Y_COMA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][COMA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][MENOR] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][MAYOR] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][CAT] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][MULTIPLICACION] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][DIVISION] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][COMILLA_SIMPLE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][LETRA_N] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][LETRA_C] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][ARROBA] = new Celda(18 ,  new AccionIgnorar());
    Matriz.matriz[17][NUMERAL] = new Celda(22 ,  new AccionInicioComentario()); 
    Matriz.matriz[17][ENTER] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][EOF] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][TAB] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[17][SPACE] = new Celda(21 ,  new AccionInicioComentario());          
    Matriz.matriz[17][CARACTER_ERROR] = new Celda(21 ,  new AccionInicioComentario());

    /////////////////////////////////////////////////////////////////////
    /////   Estado18 --   POSIBLE ANOTACION
    Matriz.matriz[18][LETRA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][NUEMRO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][GUION_BAJO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][I_MINUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][D_MINUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][D_MAYUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][SUMA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][MENOS]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][PUNTO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][DOS_PUNTOS]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][IGUAL] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][PARENTESIS_ABRE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][PARENTESIS_CIERRA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][LLAVE_ABRE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][LLAVE_CIERRA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][PUNTO_Y_COMA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][COMA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][MENOR] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][MAYOR] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][CAT] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][MULTIPLICACION] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][DIVISION] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][COMILLA_SIMPLE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][LETRA_N] = new Celda(19 ,  new AccionIgnorar());
    Matriz.matriz[18][LETRA_C] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][ARROBA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][NUMERAL] = new Celda(22 ,  new AccionInicioComentario()); 
    Matriz.matriz[18][ENTER] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][EOF] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][TAB] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][SPACE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[18][CARACTER_ERROR] = new Celda(21 ,  new AccionInicioComentario());

    /////////////////////////////////////////////////////////////////////
    /////   Estado19 --   POSIBLE ANOTACION
    Matriz.matriz[19][LETRA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][NUEMRO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][GUION_BAJO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][I_MINUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][D_MINUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][D_MAYUSCULA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][SUMA]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][MENOS]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][PUNTO]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][DOS_PUNTOS]  = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][IGUAL] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][PARENTESIS_ABRE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][PARENTESIS_CIERRA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][LLAVE_ABRE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][LLAVE_CIERRA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][PUNTO_Y_COMA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][COMA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][MENOR] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][MAYOR] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][CAT] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][MULTIPLICACION] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][DIVISION] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][COMILLA_SIMPLE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][LETRA_N] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][LETRA_C] = new Celda(20 ,  new AccionInicioAnotacion());
    Matriz.matriz[19][ARROBA] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][NUMERAL] = new Celda(22 ,  new AccionInicioComentario()); 
    Matriz.matriz[19][ENTER] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][EOF] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][TAB] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][SPACE] = new Celda(21 ,  new AccionInicioComentario());
    Matriz.matriz[19][CARACTER_ERROR] = new Celda(21 ,  new AccionInicioComentario());

    /////////////////////////////////////////////////////////////////////
    /////   Estado20 --   ANOTACION
    Matriz.matriz[20][LETRA]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][NUEMRO]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][GUION_BAJO]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][I_MINUSCULA]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][D_MINUSCULA]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][D_MAYUSCULA]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][SUMA]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][MENOS]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][PUNTO]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][DOS_PUNTOS]  = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][IGUAL] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][PARENTESIS_ABRE] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][PARENTESIS_CIERRA] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][LLAVE_ABRE] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][LLAVE_CIERRA] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][PUNTO_Y_COMA] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][COMA] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][MENOR] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][MAYOR] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][CAT] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][MULTIPLICACION] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][DIVISION] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][COMILLA_SIMPLE] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][LETRA_N] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][LETRA_C] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][ARROBA] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][NUMERAL] = new Celda(20 ,  new AccionAcumular()); 
    Matriz.matriz[20][ENTER] = new Celda(RETORNAR ,  new AccionRetornarAnotacion());
    Matriz.matriz[20][EOF] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][TAB] = new Celda(20 ,  new AccionAcumular());
    Matriz.matriz[20][SPACE] = new Celda(20 ,  new AccionAcumular());   
    Matriz.matriz[20][CARACTER_ERROR] = new Celda(20 ,  new AccionAcumular());

    /////////////////////////////////////////////////////////////////////
    /////   Estado21 --   COMENTARIO
    Matriz.matriz[21][LETRA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][NUEMRO]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][GUION_BAJO]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][I_MINUSCULA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][D_MINUSCULA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][D_MAYUSCULA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][SUMA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][MENOS]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][PUNTO]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][DOS_PUNTOS]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][IGUAL] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][PARENTESIS_ABRE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][PARENTESIS_CIERRA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][LLAVE_ABRE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][LLAVE_CIERRA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][PUNTO_Y_COMA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][COMA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][MENOR] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][MAYOR] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][CAT] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][MULTIPLICACION] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][DIVISION] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][COMILLA_SIMPLE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][LETRA_N] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][LETRA_C] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][ARROBA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][NUMERAL] = new Celda(22 ,  new AccionIgnorar()); 
    Matriz.matriz[21][ENTER] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][EOF] = new Celda(RETORNAR ,  new AccionWarningComentario());
    Matriz.matriz[21][TAB] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][SPACE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[21][CARACTER_ERROR] = new Celda(21 ,  new AccionIgnorar()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado22 --   COMENTARIO
    Matriz.matriz[22][LETRA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][NUEMRO]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][GUION_BAJO]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][I_MINUSCULA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][D_MINUSCULA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][D_MAYUSCULA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][SUMA]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][MENOS]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][PUNTO]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][DOS_PUNTOS]  = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][IGUAL] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][PARENTESIS_ABRE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][PARENTESIS_CIERRA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][LLAVE_ABRE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][LLAVE_CIERRA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][PUNTO_Y_COMA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][COMA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][MENOR] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][MAYOR] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][CAT] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][MULTIPLICACION] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][DIVISION] = new Celda(0  ,  new AccionIgnorar());   // /     VER ESTO
    Matriz.matriz[22][COMILLA_SIMPLE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][LETRA_N] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][LETRA_C] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][ARROBA] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][NUMERAL] = new Celda(22 ,  new AccionIgnorar()); 
    Matriz.matriz[22][ENTER] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][EOF] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][TAB] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][SPACE] = new Celda(21 ,  new AccionIgnorar());
    Matriz.matriz[22][CARACTER_ERROR] = new Celda(21 ,  new AccionIgnorar());

    /////////////////////////////////////////////////////////////////////
    /////   Estado23 --   INICIO CADENA
    Matriz.matriz[23][LETRA]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][NUEMRO]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][GUION_BAJO]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][I_MINUSCULA]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][D_MINUSCULA]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][D_MAYUSCULA]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][SUMA]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][MENOS]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][PUNTO]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][DOS_PUNTOS]  = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][IGUAL] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][PARENTESIS_ABRE] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][PARENTESIS_CIERRA] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][LLAVE_ABRE] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][LLAVE_CIERRA] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][PUNTO_Y_COMA] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][COMA] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][MENOR] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][MAYOR] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][CAT] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][MULTIPLICACION] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][DIVISION] = new Celda(23 ,  new AccionAcumular());   // /     VER ESTO
    Matriz.matriz[23][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarCadena());
    Matriz.matriz[23][LETRA_N] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][LETRA_C] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][ARROBA] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][NUMERAL] = new Celda(23 ,  new AccionAcumular()); 
    Matriz.matriz[23][ENTER] = new Celda(RETORNAR ,  new AccionRetornarCadena());
    Matriz.matriz[23][EOF] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][TAB] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][SPACE] = new Celda(23 ,  new AccionAcumular());
    Matriz.matriz[23][CARACTER_ERROR] = new Celda(23 ,  new AccionAcumular()); 

    /////////////////////////////////////////////////////////////////////
    /////   Estado24 --   CONSTANTE DOUBLE
    Matriz.matriz[24][LETRA]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][NUEMRO]  = new Celda(9 ,   new AccionAcumular());
    Matriz.matriz[24][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorDoublePunto()); 
    Matriz.matriz[24][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][SUMA]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][MENOS]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][PUNTO]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][IGUAL] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][COMA] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][MENOR] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][MAYOR] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][CAT] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][DIVISION] = new Celda(ERROR ,  new AccionErrorDoublePunto()); //VER ESTO
    Matriz.matriz[24][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][LETRA_N] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][LETRA_C] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][ARROBA] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][NUMERAL] = new Celda(ERROR ,  new AccionErrorDoublePunto()); 
    Matriz.matriz[24][ENTER] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][EOF] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][TAB] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][SPACE] = new Celda(ERROR ,  new AccionErrorDoublePunto());
    Matriz.matriz[24][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorDoublePunto());

    /////////////////////////////////////////////////////////////////////
    /////   Estado25 --   CAMINO MAYOR ,  MAYOR IGUAL
    Matriz.matriz[25][LETRA]  = new Celda(RETORNAR ,  new AccionRetornarMayor()); 
    Matriz.matriz[25][NUEMRO]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][GUION_BAJO]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][I_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][D_MINUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][D_MAYUSCULA]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][SUMA]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][MENOS]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][PUNTO]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][DOS_PUNTOS]  = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarMayorIgual());
    Matriz.matriz[25][PARENTESIS_ABRE] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][PARENTESIS_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][LLAVE_ABRE] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][LLAVE_CIERRA] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][PUNTO_Y_COMA] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][COMA] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][MENOR] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][MAYOR] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][CAT] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][MULTIPLICACION] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][DIVISION] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][COMILLA_SIMPLE] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][LETRA_N] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][LETRA_C] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][ARROBA] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][NUMERAL] = new Celda(RETORNAR ,  new AccionRetornarMayor()); 
    Matriz.matriz[25][ENTER] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][EOF] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][TAB] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][SPACE] = new Celda(RETORNAR ,  new AccionRetornarMayor());
    Matriz.matriz[25][CARACTER_ERROR] = new Celda(RETORNAR ,  new AccionRetornarMayor());

    /////////////////////////////////////////////////////////////////////
    /////   Estado26 --   CAMINO DISTINTO
    Matriz.matriz[26][LETRA]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][NUEMRO]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][GUION_BAJO]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][I_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][D_MINUSCULA]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][D_MAYUSCULA]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][SUMA]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][MENOS]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][PUNTO]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][DOS_PUNTOS]  = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][IGUAL] = new Celda(RETORNAR ,  new AccionRetornarDistinto());
    Matriz.matriz[26][PARENTESIS_ABRE] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][PARENTESIS_CIERRA] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][LLAVE_ABRE] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][LLAVE_CIERRA] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][PUNTO_Y_COMA] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][COMA] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][MENOR] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][MAYOR] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][CAT] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][MULTIPLICACION] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][DIVISION] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][COMILLA_SIMPLE] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][LETRA_N] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][LETRA_C] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][ARROBA] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][NUMERAL] = new Celda(ERROR ,  new AccionErrorDistinto()); 
    Matriz.matriz[26][ENTER] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][EOF] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][TAB] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][SPACE] = new Celda(ERROR ,  new AccionErrorDistinto());
    Matriz.matriz[26][CARACTER_ERROR] = new Celda(ERROR,  new AccionErrorDistinto());
    }    

	public Celda obtenerCelda(int estado, int entrada) {
		return Matriz.matriz[estado][entrada];
	}

}
