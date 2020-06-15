package modelo_d.sincronizacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.io.Serializable;

import java.net.Socket;

import java.util.ArrayList;

import modelo_d.registro_usuarios.RegistroUsuarios;

import modelo_d.sincronizacion.notificacion.Notificacion;
import modelo_d.sincronizacion.notificacion.NotificacionConexion;

public class Sincronizador implements Sincronizable {
    //se podria llegar a cambiar por una coleccion de {ip,puerto) si fuera necesario
    private String ipOtroDirectorio;
    private int otroPuerto;
    //
    private final String SEPARADOR = "\n", SEPARADOR_ATRIBUTOS = "-";
    private ServerSync sv;
    public Sincronizador(String nroIP, int otroPuerto, int estePuerto) {
        super();
        this.ipOtroDirectorio = nroIP;
        this.otroPuerto = otroPuerto;
        sv = new ServerSync(estePuerto);
    }
    //
    private void enviarMensaje(String mensaje) throws IOException {
        try (Socket socket = new Socket(ipOtroDirectorio.trim(), otroPuerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);) {
            
            out.write(mensaje);
            if (out.checkError()) {
                throw new IOException();
            }
        } 
    }
    
    
    private String enviarObjetoYRecibirOtro(Serializable objeto) throws IOException {
        try(Socket socket = new Socket(ipOtroDirectorio.trim(), otroPuerto);
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objectOut= new ObjectOutputStream(out);
            InputStream in= socket.getInputStream();
            ObjectInputStream objectIn = new ObjectInputStream(in);) {
            
            
        }
    }
    
    //separacion de responsabilidades
    
    private void notificar(String identificador, String mensaje) throws IOException {
        this.enviarMensaje(identificador + SEPARADOR + mensaje);
    }
    
    public void notificarConexion(String nombre) throws IOException {
        notificar("Notificacion Conexion", nombre);
    }

    @Override
    public void notificarRegistro(String nombre, String ip) throws IOException {
        notificar("Notificacion Registro", nombre + SEPARADOR_ATRIBUTOS + ip);
    }

    @Override
    public void notificarDesconexion(String nombre) throws IOException {
        notificar("Notificacion Desconexion", nombre);
    }
    
    @Override
    public RegistroUsuarios pedirUsuarios(RegistroUsuarios registroActual) throws IOException {
        this.enviarObjetoYRecibirOtro("Peticion Usuarios", registroActual);
    }
}
