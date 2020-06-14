package modelo_d.sincronizacion;

public class Sincronizador {
    //se podria llegar a cambiar por una coleccion de {ip,puerto) si fuera necesario
    private String ipOtroDirectorio;
    private int otroPuerto;
    
    private ServerSync sv;
    public Sincronizador(String nroIP, int otroPuerto, int estePuerto) {
        super();
        this.ipOtroDirectorio = nroIP;
        this.otroPuerto = otroPuerto;
        sv = new ServerSync(estePuerto);
    }
    
    
}
