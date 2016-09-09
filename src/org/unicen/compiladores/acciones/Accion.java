package org.unicen.compiladores.acciones;

import java.awt.List;
import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public interface Accion {
	public abstract void ejecutar(String c,Token t,Archivo a,JTable jTableTokens,List listErrores,JTable jTableTS,TablaSimbolos st);
}
