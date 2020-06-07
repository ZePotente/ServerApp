package modelo_d.echo;


import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Inv:</b> conexionesAct != null.
 */
public class Conexiones {
    private HashMap<String, Conexion> conexionesAct;
    
    public Conexiones() {
        conexionesAct = new HashMap<String, Conexion>();
    }
    
    /**
     * Agrega una conexion a la coleccion de conexiones actuales.<br>
     * <b>Pre: </b> nombre != null y socket != null.<br>
     *
     * @param nombre El nombre del usuario en conexion
     * @param socket El socket de la conexion
     * @throws IOException Arroja una IOException si la conexion fue cortada antes de intentar agregarlo.
     */
    public void agregarConexion(String nombre, Socket socket) throws IOException {
        conexionesAct.put(nombre, new Conexion(socket));
    }
    
    /**
     * Este metodo verifica las conexiones y devuelve un ArrayList de conexiones caidas.<br>
     * 
     * @return Es distinto de null, a lo sumo devuelve una lista vacia si no hubo desconexiones.
     */
    public ArrayList<String> conexionesCaidas() {
        ArrayList<String> conexCaidas = new ArrayList<String>();
        for (Map.Entry<String, Conexion> con : conexionesAct.entrySet()) {
            try {
                con.getValue().ping();
            } catch (IOException e) {
                // Se corto la conexion, se agrega a la listade conexiones caidas.
                conexCaidas.add(con.getKey());
            }
        }
        return conexCaidas;
    }
    
    /**
     * Elimina, de la coleccion de usuarios conectados, las conexiones asociadas a los nombres del ArrayList.<br>
     * <b>Pre:</b> nombres != null. (creo, no se que pasa en el for si es null.<br>
     * Aplica desconectar(String nombre) a cada elemento de la coleccion.<br>
     * 
     * @param nombres Lista de nombres a cortarles la conexion.
     */
    public void desconectar(ArrayList<String> nombres) {
        for(String con : nombres)
            desconectar(con);
    }

    /**
     * Elimina, de la coleccion de usuarios conectados, una determinada conexion.<br>
     * <b>Pre:</b> nombre != null.
     * @param nombre
     */
    public void desconectar(String nombre) {
        conexionesAct.remove(nombre);
    }
}
