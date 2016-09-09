package org.unicen.compiladores.acciones;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.Simbolo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionRetornarCadena implements Accion {

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		t.agregarLexema(c);
        t.setearNombre("Cadena");
        ((javax.swing.table.DefaultTableModel)(jTableTokens.getModel())).addRow(new String[] {t.obtenerNombre(),t.obtenerLexema()});
        st.agregarSimbolo(new Simbolo("Cadena",t.obtenerLexema().substring(1, t.obtenerLexema().length()-1)),jTableTS);
	}

}
