package main.java.edu.ingsoft.colegio.gotitas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegisterResponse;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;
import main.java.edu.ingsoft.colegio.gotitas.security.jbcrypt.BCrypt;

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
    private TextField txtFieldTelefono;
    @FXML
    private TextField txtFieldEspecialidad;

    public RegisterController(SceneManager stage, AuthService userRepo) {
        this.stage = stage;
        this.userRepo = userRepo;
    }

    @FXML
    private void handleRegister() {
        try {
            // 1. Validar campos obligatorios vacíos
            if (txtFieldNombre.getText().trim().isEmpty()
                    || txtFieldApellido.getText().trim().isEmpty()
                    || txtFieldEmail.getText().trim().isEmpty()
                    || txtFieldPass.getText().trim().isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor completa los campos obligatorios (Nombre, Apellido, Correo y Contraseña).");
                return;
            }

            String nombre = txtFieldNombre.getText().trim();
            String apellido = txtFieldApellido.getText().trim();
            String email = txtFieldEmail.getText().trim();
            String password = txtFieldPass.getText();
            String telefono = txtFieldTelefono.getText().trim();
            String especialidad = txtFieldEspecialidad.getText().trim();

            // 2. Validar formato de correo electrónico
            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                mostrarAlerta(Alert.AlertType.WARNING, "Correo inválido", "Por favor ingresa una dirección de correo electrónico válida.");
                txtFieldEmail.requestFocus();
                return;
            }

            // 3. Validar longitud mínima de la contraseña (ej. mínimo 6 caracteres)
            if (password.length() < 6) {
                mostrarAlerta(Alert.AlertType.WARNING, "Contraseña débil", "La contraseña debe tener al menos 6 caracteres.");
                txtFieldPass.requestFocus();
                return;
            }

            // 4. Validar formato de teléfono (opcional, solo si se llena y contiene letras)
            if (!telefono.isEmpty() && !telefono.matches("^[0-9+\\-\\s()]{7,15}$")) {
                mostrarAlerta(Alert.AlertType.WARNING, "Teléfono inválido", "Por favor ingresa un número de teléfono válido.");
                txtFieldTelefono.requestFocus();
                return;
            }

            // Aplicar hashing seguro a la contraseña con BCrypt
            String contrasenaHashed = BCrypt.hashpw(password, BCrypt.gensalt());

            // Construir el objeto Request
            RegisterRequest request = new RegisterRequest(
                    null, // idDocente
                    email,
                    contrasenaHashed,
                    2, // idRol: 2 = Docente
                    nombre,
                    apellido,
                    telefono.isEmpty() ? null : telefono,
                    especialidad.isEmpty() ? null : especialidad
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
        if (txtFieldTelefono != null) {
            txtFieldTelefono.clear();
        }
        if (txtFieldEspecialidad != null) {
            txtFieldEspecialidad.clear();
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
