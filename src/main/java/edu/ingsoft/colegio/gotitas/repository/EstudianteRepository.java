package main.java.edu.ingsoft.colegio.gotitas.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EstudianteRepository {

    // Rol de Estudiante en la tabla 'roles' (id_rol = 3)
    private static final int ROL_ESTUDIANTE = 3;

    /* =========================================
       1. READ ALL (Obtener todos los estudiantes)
    ========================================= */
    public ObservableList<Estudiante> findAll() {
        String sql = "SELECT "
                + "e.id_estudiante, "
                + "e.id_usuario, "
                + "e.id_ciudad, "
                + "e.nombre, "
                + "e.apellido, "
                + "e.fecha_nacimiento, "
                + "e.telefono_encargado, "
                + "u.email AS correo_electronico, "
                + "s.nombre_seccion, "
                + "c.nombre_curso, "
                + "d.nombre AS nombre_docente, "
                + "d.apellido AS apellido_docente "
                + "FROM estudiantes e "
                + "INNER JOIN usuarios u ON u.id_usuario = e.id_usuario "
                + "LEFT JOIN matriculas m ON m.id_estudiante = e.id_estudiante "
                + "LEFT JOIN secciones s ON s.id_seccion = m.id_seccion "
                + "LEFT JOIN inscripciones_cursos ic ON ic.id_matricula = m.id_matricula "
                + "LEFT JOIN cursos c ON c.id_curso = ic.id_curso "
                + "LEFT JOIN docentes d ON d.id_docente = c.id_docente;";

        ObservableList<Estudiante> studentList = FXCollections.observableArrayList();

        try (Connection conn = DataBaseConnection.getConnectionDataBase(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                studentList.add(mapResultSetToEstudiante(rs));
            }
            return studentList;

        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar estudiantes: " + e.getMessage(), e);
        }
    }

    /* =========================================
       2. LEER POR ID (cRud)
    ========================================= */
    public Estudiante findById(int idEstudiante) {
        String sql = "SELECT "
                + "e.id_estudiante, "
                + "e.id_usuario, "
                + "e.id_ciudad, "
                + "e.nombre, "
                + "e.apellido, "
                + "e.fecha_nacimiento, "
                + "e.telefono_encargado, "
                + "u.email AS correo_electronico, "
                + "s.nombre_seccion, "
                + "c.nombre_curso, "
                + "d.nombre AS nombre_docente, "
                + "d.apellido AS apellido_docente "
                + "FROM estudiantes e "
                + "INNER JOIN usuarios u ON u.id_usuario = e.id_usuario "
                + "LEFT JOIN matriculas m ON m.id_estudiante = e.id_estudiante "
                + "LEFT JOIN secciones s ON s.id_seccion = m.id_seccion "
                + "LEFT JOIN inscripciones_cursos ic ON ic.id_matricula = m.id_matricula "
                + "LEFT JOIN cursos c ON c.id_curso = ic.id_curso "
                + "LEFT JOIN docentes d ON d.id_docente = c.id_docente "
                + "WHERE e.id_estudiante = ?;";

        try (Connection conn = DataBaseConnection.getConnectionDataBase(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, idEstudiante);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEstudiante(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar el estudiante con ID " + idEstudiante + ": " + e.getMessage(), e);
        }
        return null;
    }

    /* =========================================
       3. CREATE (Crud)
    ========================================= */
    public boolean save(Estudiante estudiante, String contrasenaHash) {
        String userSql = "INSERT INTO usuarios (id_rol, email, contrasena_hash) VALUES (?, ?, ?)";
        String estudianteSql = "INSERT INTO estudiantes (id_usuario, id_ciudad, nombre, apellido, fecha_nacimiento, telefono_encargado) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = DataBaseConnection.getConnectionDataBase();
            conn.setAutoCommit(false); // Iniciar transacción

            int idUsuarioGenerado = -1;

            // 1. Insertar usuario para credenciales de acceso
            try (PreparedStatement pstmUser = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmUser.setInt(1, ROL_ESTUDIANTE);
                pstmUser.setString(2, estudiante.getCorreoElectronico());
                pstmUser.setString(3, contrasenaHash);
                pstmUser.executeUpdate();

                try (ResultSet generatedKeys = pstmUser.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idUsuarioGenerado = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("No se pudo obtener el id_usuario generado.");
                    }
                }
            }

            // 2. Insertar información personal del estudiante
            try (PreparedStatement pstmEstudiante = conn.prepareStatement(estudianteSql)) {
                pstmEstudiante.setInt(1, idUsuarioGenerado);

                if (estudiante.getIdCiudad() != null) {
                    pstmEstudiante.setInt(2, estudiante.getIdCiudad());
                } else {
                    pstmEstudiante.setNull(2, java.sql.Types.INTEGER);
                }

                pstmEstudiante.setString(3, estudiante.getNombre());
                pstmEstudiante.setString(4, estudiante.getApellido());
                pstmEstudiante.setString(5, estudiante.getFechaNacimiento());
                pstmEstudiante.setString(6, estudiante.getTelefonoEncargado());

                pstmEstudiante.executeUpdate();
            }

            conn.commit(); // Confirmar la transacción
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error durante el rollback: " + rollbackEx.getMessage());
                }
            }
            throw new RuntimeException("Error al guardar el estudiante: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.err.println("Error al restaurar autoCommit: " + ex.getMessage());
                }
            }
        }
    }

    /* =========================================
       4. ACTUALIZAR (crUd)
    ========================================= */
    public boolean update(Estudiante estudiante) {
        String estudianteSql = "UPDATE estudiantes SET nombre = ?, apellido = ?, id_ciudad = ?, "
                + "fecha_nacimiento = ?, telefono_encargado = ? WHERE id_estudiante = ?";

        String usuarioSql = "UPDATE usuarios u "
                + "INNER JOIN estudiantes e ON e.id_usuario = u.id_usuario "
                + "SET u.email = ? WHERE e.id_estudiante = ?";

        Connection conn = null;

        try {
            conn = DataBaseConnection.getConnectionDataBase();
            conn.setAutoCommit(false);

            // 1. Actualizar datos en la tabla estudiantes
            try (PreparedStatement pstmEst = conn.prepareStatement(estudianteSql)) {
                pstmEst.setString(1, estudiante.getNombre());
                pstmEst.setString(2, estudiante.getApellido());

                if (estudiante.getIdCiudad() != null) {
                    pstmEst.setInt(3, estudiante.getIdCiudad());
                } else {
                    pstmEst.setNull(3, java.sql.Types.INTEGER);
                }

                pstmEst.setString(4, estudiante.getFechaNacimiento());
                pstmEst.setString(5, estudiante.getTelefonoEncargado());
                pstmEst.setInt(6, estudiante.getIdEstudiante());
                pstmEst.executeUpdate();
            }

            // 2. Actualizar email en la tabla usuarios
            try (PreparedStatement pstmUser = conn.prepareStatement(usuarioSql)) {
                pstmUser.setString(1, estudiante.getCorreoElectronico());
                pstmUser.setInt(2, estudiante.getIdEstudiante());
                pstmUser.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error durante el rollback: " + rollbackEx.getMessage());
                }
            }
            throw new RuntimeException("Error al actualizar el estudiante: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    System.err.println("Error al restaurar autoCommit: " + ex.getMessage());
                }
            }
        }
    }

    /* =========================================
       5. ELIMINAR (cruD)
    ========================================= */
    public boolean delete(int idEstudiante) {
        // Al borrar el registro de usuarios, la restricción ON DELETE CASCADE
        // elimina automáticamente el estudiante y sus relaciones asociadas.
        String sql = "DELETE u FROM usuarios u "
                + "INNER JOIN estudiantes e ON e.id_usuario = u.id_usuario "
                + "WHERE e.id_estudiante = ?";

        try (Connection conn = DataBaseConnection.getConnectionDataBase(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, idEstudiante);
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estudiante: " + e.getMessage(), e);
        }
    }

    /* =========================================
       MÉTODO AUXILIAR DE MAPEO (ResultSet -> Estudiante)
    ========================================= */
    private Estudiante mapResultSetToEstudiante(ResultSet rs) throws SQLException {
        Estudiante e = new Estudiante();
        e.setIdEstudiante(rs.getInt("id_estudiante"));
        e.setIdUsuario(rs.getInt("id_usuario"));

        // Manejo de id_ciudad que puede ser nulo en BD
        int idCiudad = rs.getInt("id_ciudad");
        e.setIdCiudad(rs.wasNull() ? null : idCiudad);

        e.setNombre(rs.getString("nombre"));
        e.setApellido(rs.getString("apellido"));
        e.setFechaNacimiento(rs.getString("fecha_nacimiento"));
        e.setTelefonoEncargado(rs.getString("telefono_encargado"));
        e.setCorreoElectronico(rs.getString("correo_electronico"));
        e.setNombreSeccion(rs.getString("nombre_seccion"));
        e.setNombreCurso(rs.getString("nombre_curso"));
        e.setNombreDocente(rs.getString("nombre_docente"));
        e.setApellidoDocente(rs.getString("apellido_docente"));

        return e;
    }
}
