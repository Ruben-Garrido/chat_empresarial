package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SalaChatControlador  {

	@FXML
    private Button botnEnviar;

    @FXML
    private TextField txtEscribirMensajes;

    @FXML
    private TextArea txtMostrarChat;

    @FXML
    private TextArea txtMostrarActivos;
    
    private PrintWriter writer;
    private Socket socket;
    private String nombreUsuario;

    public SalaChatControlador(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    
    
    public void EnviarMensaje(ActionEvent event) {
        String mensaje = txtEscribirMensajes.getText();
        writer.println(nombreUsuario + ": " + mensaje);
        txtEscribirMensajes.clear();
        // mostrar en la propia pantalla
        txtMostrarChat.appendText(nombreUsuario+": "+mensaje+"\n");
    }

    
    public void recibirMensaje() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());

            while (scanner.hasNextLine()) {
                String mensaje = scanner.nextLine();
                String[] partes = mensaje.split(":", 2); // Divide en 2 partes usando ":" como separador
                String nombreUsuario = partes[0];
                String mensajeUsuario = partes[1];
                
                Platform.runLater(() -> {
                    txtMostrarChat.appendText(nombreUsuario + ": " + mensajeUsuario + "\n");
                });
            }

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




	public void closeWindows() {
		 try {   
             FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/login.fxml"));
              
              Parent root = loader.load();
     
      
              //Controlador de la vista
              LoginControlador controlador = loader.getController();//poner la clase del controlador 
              controlador.setNombreUsuario(nombreUsuario);
              Scene scene = new Scene(root);
              Stage stage = new Stage ();
             
              stage.setScene(scene);
              stage.show();
            
              //Stage cerrarPantalla = (Stage) this.botonRegistrar.getScene().getWindow();
              //cerrarPantalla.close();
		  } catch (IOException ex) {
	            System.out.println("Errorrr..");
	        }
		
	}
	
	
	public void setWriterAndSocket(PrintWriter writer, Socket socket) {
	    this.writer = writer;
	    this.socket = socket;
	}

  

      
}
