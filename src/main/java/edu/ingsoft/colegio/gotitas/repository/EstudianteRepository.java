package main.java.edu.ingsoft.colegio.gotitas.repository;

import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;

public class EstudianteRepository {

    public ObservableList<Estudiante> findAll() {
        String sql = "select\n"
                + "e.id_estudiante, \n"
                + "e.nombre, \n"
                + "e.apellido, \n"
                + "e.correo_electronico,\n"
                + "s.nombre_seccion,\n"
                + "c.nombre_curso,\n"
                + "d.nombre, \n"
                + "d.apellido from asignacion_cursos as ac inner join matriculas as m on m\n"
                + ".id_matricula = ac.id_matricula\n"
                + "inner join secciones as s on s.id_seccion = ac.id_seccion\n"
                + "inner join cursos as c on c.id_curso = ac.id_curso\n"
                + "inner join docentes as d on d.id_docente = ac.id_docente\n"
                + "inner join estudiantes as e on e.id_estudiante = m.id_estudiante;";

        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            ObservableList<Estudiante> studentList = FXCollections.observableArrayList();

            while (rs.next()) {
                studentList.add(new Estudiante(
                        rs.getString("id_estudiante"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo_electronico"),
                        rs.getString("nombre_seccion"),
                        rs.getString("nombre_curso"),
                        rs.getString("nombre"),
                        rs.getString("apellido")
                ));
            }
            return studentList;
        } catch (SQLException e) {
            throw new RuntimeException("error en la consulta: " + e.getMessage());
        }
    }
}
