package org.unicen.compiladores.estructuras;

import org.unicen.compiladores.acciones.Accion;

public class Celda {
	
	private int estadoSiguiente;
	private Accion accion;
	
	
	public Celda(int i, Accion accion) {
		this.estadoSiguiente=i;
		this.accion=accion;
	}

	public Accion ObtenerAccion() {
		return this.accion;
	}

	public int ObtenerEstadoSiguiente() {
		return this.estadoSiguiente;
	}

}
