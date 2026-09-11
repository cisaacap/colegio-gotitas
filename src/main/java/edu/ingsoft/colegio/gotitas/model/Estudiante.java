/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.edu.ingsoft.colegio.gotitas.model;

/**
 *
 * @author informatica
 */
public class Estudiante {
    // Atributos basados en la tabla 'estudiantes' y relaciones de la base de datos
    private int idEstudiante;
    private int idUsuario;
    private Integer idCiudad; // Usamos Integer para permitir valores NULL tal como en la BD
    private String nombre;
    private String apellido;
    private String fechaNacimiento; // O java.sql.Date / LocalDate según prefieras manejar fechas
    private String telefonoEncargado;
    
    // Campos adicionales para consultas (vistas / JOINS con usuarios, secciones, cursos y docentes)
    private String correoElectronico; // Viene de la tabla 'usuarios' (email)
    private String nombreSeccion;
    private String nombreCurso;
    private String nombreDocente;
    private String apellidoDocente;

    // Constructor vacío
    public Estudiante() {
    }

    // Constructor completo con todos los atributos de la base de datos y relaciones de vista
    public Estudiante(int idEstudiante, int idUsuario, Integer idCiudad, String nombre, String apellido, String fechaNacimiento, String telefonoEncargado, String correoElectronico, String nombreSeccion, String nombreCurso, String nombreDocente, String apellidoDocente) {
        this.idEstudiante = idEstudiante;
        this.idUsuario = idUsuario;
        this.idCiudad = idCiudad;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoEncargado = telefonoEncargado;
        this.correoElectronico = correoElectronico;
        this.nombreSeccion = nombreSeccion;
        this.nombreCurso = nombreCurso;
        this.nombreDocente = nombreDocente;
        this.apellidoDocente = apellidoDocente;
    }

    // Getters y Setters
    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Integer idCiudad) {
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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefonoEncargado() {
        return telefonoEncargado;
    }

    public void setTelefonoEncargado(String telefonoEncargado) {
        this.telefonoEncargado = telefonoEncargado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getApellidoDocente() {
        return apellidoDocente;
    }

    public void setApellidoDocente(String apellidoDocente) {
        this.apellidoDocente = apellidoDocente;
    }
}