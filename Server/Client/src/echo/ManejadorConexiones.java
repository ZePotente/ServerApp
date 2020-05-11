package echo;

import client.Sistema;

import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ManejadorConexiones {
    private Conexiones conexionesAct;

    public ManejadorConexiones() {
        conexionesAct = new Conexiones();
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
                0, 5, TimeUnit.SECONDS);
        }

    public void verificarDesconexiones() {
        ArrayList<String> conexiones = conexionesAct.conexionesCaidas();
        if (conexiones.size() > 0) { // sino, para que llamarlos
            conexionesAct.desconectar(conexiones);
            this.notificarDesconexiones();
        }
    }

    // tal vez se le podria avisar de otra manera.
    // crear un hilo aparte o usar otro que ya esta hecho.
    public void notificarDesconexiones() {
        Sistema.getInstance().desconectar(conexionesAct.conexionesCaidas());
    }
    
    public void agregarConexion(String nombre, Socket socket) throws IOException {
        synchronized(conexionesAct) {
            conexionesAct.agregarConexion(nombre, socket);
        }
    }
}
