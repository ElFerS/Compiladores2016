package org.unicen.compiladores.acciones;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionRetornarMayorIgual implements Accion {

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		//TODO no se esta retrocediendo
		t.agregarLexema(c);
        t.setearNombre("MayorIgual");
        ((javax.swing.table.DefaultTableModel)(jTableTokens.getModel())).addRow(new String[] {t.obtenerNombre(),t.obtenerLexema()});
	}

}
