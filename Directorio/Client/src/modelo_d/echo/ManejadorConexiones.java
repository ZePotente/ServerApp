package modelo_d.echo;

import modelo_d.Sistema;

import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ManejadorConexiones extends Observable {
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
        System.out.println("Verificando conexiones.");
        ArrayList<String> conexiones = conexionesAct.conexionesCaidas();
        if (conexiones.size() > 0) { // sino, para que llamarlos
            System.out.println("Hay desconexiones nuevas.");
            conexionesAct.desconectar(conexiones);
            notificarDesconexiones(conexiones);
        }
    }
    
    // tal vez se le podria avisar de otra manera.
    // crear un hilo aparte o usar otro que ya esta hecho,
    // para que no tenga que ser el timer el que lo haga
    /*
    public void notificarDesconexiones(ArrayList<String> conexiones) {
        Sistema.getInstance().desconectar(conexiones);
    }
    
    */
    public void notificarDesconexiones(ArrayList<String> conexiones) {
        setChanged();
        notifyObservers(conexiones);
    }
    
    public void agregarConexion(String nombre, Socket socket) throws IOException {
        synchronized(conexionesAct) {
            conexionesAct.agregarConexion(nombre, socket);
        }
    }
    
    
}
