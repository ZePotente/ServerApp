package configuracion;

public class Configuracion {
    private int puertoResto;
    private int puertoSync;
    private String IPOtroDir = "";
    private int puertoOtroDir;
    
    public Configuracion(String puertoResto, String puertoSync, String IPOtroDir, String puertoOtroDir) throws NumberFormatException {
        this.puertoResto = Integer.parseInt(puertoResto);
        this.puertoSync = Integer.parseInt(puertoSync);
        this.IPOtroDir = IPOtroDir;
        this.puertoOtroDir = Integer.parseInt(puertoOtroDir);
    }

    public int getPuertoResto() {
        return puertoResto;
    }

    public int getPuertoSync() {
        return puertoSync;
    }

    public String getIPOtroDir() {
        return IPOtroDir;
    }

    public int getPuertoOtroDir() {
        return puertoOtroDir;
    }
}
