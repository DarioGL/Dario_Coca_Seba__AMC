/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica3;

/**
 * La interfaz proceso proporciona varios metodos para
 * poder trabajar con los distintos automatas.
 * 
 * @author Sergio Rodriguez Garcia
 */
public interface Proceso {

    /**
     * Metodo que devuelve verdadero si el estado que
     * recibe por parametro es un estado final.
     * 
     * @param estado
     * @return 
     */
    public abstract boolean esFinal(int estado); //true si estado es un estado final
    
    /**
     * Metodo que devuelve verdadero si la cadena que
     * recibe por parametro es reconocida por el 
     * automata
     * 
     * @param cadena
     * @return 
     */
    public abstract boolean reconocer(String cadena); //true si la cadena es reconocida
   
    /**
     * Metodo que muestra las transiciones y estados
     * finales del automata que hace la llamada.
     * 
     * @return 
     */
    public abstract String toString();//muestra las transiciones y estados finales
}
