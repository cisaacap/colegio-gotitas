package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;
import javafx.scene.control.Alert;
import main.java.edu.ingsoft.colegio.gotitas.dto.request.LoginRequest;
import main.java.edu.ingsoft.colegio.gotitas.dto.response.LoginResponse;

public class LoginController implements Initializable {

    //atributos
    private final AuthService authService;
    private final SceneManager sceneManager;

    //creamos dependencias hacia los botones
    @FXML
    private TextField txtFieldEmail;

    @FXML
    private TextField txtFieldPass;

    //constructor, inyectamos la dependencia
    public LoginController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.err.println("TODO LO QUE ESTE ACA SE EJECUTA CUANDO SE MUESTRA LA VISTA");
    }

    //METODOS
    public void hadleLogin() throws Exception {
        if (txtFieldEmail.getText().isEmpty() || txtFieldPass.getText().isEmpty()) {
            //lanzamos una excepcion
            sceneManager.showInfoAlert("Campos faltantes", "Revisar la informacion", "uno o mas campos estan vacios", Alert.AlertType.INFORMATION);
        } else {
            try {
                LoginResponse responseService = authService.login(new LoginRequest(txtFieldEmail.getText(), txtFieldPass.getText()));
                LoginResponse userLogged = new LoginResponse(responseService.getNombre(), responseService.getApellido());
                sceneManager.showInfoAlert("Bienvendo a Gotitas del Saber", "Inicio exitoso", "Bienvenido: " + userLogged.getNombre(), Alert.AlertType.INFORMATION);
                sceneManager.showDashBoardView();
            } catch (RuntimeException e) {
                sceneManager.showInfoAlert("Datos incorrectos", "Revisa tu informacion", "Intenta de nuevo", Alert.AlertType.INFORMATION);
            }

        }//llamar al nuevo metodo
    }

    public void handleRegister() throws Exception {
        sceneManager.showRegisterView();
    }
}

//metodo de la nueva escena

/*LoginResponse responseService = authService.login(new LoginRequest(txtFieldEmail.getText(), txtFieldPass.getText()));
            //se guarda en variable si lo usaremos mas adelante,  
            //en este caso, solo creamos un objeto
            LoginResponse userLogged = new LoginResponse(responseService.getNombre(), responseService.getApellido());
            System.out.println("Usuario loggeado: " + userLogged.getNombre() + ", Apellido usuario: " + userLogged.getApellido());*/
