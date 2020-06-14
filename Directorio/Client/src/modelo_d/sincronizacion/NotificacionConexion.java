package modelo_d.sincronizacion;

public class NotificacionConexion extends Notificacion {
    private String nombre;
    
    public NotificacionConexion(String nombre) {
        super();
        this.nombre = nombre;
    }
    
    @Override
    public String getFormato() {
        return this.nombre + super.SEPARADOR_ATRIBUTOS + "online";
    }
}
