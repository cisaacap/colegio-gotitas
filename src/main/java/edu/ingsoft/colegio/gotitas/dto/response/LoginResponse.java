package main.java.edu.ingsoft.colegio.gotitas.dto.response;

public class LoginResponse {
    //Atributos
    private String nombre;
    private String apellido;
    private String contrasena_hash;

    public LoginResponse(String nombre, String apellido, String contrasena_hash) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena_hash = contrasena_hash;
    }
    
    //sobre carga - mismo nombre, distintos parametros
    public LoginResponse(String nombre, String apellido){
        this.nombre = nombre;
        //this.global = local
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContrasena_hash() {
        return contrasena_hash;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setContrasena_hash(String contrasena_hash) {
        this.contrasena_hash = contrasena_hash;
    }
    
    
}
