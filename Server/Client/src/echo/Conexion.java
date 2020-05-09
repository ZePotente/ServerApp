package echo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.net.Socket;

public class Conexion {
    private Socket socket;
    private OutputStream out;
    
    public Conexion(Socket socket) throws IOException {
        this.socket = socket;
        out = socket.getOutputStream();
    }

    /**
     * Este metodo revisa la conexion.
     * @throws IOException Se lanza si se rompio la conexion.
     */
    public void ping() throws IOException {
        out.write((byte)'\n');
    }
}
