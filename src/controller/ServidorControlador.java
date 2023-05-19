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
        Thread hiloServidor = new Thread(this::startServer);
        hiloServidor.start();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(8000)) {
            appendMessage("Servidor escuchando en el puerto 8000");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                appendMessage("Cliente conectado: " + clienteSocket.getInetAddress());

                PrintWriter writer = new PrintWriter(clienteSocket.getOutputStream(), true);
                guardarClientes.add(writer);

                executorService.execute(new ClientHandler(clienteSocket, writer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
			
		}
    }

    private void appendMessage(String message) {
        txtMostrarChat.appendText(message + "\n");
    }

    private void broadcastMessage(String message) {
        // Llamar a SalaChatControlador.recibirNuevoMensaje() para que todas las instancias muestren el nuevo mensaje
        //SalaChatControlador.recibirNuevoMensaje(message);
          //reenvia a todos los conectados 
        for (PrintWriter writer : guardarClientes) {
            writer.println(message);
        }
    }

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

                String message;
                while (scanner.hasNextLine()) {
                    message = scanner.nextLine();
                    appendMessage(message);
                    broadcastMessage(message);
                }

                clientSocket.close();
                //appendMessage("desconetado cliente"+writer);
                guardarClientes.remove(writer);
            } catch (IOException e) {
                e.printStackTrace();
            
			}
        }
    }
}


//public class ServidorControlador {
//
//    @FXML
//    private TextArea txtMostrarChat;
//
//    private List<PrintWriter> escribirClientes = new ArrayList<>();
//
//    public void initialize() {
//        Thread hiloServidor = new Thread(this::startServer);
//        hiloServidor.start();
//    }
//
//    private void startServer() {
//        try (ServerSocket serverSocket = new ServerSocket(8000)) {
//            appendMessage("Servidor escuchando en el puerto 8000");
//
//            while (true) {
//                Socket clienteSocket = serverSocket.accept();
//                appendMessage("Cliente conectado: " + clienteSocket.getInetAddress());
//
//                PrintWriter writer = new PrintWriter(clienteSocket.getOutputStream(), true);
//                escribirClientes.add(writer);
//
//                Thread clientThread = new Thread(new ClientHandler(clienteSocket, writer));
//                clientThread.start();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void appendMessage(String message) {
//        txtMostrarChat.appendText(message + "\n");
//    }
//
//    private void broadcastMessage(String message) {
//        for (PrintWriter writer : escribirClientes) {
//            writer.println(message);
//        }
//    }
//
//    private class ClientHandler implements Runnable {
//        private Socket clientSocket;
//        private PrintWriter writer;
//
//        public ClientHandler(Socket clientSocket, PrintWriter writer) {
//            this.clientSocket = clientSocket;
//            this.writer = writer;
//        }
//
//        @Override
//        public void run() {
//            try {
//                Scanner scanner = new Scanner(clientSocket.getInputStream());
//
//                String message;
//                while (scanner.hasNextLine()) {
//                    message = scanner.nextLine();
//                    appendMessage("Received from client: " + message);
//                    broadcastMessage(message);
//                }
//
//                clientSocket.close();
//                escribirClientes.remove(writer);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}


