package modelo_d.sincronizacion;

import java.util.Observable;

public class ServerSync extends Observable {
    private int puerto;
    
    public ServerSync(int puerto) {
        super();
        this.puerto = puerto;
        abrirSv(puerto);
    }
    
    private void abrirSv(int puerto) {
        
    }
}
