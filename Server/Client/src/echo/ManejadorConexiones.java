package echo;

import client.Server;

import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * <b>Inv:</b> conexionesAct != null.
 */
public class ManejadorConexiones {
    private Server server;
    private HashMap<String, Conexion> conexionesAct;

    public ManejadorConexiones(Server server) {
        this.server = server;
        this.conexionesAct = new HashMap<String, Conexion>();
        executePeriodUsersRequest();
    }

    public void executePeriodUsersRequest() {
            ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
            es.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        verificarDesconexiones();
                    }
                }, 
                0, 7, TimeUnit.SECONDS);
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

    public void verificarDesconexiones() {
        ArrayList<String> conexiones = conexionesCaidas();
        if (conexiones.size() > 0) { // sino, para que llamarlos
            this.desconectar(conexiones);
            this.notificarDesconexiones();
        }
    }

    // tal vez se le podria avisar de otra manera.
    // crear un hilo aparte o usar otro que ya esta hecho.
    public void notificarDesconexiones() {
        //En realidad Sistema.desconectar(conexionesCaidas())
        //server.ponerOffline(conexionesCaidas());
    }
    
    /**
     * Este metodo verifica las conexiones y devuelve un ArrayList de conexiones caidas.<br>
     * 
     * @return Es distinto de null, a lo sumo devuelve una lista vacia si no hubo desconexiones.
     */
    private ArrayList<String> conexionesCaidas() {
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
     * <b>Pre:</b> conexiones != null. (creo, no se que pasa en el for si es null.
     * 
     * @param conexiones Lista de nombres a desconectar.
     */
    private void desconectar(ArrayList<String> nombres) {
        for(String con : nombres)
            conexionesAct.remove(con);
    }
    
    public void desconectar(String nombre) {
        conexionesAct.remove(nombre);
    }
}
