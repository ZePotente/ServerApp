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
    
    public void avisoDeConexion(String nombre, String ip, Socket socket) {
        if (!usuarios.isRegistrado(nombre)) 
            usuarios.registrar(nombre, ip);   
        mancon.agregarConexion(nombre, socket);
        usuarios.ponerOnline(nombre);
    }
    
    public void desconectar(ArrayList<String> nombres) {
        usuarios.ponerOffline(nombres);
    }
    
    public UsuariosRegistrados getListaUsuariosRegistrados() {
        return usuarios;
    }
}

