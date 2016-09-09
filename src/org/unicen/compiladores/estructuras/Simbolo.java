package org.unicen.compiladores.estructuras;

public class Simbolo {

    
    private String nombre;
    private String valor;
    //private int contadorReferencias = 0;
    
    public Simbolo(String n, String v){
        this.nombre = n;
        this.valor = v;
        //this.contadorReferencias = 1;
    }
    
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
