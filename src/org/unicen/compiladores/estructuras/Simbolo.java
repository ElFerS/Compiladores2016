package org.unicen.compiladores.estructuras;

public class Simbolo {

    
    private String nombre;
    private String valor;
    private int contadorReferencias = 0;
    
    public Simbolo(String n, String v){
        this.nombre = n;
        this.valor = v;
        this.setContadorReferencias(1);
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
	public String toString(){
		return nombre+" "+valor;
	}
	public void sumarReferencia(){
        this.setContadorReferencias(this.getContadorReferencias() + 1);
    }
    
    public void restarReferencias(){
        this.setContadorReferencias(this.getContadorReferencias() - 1);
    }

	public int getContadorReferencias() {
		return contadorReferencias;
	}

	public void setContadorReferencias(int contadorReferencias) {
		this.contadorReferencias = contadorReferencias;
	}
}
