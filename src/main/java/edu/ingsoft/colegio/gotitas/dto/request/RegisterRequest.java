package main.java.edu.ingsoft.colegio.gotitas.dto.request;

import java.util.Date;

public class RegisterRequest {

    private String idDocente;
    private String email;
    private String contrasenaHashed;
    private int idRol;
    private String idEstudiante;
    private String idCiudad;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;

    public RegisterRequest(String idDocente, String email, String contrasenaHashed, int idRol, String idEstudiante, String idCiudad, String nombre, String apellido, Date fechaNacimiento) {
        this.idDocente = idDocente;
        this.email = email;
        this.contrasenaHashed = contrasenaHashed;
        this.idRol = idRol;
        this.idEstudiante = idEstudiante;
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
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

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String idCiudad) {
        this.idCiudad = idCiudad;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
