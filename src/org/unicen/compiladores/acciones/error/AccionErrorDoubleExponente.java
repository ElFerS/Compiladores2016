package org.unicen.compiladores.acciones;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionErrorDoubleExponente implements Accion {

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		if (c!=null)
			a.retrocederIndice();
		listErrores.add("Error en línea "+a.obtenerLineaActual()+": se esperaba el signo o dígito del exponente.");
	}

}
