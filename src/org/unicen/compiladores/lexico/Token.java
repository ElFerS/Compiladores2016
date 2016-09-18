package org.unicen.compiladores.lexico;

public class Token {
	
	 private String lexema = "";
	 private String nombre = "";
	 private int linea ;

	 public Token(){

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

	 public void quitarprefijo(){
		 String value = this.lexema;
		 this.setearLexema(value.substring(2));
	 }

	 public void setearlinea(int l){
		 this.linea = l;
	 }

	 public int getlinea(){
		 return this.linea;
	 }
    public String toString(){
    	return "nombre: "+nombre+" lexema: "+lexema;
    }
}
