package practica3;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Clase Transion AFND que contiene los métodos necesarios para la utilización clase
 * @author Sergio Rodriguez Garcia
 */
public class TransicionAFND {
	private int estado1;
	private ArrayList<Integer> estado2;
	private char simbolo;

        /**
         * Constructor de la clase transion AFND que recibe por parametros 
         * el estado origen, el simbolo de la transición y una tabla de los estados destinos
         * 
         * @param estado1
         * @param simbolo
         * @param estado2 
         */
	public TransicionAFND(int estado1, char simbolo, ArrayList<Integer> estado2) {
		this.estado1 = estado1;
		this.simbolo = simbolo;
		this.estado2 = estado2;
	}

        /**
         * Método que devuelve el elemento origen de la transición
         * @return 
         */
	public int getEstadoOrigen() {
		return estado1;
	}

        /**
         * Método que devuelve los elementos destinos de la transición
         * @return 
         */
	public ArrayList<Integer> getEstadosDestino() {
		return (ArrayList<Integer>) estado2.clone();
	}

        /**
         * Método que devuelve el simbolo de la transición
         * @return 
         */
	public char getSimbolo() {
		return simbolo;
	}
}
