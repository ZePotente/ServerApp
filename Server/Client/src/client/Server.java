package client;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static ServerSocket server;
    private static Map<String, Usuario> usuarios = Collections.synchronizedMap(new HashMap<>());
    
    public Server() {
        
    }
    
    public void abrirSv(int port) throws IOException {
        new Thread() {
            public void run() {
                    server = new ServerSocket(port);
                    while (true) {
                        Socket socket = server.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String identificador = in.readLine();
                        if (identificador.equalsIgnoreCase("AvisoConexion")) {
                            avisoConexion(in);
                        } else if (identificador.equalsIgnoreCase("RequestReceptores")) {
                            requestReceptores(out);
                        }
                        socket.close();
                    }
            }
        }.start();
    }
    private void avisoConexion(BufferedReader in) throws IOException {
        String aux;
        // Leo el nombre del usuario
        aux = in.readLine();
        Usuario usuario = usuarios.get(aux);
        
        if (usuario != null) { // Ya esta agregado, solo debo modificar su estado
            // Leo la IP del usuario que en este caso no se utiliza
            aux = in.readLine();
            // Leo el estado del usuario
            aux = in.readLine();
            usuario.setEstado(aux.equalsIgnoreCase("true"));
        } else { // No esta agregado, debo agregarlo
            usuarios.put(aux, new Usuario(aux, in.readLine(), true));
        }
    }
    
    private void requestReceptores(PrintWriter out) {
        out.println(FormateadorListaUsuarios.escribeListUsuarios(usuarios));
        // out.flush();// no es necesario porque tiene autoflush
    }

    public void desconectarUsuarios(ArrayList<String> nombres) {
        new Thread() {
            public void run() {
                ponerOffline(nombres);
            }
        }.start();
    }
    
    /**
     * Desconecta a los usuarios cuyo nombre este en la lista.<br>
     * 
     * <b>Pre:</b> nombres != null, y ninguna componente suya es null.
     * 
     * @param nombres Lista de nombres a poner como offline.
     */
    public void ponerOffline(ArrayList<String> nombres) {
        for(String nom : nombres) {
            ponerOffline(nom);
        }
    }

    /**
     * Establece el estado de un Usuario como desconectado.
     * <b>Pre:</b> nombre != null
     * 
     * @param nombre El nombre del usuario
     */
    private void ponerOffline(String nombre) {
        usuarios.get(nombre).setEstado(false);
    }

    /**Establece el estado de un Usuario como conectado.
     * <b>Pre:</b> nombre != null
     * 
     * @param nombre El nombre del usuario
     * @param nombre
     */
    private void ponerOnline(String nombre) {
        usuarios.get(nombre).setEstado(true);
    }
}

