/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.edu.ingsoft.colegio.gotitas.model;

/**
 *
 * @author informatica
 */
public class Auth {

    private int idUsuario;
    private int idRol;
    private String email;
    private String contrasenaHash;
    private String fechaCreacion;
    private String nombreRol;

    public Auth() {
    }

    public Auth(int idUsuario, int idRol, String email, String contrasenaHash, String fechaCreacion, String nombreRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.email = email;
        this.contrasenaHash = contrasenaHash;
        this.fechaCreacion = fechaCreacion;
        this.nombreRol = nombreRol;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
