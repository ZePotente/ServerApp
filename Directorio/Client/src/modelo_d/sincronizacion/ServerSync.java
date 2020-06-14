package modelo_d.sincronizacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.Observable;

import modelo_d.Sistema;

public class ServerSync extends Observable implements IServerSync {
    private int puerto;
    private ServerSocket sv;
    public ServerSync(int puerto) {
        super();
        this.puerto = puerto;
        abrirSv(puerto);
    }
    
    private void abrirSv(int puerto) {
        new Thread() {
            public void run() {
                try {
                    sv = new ServerSocket(puerto);
                    while (true) {
                        Socket socket = sv.accept();
                        System.out.println("Se recibio una notificacion de sincronizacion.");
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String identificador = in.readLine();
                        if (identificador == PETICION_USUARIOS) {
                            //Sistema.
                        }else {
                            //ArrayList<
                            String usuario = in.readLine();
                            do {
                                
                            }
                            while (i)
                            if (identificador == ) {
                            }else if (identificador == ) {
                                    
                            }else if (identificador == ) {
                                    
                            }
                        }
                    }
                }catch (IOException e) {
                    System.out.println("Error al abrir el servidor de sincronizacion.");
                    System.out.println("Por favor reinicie la aplicacion.");
                }
            }
        }.start();
    }
}
