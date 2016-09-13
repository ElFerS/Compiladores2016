package org.unicen.compiladores.lexico;

public class Token {
	
	private String lexema;
    private String nombre;
    
    public Token(){
    	lexema = "";
    	nombre = "";
    }
    
    public void agregarLexema(String c){
        this.lexema += c;
    }
    
    public String obtenerLexema(){
        return this.lexema;
    }
    
    public void setearLexema(String l){
        this.lexema = l;
    }
    
    public void limpiarLexema(){
        this.lexema = "";
    }
    
    public void setearNombre(String n){
        this.nombre = n;
    }
    
    public String obtenerNombre(){
        return this.nombre;
    }
    public String toString(){
    	return "nombre: "+nombre+" lexema: "+lexema;
    }
}
