package practica3;

/**
 * Clase TransionAFD que contiene los métodos para la utilización de la clase
 *
 * @author Sergio Rodriguez Garcia 
 */
public class TransicionAFD {

	private int estado1, estado2;
	private char simbolo;

        /**
         * Este metodo es el constructor de la clase transionAFD y recibe por 
         * parametro el origen, el simbolo y el destino.
         * 
         * @param estado1: estado origen
         * @param simbolo: simbolo de la transion
         * @param estado2: estado destino
         */
	public TransicionAFD(int estado1, char simbolo, int estado2) {
		this.estado1 = estado1;
		this.simbolo = simbolo;
		this.estado2 = estado2;
	}

        /**
         * Método que devuelve el elemento origen de la transión,
         * @return 
         */
	public int getEstadoOrigen() {
		return estado1;
	}

        /**
         * Método que devuelve el elemento destino de la transión
         * @return 
         */
	public int getEstadoDestino() {
		return estado2;
	}

        /**
         * Método que devuelve el simbolo de la transición
         * @return 
         */
	public char getSimbolo() {
		return simbolo;
	}
}
