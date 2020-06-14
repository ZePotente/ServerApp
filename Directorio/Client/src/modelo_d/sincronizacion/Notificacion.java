package modelo_d.sincronizacion;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Notificacion {
    protected final String SEPARADOR_ATRIBUTOS = "-";
    public Notificacion() {
        super();
    }
    
    public final void notificar(String ip, int puerto) throws UnknownHostException, IOException {
        Socket socket = new Socket(ip.trim(), puerto);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String identificador = getIdentificador();
        String msg = getFormato();
        
        out.println(msg);
        socket.close();
    }
    
    protected abstract String getFormato();
    protected abstract String getIdentificador();
}
