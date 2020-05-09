package client;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;

import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

import java.net.UnknownHostException;

import java.util.HashMap;
import java.util.HashSet;

public class Server {
    private static ServerSocket server;
    private static HashMap<String, Usuario> usuarios = new HashMap<>();
    
    public Server() {
        
    }
    
    public void abrirSv(int port) throws IOException {
        new Thread() {
            public void run() {
                    server = new ServerSocket(port);
                    while (true) {
                        Socket socket = server.accept();
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
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
}

