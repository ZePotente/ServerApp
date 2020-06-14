package modelo_d.sincronizacion;

public interface Sincronizable {
    public void conectarDir();
    public void notificarConexion();
    public void notificarDesconexiones();
    public void notificarRegistro();
    public void pedirUsuarios();
}
