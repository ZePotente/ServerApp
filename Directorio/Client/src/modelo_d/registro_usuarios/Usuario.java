package modelo_d.registro_usuarios;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String numeroDeIP;
    private boolean estado;

    public Usuario(String nombre, String numeroDeIP, boolean estado) {
        this.nombre = nombre;
        this.numeroDeIP = numeroDeIP;
        this.estado = estado;
    }
    
    public String getNumeroDeIP() {
        return numeroDeIP;
    }
    
    public boolean getEstado() {
        return estado;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
