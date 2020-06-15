package configuracion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LectorConfiguracion {
    /**
         * @param nombreArch
         * Lee el archivo de configuracion.txt
         * y crea un objeto de clase <i>Configuracion</i> con su contenido.
         *
         * @throws NoLecturaConfiguracionException
         * Si ocurre un error con la lectura del archivo de configuracion.
         */
        public static Configuracion leerConfig(String nombreArch) throws NoLecturaConfiguracionException {
            try {
                FileInputStream arch;
                arch = new FileInputStream(nombreArch);
                Scanner sc = new Scanner(arch);    
                
                String puertoResto = sc.nextLine();
                String puertoSync = sc.nextLine();
                //otro directorio
                String IPOtroDir = sc.nextLine();
                String puertoOtroDir = sc.nextLine();
                sc.close();
                
                Configuracion config = new Configuracion(puertoResto, puertoSync, IPOtroDir, puertoOtroDir);
                return config;
            } catch (FileNotFoundException e) {
                throw new NoLecturaConfiguracionException(e);
            } catch (NoSuchElementException e) {
                throw new NoLecturaConfiguracionException(e);
            }
        }
}
