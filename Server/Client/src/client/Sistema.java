package client;

import java.io.IOException;

public class Sistema {
    private static Sistema sistema = null;
    
    private final int port = 100;
    private Server sv = null;
    
    public static synchronized Sistema getInstance() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }
    
    private Sistema() {
        sv = new Server();
        iniciarSv();
    }
    
    private void iniciarSv() {
        try {
            sv.abrirSv(port);
        } catch (IOException e) {
            System.out.println("Error al abrir el Directorio. El programa se cerrara (probablemente).");
        }
    }
}

