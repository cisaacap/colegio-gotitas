package main.java.edu.ingsoft.colegio.gotitas.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//clase con patrón de diseño Singleton
public class DataBaseConnection {
    
    //atributos - Connection es una clase, por defecto es nulo
    //o sea que existe y no ha sido construida
    private static Connection connection;
    
    
    //constructor
    /*el constructor de diseño tiene que ser privado, 
    esto para evitar que se creen instancias de esta clase.*/
    private DataBaseConnection(){}
    
    
    //metodo -  tiene que ser public para acceder a él
    public static Connection getConnectionDataBase() throws SQLException{
        
     if(connection == null || connection.isClosed()){
     //si la conexion es nula o esta cerrada la creamos:
     
        connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/colegio_gotitas_in4bm?useSSL=false&serverTimezone=UTC", "IN4BM", "$DmynM4A");
     }
        return connection;
    }
    //para acceder a un atributo estático usamos el nombre de la clase.
}
