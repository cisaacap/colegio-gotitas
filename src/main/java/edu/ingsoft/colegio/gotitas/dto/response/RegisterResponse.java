package main.java.edu.ingsoft.colegio.gotitas.dto.response;

public class RegisterResponse {

    private boolean success;
    private String message;
    private String nombre;

    public RegisterResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public RegisterResponse(boolean success, String message, String nombre) {
        this.success = success;
        this.message = message;
        this.nombre = nombre;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
