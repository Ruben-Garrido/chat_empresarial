package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class LoginControlador  {
	

		@FXML
		private Button botonIngresar;
		//variables
		@FXML
		private TextField cajaUsuario;
		private Socket socket;
	    private PrintWriter escribir;
	    private String nombreUsuario;

		

	    @FXML
	    void IngresarAlChat(ActionEvent event) {
	    	
	    	nombreUsuario = cajaUsuario.getText();
	    	
	    	// toca instanciar el controlador  al cual quiero mandar el nombre
	    	
	    	// Crear una instancia de SalaChatControlador y pasar el nombre de usuario
	        SalaChatControlador controlador = new SalaChatControlador(nombreUsuario);
	    	// conectar al servidor 
	    	conectar();
	    	

	    	
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SalaChat.fxml"));
	    	loader.setControllerFactory(param -> new SalaChatControlador(nombreUsuario));
            Parent root;
			try {
				root = loader.load();
			
            //Controlador de la vista
            SalaChatControlador controlador2 = loader.getController();
            controlador2.setWriterAndSocket(escribir, socket);
            
            Scene scene = new Scene(root);
            Stage stage = new Stage ();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();
            
            stage.setOnCloseRequest(e -> controlador.closeWindows());
            
            
            
            //-----------------------------------------------------------
            
            Stage cerrarPantalla = (Stage) this.botonIngresar.getScene().getWindow();
            cerrarPantalla.close();
            
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }
	    
	    private void conectar() {
	// TODO Auto-generated method st
	    	try {
	            socket = new Socket("192.168.199.34", 8000);
	            escribir = new PrintWriter(socket.getOutputStream(), true);

	            Thread hiloConectar = new Thread();
	            hiloConectar.start();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
}

		public void setNombreUsuario(String nombreUsuario) {
			// TODO Auto-generated method stub
			this.nombreUsuario = nombreUsuario;
		}

		
		
	    
	    
	   }
