package org.unicen.compiladores.acciones.error;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.acciones.Accion;
import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionWarningComentario implements Accion {

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		listErrores.add("Warning en línea "+a.obtenerLineaActual()+": no se cerró comentario.");
	}

}
