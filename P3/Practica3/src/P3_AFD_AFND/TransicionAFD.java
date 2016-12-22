package P3_AFD_AFND;

/**
 * Clase TransionAFD que contiene los métodos para la utilización de la clase
 *
 * @author Fabio IC  Dario GL
 */
public class TransicionAFD {

	private final int estadoOrigen, estadoDestino;
	private final char simbolo;

        /**
         * Este metodo es el constructor de la clase transionAFD y recibe por 
         * parametro el origen, el simbolo y el destino.
         * 
         * @param estadoOrigen estado origen donde empieza el AFD
         * @param simbolo simbolo de la transion para avanzar a la siguiente
         * @param estadoDestino estado destino donde llega la transicion
         */
	public TransicionAFD(int estadoOrigen, char simbolo, int estadoDestino) {
		this.estadoOrigen = estadoOrigen;
		this.simbolo = simbolo;
		this.estadoDestino = estadoDestino;
    //hola k ase
	}

        /**
         * Método que devuelve el elemento origen de la transión,
         * @return el estado origen de la transicion
         */
	public int getEstadoOrigen() {
		return estadoOrigen;
	}

        /**
         * Método que devuelve el elemento destino de la transión
         * @return el estado destino de la transicion
         */
	public int getEstadoDestino() {
		return estadoDestino;
	}

        /**
         * Método que devuelve el simbolo de la transición
         * @return simbolo que se necesita para desplazarnos en estra transicion
         */
	public char getSimbolo() {
		return simbolo;
	}
}
