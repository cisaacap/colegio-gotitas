package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.RegisterRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.RegisterResponse;

public class AuthRepository {

    // Constante para el rol de Docente (id_rol = 2)
    private static final int ROL_DOCENTE = 2;

    public LoginResponse findUserByEmail(LoginRequest loginRequest) throws Exception {
        // Consulta que busca en usuarios y obtiene nombre y apellido desde la tabla que corresponda
        String sql = "SELECT "
                + "  COALESCE(d.nombre, doc.nombre, e.nombre) AS nombre, "
                + "  COALESCE(d.apellido, doc.apellido, e.apellido) AS apellido, "
                + "  u.contrasena_hash "
                + "FROM usuarios u "
                + "LEFT JOIN director d ON u.id_usuario = d.id_usuario "
                + "LEFT JOIN docentes doc ON u.id_usuario = doc.id_usuario "
                + "LEFT JOIN estudiantes e ON u.id_usuario = e.id_usuario "
                + "WHERE u.email = ?";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, loginRequest.getEmail());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return new LoginResponse(
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("contrasena_hash")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al encontrar el EMAIL: " + e.getMessage());
        }
        return null;
    }

    public RegisterResponse saveDocente(RegisterRequest registerRequest) throws Exception {
        // Consultas ajustadas a las llaves primarias autoincrementables
        String userSql = "INSERT INTO usuarios (id_rol, email, contrasena_hash) VALUES (?, ?, ?)";
        String docenteSql = "INSERT INTO docentes (id_usuario, nombre, apellido) VALUES (?, ?, ?)";

        Connection conn = null;

        try {
            conn = DataBaseConnection.getConnectionDataBase();
            conn.setAutoCommit(false); // Iniciamos la transacción

            int idUsuarioGenerado = -1;

            // 1. Insertar primero en la tabla usuarios (generando id_usuario autoincremental)
            try (PreparedStatement pstmUser = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmUser.setInt(1, ROL_DOCENTE); // Rol 2 = Docente
                pstmUser.setString(2, registerRequest.getEmail());
                pstmUser.setString(3, registerRequest.getContrasenaHashed());
                pstmUser.executeUpdate();

                // Obtener el ID generado para el usuario recién insertado
                try (ResultSet generatedKeys = pstmUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUsuarioGenerado = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("No se pudo obtener el ID del usuario generado.");
                    }
                }
            }

            // 2. Insertar en la tabla docentes relacionándolo con id_usuario
            try (PreparedStatement pstmDocente = conn.prepareStatement(docenteSql)) {
                pstmDocente.setInt(1, idUsuarioGenerado);
                pstmDocente.setString(2, registerRequest.getNombre());
                pstmDocente.setString(3, registerRequest.getApellido());
                pstmDocente.executeUpdate();
            }

            // Si ambas inserciones fueron exitosas, confirmamos la transacción
            conn.commit();
            return new RegisterResponse(true, "Docente registrado exitosamente", registerRequest.getNombre());

        } catch (SQLException e) {
            // Revertir cambios en caso de error
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
            // Restaurar el comportamiento de autoCommit
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
