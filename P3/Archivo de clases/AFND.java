package practica3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase AFD contendrá los métodos necesarios para la ejecución del segundo
 * apartado de la practica: Automata Finito No Determinista.
 *
 * @author Sergio Rodriguez Garcia
 */
public class AFND implements Cloneable, Proceso {

    private ArrayList<Integer> estadosFinales;
    private ArrayList<TransicionAFND> transiciones;
    private ArrayList<TransicionLambda> transicionesLambda;

    /**
     * Constructor de la clase AFND donde se declaran tres tablas hash, Una de
     * para estados finales, otra para transiciones y otra para transiciones
     * lambda.
     */
    public AFND() {
        estadosFinales = new ArrayList<Integer>();
        transiciones = new ArrayList<TransicionAFND>();
        transicionesLambda = new ArrayList<TransicionLambda>();
    }

    /**
     * Este método se utiliza para cargar un fichero, pasado por parametro, que
     * contendrá los posibles estados finales, iniciales, transiciones y
     * transiones lambda con las que trabajara nuestro AFND.
     *
     * @param ruta
     * @throws Exception
     */
    public void cargarAFND(String ruta) throws Exception {
        BufferedReader br;
        String linea;
        String[] partes, estadosI, estadosF, estados, estadoslambda, transi, letras;
        br = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separatorChar + ruta));
        System.out.println("El fichero " + ruta + " contiene: ");

        try {
            while ((linea = br.readLine()) != null) {
                partes = linea.trim().split(":");

                switch (partes[0]) {
                    case "ei": {
                        estadosI = partes[1].trim().split(",");

                        System.out.println("Estados Iniciales: ");
                        for (int i = 0; i < estadosI.length; i++) {
                            System.out.print("" + estadosI[i] + ",");
                        }
                        System.out.println("");
                    }
                    break;

                    case "ef": {
                        estadosF = partes[1].trim().split(",");
                        estadosFinales = new ArrayList<Integer>();

                        System.out.println("Estados Finales: ");
                        for (int i = 0; i < estadosF.length; i++) {
                            System.out.print("" + estadosF[i] + ",");
                            estadosFinales.add(Integer.valueOf(estadosF[i]));
                        }
                        System.out.println("");
                    }
                    break;

                    case "trans": {
                        transi = partes[1].trim().split(",");

                        System.out.println("Transiciones: ");
                        for (int i = 0; i < transi.length; i++) {
                            System.out.print("(" + transi[i] + "),");
                        }
                        System.out.println("");
                    }
                    break;

                    case "tlambda": {
                        estadoslambda = partes[1].trim().split(",");

                        System.out.println("Transiciones Lambda: ");
                        for (int i = 0; i < estadoslambda.length; i++) {
                            System.out.print("" + estadoslambda[i] + ",");
                        }
                        System.out.println("");
                    }
                    break;

                    case "t": {
                        int origen = Integer.parseInt(partes[1]);
                        char simbolo = partes[2].charAt(0);
                        estados = partes[3].trim().split(",");
                        ArrayList<Integer> destinos = new ArrayList<Integer>();

                        for (int i = 0; i < estados.length; i++) {
                            destinos.add(Integer.valueOf(estados[i]));
                        }

                        anadirTransicion(origen, simbolo, destinos);
                    }
                    break;
                    case "tl": {
                        int origen = Integer.parseInt(partes[1]);
                        estados = partes[2].trim().split(",");
                        ArrayList<Integer> destinos = new ArrayList<Integer>();

                        for (int i = 0; i < estados.length; i++) {
                            destinos.add(Integer.valueOf(estados[i]));
                        }

                        anadirTransicionLambda(origen, destinos);
                    }
                    break;
                    default: {
                        throw new Exception("ERROR FORMATO DE FICHERO");
                    }
                }
            }
        } finally {
            br.close();
        }
    }

    /**
     * Este método se encarga de añadir nuevas transiciones Recibe por
     * parametros el origen, el simbolo de dicha transion y una tabla de los
     * destinos a los que puede llegar.
     *
     * @param estado1
     * @param simbolo
     * @param estado2
     * @throws Exception
     */
    public void anadirTransicion(int estado1, char simbolo, ArrayList<Integer> estado2) throws Exception {
        TransicionAFND transicion = new TransicionAFND(estado1, simbolo, estado2);
        transiciones.add(transicion);
    }

    /**
     * Este método se encarga de añadir nuevas transiciones lambda Recibe por
     * parametros el origen y una tabla de los destinos a los que puede llegar A
     * diferencia del metodo anterior, las transiciones lambda no contienen
     * simbolo.
     *
     * @param estado1
     * @param estado2
     * @throws Exception
     */
    public void anadirTransicionLambda(int estado1, ArrayList<Integer> estado2) throws Exception {
        TransicionLambda transicion = new TransicionLambda(estado1, estado2);
        transicionesLambda.add(transicion);
    }

    /**
     * Este método se encarga de añadir estados finales recogidos por teclado Se
     * le pasa por parametro un entero y se añade a la tabla de estados finales.
     *
     * @param estadoFinal
     * @throws Exception
     */
    public void anadirEstadoFinal(int estadoFinal) throws Exception {
        estadosFinales.add(estadoFinal);
    }

    /**
     * Este método se utiliza para saber si un entero es un estado final
     * Devuelve true si el entero está en la tabla de estados finales y false si
     * no se encuentra en dicha tabla.
     *
     * @param estado
     * @return
     */
    public boolean esFinal(int estado) {
        return estadosFinales.contains(estado);
    }

    /**
     * Este método se utiliza para saber si una tabla de estados contiene algun
     * estado que es final.
     *
     * @param estados
     * @return
     */
    public boolean esFinal(ArrayList<Integer> estados) {
        Iterator<Integer> it = estados.iterator();

        while (it.hasNext()) {
            if (esFinal(it.next())) {
                return true;
            }

        }

        return false;
    }

    /**
     * Este método recibe por parametros un entero 'origen' y un char 'simbolo'
     * y comprueba si se trata de una transición, en cuyo caso devuelve un array
     * con los posibles estados destino.
     *
     * @param estado
     * @param simbolo
     * @return
     */
    public ArrayList<Integer> transicion(int estado, char simbolo) {
        Iterator<TransicionAFND> it = transiciones.iterator();
        TransicionAFND t;

        while (it.hasNext()) {
            t = it.next();

            if (t.getEstadoOrigen() == estado && t.getSimbolo() == simbolo) {
                return new ArrayList<Integer>(t.getEstadosDestino());
            }
        }

        return new ArrayList<Integer>();
    }

    /**
     * Este metodo recibe un array de estados iniciales y un char 'simbolo' y
     * comprueba si forman una transicion en cuyo caso añade la transicion a un
     * nuevo array y se lo pasa al metodo lambdaClausura
     *
     * @param macroestado
     * @param simbolo
     * @return
     */
    public ArrayList<Integer> transicion(ArrayList<Integer> macroestado, char simbolo) {
        ArrayList<Integer> resultado = new ArrayList<Integer>();
        Iterator<Integer> it = macroestado.iterator();

        while (it.hasNext()) {
            resultado.addAll(transicion(it.next(), simbolo));
        }

        return lambdaClausura(resultado);
    }

    /**
     * Este metodo devuelve un array con todos las transiciones lambda que tiene
     * como destino el estado origen que recibe por parametro
     *
     * @param estado
     * @return
     */
    public ArrayList<Integer> transicionLambda(int estado) {
        Iterator<TransicionLambda> it = transicionesLambda.iterator();
        TransicionLambda tl;

        while (it.hasNext()) {
            tl = it.next();

            if (tl.getEstadoOrigen() == estado) {
                return new ArrayList<Integer>(tl.getEstadosDestino());
            }
        }

        return new ArrayList<Integer>();
    }

    /**
     * Le aplicamos lambdaClausura al array de macroestados que recibe por
     * parametro
     *
     * @param macroestado
     * @return
     */
    public ArrayList<Integer> lambdaClausura(ArrayList<Integer> macroestado) {
        ArrayList<Integer> resultado = new ArrayList<Integer>(macroestado);
        ArrayList<Integer> aux = new ArrayList<Integer>(macroestado);
        ArrayList<Integer> iteracion = new ArrayList<Integer>();
        Iterator<TransicionLambda> it = transicionesLambda.iterator();
        TransicionLambda tl;
        boolean seguir = true;

        while (seguir) {
            while (it.hasNext()) {
                tl = it.next();

                if (aux.contains(tl.getEstadoOrigen())) {
                    iteracion.addAll(tl.getEstadosDestino());
                }
            }

            it = transicionesLambda.iterator();
            resultado.addAll(iteracion);
            aux.clear();
            aux.addAll(iteracion);
            seguir = !iteracion.isEmpty();
            iteracion.clear();
        }

        return resultado;
    }

    /**
     * Este método recibe por parametro una cadena de entero y se encarga de
     * comprobar si dicha cadena cumple las condiciones del AFD y se puede
     * construir.
     *
     * @param cadena
     * @return
     */
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        ArrayList<Integer> estado = new ArrayList<Integer>();
        estado.add(0);
        estado = lambdaClausura(estado);

        for (int i = 0; i < simbolo.length; i++) {
            estado = transicion(estado, simbolo[i]);

            if (estado.size() == 0) {
                return false;
            }
        }

        return esFinal(estado);
    }

    /**
     * Este método que devuelve una copia binaria de los
     * atributos de la clase para poder trabajar con ellos
     * sin riesgo a modificar los datos originales
     *
     * @return
     */
    @Override
    public Object clone() {

        AFND obj = null;

        try {
            obj = (AFND) super.clone();
        } catch (Exception e) {

        }
        obj.estadosFinales = (ArrayList<Integer>) obj.estadosFinales.clone();
        obj.transiciones = (ArrayList<TransicionAFND>) obj.transiciones.clone();
        obj.transicionesLambda = (ArrayList<TransicionLambda>) obj.transicionesLambda.clone();

        return obj;
    }

    /**
     * METODOS ADICIONALES PROPIOS NECESARIOS PARA LA REALIZACION EN MODO
     * GRAFICO.
     */
    
    /**
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena la
     * transicion del elemento: el estado origen, el simbolo y los posibles
     * estados destinos a los que llega.
     *
     * @param pos
     * @return
     */
    public String getCadenaTransicion(int pos) {

        Iterator<TransicionAFND> it = transiciones.iterator();
        TransicionAFND t = null;

        String cad = new String();
        String eDestinos = new String();

        for (int i = 0; i <= pos; i++) {
            t = it.next();
        }

        for (int i = 0; i < t.getEstadosDestino().size(); i++) {
            eDestinos += t.getEstadosDestino().get(i) + ",";
        }

        cad = t.getEstadoOrigen() + " > " + t.getSimbolo() + " > " + eDestinos;

        return cad;
    }

    /**
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena la
     * transicion lambda del elemento: el estado origen y los posibles estados
     * destinos a los que llega.
     *
     * @param pos
     * @return
     */
    public String getCadTransLambda(int pos) {
        Iterator<TransicionLambda> it = transicionesLambda.iterator();
        TransicionLambda t = null;

        String cad = new String();
        String eDestinos = new String();

        for (int i = 0; i <= pos; i++) {
            t = it.next();
        }

        for (int i = 0; i < t.getEstadosDestino().size(); i++) {
            eDestinos += t.getEstadosDestino().get(i) + ",";
        }

        cad = t.getEstadoOrigen() + " > λ >" + eDestinos;

        return cad;
    }

    /**
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena el
     * estado final que representa.
     *
     * @param pos
     * @return
     */
    public String getCadenaEstadosF(int pos) {

        String cad = new String();

        cad = estadosFinales.get(pos) + "";

        return cad;
    }

    /**
     * Este método que devuelve el tamaño del array transiciones.
     *
     * @return
     */
    public int getNumTransiciones() {
        return transiciones.size();
    }

    /**
     * Este método que devuelve el tamaño del array transicionesLambda.
     *
     * @return
     */
    public int getNumTransicionesLambda() {
        return transicionesLambda.size();
    }

    /**
     * Este método que devuelve el tamaño del array estadosFinales.
     *
     * @return
     */
    public int getNumEstadosF() {
        return estadosFinales.size();
    }
}
