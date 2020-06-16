package modelo_d.sincronizacion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

import modelo_d.Sistema;

import modelo_d.registro_usuarios.RegistroUsuarios;


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
             OutputStream os = socket.getOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)){
            
            oos.writeUTF(mensaje);
        }
    }
    
    private void notificar(String identificador, Notificacion mensaje) throws IOException {
        try (Socket socket = new Socket(ipOtroDirectorio.trim(), otroPuerto);
             OutputStream os = socket.getOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeUTF(identificador);
            oos.flush();
            oos.writeObject(mensaje);
            oos.flush();
        }
    }
    
    @Override
    public void notificarRegistro(String nombre, String ip) throws IOException {
        Notificacion noti = new Notificacion(nombre, ip);
        notificar(IServerSync.NOTIF_REGISTRO, noti);
    }
    
    @Override
    public void notificarConexion(String nombre) throws IOException {
        notificar(IServerSync.NOTIF_CONEXION, new Notificacion(nombre));
    }

    @Override
    public void notificarDesconexion(String nombre) throws IOException {
        notificar(IServerSync.NOTIF_DESCONEXION, new Notificacion(nombre));
    }
    
    @Override
    public RegistroUsuarios pedirUsuarios() throws IOException {
        RegistroUsuarios usuarios;
        try (Socket socket = new Socket(ipOtroDirectorio.trim(), otroPuerto);
             OutputStream os = socket.getOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os);
             InputStream is = socket.getInputStream();
             ObjectInputStream ois = new ObjectInputStream(is)){
            String identificador = IServerSync.PETICION_USUARIOS;
            oos.flush();
            oos.writeUTF(identificador);
            oos.flush();
            System.out.println("Peticion enviada.");
            usuarios = (RegistroUsuarios) ois.readObject();
            System.out.println("Peticion recibida.");
            
        } catch (ClassNotFoundException e) {
            System.out.println("Se leyo un objeto que no era el que se esperaba.");
            throw new IOException (e);
        }
        return usuarios;
    }
}
