package echo;

import java.io.PrintWriter;

import java.net.Socket;

import java.util.HashMap;
import java.util.Map;

public class ManejadorConexiones extends Thread {
    HashMap<String, Socket> conexiones;

    public ManejadorConexiones() {
        this.conexiones = new HashMap<String, Socket>();
    }
    
    public void agregarConexion(String nombre, Socket socket) {
        
        conexiones.put(nombre, socket);
    }
    
    public void revisarConexiones() {
        for (Map.Entry<String, Socket> var : conexiones.entrySet()) {
            //var.
            PrintWriter out = new PrintWriter();
            out.write("a");
                
        }
    }
}
