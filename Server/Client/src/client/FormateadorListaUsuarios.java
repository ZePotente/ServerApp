package client;

import java.util.HashMap;

public class FormateadorListaUsuarios {
    private static final String SEPARADOR_ATRIBUTOS = "_", SEPARADOR_USUARIOS = "-";
    
    public static String escribeListUsuarios(HashMap<String, Usuario> usuarios) {
        String retorno = "";
        for (Usuario usuario: usuarios.values()) {
            retorno += usuario.getNombre() + SEPARADOR_ATRIBUTOS + usuario.getNumeroDeIP() + SEPARADOR_ATRIBUTOS + usuario.getEstado() + SEPARADOR_USUARIOS;
        }
        // retorno = pruebaEnvio();
        return retorno;
    }
    
    private String pruebaEnvio() {
        return "Lo que sigue es solamente una prueba (cambiar en el Formateador):" +
               "Pepito" + SEPARADOR_ATRIBUTOS + "SuIP" + SEPARADOR_USUARIOS + 
               "Pepote" + SEPARADOR_ATRIBUTOS + "SuIPTambien" + SEPARADOR_USUARIOS;
    }
}
