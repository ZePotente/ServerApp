package client;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {    
    private ServerSocket server;
    
    public Server() {
    }
    
    public void abrirSv(int port) {
        new Thread() {
            public void run() {
                try {
                    server = new ServerSocket(port);
                    while (true) {
                        Socket socket = server.accept();
                        // identificacion
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String identificador = in.readLine();
                        
                        if (identificador.equalsIgnoreCase("AvisoConexion")) {
                            try {
                                avisoDeConexion(socket);
                            } catch (IOException e) {
                                System.out.println("Error al procesar una conexion al servidor.");
                            }
                        } else if (identificador.equalsIgnoreCase("RequestReceptores")) {
                            try {
                                requestReceptores(socket);
                            } catch (IOException e) {
                                System.out.println("Error al obtener los receptores registrados.");
                            }
                        }
                        else {
                            // mandaron cualquier cosa
                        }
                    
                    }
                }catch (IOException e) {
                    System.out.println("Error al crear el servidor.");
                    System.out.println("Por favor reinicie la aplicacion.");
                }
            }
        }.start();
    }
    
    private void avisoDeConexion(Socket socket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String nombre, ip, estado;
        nombre = in.readLine(); // leo el noombre del receptor
        ip = in.readLine();     // leo la ip
        
        try {
        Sistema.getInstance().agregarUsuario(nombre, ip, socket);
        }
        catch (IOException e){
            System.out.println("Error. El usuario fue registrado correctamente, pero se perdio la conexion.");
        }
    }
    
    private void requestReceptores(Socket socket) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        UsuariosRegistrados usuarios;
        String lista;
        
        usuarios = Sistema.getInstance().getListaUsuariosRegistrados();
        lista = FormateadorListaUsuarios.escribeListUsuarios(usuarios);
        
        out.println(lista);
        socket.close();
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

