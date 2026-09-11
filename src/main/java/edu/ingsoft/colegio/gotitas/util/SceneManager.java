package main.java.edu.ingsoft.colegio.gotitas.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import main.java.edu.ingsoft.colegio.gotitas.controller.DashboardController;
import main.java.edu.ingsoft.colegio.gotitas.controller.LoginController;
import main.java.edu.ingsoft.colegio.gotitas.controller.RegisterController;
import main.java.edu.ingsoft.colegio.gotitas.repository.AuthRepository;
import main.java.edu.ingsoft.colegio.gotitas.repository.EstudianteRepository;
import main.java.edu.ingsoft.colegio.gotitas.service.AuthService;
import main.java.edu.ingsoft.colegio.gotitas.service.DashBoardService;

public class SceneManager {

    //atributos
    private Stage primaryStage;
    private final String FXML_PATH = "/main/resources/view/";

    //constructor
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //metodo
    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "login-view.fxml"));
        loader.setControllerFactory(
                clazz -> {
                    if (clazz == LoginController.class) {
                        AuthRepository authRepository = new AuthRepository();
                        AuthService authService = new AuthService(authRepository);
                        return new LoginController(authService, this);
                    }
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor" + e.getMessage());
                    }
                });
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**/
    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "register-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == RegisterController.class) {
                AuthRepository authRepository = new AuthRepository();
                AuthService authService = new AuthService(authRepository);
                return new RegisterController(this, authService);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("error al crear el constructor" + e.getMessage());
            }
        }
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    //implementa el cambio de scene hacia el dashboard
    public void showDashBoardView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "dashboard-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == DashboardController.class) {
                EstudianteRepository estudianteRepository = new EstudianteRepository();
                DashBoardService dasboardService = new DashBoardService(estudianteRepository);
                return new DashboardController(dasboardService, this);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("error al crear el constructor" + e.getMessage());
            }
        }
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, 1250, 650);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    //venta modal, para mostrar alerta
    public void showInfoAlert(String head, String title, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.initOwner(this.primaryStage);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.setHeaderText(head);
        alert.showAndWait();
    }
}
