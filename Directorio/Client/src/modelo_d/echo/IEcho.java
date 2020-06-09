package modelo_d.echo;

import java.io.IOException;

import java.net.Socket;

import java.util.ArrayList;

public interface IEcho {
    public void notificarDesconexiones(ArrayList<String> conexiones);
    public void agregarConexion(String nombre, Socket socket) throws IOException;
}
