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
    private static final int port = 100;
    
    private ServerSocket server;
    private UsuariosRegistrados usuarios;
    private ManejadorConexiones mancon;
    
    public Server() {
        usuarios = new UsuariosRegistrados();
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
                        // en realidad Sistema.avisoDeConexion(socket)
                        avisoDeConexion(socket, in);
                    } else if (identificador.equalsIgnoreCase("RequestReceptores")) {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        requestReceptores(out);
                    }
                    if (!identificador.equalsIgnoreCase("RequestReceptores"))
                        try {
                            socket.close();
                        } catch (IOException e) {
                            //Error al cerrar el socket
                        }
                    else {
                        // mandaron cualquier cosa
                    }
                }
            }
        }.start();
    }
    
    private void avisoDeConexion(Socket socket, BufferedReader in) throws IOException {
        String nombre, ip;
        // Leo el nombre e ip del usuario
        nombre = in.readLine();
        ip = in.readLine(); //se usa si no esta registrado
        if (usuarios.isRegistrado(nombre)) {
            // Leo el estado del usuario
            String estado = in.readLine();
            boolean conectar = estado.equalsIgnoreCase("true");
            if (conectar) { 
                usuarios.ponerOnline(nombre);
                // agregar conexion
            } else { 
                // this.ponerOffline(nombre);
            }
        }
        else { //registrar
            this.registrar(nombre, ip);
            this.co
        }
    }
    
    private void requestReceptores(PrintWriter out) {
        out.println(FormateadorListaUsuarios.escribeListUsuarios(usuarios.getMap()));
        // out.flush();// no es necesario porque tiene autoflush
    }
    
    /*
    public void desconectarUsuarios(ArrayList<String> nombres) {
        new Thread() { // habria que ver de tener ya un hilo y correrlo.
            public void run() {
                ponerOffline(nombres);
            }
        }.start();
    }
    */
    
    
}

