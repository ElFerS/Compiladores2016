package org.unicen.compiladores.estructuras;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class PalabrasReservadas {

	private Vector<String> palabrasReservadas = new Vector<String>();
	private BufferedReader entrada;
    public PalabrasReservadas() throws IOException{
    	
    	entrada = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/Archivos/palabrasReservadas.txt"));
    	String linea = entrada.readLine();
    	
		while (linea != null){
			palabrasReservadas.addElement(linea);
			linea = entrada.readLine();
		}
		        
    }
    
    public boolean esPalabrareservada(String p){
        return this.palabrasReservadas.contains(p.toUpperCase());
    }
    public String toString(){
		return palabrasReservadas.toString();
    	
    }
}
