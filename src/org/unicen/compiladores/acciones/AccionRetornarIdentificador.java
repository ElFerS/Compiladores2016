package org.unicen.compiladores.acciones;

import java.awt.List;
import javax.swing.JTable;

import org.unicen.compiladores.estructuras.Archivo;
import org.unicen.compiladores.estructuras.PalabrasReservadas;
import org.unicen.compiladores.estructuras.Simbolo;
import org.unicen.compiladores.estructuras.TablaSimbolos;
import org.unicen.compiladores.lexico.Token;

public class AccionRetornarIdentificador implements Accion {
	
	private PalabrasReservadas palRes;
	
	public AccionRetornarIdentificador(PalabrasReservadas pr){
		palRes = pr;
	}

	@Override
	public void ejecutar(String c, Token t, Archivo a, JTable jTableTokens, List listErrores, JTable jTableTS,
			TablaSimbolos st) {
		if (c!=null)
            a.retrocederIndice(); 
		
        validarIdentificador(t,a,listErrores);
        
        if (palRes.esPalabrareservada(t.obtenerLexema())){
            t.setearNombre("P. Reservada");
            ((javax.swing.table.DefaultTableModel)(jTableTokens.getModel())).addRow(new String[] {t.obtenerNombre(),t.obtenerLexema()});
        }
        else{
            t.setearNombre("Identificador");
            ((javax.swing.table.DefaultTableModel)(jTableTokens.getModel())).addRow(new String[] {t.obtenerNombre(),t.obtenerLexema()});
            st.agregarSimbolo(new Simbolo("Identificador",t.obtenerLexema()),jTableTS);
        }                       
    }
    
    public void validarIdentificador(Token t,Archivo a, List listErrores){
        if (t.obtenerLexema().length()>20){
            t.setearLexema(t.obtenerLexema().substring(0,20));
            listErrores.add("Warning en línea "+a.obtenerLineaActual()+": identificador truncado en 20 caracteres.");
        }
    }

}
