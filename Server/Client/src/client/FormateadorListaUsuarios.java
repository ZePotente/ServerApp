package client;

import java.util.Map;

public class FormateadorListaUsuarios {
    private static final String SEPARADOR_ATRIBUTOS = "_", SEPARADOR_USUARIOS = "-";
    
    public static String escribeListUsuarios(Map<String, Usuario> usuarios) {
        String retorno = "";
        synchronized(usuarios) { // para iterar se necesita bloquear.
            for (Usuario usuario: usuarios.values()) {
                retorno += usuario.getNombre() + SEPARADOR_ATRIBUTOS + usuario.getNumeroDeIP() + SEPARADOR_ATRIBUTOS + usuario.getEstado() + SEPARADOR_USUARIOS;
            }
        }
        return retorno;
    }
    
    private String pruebaEnvio() {
        return "Lo que sigue es solamente una prueba (cambiar en el Formateador):" +
               "Pepito" + SEPARADOR_ATRIBUTOS + "SuIP" + SEPARADOR_USUARIOS + 
               "Pepote" + SEPARADOR_ATRIBUTOS + "SuIPTambien" + SEPARADOR_USUARIOS;
    }
}
