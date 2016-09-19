package org.unicen.compiladores.estructuras;

import java.awt.List;
import java.util.HashMap;

import javax.swing.JTable;

public class TablaSimbolos {

	private HashMap<String, Simbolo> tSimbol;

	public TablaSimbolos() {
		tSimbol=new HashMap<String, Simbolo>();
	}
	public Simbolo buscarSimbolo(Simbolo s){

		return this.tSimbol.get(s.getNombre()+s.getValor());
	}

	public void agregarSimbolo(Simbolo s, JTable ts){

		Simbolo s1 = this.buscarSimbolo(s);

		if (s1 == null){
			this.tSimbol.put(s.getNombre()+s.getValor(),s);
			if (ts!= null){
				javax.swing.table.DefaultTableModel modelo = ((javax.swing.table.DefaultTableModel)(ts.getModel()));
				int salida = 0;
				for(int i=0;i < modelo.getRowCount() && salida==0;i++){
					if (modelo.getValueAt(i,0).equals(s.getNombre())&&modelo.getValueAt(i,1).equals(s.getValor()))
						salida = 1;
				}
				if (salida == 0)
					((javax.swing.table.DefaultTableModel)(ts.getModel())).addRow(new String[] {s.getNombre(),s.getValor()});    
			}
		}
		else this.buscarSimbolo(s).sumarReferencia();  // sumo a la cantidad de refencia q tiene ese simbolo
	}

	public void borrarsimbolo(Simbolo s){
		
		if ( this.tSimbol.containsKey(s.getNombre()+s.getValor()) ) {
	        
            if (s.getContadorReferencias() == 1)
                this.tSimbol.remove(s);
            else
                s.restarReferencias();
            }
	}
	
	public void limpiar(){
		this.tSimbol.clear();
	}

	public Double obtenerDouble(String s) {
		double d = 0;
		double exp = 0;
		if (s.toUpperCase().contains("D")){
			d = Double.valueOf(s.substring(0,s.toUpperCase().indexOf("D")));
			if (s.contains("+"))
				exp = Double.valueOf(s.substring(s.toUpperCase().indexOf("D")+2,s.length()));
			else if(s.contains("-"))
				exp = Double.valueOf(s.substring(s.toUpperCase().indexOf("D")+2,s.length())) * -1;
			else
				exp = Double.valueOf(s.substring(s.toUpperCase().indexOf("D")+1,s.length()));
		}
		else
			d = Double.valueOf(s);
		System.out.println("d: "+d+"  EXP:"+ exp);
		return new Double(d * Math.exp(exp));
	}

	public void tratarDoubleNegativo(String d){
		Double d1 = this.obtenerDouble(d);
		Simbolo s = new Simbolo("Double",d1.toString());
		this.borrarsimbolo(s);
		s.setValor(String.valueOf(d1.doubleValue()*-1));
		this.agregarSimbolo(s, null);
	}

	public void tratarEnteroNegativo(Integer d){
		this.borrarsimbolo(new Simbolo("Entero",new Integer(d*(-1)).toString()));
		this.agregarSimbolo(new Simbolo("Entero",d.toString()), null);
	}

	public void tratarEnteroPositivo(Integer d,List e, int l, JTable tsLex){
		if (Double.valueOf(d).equals(Math.pow(2, 15))){
			this.borrarsimbolo(new Simbolo("Entero",d.toString()));
			javax.swing.table.DefaultTableModel modelo = ((javax.swing.table.DefaultTableModel)(tsLex.getModel()));
			modelo.removeRow(modelo.getRowCount()-1);
			e.add("Línea "+l+". Entero fuera de rango");
		}
	}
	
	public String toString(){
		String tabla = null;

		for (String key : tSimbol.keySet()){
			tabla+=" "+tSimbol.get(key);
		}
		return tabla;
	}
	public void imprimir(JTable ts){
        
        for (String key : tSimbol.keySet()){
          Simbolo s = (Simbolo) tSimbol.get(key);
          ((javax.swing.table.DefaultTableModel)(ts.getModel())).addRow(new String[] {s.getNombre(),s.getValor()});
        }
	}
}
