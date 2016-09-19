package org.unicen.compiladores.acciones;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.Simbolo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionRetornarEntero implements Accion {

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		if (c!=null)
            a.retrocederIndice();
		t.quitarprefijo(); //sin prefijo _i para obtener el valor entero
		if (validarEntero(Integer.valueOf(t.obtenerLexema()))){
			t.setearNombre("Entero");
			((javax.swing.table.DefaultTableModel)(jTableTokens.getModel())).addRow(new String[] {t.obtenerNombre(),t.obtenerLexema()});
			st.agregarSimbolo(new Simbolo("Entero",t.obtenerLexema().substring(0, t.obtenerLexema().length()-1)),jTableTS);
		}
		else
			listErrores.add("Error en línea "+a.obtenerLineaActual()+": entero fuera de rango.");
	}

    public boolean validarEntero(Integer i){
        return new Double(i.intValue()).compareTo(Math.pow(2, 15))<=0;
    }

}
