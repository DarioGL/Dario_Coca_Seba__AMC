package P3_AFD_AFND;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * La clase AFD contendrá los métodos necesarios para la ejecución del primer
 * apartado de la practica: Automata Finito Determinista.
 *
 * @author Fabio IC Dario GL
 */
public class AFD implements Cloneable, Proceso {

    private ArrayList<Integer> estadosFinales;
    private ArrayList<TransicionAFD> transiciones;

    /**
     * Constructor de la clase AFD donde se declaran dos tablas hash, Una de
     * para estados finales y otra para transiciones.
     */
    public AFD() {
        estadosFinales = new ArrayList<>();
        transiciones = new ArrayList<>();
    }

    /**
     * Este metodo se utiliza para cargar un fichero, pasado por parametro, que
     * contendrá los posibles estados finales, iniciales y transiciones con las
     * que trabajara nuestro AFD.
     *
     * @param ruta direccion del fichero que vamos a leer para cargar el AFD contenido en el
     * @throws Exception error en caso de no poder abrir el fichero
     */
    public void cargarAFD(String ruta) throws Exception {
        BufferedReader br;
        String linea;
        String[] partes, estadosF, estadosI, transi;
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
                        estadosFinales = new ArrayList<>();

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

                    case "t": {
                        int origen = Integer.parseInt(partes[1]);
                        char simbolo = partes[2].charAt(0);
                        int destino = Integer.parseInt(partes[3]);
                        anadirTransicion(origen, simbolo, destino);
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
     * parametros el origen, el simbolo de dicha transion y el destino al que
     * lleva.
     *
     * @param estadoOrigen estado inicial del AFD
     * @param simbolo variable que determina el siguiente estado al que vamos de nuestro AFD
     * @param estadodestino estado final del AFD
     * @throws Exception error si no puede añadir una transicion al AFD
     */
    public void anadirTransicion(int estadoOrigen, char simbolo, int estadodestino) throws Exception {
        TransicionAFD transicion = new TransicionAFD(estadoOrigen, simbolo, estadodestino);
        transiciones.add(transicion);
    }

    /**
     * Este método se encarga de añadir estados finales recogidos por teclado Se
     * le pasa por parametro un entero y se añade a la tabla de estados finales.
     *
     * @param estadoFinal estado final del AFD
     * @throws Exception error si no se puede añadir un estado final
     */
    public void anadirEstadoFinal(int estadoFinal) throws Exception {
        estadosFinales.add(estadoFinal);
    }

    /**
     * Este método se utiliza para saber si un entero es un estado final
     * Devuelve true si el entero está en la tabla de estados finales y false si
     * no se encuentra en dicha tabla.
     *
     * @param estado estado del AFD por el que preguntamos si es final
     * @return true si el estado es final, false en otro caso
     */
    @Override
    public boolean esFinal(int estado) {
        return estadosFinales.contains(estado);
    }

    /**
     * Este método recibe por parametros un entero 'origen' y un char 'simbolo'
     * y comprueba si se trata de una transición, en cuyo caso devuelve el
     * entero del estado destino.
     *
     * @param estado origen para realizar la comprobacion si esa transicion existe o no
     * @param simbolo variable para ir al siguiente estado del AFD
     * @return estado destino partiendo del origen y con el simbolo pasado
     */
    public int transicion(int estado, char simbolo) {
        Iterator<TransicionAFD> it = transiciones.iterator();
        TransicionAFD t;

        while (it.hasNext()) {
            t = it.next();

            if (t.getEstadoOrigen() == estado && t.getSimbolo() == simbolo) {
                return t.getEstadoDestino();
            }
        }

        return -1;
    }

    /**
     * Este método recibe por parametro una cadena de entero y se encarga de
     * comprobar si dicha cadena cumple las condiciones del AFD y se puede
     * construir.
     *
     * @param cadena transicion del AFD en forma de cadena de texto
     * @return verdadero si la cadena es correcta, falso en otro caso
     */
    @Override
    public boolean reconocer(String cadena) {
        char[] simbolo = cadena.toCharArray();
        int estado = 0;

        for (int i = 0; i < simbolo.length; i++) {
            estado = transicion(estado, simbolo[i]);

            if (estado == -1) {
                return false;
            }
        }

        return esFinal(estado);
    }

    /**
     * Este método que devuelve una copia binaria de los atributos de la clase
     * para poder trabajar con ellos sin riesgo a modificar los datos originales
     *
     * @return copia binaria del objeto indicado
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {

        AFD obj = null;

        try {
            obj = (AFD) super.clone();
        } catch (Exception e) {

        }
        obj.estadosFinales = (ArrayList<Integer>) obj.estadosFinales.clone();
        obj.transiciones = (ArrayList<TransicionAFD>) obj.transiciones.clone();

        return obj;
    }

    /**
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena la
     * transicion del elemento: el estado origen, el simbolo y el estado destino
     * al que llega.
     *
     * @param pos posicion a buscar en la lista de transiciones
     * @return devuelve el estado origen, el simbolo y el estado destino
     */
    public String getCadenaTransicion(int pos) {
        Iterator<TransicionAFD> it = transiciones.iterator();
        TransicionAFD t = null;
        for (int i = 0; i <= pos; i++) {
            t = it.next();
        }
        return t.getEstadoOrigen() + " > " + t.getSimbolo() + " > " + t.getEstadoDestino();
    }

    /**
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena el
     * estado final que representa.
     *
     * @param pos  posicion a buscar en la lista de transiciones
     * @return estado final de la transicion
     */
    public String getCadenaEstadosF(int pos) {
        return estadosFinales.get(pos) + "";
    }

    /**
     * Este método que devuelve el tamaño del array transiciones.
     *
     * @return devuelve numero de transiciones
     */
    public int getNumTransiciones() {
        return transiciones.size();
    }

    /**
     * Este método que devuelve el tamaño del array estadosFinales.
     *
     * @return devuelve numero de estados finales
     */
    public int getNumEstadosF() {
        return estadosFinales.size();
    }
}