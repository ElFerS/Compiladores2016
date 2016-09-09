package org.unicen.compiladores.acciones;

import java.awt.List;

import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.Simbolo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionRetornarDouble implements Accion {

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		// TODO Auto-generated method stub
		if (c!=null)
            a.retrocederIndice();
        Double d = obtenerDouble(t.obtenerLexema());
        if (validarDouble(d)){
            t.setearNombre("Double");            
            ((javax.swing.table.DefaultTableModel)(jTableTokens.getModel())).addRow(new String[] {t.obtenerNombre(),t.obtenerLexema()});
            st.agregarSimbolo(new Simbolo("Double",d.toString()),jTableTS);
        }
        else
            listErrores.add("Error en línea "+a.obtenerLineaActual()+": double fuera de rango.");
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
        //TODO la funcion Math.exp(exp) devuelve e**exp, que no da el resultado esperado, CAMBIAR
        return new Double(d * Math.exp(exp));
    }
 
    public boolean validarDouble(Double d){
        double limiteInferior = 2.2250738585072014 * Math.exp(-308);
        double limiteSuperior = 1.7976931348623157 * Math.exp(308);
        return (   Math.abs(d.doubleValue()) > limiteInferior
                && Math.abs(d.doubleValue()) < limiteSuperior
               );
    }
}
