package main.java.edu.ingsoft.colegio.gotitas.service;

import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegisterResponse;
import main.java.edu.ingsoft.colegio.gotitas.repository.AuthRepository;
import main.java.edu.ingsoft.colegio.gotitas.security.jbcrypt.BCrypt;

public class AuthService {

    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        if (loginRequest == null) {
            throw new RuntimeException("Credenciales vacías.");
        } else if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()
                || loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
            throw new RuntimeException("El correo o la contraseña no pueden estar vacíos.");
        }

        LoginResponse response = authRepository.findUserByEmail(loginRequest);

        if (response == null) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        String contrasenaHashed = response.getContrasena_hash();

        if (contrasenaHashed == null) {
            throw new RuntimeException("Contraseña inválida en el sistema.");
        }

        if (BCrypt.checkpw(loginRequest.getPassword(), contrasenaHashed)) {
            return response;
        } else {
            throw new RuntimeException("Contraseña incorrecta.");
        }
    }

    public RegisterResponse saveDocente(RegisterRequest request) throws Exception {
        if (request == null) {
            throw new RuntimeException("Los datos de registro no pueden estar vacíos.");
        }

        // Validaciones de negocio robustas
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new RuntimeException("El correo electrónico es obligatorio.");
        }
        if (!request.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            throw new RuntimeException("El formato del correo electrónico no es válido.");
        }
        if (request.getContrasenaHashed() == null || request.getContrasenaHashed().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria.");
        }
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()
                || request.getApellido() == null || request.getApellido().trim().isEmpty()) {
            throw new RuntimeException("El nombre y el apellido son obligatorios.");
        }

        // Llamada al repositorio para registrar al docente
        return authRepository.saveDocente(request);
    }
}
