package client;

import java.util.HashMap;

public class FormateadorListaUsuarios {
    public FormateadorListaUsuarios() {
        super();
    }
    
    public String escribeListUsuarios(HashMap<String, Usuario> usuarios) {
        String retorno = "";
        for (Usuario usuario: usuarios.values()) {
            retorno += usuario.getNombre() + "-" + usuario.getNumeroDeIP() + "\n";
        }
        return retorno;
    }
}
