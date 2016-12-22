package P3_AFD_AFND;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * La clase AFD contendrá los métodos necesarios para la ejecución del segundo
 * apartado de la practica: Automata Finito No Determinista.
 *
 * @author Fabio IC Dario GL
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
        estadosFinales = new ArrayList<>();
        transiciones = new ArrayList<>();
        transicionesLambda = new ArrayList<>();
    }

    /**
     * Este método se utiliza para cargar un fichero, pasado por parametro, que
     * contendrá los posibles estados finales, iniciales, transiciones y
     * transiones lambda con las que trabajara nuestro AFND.
     *
     * @param nombreFichero ruta direccion del fichero que vamos a leer para cargar el AFND contenido en el
     * @throws Exception error si no se encuentra el fichero en la ruta indicada
     */
    public void cargarAFND(String nombreFichero) throws Exception {
        BufferedReader br;
        String linea;
        String[] partes, estadosI, estadosF, estados, estadoslambda, transi;
        br = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separatorChar + nombreFichero));
        System.out.println("El fichero " + nombreFichero + " contiene: ");

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
                        ArrayList<Integer> destinos = new ArrayList<>();

                        for (int i = 0; i < estados.length; i++) {
                            destinos.add(Integer.valueOf(estados[i]));
                        }

                        anadirTransicion(origen, simbolo, destinos);
                    }
                    break;
                    case "tl": {
                        int origen = Integer.parseInt(partes[1]);
                        estados = partes[2].trim().split(",");
                        ArrayList<Integer> destinos = new ArrayList<>();

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
     * @param estadoOrigen estado del que parte la transicion
     * @param simbolo simbolo que identifica el paso de un estado a otro
     * @param estadoDestino estado final de la transicion
     * @throws Exception error en caso de no poder haber añadido la transicion
     * al AFND
     */
    public void anadirTransicion(int estadoOrigen, char simbolo, ArrayList<Integer> estadoDestino) throws Exception {
        TransicionAFND transicion = new TransicionAFND(estadoOrigen, simbolo, estadoDestino);
        transiciones.add(transicion);
    }

    /**
     * Este método se encarga de añadir nuevas transiciones lambda. Recibe por
     * parametros el origen y una tabla de los destinos a los que puede llegar.
     * A diferencia del metodo anterior, las transiciones lambda no contienen
     * simbolo.
     *
     * @param estadoOrigen estado del que parte la transicion
     * @param estadosDestino lista de estados a los que puede llegar la
     * transicion
     * @throws Exception error en caso de no poder haber añadido la transicion
     */
    public void anadirTransicionLambda(int estadoOrigen, ArrayList<Integer> estadosDestino) throws Exception {
        TransicionLambda transicion = new TransicionLambda(estadoOrigen, estadosDestino);
        transicionesLambda.add(transicion);
    }

    /**
     * Este método se encarga de añadir estados finales recogidos por teclado Se
     * le pasa por parametro un entero y se añade a la tabla de estados finales.
     *
     * @param estadoFinal añade el estado a la lista de estados final
     * @throws Exception error en caso de no poder haber sido añadido a la lista
     * de estados finales
     */
    public void anadirEstadoFinal(int estadoFinal) throws Exception {
        estadosFinales.add(estadoFinal);
    }

    /**
     * Este método se utiliza para saber si un estado es final
     *
     * @param estado estado que añadiremos a la lista de estados finales
     * @return true si el estado es final, false en otro caso
     */
    @Override
    public boolean esFinal(int estado) {
        return estadosFinales.contains(estado);
    }

    /**
     * Este método se utiliza para saber si una tabla de estados contiene algun
     * estado que es final.
     *
     * @param estados lista de estados que comprobaremos si contiene algun
     * estado final
     * @return true en caso de contener un estado final el array pasado por
     * parametro, false en otro caso
     */
    public boolean esFinal(ArrayList<Integer> estados) {
        Iterator<Integer> itEstados = estados.iterator();

        while (itEstados.hasNext()) {
            if (esFinal(itEstados.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Este método recibe por parametros un origen y un simbolo y comprueba si
     * se trata de una transición, en cuyo caso devuelve un array con los
     * posibles estados destino.
     *
     * @param estadoOrigen estado origen de la transicion que estamos buscando
     * @param simbolo simbolo que identifica la transicion que estamos buscando
     * @return array vacio en caso de no ser una transicion posible o un array
     * de destinos si hay transiciones posibles
     */
    public ArrayList<Integer> transicion(int estadoOrigen, char simbolo) {
        Iterator<TransicionAFND> itTransiciones = transiciones.iterator();
        TransicionAFND transicionAFND;

        while (itTransiciones.hasNext()) {
            transicionAFND = itTransiciones.next();
            if (transicionAFND.getEstadoOrigen() == estadoOrigen && transicionAFND.getSimbolo() == simbolo) {
                return new ArrayList<>(transicionAFND.getEstadosDestino());
            }
        }
        return new ArrayList<>();
    }

    /**
     * Este metodo recibe un array de estados iniciales y un simbolo y comprueba
     * si forman una transicion, en cuyo caso añade la transicion a un nuevo
     * array y se lo pasa al metodo lambdaClausura
     *
     * @param macroestado lista de estados en la que comprobaremos si es posible
     * la transicion
     * @param simbolo simbolo que identifica la transicion que estamos buscando
     * @return el array de estados con lambdaClausura aplicado para saber a cual
     * de ellos podemos ir desde el estado
     */
    public ArrayList<Integer> transicion(ArrayList<Integer> macroestado, char simbolo) {
        ArrayList<Integer> resultado = new ArrayList<>();
        Iterator<Integer> itMacroEstado = macroestado.iterator();

        while (itMacroEstado.hasNext()) {
            resultado.addAll(transicion(itMacroEstado.next(), simbolo));
        }
        return lambdaClausura(resultado);
    }

    /**
     * Este metodo devuelve un array con todos las transiciones lambda que tiene
     * como destino el estado origen que recibe por parametro
     *
     * @param estado estado inicial que comprobaremos para saber que
     * transiciones lambda contiene
     * @return vacío en caso de no poder ir a ningun destino, en caso contrario
     * devuelve los estados destinos a los que podemos ir desde este
     */
    public ArrayList<Integer> transicionLambda(int estado) {
        Iterator<TransicionLambda> it = this.transicionesLambda.iterator();
        TransicionLambda transicionLambda;

        while (it.hasNext()) {
            transicionLambda = it.next();
            if (transicionLambda.getEstadoOrigen() == estado) {
                return new ArrayList<>(transicionLambda.getEstadosDestino());
            }
        }
        return new ArrayList<>();
    }

    /**
     * Le aplicamos lambdaClausura al array de macroestados que recibe por
     * parametro para saber a cuantos estados podemos acceder sin consumir
     * ningun simbolo de nuestra cadena
     *
     * @param macroestado lista de estados a los que le aplicaremos
     * lambdaClausura
     * @return estados a los que podemos acceder sin gastar ningun simbolo
     */
    public ArrayList<Integer> lambdaClausura(ArrayList<Integer> macroestado) {
        ArrayList<Integer> resultado = new ArrayList<>(macroestado);
        ArrayList<Integer> auxMacroestados = new ArrayList<>(macroestado);
        ArrayList<Integer> iteracion = new ArrayList<>();
        Iterator<TransicionLambda> itLambda = this.transicionesLambda.iterator();
        TransicionLambda transicionLambda;
        boolean seguir = true;

        while (seguir) {
            while (itLambda.hasNext()) {
                transicionLambda = itLambda.next();
                if (auxMacroestados.contains(transicionLambda.getEstadoOrigen())) {
                    iteracion.addAll(transicionLambda.getEstadosDestino());
                }
            }
            itLambda = this.transicionesLambda.iterator();
            resultado.addAll(iteracion);
            auxMacroestados.clear();
            auxMacroestados.addAll(iteracion);
            seguir = !iteracion.isEmpty();
            iteracion.clear();
        }

        return resultado;
    }

    /**
     * Este método recibe por parametro una cadena de números enteros y se
     * encarga de comprobar si dicha cadena cumple las condiciones del AFND
     *
     * @param cadena cadena que tiene que comprobar si existe en nuestro AFND
     * @return true en caso de ser una cadena reconocida por el AFND, false en
     * otro caso
     */
    @Override
    public boolean reconocer(String cadena) {
        char[] listaSimbolos = cadena.toCharArray();
        ArrayList<Integer> listaEstados = new ArrayList<>();
        listaEstados.add(0);
        listaEstados = lambdaClausura(listaEstados);

        for (int i = 0; i < listaSimbolos.length; i++) {
            listaEstados = transicion(listaEstados, listaSimbolos[i]);
            if (listaEstados.isEmpty()) {
                return false;
            }
        }
        return esFinal(listaEstados);
    }

    /**
     * Este método que devuelve una copia binaria de los atributos de la clase
     * para poder trabajar con ellos sin riesgo a modificar los datos originales
     *
     * @return copia exacta del objeto que lo invoca
     * @throws java.lang.CloneNotSupportedException lanzada en caso de daro un
     * error en la copia del objeto
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
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
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena la
     * transicion del elemento: el estado origen, el simbolo y los posibles
     * estados destinos a los que llega.
     *
     * @param pos posicion en la que se encuentra el elemento que queremos
     * buscar
     * @return String de transiciones a las que llegamos desde un estado dado
     */
    public String getCadenaTransicion(int pos) {
        Iterator<TransicionAFND> it = transiciones.iterator();
        TransicionAFND t = null;
        String eDestinos = "";

        for (int i = 0; i <= pos; i++) {
            t = it.next();
        }
        for (int i = 0; i < t.getEstadosDestino().size(); i++) {
            eDestinos += t.getEstadosDestino().get(i) + ",";
        }
        return t.getEstadoOrigen() + " > " + t.getSimbolo() + " > " + eDestinos;
    }

    /**
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena la
     * transicion lambda del elemento: el estado origen y los posibles estados
     * destinos a los que llega.
     *
     * @param pos posicion en la que se encuentra el elemento que queremos
     * buscar
     * @return String de transiciones lambda que llegamos desde el estado dado
     */
    public String getCadTransLambda(int pos) {
        Iterator<TransicionLambda> it = transicionesLambda.iterator();
        TransicionLambda t = null;
        String eDestinos = "";

        for (int i = 0; i <= pos; i++) {
            t = it.next();
        }

        for (int i = 0; i < t.getEstadosDestino().size(); i++) {
            eDestinos += t.getEstadosDestino().get(i) + ",";
        }
        return t.getEstadoOrigen() + " > λ >" + eDestinos;
    }

    /**
     * Este método recibe por parametro la posicion del elemento que queremos
     * buscar dentro del array. Una vez encontrado devuelve en una cadena el
     * estado final que representa.
     *
     * @param pos posicion en la que se encuentra el elemento buscar
     * @return estado final en una cadena
     */
    public String getCadenaEstadosF(int pos) {
        return estadosFinales.get(pos) + "";
    }

    /**
     * Devuelve el número de transiciones que contiene el AFND.
     *
     * @return número de transiciones
     */
    public int getNumTransiciones() {
        return transiciones.size();
    }

    /**
     * Devuelve el número de transiciones lambda que contiene el AFND.
     *
     * @return número de transiciones lambda
     */
    public int getNumTransicionesLambda() {
        return transicionesLambda.size();
    }

    /**
     * Devuelve el número de estados finales.
     *
     * @return número de estados finales
     */
    public int getNumEstadosFinales() {
        return estadosFinales.size();
    }
}
