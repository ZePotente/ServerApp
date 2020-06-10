package modelo_d.registro_usuarios;

import java.util.ArrayList;

public interface RegistroUsuarios {
    public boolean isRegistrado(String nombre);
    public void registrar(String nombre, String ip);
    public void ponerOffline(ArrayList<String> nombres);
    public void ponerOffline(String nombre);
    public void ponerOnline(ArrayList<String> nombres);
    public void ponerOnline(String nombre);
}
