package client;

import echo.ManejadorConexiones;

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
    private ServerSocket server;
    private static Map<String, Usuario> usuarios = Collections.synchronizedMap(new HashMap<>());
    private ManejadorConexiones mancon;
    
    public Server() {
        mancon = new ManejadorConexiones(this);
    }
    
    public void abrirSv(int port) throws IOException {
        new Thread() {
            public void run() {
                server = new ServerSocket(port);
                while (true) {
                    Socket socket = server.accept();
                    // identificacion
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String identificador = in.readLine();
                    if (identificador.equalsIgnoreCase("AvisoConexion")) {
                        avisoDeConexion(socket, in);
                    } else if (identificador.equalsIgnoreCase("RequestReceptores")) {
                        requestReceptores(out);
                    }
                    if (!identificador.equalsIgnoreCase("RequestReceptores"))
                        try {
                            socket.close();
                        } catch (IOException e) {
                            //Error al cerrar el socket
                        }
                }
            }
        }.start();
    }
    /*
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
    */
    private void avisoDeConexion(Socket socket, BufferedReader in) throws IOException {
        String nombre, ip;
        // Leo el nombre e ip del usuario
        nombre = in.readLine();
        ip = in.readLine(); //se usa si no esta registrado
        if (isRegistrado(nombre)) {
            // Leo el estado del usuario
            String estado = in.readLine();
            boolean conectar = estado.equalsIgnoreCase("true");
            if (conectar) { 
                this.ponerOnline(nombre);
                
            } else { 
                this.ponerOffline(nombre);
            }
        }
        else { //registrar
            this.registrar(nombre, ip);
            this.co
        }
    }
    
    private void requestReceptores(PrintWriter out) {
        out.println(FormateadorListaUsuarios.escribeListUsuarios(usuarios));
        // out.flush();// no es necesario porque tiene autoflush
    }
    
    private boolean isRegistrado(String nombre) {
        Usuario us = usuarios.get(nombre);
        return us != null;
    }
    
    private void registrar(String nombre, String ip) {
        usuarios.put(nombre, new Usuario(nombre, ip, false));
    }
    
    public void desconectarUsuarios(ArrayList<String> nombres) {
        new Thread() { // habria que ver de tener ya un hilo y correrlo.
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

