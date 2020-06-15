package modelo_d.sincronizacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.io.OutputStream;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Observable;

import modelo_d.Sistema;

import modelo_d.registro_usuarios.RegistroUsuarios;
import modelo_d.registro_usuarios.UsuariosRegistrados;

public class ServerSync implements IServerSync {
    private int puerto;
    private ServerSocket sv;
    public ServerSync(int puerto) {
        super();
        this.puerto = puerto;
        abrirSv(puerto);
    }
    
    private void abrirSv(int puerto) {
        new Thread() {
            public void run() {
                try {
                    sv = new ServerSocket(puerto);
                    while (true) {
                        Socket socket = sv.accept();
                        System.out.println("Se recibio una notificacion de sincronizacion.");
                        InputStream is = socket.getInputStream();
                        ObjectInputStream ois = new ObjectInputStream(is);
                        OutputStream os = socket.getOutputStream();
                        ObjectOutputStream oos = new ObjectOutputStream(os);
                        
                        System.out.println("HOLA");
                        String identificador = ois.readUTF();
                        System.out.println(identificador);
                        try {
                            if (identificador.equals(PETICION_USUARIOS)) {
                                RegistroUsuarios usuarios = Sistema.getInstance().sincronizacionUsuarios();
                                System.out.println("Por enviar objeto.");
                                oos.writeObject(usuarios);
                                System.out.println("Objeto enviado.");
                            }else{
                                Notificacion noti = (Notificacion) ois.readObject();
                                System.out.println(noti.getAtributos().get(0));
                                //get(0) es el nombre, get(1) la ip, si estuviera
                                if (identificador.equals(NOTIF_REGISTRO)) {
                                    Sistema.getInstance().notificacionRegistro(noti.getAtributos().get(0), noti.getAtributos().get(1));
                                }else if (identificador.equals(NOTIF_CONEXION)) {
                                    Sistema.getInstance().notificacionConexion(noti.getAtributos().get(0));
                                }else if (identificador.equals(NOTIF_DESCONEXION)) {
                                    Sistema.getInstance().notificacionDesconexion(noti.getAtributos().get(0));
                                }
                            }
                        } catch (ClassNotFoundException e) {
                                System.out.println("Se leyo un objeto que no era el que se esperaba.");
                        }
                    }
                }catch (IOException e) {
                    System.out.println("Error al abrir el servidor de sincronizacion.");
                    System.out.println("Por favor reinicie la aplicacion.");
                }
            }
        }.start();
    }
}
