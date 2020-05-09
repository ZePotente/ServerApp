package echo;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManejadorConexiones extends Thread {
    HashMap<String, Conexion> conexiones;

    public ManejadorConexiones() {
        this.conexiones = new HashMap<String, Conexion>();
    }

    /**
     * Agrega una conexion a la coleccion de conexiones actuales.<br>
     * <b>Pre: </b> nombre != null y socket != null.<br>
     * 
     * @param nombre El nombre de el usuario en conexion
     * @param socket El socket de la conexion
     * @throws IOException Arroja una IOException si la conexion fue cortada antes de intentar agregarlo.
     */
    public void agregarConexion(String nombre, Socket socket) throws IOException {
        conexiones.put(nombre, new Conexion(socket));
    }
    
    private ArrayList<String> conexionesCaidas() {
        ArrayList<String> conexCaidas = new ArrayList<String>();
        for (Map.Entry<String, Conexion> con : conexiones.entrySet()) {
            con.getValue().
                
        }
    }
}
