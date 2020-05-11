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
                0, 7, TimeUnit.SECONDS);
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
        //En realidad Sistema.desconectar(conexionesCaidas())
        //server.ponerOffline(conexionesCaidas());
    }
}
