package main.java.edu.ingsoft.colegio.gotitas.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.edu.ingsoft.colegio.gotitas.config.DataBaseConnection;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;

public class AuthRepository {

    //atributos
    private boolean sqlStatus = false;

    //constructor
    /*Los métodos son acciones especificas, son tareas
    individuales, algunos métodos solo realizan una 
    tarea, pero no retornan nada son "void", otros métodos,
    realizan tareas, y retornan un tipo de dato primitivo o 
    comúesto (Clase). Divide y venceras: un método debe ser
    encargado de reañizar únicamente una tarea especifica, el
    nombre de ese metodo debe ser modular, directo*/
    
    public LoginResponse findUserByEmail(LoginRequest loginRequest) throws Exception {
        String sql = "select d.nombre, d.apellido, u.contrasena_hash from usuarios as u"
                + " right join docentes as d"
                + " on d.id_docente = u.id_docente"
                + " where email = ? ";
        try (PreparedStatement pstm = DataBaseConnection.getConnectionDataBase().prepareStatement(sql)) {
            pstm.setString(1, loginRequest.getEmail());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {

                return new LoginResponse(rs.getString("nombre"), rs.getString("apellido"), rs.getString("contrasena_hash"));
            }
        } catch (SQLException e) {
            System.out.println("error al encontrar el EMAIL " + e.getMessage());
        }
        return null;
    }
}
