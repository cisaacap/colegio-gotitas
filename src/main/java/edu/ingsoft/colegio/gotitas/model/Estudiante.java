package main.java.edu.ingsoft.colegio.gotitas.model;

public class Estudiante {
    //atributos
    private String idEstudiante;
    private String nombre;
    private String apellido;
    private String correoElectronico;
    private String nombreSeccion;
    private String nombreCurso;
    private String nombreDocente;
    private String apellidoDocente;
    
    //constructor
    public Estudiante(String idEstudiante, String nombre, String apellido, String correoElectronico, String nombreSeccion, String nombreCurso, String nombreDocente, String apellidoDocente) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correoElectronico = correoElectronico;
        this.nombreSeccion = nombreSeccion;
        this.nombreCurso = nombreCurso;
        this.nombreDocente = nombreDocente;
        this.apellidoDocente = apellidoDocente;
    }
    
    //metodos
    public String getIdEstudiante() {
        return idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public String getApellidoDocente() {
        return apellidoDocente;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public void setApellidoDocente(String apellidoDocente) {
        this.apellidoDocente = apellidoDocente;
    }
    
}
