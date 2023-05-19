package logica;

public class Mensaje {
    private String mensaje;
    private String fecha;
    private String hora;
    private String cedulaUsuario;

    public Mensaje(String mensaje, String fecha, String hora, String cedulaUsuario) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.hora = hora;
        this.cedulaUsuario = cedulaUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }
}
