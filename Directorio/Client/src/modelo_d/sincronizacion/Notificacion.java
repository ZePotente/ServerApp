package modelo_d.sincronizacion;

import java.io.Serializable;

import java.util.ArrayList;

public class Notificacion implements Serializable {
    private ArrayList<String> atributos;
    
    public Notificacion(String nombre) {
        atributos = new ArrayList<String>();
        atributos.add(0, nombre);
    }
    
    public Notificacion(String nombre, String ip) {
        atributos = new ArrayList<String>();
        atributos.add(0, nombre);
        atributos.add(1, ip);
    }

    public ArrayList<String> getAtributos() {
        return atributos;
    }
}
