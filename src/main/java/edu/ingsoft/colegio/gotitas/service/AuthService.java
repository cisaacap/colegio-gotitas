package main.java.edu.ingsoft.colegio.gotitas.service;

import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegisterResponse;
import main.java.edu.ingsoft.colegio.gotitas.repository.AuthRepository;
import main.java.edu.ingsoft.colegio.gotitas.security.jbcrypt.BCrypt;

public class AuthService {

    // Atributo
    private final AuthRepository authRepository;

    // Constructor
    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        if (loginRequest == null) {
            throw new RuntimeException("Credenciales vacías.");
        } else if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            throw new RuntimeException("El correo o la contraseña no pueden estar vacíos.");
        }
        LoginResponse response = authRepository.findUserByEmail(loginRequest);

        if (response == null) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        String contrasenaHashed = response.getContrasena_hash();

        if (contrasenaHashed == null) {
            throw new RuntimeException("Contraseña inválida.");
        } else {
            if (BCrypt.checkpw(loginRequest.getPassword(), contrasenaHashed)) {
                return response;
            }
        }
        return null;
    }

    public RegisterResponse saveDocente(RegisterRequest request) throws Exception {
        // Validación opcional antes de guardar al docente si lo consideras necesario
        if (request == null) {
            throw new RuntimeException("Los datos de registro no pueden estar vacíos.");
        }

        // Llamada al repositorio actualizado para registrar al docente
        return authRepository.saveDocente(request);
    }
}
