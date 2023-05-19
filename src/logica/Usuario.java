package logica;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String nombre;
    private String apellido;
    private String cedula;
    private String programa;
    private String clave;
    
	public Usuario(String nombre, String apellido, String cedula, String programa, String clave) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.cedula = cedula;
		this.programa = programa;
		this.clave = clave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellido=" + apellido + ", cedula=" + cedula + ", programa=" + programa
				+ ", clave=" + clave + "]";
	}	
}