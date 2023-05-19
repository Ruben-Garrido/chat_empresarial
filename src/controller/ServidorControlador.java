package controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Scanner;
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

public class ServidorControlador {

    @FXML
    private TextArea txtMostrarChat;

    private List<PrintWriter> guardarClientes = new ArrayList<>();
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private Scanner scanner;

    public void initialize() {
        Thread hiloServidor = new Thread(this::startServer);// hace posible la conexion
        hiloServidor.start();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            mostrarMensaje("Servidor escuchando en el puerto 8000");
            
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                mostrarMensaje("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());

                PrintWriter writer = new PrintWriter(clienteSocket.getOutputStream(), true);
                guardarClientes.add(writer);

                ClientHandler clientHandler = new ClientHandler(clienteSocket, writer);
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

    
    private void reenviarMensaje(PrintWriter writer, String mensaje) {
        // Reenviar a todos los conectados
        for (PrintWriter clientWriter : guardarClientes) {
            if (clientWriter != writer) {
                clientWriter.println(mensaje);
            }
        }
    }

//    private void reenviarMensaje(String mensaje) {
//               //reenvia a todos los conectados 
//        for (PrintWriter writer : guardarClientes) {
//            writer.println(mensaje);
//        }
//    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter writer;

        public ClientHandler(Socket clientSocket, PrintWriter writer) {
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
                mostrarMensaje("desconetado cliente");
                guardarClientes.remove(writer);
            } catch (IOException e) {
                e.printStackTrace();
            
			}
        }
    }
}




