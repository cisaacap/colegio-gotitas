package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegisterResponse;

public class AuthRepository {

    // Atributos
    private boolean sqlStatus = false;

    public LoginResponse findUserByEmail(LoginRequest loginRequest) throws Exception {
        String sql = "select d.nombre, d.apellido, u.contrasena_hash from usuarios as u"
                + " right join docentes as d"
                + " on d.id_docente = u.id_docente"
                + " where u.email = ? ";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, loginRequest.getEmail());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return new LoginResponse(rs.getString("nombre"), rs.getString("apellido"), rs.getString("contrasena_hash"));
            }
        } catch (SQLException e) {
            System.out.println("Error al encontrar el EMAIL: " + e.getMessage());
        }
        return null;
    }

    public RegisterResponse saveDocente(RegisterRequest registerRequest) throws Exception {
        // Consultas SQL adaptadas al esquema de docentes y usuarios
        String docenteSql = "insert into docentes (id_docente, nombre, apellido, correo_electronico) values (?, ?, ?, ?)";
        String userSql = "insert into usuarios (id_usuario, id_docente, contrasena_hash, id_rol, email) values (uuid(), ?, ?, ?, ?)";

        java.sql.Connection conn = null;

        try {
            conn = DataBaseConnection.getConnectionDataBase();
            conn.setAutoCommit(false); // Iniciamos la transacción

            // Generar un ID único para el docente (puedes usar UUID o una lógica propia, ej: UUID aleatorio o secuencial)
            String idDocenteGenerado = java.util.UUID.randomUUID().toString();

            // 1. Insertar en la tabla docentes
            try (PreparedStatement pstmDocente = conn.prepareStatement(docenteSql)) {
                pstmDocente.setString(1, idDocenteGenerado);
                pstmDocente.setString(2, registerRequest.getNombre());
                pstmDocente.setString(3, registerRequest.getApellido());
                pstmDocente.setString(4, registerRequest.getEmail());
                pstmDocente.executeUpdate();
            }

            // 2. Insertar en la tabla usuarios relacionándolo con el docente recién creado
            try (PreparedStatement pstmUser = conn.prepareStatement(userSql)) {
                pstmUser.setString(1, idDocenteGenerado); // Llave foránea hacia docentes
                pstmUser.setString(2, registerRequest.getContrasenaHashed());
                pstmUser.setInt(3, 1); // Rol por defecto (ej: 1 = usuario / docente)
                pstmUser.setString(4, registerRequest.getEmail());
                pstmUser.executeUpdate();
            }

            // Si todo sale bien, confirmamos la transacción
            conn.commit();
            return new RegisterResponse(true, "Docente registrado exitosamente", registerRequest.getNombre());

        } catch (SQLException e) {
            // En caso de fallar, revertimos los cambios realizados
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.out.println("Error en rollback: " + rollbackEx.getMessage());
                }
            }
            System.out.println("Error en saveDocente: " + e.getMessage());
            return new RegisterResponse(false, "Error de SQL: " + e.getMessage());
        } finally {
            // Restauramos el comportamiento por defecto de la conexión
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.out.println("Error al restaurar autoCommit: " + ex.getMessage());
                }
            }
        }
    }
}