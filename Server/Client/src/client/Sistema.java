package client;

import echo.ManejadorConexiones;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.Socket;

import java.util.ArrayList;

public class Sistema {
    private static final int PUERTO = 100;
    private static Sistema sistema = null;
    
    private Server sv;
    private UsuariosRegistrados usuarios;
    private ManejadorConexiones mancon;
    
    public static synchronized Sistema getInstance() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }
    
    private Sistema() {
        sv = new Server();
        iniciarSv();
        usuarios = new UsuariosRegistrados();
        mancon = new ManejadorConexiones();
    }
    
    private void iniciarSv() {
        sv.abrirSv(Sistema.PUERTO);
    }
    
    public void agregarUsuario(String nombre, String ip, Socket socket) throws IOException {
        if (!usuarios.isRegistrado(nombre)) 
            usuarios.registrar(nombre, ip);
            System.out.println("Se registro un usuario llamado: " + nombre + ".");
        mancon.agregarConexion(nombre, socket);
        usuarios.ponerOnline(nombre);
        System.out.println("Se puso online un usuario llamado: " + nombre + ".");
    }
    
    public void desconectar(ArrayList<String> nombres) {
        usuarios.ponerOffline(nombres);
    }
    
    public UsuariosRegistrados getListaUsuariosRegistrados() {
        return usuarios;
    }   
}

