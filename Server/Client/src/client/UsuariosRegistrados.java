package client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UsuariosRegistrados {
    private static Map<String, Usuario> usuarios;
    
    public UsuariosRegistrados() {
        usuarios = Collections.synchronizedMap(new HashMap<>());
    }
    
    public Map<String, Usuario> getMap() {
        return usuarios;   
    }
    
    public boolean isRegistrado(String nombre) {
        Usuario us = usuarios.get(nombre);
        return us != null;
    }

    /**
     * Se registra un usuario nuevo en la lista y se lo pone <b><i>offline</i></b>.
     * @param nombre
     * @param ip
     */
    public void registrar(String nombre, String ip) {
        usuarios.put(nombre, new Usuario(nombre, ip, false));
    }
    
    /**
     * Establece como <i>offline</i> el estado de los usuarios cuyo nombre este en la lista.<br>
     * 
     * <b>Pre:</b> nombres != null, y ninguna componente suya es null.
     * 
     * @param nombres Lista de nombres a poner como <i>offline</i>.
     */
    public void ponerOffline(ArrayList<String> nombres) {
        for(String nom : nombres) {
            ponerOffline(nom);
        }
    }

    /**
     * Establece el estado de un Usuario como <i>offline</i>.<br>
     * <b>Pre:</b> nombre != null
     * 
     * @param nombre El nombre del usuario
     */
     public void ponerOffline(String nombre) {
        usuarios.get(nombre).setEstado(false);
        System.out.println("Se puso offline un usuario llamado:" + nombre + ".");
    }


    /**
     * Establece como <i>online</i> el estado de los usuarios cuyo nombre este en la lista.<br>
     * 
     * <b>Pre:</b> nombres != null, y ninguna componente suya es null.
     * @param nombres Lista de nombres a poner como <i>offline</i>
     */
    public void ponerOnline(ArrayList<String> nombres) {
        for(String nom : nombres) {
            ponerOnline(nom);
        }
    }
    
    /**Establece el estado de un Usuario como conectado.<br>
     * <b>Pre:</b> nombre != null
     * 
     * @param nombre El nombre del usuario
     */
     public void ponerOnline(String nombre) {
        usuarios.get(nombre).setEstado(true);
    }
}
