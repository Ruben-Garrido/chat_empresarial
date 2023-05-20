package logica;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String nombre;
    
    
	public Usuario(String nombre) {
		this.nombre = nombre;
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + "]";
	}	
}