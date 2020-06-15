package modelo_d.sincronizacion;

import java.io.IOException;

import modelo_d.registro_usuarios.RegistroUsuarios;

import modelo_d.sincronizacion.notificacion.Notificacion;

public interface Sincronizable {
    public void notificarRegistro(String nombre, String ip)throws IOException;
    public void notificarConexion(String nombre) throws IOException;
    public void notificarDesconexion(String nombre) throws IOException;
    public RegistroUsuarios pedirUsuarios(RegistroUsuarios registroActual) throws IOException;
}
