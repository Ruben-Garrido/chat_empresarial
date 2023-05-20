package controller;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;
import java.util.Vector;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import logica.Usuario;

public class ServidorControlador {

    @FXML
    private TextArea txtMostrarChat;

    private List<String> guardarUsuarios = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Scanner scanner;
    Usuario usuario = new Usuario();

    public void initialize() {
        Thread hiloServidor = new Thread(this::startServer);// hace posible la conexion
        hiloServidor.start();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            mostrarMensaje("Servidor escuchando en el puerto 8000");
            
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                //mostrarMensaje("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());
                
                String nombreUsuario = clienteSocket.getInetAddress().getHostAddress();
                guardarUsuarios.add(nombreUsuario);
                System.out.println("Usuario conectado: " + nombreUsuario);
                
                // no logro que se guarde el nombre del usuario creo que se debe implementar el hilo
                System.out.println("Se guardo el cliente");
                for (String usuario : guardarUsuarios) {
                    System.out.println("Usuario: " + usuario);
                }

                
             
                    

 

                
                
                ClientHandler clientHandler = new ClientHandler(clienteSocket, nombreUsuario);
                executorService.execute(clientHandler);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
			
		}
    }

    private void mostrarMensaje(String mensaje) {
        txtMostrarChat.appendText(mensaje + "\n");
    }

    
    private void reenviarMensaje(String writer, String mensaje) {
        // Reenviar a todos los conectados
//        for (PrintWriter clientWriter : guardarUsuarios) {
//            if (clientWriter != writer) {
//                clientWriter.println(mensaje);
//            }
//        }
    }

//----------------------hilo-------------------------------------

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String writer;
       

//        public ClientHandler(Socket clientSocket, PrintWriter writer) {
//            this.clientSocket = clientSocket;
//            this.writer = writer;
//        }
        
        public ClientHandler(Socket clientSocket, String writer) {
            this.clientSocket = clientSocket;
            this.writer = writer;
        }

        


		@Override
        public void run() {
            try {
                Scanner scanner = new Scanner(clientSocket.getInputStream());

                String mensaje;
                while (scanner.hasNextLine()) {
                    mensaje = scanner.nextLine();
                    mostrarMensaje(mensaje);
                    reenviarMensaje(writer, mensaje);

                }

                clientSocket.close();
                mostrarMensaje("desconectado cliente");
                guardarUsuarios.remove(writer);
            } catch (IOException e) {
                e.printStackTrace();
            
			}
        }
    }
}




