package main.java.edu.ingsoft.colegio.gotitas.dto.request;

public class RegisterRequest {

    private String idDocente;
    private String email;
    private String contrasenaHashed;
    private int idRol;
    private String nombre;
    private String apellido;
    private String telefono;
    private String especialidad;

    public RegisterRequest(String idDocente, String email, String contrasenaHashed, int idRol, String nombre, String apellido, String telefono, String especialidad) {
        this.idDocente = idDocente;
        this.email = email;
        this.contrasenaHashed = contrasenaHashed;
        this.idRol = idRol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.especialidad = especialidad;
    }

    public String getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(String idDocente) {
        this.idDocente = idDocente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenaHashed() {
        return contrasenaHashed;
    }

    public void setContrasenaHashed(String contrasenaHashed) {
        this.contrasenaHashed = contrasenaHashed;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}
