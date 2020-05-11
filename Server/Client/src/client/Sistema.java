package client;

import java.io.IOException;

public class Sistema {
    private static final int PUERTO = 100;
    private static Sistema sistema = null;
    
    private Server sv;
    private UsuariosRegistrados usuarios;
    
    public static synchronized Sistema getInstance() {
        if (sistema == null) {
            sistema = new Sistema();
        }
        return sistema;
    }
    
    private Sistema() {
        sv = new Server();
        iniciarSv();
        usuarios = new UsuariosRegistrados();
    }
    
    private void iniciarSv() {
        try {
            sv.abrirSv(Sistema.PUERTO);
        } catch (IOException e) {
            System.out.println("Error al abrir el Directorio. El programa se cerrara (probablemente).");
        }
    }
    
    
    
}

