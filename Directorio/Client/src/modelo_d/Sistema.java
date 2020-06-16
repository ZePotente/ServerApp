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

import modelo_d.registro_usuarios.RegistroUsuarios;
import modelo_d.registro_usuarios.UsuariosRegistrados;

import modelo_d.server.Server;

import modelo_d.sincronizacion.Sincronizable;
import modelo_d.sincronizacion.Sincronizador;

public class Sistema implements Observer {
    private static Sistema sistema = null;
    private static final String ARCHIVO_CONFIG = "configuracionD.txt";
    
    private Server sv;
    private RegistroUsuarios usuarios;
    private ManejadorConexiones mancon;
    private Configuracion config;
    private Sincronizable sincronizador;
    
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
        sincronizador = new Sincronizador(config.getIPOtroDir(), config.getPuertoOtroDir(), config.getPuertoSync());
        new Thread() {
            public void run() {
                try {
                    usuarios = sincronizador.pedirUsuarios();
                } catch (IOException e) {
                    System.out.println("No se sincronizo con otro Directorio.");
                }
            }
        }.start();
    }
    
    private void iniciarSv() {
        sv.abrirSv(config.getPuertoResto());
    }
    
    public void agregarUsuario(String nombre, String ip, Socket socket) throws IOException {
        if (!usuarios.isRegistrado(nombre)) {
            usuarios.registrar(nombre, ip);
            System.out.println("Se registro un usuario llamado: " + nombre + ".");
            new Thread() {
                public void run() {
                    try {
                        sincronizador.notificarRegistro(nombre, ip);
                    } catch (IOException e) {
                        System.out.println("No se pudo notificar sincronizacion.");
                    }
                }
            }.start();
        }
        mancon.agregarConexion(nombre, socket);
        usuarios.ponerOnline(nombre);
        System.out.println("Se puso online un usuario llamado: " + nombre + ".");
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    sincronizador.notificarConexion(nombre);
                } catch (IOException e) {
                    System.out.println("No se pudo notificar sincronizacion.");
                } catch (InterruptedException e) {
                }
            }
        }.start();
    }
    
    public void desconectar(ArrayList<String> nombres) {
        usuarios.ponerOffline(nombres);
        new Thread() {
            public void run() {
                try {
                    for (String nombre : nombres) {
                       sincronizador.notificarDesconexion(nombre);
                    }
                } catch (IOException e) {
                    System.out.println("No se pudo notificar sincronizacion.");
                }
            }
        }.start();
    }
    
    public UsuariosRegistrados getListaUsuariosRegistrados() {
        return (UsuariosRegistrados) usuarios;
    }
    
    public RegistroUsuarios sincronizacionUsuarios() {
        return usuarios;
    }
    
    public void notificacionRegistro(String nombre, String ip) {
        this.usuarios.registrar(nombre, ip);
    }
    
    public void notificacionConexion(String nombre) {
        this.usuarios.ponerOnline(nombre);
    }
    
    public void notificacionDesconexion(String nombre) {
        this.usuarios.ponerOffline(nombre);
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

