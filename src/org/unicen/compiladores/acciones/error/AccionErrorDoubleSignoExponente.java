/**
 * 
 */
package org.unicen.compiladores.acciones;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

/**
 * @author Fer ando
 *
 */
public class AccionErrorDoubleSignoExponente implements Accion {

	/* (non-Javadoc)
	 * @see org.unicen.compiladores.acciones.Accion#ejecutar(java.lang.String, org.unicen.compiladores.lexico.Token, org.unicen.compiladores.estructuras.Archivo, javax.swing.JTable, java.awt.List, javax.swing.JTable, org.unicen.compiladores.estructuras.TablaSimbolos)
	 */
	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		if (c!=null)
			a.retrocederIndice();
		listErrores.add("Error en línea "+a.obtenerLineaActual()+": se esperaba un dígito después del signo del exponente.");
	}

}
