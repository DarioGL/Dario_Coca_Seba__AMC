package P3_AFD_AFND;

import java.util.ArrayList;

/**
 * Clase Transion AFND que contiene los métodos necesarios para la utilización clase
 * @author Fabio IC  Dario GL
 */
public class TransicionAFND {
	private final int estadoOrigen;
	private final ArrayList<Integer> estadoDestino;
	private final char simbolo;

        /**
         * Constructor de la clase transion AFND que recibe por parametros 
         * el estado origen, el simbolo de la transición y una tabla de los estados destinos
         * 
         * @param estadoOrigen estado origen donde empieza el AFD
         * @param simbolo simbolo de la transion para avanzar a la siguiente
         * @param estadoDestino estado destino donde llega la transicion
         */
	public TransicionAFND(int estadoOrigen, char simbolo, ArrayList<Integer> estadoDestino) {
		this.estadoOrigen = estadoOrigen;
		this.simbolo = simbolo;
		this.estadoDestino = estadoDestino;
	}

        /**
         * Método que devuelve el elemento origen de la transición
         * @return el estado origen de la transicion
         */
	public int getEstadoOrigen() {
		return estadoOrigen;
	}

        /**
         * Método que devuelve los elementos destinos de la transición
         * @return el estado destino de la transicion
         */
	public ArrayList<Integer> getEstadosDestino() {
		return (ArrayList<Integer>) estadoDestino.clone();
	}

        /**
         * Método que devuelve el simbolo de la transición
         * @return simbolo que se necesita para desplazarnos en estra transicion
         */
	public char getSimbolo() {
		return simbolo;
	}
}
