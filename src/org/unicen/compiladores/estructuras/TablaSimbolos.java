package org.unicen.compiladores.estructuras;

import java.util.HashMap;

import javax.swing.JTable;

public class TablaSimbolos {

	private HashMap<String, Simbolo> tSimbol;

	public TablaSimbolos() {
		tSimbol=new HashMap<String, Simbolo>();
	}
	public Simbolo buscarSimbolo(Simbolo s){

		return this.tSimbol.get(s.getNombre());
	}

	public void agregarSimbolo(Simbolo s, JTable ts){

		Simbolo s1 = this.buscarSimbolo(s);

		if (s1 == null){
			this.tSimbol.put(s.getNombre(),s);
		}
	}

	public void borrarsimbolo(Simbolo s){
		this.tSimbol.remove(s.getNombre());
	}

}
