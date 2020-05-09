package echo;

import java.io.IOException;
import java.io.PrintWriter;

import java.net.Socket;

public class Conexion {
    private Socket socket;
    private PrintWriter out;
    
    public Conexion(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void ping() {
        try {
            out.write((byte)'\n');
        }
        catch (IOException e){
            
        }
    }
}
