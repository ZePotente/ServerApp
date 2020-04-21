package client;

import java.io.IOException;

public class Sistema {
    private static Sistema sistema = null;
    
    private final int port = 100;
    private SocketServer sv = null;
    
    public static synchronized Sistema getInstance() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }
    
    private Sistema() {
        sv = new SocketServer();
        iniciarSv();
    }
    
    private void iniciarSv() {
        try {
            sv.abrirSv(port);
        } catch (ClassNotFoundException | IOException e) {
        }
    }
}
