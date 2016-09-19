package org.unicen.compiladores.acciones.error;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.acciones.Accion;
import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionErrorCadena implements Accion {

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		if (c==null)
			listErrores.add("Error en línea "+a.obtenerLineaActual()+": no se cerró o abrió el delimitador de cadena.");
		else
			listErrores.add("Error en línea "+(a.obtenerLineaActual()-1)+": no se cerró o abrió el delimitador de cadena.");
		t.setearLexema("");
	}

}
