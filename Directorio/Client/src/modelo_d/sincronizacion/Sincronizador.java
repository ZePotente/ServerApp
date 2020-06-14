package modelo_d.sincronizacion;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.Socket;

import java.util.ArrayList;

public class Sincronizador implements Sincronizable {
    //se podria llegar a cambiar por una coleccion de {ip,puerto) si fuera necesario
    private String ipOtroDirectorio;
    private int otroPuerto;
    //
    private final String SEPARADOR = "-";
    private ServerSync sv;
    public Sincronizador(String nroIP, int otroPuerto, int estePuerto) {
        super();
        this.ipOtroDirectorio = nroIP;
        this.otroPuerto = otroPuerto;
        sv = new ServerSync(estePuerto);
    }
    
    @Override
    private void notificar(String identificador, String mensaje) {
        Socket socket = new Socket(ip.trim(), puerto);
        OutputStream out = socket.getOutputStream();
        
        
        out.write(identificador + SEPARADOR + mensaje);
        try {
            socket.close();
        } catch (IOException e) {
            //ya se mando igualmente
        }
    }
    
    public void notificarConexion(String nombre) {
        Notificacion noti = new NotificacionConexion(nombre);
        noti.notificar(ipOtroDirectorio, otroPuerto);
    }

    @Override
    public void notificarDesconexiones(ArrayList<String> nombres) {
        // TODO Implement this method
    }

    @Override
    public void notificarRegistro() {
        //tal vez si el registro fuera mas que dar el nombre habria que agregar otra cosa
        // TODO Implement this method
    }

    @Override
    public void pedirUsuarios() {
        // TODO Implement this method
    }
}
