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

public class SocketServer {
    private static ServerSocket server;
    private static HashMap<String, Usuario> usuarios = new HashMap<>();
    private static FormateadorListaUsuarios formateador = new FormateadorListaUsuarios();
    
    public SocketServer() {
        super();
    }
    
    public void abrirSv(int port) throws IOException, ClassNotFoundException {
        new Thread() {
            public void run() {
                try {
                    server = new ServerSocket(port);
                    while (true) {
                        Socket socket = server.accept();
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader objectIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String aux;
                        String identificador = objectIn.readLine();
                        if (identificador.equalsIgnoreCase("AvisoConexion")) {
                            // Leo el nombre del usuario
                            aux = objectIn.readLine();
                            Usuario usuario = usuarios.get(aux);
                            // Ya esta agregado, solo debo modificar su estado
                            if (usuario != null) {
                                // Leo la IP del usuario que en este caso no se utiliza
                                aux = objectIn.readLine();
                                // Leo el estado del usuario
                                aux = objectIn.readLine();
                                usuario.setEstado(aux.equalsIgnoreCase("true"));
                            } else { // No esta agregado, debo agregarlo
                                usuarios.put(aux, new Usuario(aux, objectIn.readLine(), true));
                            }
                        } else if (identificador.equalsIgnoreCase("RequestReceptores")) {
                            System.out.println("imprimiendo lista.");
                            out.println(formateador.escribeListUsuarios(usuarios));
                            System.out.println("lista impresa.");
                        }
                        out.close();
                        socket.close();
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
        }.start();
    }
}

