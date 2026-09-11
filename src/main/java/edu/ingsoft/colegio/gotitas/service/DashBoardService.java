package main.java.edu.ingsoft.colegio.gotitas.service;

import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import main.java.edu.ingsoft.colegio.gotitas.repository.EstudianteRepository;

import java.util.regex.Pattern;

public class DashBoardService {

    private final EstudianteRepository estudianteRepository;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$"; // Formato YYYY-MM-DD

    public DashBoardService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    /* =========================================
       1. LISTAR
    ========================================= */
    public ObservableList<Estudiante> listStudent() {
        ObservableList<Estudiante> list = estudianteRepository.findAll();
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("Sin datos que mostrar. No hay estudiantes registrados.");
        }
        return list;
    }

    /* =========================================
       2. BUSCAR POR ID
    ========================================= */
    public Estudiante findStudentById(int idEstudiante) {
        validarId(idEstudiante);
        Estudiante estudiante = estudianteRepository.findById(idEstudiante);
        if (estudiante == null) {
            throw new RuntimeException("No se encontró ningún estudiante con el ID: " + idEstudiante);
        }
        return estudiante;
    }

    // Sobrecarga por si se recibe el ID desde una vista JavaFX (TextField)
    public Estudiante findStudentById(String idEstudianteStr) {
        int id = parseAndValidateId(idEstudianteStr);
        return findStudentById(id);
    }

    /* =========================================
       3. CREAR (SAVE)
    ========================================= */
    public boolean saveStudent(Estudiante estudiante, String contrasenaHash) {
        validarEstudianteParaCrear(estudiante, contrasenaHash);

        return estudianteRepository.save(estudiante, contrasenaHash);
    }

    /* =========================================
       4. ACTUALIZAR (UPDATE)
    ========================================= */
    public boolean updateStudent(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El objeto Estudiante no puede ser nulo.");
        }

        validarId(estudiante.getIdEstudiante());

        if (estudianteRepository.findById(estudiante.getIdEstudiante()) == null) {
            throw new RuntimeException("No se puede actualizar: El estudiante con ID " + estudiante.getIdEstudiante() + " no existe.");
        }

        validarDatosPersonales(estudiante.getNombre(), estudiante.getApellido(), estudiante.getCorreoElectronico());
        validarCamposOpcionales(estudiante.getFechaNacimiento(), estudiante.getTelefonoEncargado());

        return estudianteRepository.update(estudiante);
    }

    /* =========================================
       5. ELIMINAR (DELETE)
    ========================================= */
    public boolean deleteStudent(int idEstudiante) {
        validarId(idEstudiante);

        if (estudianteRepository.findById(idEstudiante) == null) {
            throw new RuntimeException("No se puede eliminar: El estudiante con ID " + idEstudiante + " no existe.");
        }

        return estudianteRepository.delete(idEstudiante);
    }

    // Sobrecarga para ID proveniente de vista JavaFX
    public boolean deleteStudent(String idEstudianteStr) {
        int id = parseAndValidateId(idEstudianteStr);
        return deleteStudent(id);
    }

    /* =========================================
       MÉTODOS AUXILIARES DE VALIDACIÓN
    ========================================= */
    private void validarEstudianteParaCrear(Estudiante estudiante, String contrasenaHash) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El objeto Estudiante no puede ser nulo.");
        }

        if (contrasenaHash == null || contrasenaHash.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria para registrar al estudiante.");
        }

        validarDatosPersonales(estudiante.getNombre(), estudiante.getApellido(), estudiante.getCorreoElectronico());
        validarCamposOpcionales(estudiante.getFechaNacimiento(), estudiante.getTelefonoEncargado());
    }

    private void validarDatosPersonales(String nombre, String apellido, String correo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }
        if (!Pattern.matches(EMAIL_REGEX, correo.trim())) {
            throw new IllegalArgumentException("El formato del correo electrónico es inválido.");
        }
    }

    private void validarCamposOpcionales(String fechaNacimiento, String telefonoEncargado) {
        if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
            if (!Pattern.matches(DATE_REGEX, fechaNacimiento.trim())) {
                throw new IllegalArgumentException("El formato de la fecha de nacimiento debe ser YYYY-MM-DD.");
            }
        }
        if (telefonoEncargado != null && !telefonoEncargado.trim().isEmpty()) {
            if (telefonoEncargado.trim().length() < 8) {
                throw new IllegalArgumentException("El número de teléfono debe contener al menos 8 dígitos.");
            }
        }
    }

    private void validarId(int idEstudiante) {
        if (idEstudiante <= 0) {
            throw new IllegalArgumentException("El ID del estudiante debe ser un entero positivo mayor a 0.");
        }
    }

    private int parseAndValidateId(String idEstudianteStr) {
        if (idEstudianteStr == null || idEstudianteStr.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del estudiante no puede estar vacío.");
        }
        try {
            int id = Integer.parseInt(idEstudianteStr.trim());
            validarId(id);
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID ingresado debe ser un número entero válido.");
        }
    }
}
