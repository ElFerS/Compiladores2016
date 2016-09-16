package org.unicen.compiladores.estructuras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JTextArea;

public class Archivo {
	private Vector<String> caracteres = new Vector<String>();
	private static int lineaActual = 0;
	private static int indiceActual = 0;
	private BufferedReader entrada;

	public void load(File f,JTextArea jTextArea1) throws FileNotFoundException, IOException{
		this.caracteres.removeAllElements();
		entrada = new BufferedReader(new FileReader(f));
		String linea = entrada.readLine();
		while (linea != null){         
			for (int i = 0; i < linea.length();i++)
				this.caracteres.add(String.valueOf(linea.charAt(i)));
			linea = entrada.readLine();
			if (linea!=null)
				this.caracteres.add(String.valueOf("\n"));
		}
		lineaActual = 1;   
		indiceActual = 0;
		for(int i=0;i<this.caracteres.size();i++){
			jTextArea1.append(this.caracteres.get(i));
		}
	}

	public String obtenerCaracter(){
		if (Archivo.indiceActual == this.caracteres.size())
			return null;
		String caracter = this.caracteres.get(Archivo.indiceActual);
		if (caracter.equals("\n"))
			Archivo.lineaActual++;
		Archivo.indiceActual++;
		return caracter;
	}

	public void retrocederIndice(){
		if (Archivo.indiceActual>0){
			Archivo.indiceActual--;
			if (this.caracteres.get(Archivo.indiceActual).equals("\n"))
				Archivo.lineaActual--;
		}
	}

	public int obtenerLineaActual(){
		return Archivo.lineaActual;
	}

	public void obtenerPosición(int p,javax.swing.JTextField f, javax.swing.JTextField c){
		System.out.println(p);
		int fila = 1;
		int columna = 1;
		int indice = 0;
		while (p>0){
			if(this.caracteres.get(indice).equals("\n")){
				fila++;
				columna = 0;
			}
			p--;
			columna++;
			indice ++;
		}
		f.setText(String.valueOf(fila));
		c.setText(String.valueOf(columna));
	}

	public int obtenerMayorColumna(int f){
		int indice = 0;
		for(indice=0;f>1;indice++)
			if(this.caracteres.get(indice).equals("\n"))
				f--;
		int columna = 1;
		while (!this.caracteres.get(indice).equals("\n")){
			indice++;
			columna ++;
		}
		return columna;
	}

	public int obtenerComienzoLinea(int l){
		int indice = 0;
		while (l>1){
			if (this.caracteres.get(indice).equals("\n"))
				l--;
			indice++;
		}
		return indice;
	}

	public int obtenerFin(int c){
		while(c < this.caracteres.size() && !this.caracteres.get(c).equals("\n"))
			c++;
		return c;
	}
}
