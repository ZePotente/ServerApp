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
    private static int port = 123;
    private static FormateadorListaUsuarios formateador = new FormateadorListaUsuarios();
    
    public SocketServer() {
        super();
    }
    
    public static void main(String[] args) throws IOException, ClassNotFoundException {
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
                                usuario.setEstado(aux.equalsIgnoreCase("online"));
                            } else { // No esta agregado, debo agregarlo
                                usuarios.put(aux, new Usuario(aux, objectIn.readLine(), true));
                            }
                        } else if (identificador.equalsIgnoreCase("RequestReceptores")) {
                            // Leo la IP del emisor que pide la lista de receptores
                            aux = objectIn.readLine();
                            enviarListaUsuarios(aux);
                        }
                        socket.close();
                        out.close();
                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
        }.start();
    }
    
    public static void enviarListaUsuarios(String nroIP) throws UnknownHostException, IOException {
        Socket socket = new Socket(nroIP.trim(), 1234);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("probando");
        out.close();
        socket.close();
    }
}

