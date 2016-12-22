package P3_AFD_AFND;

import java.util.ArrayList;

/**
 * Clase Transion Lambda que contiene los métodos necesarios para la utilización la clase
 * 
 * @author Fabio IC  Dario GL
 */
public class TransicionLambda {

	private final int estadoOrigen;
	private final ArrayList<Integer> estadosDestino;

        /**
         * Constructor de la clase transion lambda que recibe por parametros 
         * el estado origen y una tabla de los estados destinos
         * 
         * @param estadoOrigen estado inicial del automata
         * @param estadosDestino lista de los estados destino a los que podemos ir segun las opciones que decidamos
         */
	public TransicionLambda(int estadoOrigen, ArrayList<Integer> estadosDestino) {
		this.estadoOrigen = estadoOrigen;
		this.estadosDestino = estadosDestino;
	}

        /**
         * Método que devuelve el elemento origen de la transión
         * @return estado origen de una transicion
         */
	public int getEstadoOrigen() {
		return estadoOrigen;
	}

        /**
         * Método que devuelve los elementos destinos de la transición
         * @return los estados destinos de una transicion lambda
         */
	public ArrayList<Integer> getEstadosDestino() {
		return (ArrayList<Integer>) estadosDestino.clone();
	}
}
