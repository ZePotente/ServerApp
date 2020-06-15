package modelo_d;

import configuracion.Configuracion;

import configuracion.LectorConfiguracion;

import configuracion.NoLecturaConfiguracionException;

import modelo_d.echo.ManejadorConexiones;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.Socket;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import modelo_d.registro_usuarios.UsuariosRegistrados;

import modelo_d.server.Server;

public class Sistema implements Observer {
    private static final int PUERTO = 100;
    private static Sistema sistema = null;
    private static final String ARCHIVO_CONFIG = "configuracionD.txt";
    
    private Server sv;
    private UsuariosRegistrados usuarios;
    private ManejadorConexiones mancon;
    private Configuracion config;
    
    public static synchronized Sistema getInstance() {
        if (sistema == null) {
            try {
                sistema = new Sistema();
            } catch (NoLecturaConfiguracionException e) {
                System.out.println("Error al leer la configuracion.");
                System.out.println("Por favor reinicie el Directorio.");
            }
        }
        return sistema;
    }
    
    private Sistema() throws NoLecturaConfiguracionException {
        config = LectorConfiguracion.leerConfig(ARCHIVO_CONFIG);
        sv = new Server();
        iniciarSv();
        usuarios = new UsuariosRegistrados();
        mancon = new ManejadorConexiones();
        mancon.addObserver(this);
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

    @Override
    public void update(Observable o, Object arg) {
        if (o != mancon) {
            throw new IllegalArgumentException(); //progra 3
        }
        ArrayList<String> nombres = (ArrayList<String>) arg;
        desconectar(nombres);
    }
}

