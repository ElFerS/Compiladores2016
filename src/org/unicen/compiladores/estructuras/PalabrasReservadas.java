package org.unicen.compiladores.estructuras;

import java.util.Vector;

public class PalabrasReservadas {

	private Vector<String> palabrasReservadas = new Vector<String>();
    //TODO levantar las palabras reservadas desde archivo
    public PalabrasReservadas(){
       this.palabrasReservadas.add("IF");
        this.palabrasReservadas.add("ELSE");
        this.palabrasReservadas.add("ENDIF");
        this.palabrasReservadas.add("PRINT");        
        this.palabrasReservadas.add("INTEGER");
        this.palabrasReservadas.add("DOUBLE"); 
        this.palabrasReservadas.add("WHILE");
        this.palabrasReservadas.add("FUNCTION");
        this.palabrasReservadas.add("RETURN");
        this.palabrasReservadas.add("INTTODOUBLE");
        this.palabrasReservadas.add("TO");
        this.palabrasReservadas.add("ALLOW");        
    }
    
    public boolean esPalabrareservada(String p){
        return this.palabrasReservadas.contains(p.toUpperCase());
    }
}
