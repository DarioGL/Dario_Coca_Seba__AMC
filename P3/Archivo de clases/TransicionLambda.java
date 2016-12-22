package practica3;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Clase Transion Lambda que contiene los métodos necesarios para la utilización la clase
 * 
 * @author Sergio Rodriguez Garcia
 */
public class TransicionLambda {

	private int estado1;
	private ArrayList<Integer> estado2;

        /**
         * Constructor de la clase transion lambda que recibe por parametros 
         * el estado origen y una tabla de los estados destinos
         * 
         * @param estado1
         * @param estado2 
         */
	public TransicionLambda(int estado1, ArrayList<Integer> estado2) {
		this.estado1 = estado1;
		this.estado2 = estado2;
	}

        /**
         * Método que devuelve el elemento origen de la transión
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
}
