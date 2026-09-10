package main.java.edu.ingsoft.colegio.gotitas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegisterResponse;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;
import main.java.edu.ingsoft.colegio.gotitas.security.jbcrypt.BCrypt;

import java.time.ZoneId;
import java.util.Date;

public class RegisterController {

    SceneManager stage;
    AuthService userRepo;

    @FXML
    private TextField txtFieldNombre;
    @FXML
    private TextField txtFieldApellido;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private PasswordField txtFieldPass;
    @FXML
    private DatePicker datePickerFechaNacimiento;

    public RegisterController(SceneManager stage, AuthService userRepo) {
        this.stage = stage;
        this.userRepo = userRepo;
    }

    @FXML
    private void handleRegister() {
        try {
            // Validar campos obligatorios básicos según la vista FXML
            if (txtFieldNombre.getText().trim().isEmpty()
                    || txtFieldApellido.getText().trim().isEmpty()
                    || txtFieldEmail.getText().trim().isEmpty()
                    || txtFieldPass.getText().trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor completa todos los campos obligatorios.");
                return;
            }

            // Recoger datos de la vista
            String nombre = txtFieldNombre.getText().trim();
            String apellido = txtFieldApellido.getText().trim();
            String email = txtFieldEmail.getText().trim();
            
            // Aplicar hashing seguro a la contraseña con BCrypt
            String contrasenaHashed = BCrypt.hashpw(txtFieldPass.getText(), BCrypt.gensalt());

            // Convertir LocalDate de JavaFX a java.util.Date si la vista lo provee
            Date fechaNacimiento = null;
            if (datePickerFechaNacimiento != null && datePickerFechaNacimiento.getValue() != null) {
                fechaNacimiento = Date.from(datePickerFechaNacimiento.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            }

            // Construir el objeto Request para el registro de docentes
            RegisterRequest request = new RegisterRequest(
                    null, // idDocente (generado en repositorio)
                    email,
                    contrasenaHashed,
                    1, // idRol por defecto (1 = usuario)
                    null, // idEstudiante
                    null, // idCiudad
                    nombre,
                    apellido,
                    fechaNacimiento
            );

            // Llamada al servicio para guardar el docente
            RegisterResponse response = invocarGuardadoDocente(request);

            if (response != null && response.isSuccess()) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Exitoso", response.getMessage());
                limpiarCampos();
            } else {
                String mensajeError = response != null ? response.getMessage() : "Error desconocido al registrar.";
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Registro", mensajeError);
            }

        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error Inesperado", "Ocurrió un error en el sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleVolverLogin() throws Exception {
        stage.showLoginView();
    }

    private RegisterResponse invocarGuardadoDocente(RegisterRequest request) throws Exception {
        return userRepo.saveDocente(request);
    }

    private void limpiarCampos() {
        txtFieldNombre.clear();
        txtFieldApellido.clear();
        txtFieldEmail.clear();
        txtFieldPass.clear();
        if (datePickerFechaNacimiento != null) {
            datePickerFechaNacimiento.setValue(null);
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}