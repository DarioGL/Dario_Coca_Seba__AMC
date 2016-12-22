/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package P3_AFD_AFND;

/**
 * La interfaz proceso proporciona varios metodos para
 * poder trabajar con los distintos automatas.
 * 
 * @author Fabio IC  Dario GL
 */
public interface Proceso {

    /**
     * Metodo que devuelve verdadero si el estado que
     * recibe por parametro es un estado final.
     * 
     * @param estado estado del que queremos saber si es final o no
     * @return true si el estado es final, false en otro caso 
     */
    public abstract boolean esFinal(int estado); //true si estado es un estado final
    
    /**
     * Metodo que devuelve verdadero si la cadena que
     * recibe por parametro es reconocida por el 
     * automata
     * 
     * @param cadena conjunto de caracteres que pretendemos añadir como secuencia del automata
     * @return true si es una cadena valida, false en otro caso 
     */
    public abstract boolean reconocer(String cadena); //true si la cadena es reconocida
   
    /**
     * Metodo que muestra las transiciones y estados
     * finales del automata que hace la llamada.
     * 
     * @return transiciones del autonama
     */
    @Override
    public abstract String toString();//muestra las transiciones y estados finales
}
