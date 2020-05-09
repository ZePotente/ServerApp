package echo;

import client.Server;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManejadorConexiones extends Thread {
    Server server;
    HashMap<String, Conexion> conexiones;

    public ManejadorConexiones(Server server) {
        this.server = server;
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

    public void notificarDesconexiones() {
        server.desconectar(nombresDesc);
    }
    
    /**
     * Este metodo devuelve un ArrayList de conexiones caidas.<br>
     * Ademas elimina estas conexiones de la coleccion de conexiones.<br>
     * 
     * @return Es distinto de null, a lo sumo devuelve una lista vacia.
     */
    private ArrayList<String> conexionesCaidas() {
        ArrayList<String> conexCaidas = new ArrayList<String>();
        for (Map.Entry<String, Conexion> con : conexiones.entrySet()) {
            try {
                con.getValue().ping();
            } catch (IOException e) {
                // Se corto la conexion, se agrega a la lista y se lo borra de las conexiones
                conexCaidas.add(con.getKey());
                conexiones.remove(con.getKey());
            }
        }
        return conexCaidas;
    }
}
