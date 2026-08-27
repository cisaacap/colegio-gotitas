package main.java.edu.ingsoft.colegio.gotitas;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class MainApp extends Application{    
    //del lado izquierdo de extends siempre esta
    //la clase hija y en la derecha la padre
    
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage)throws Exception{
        this.primaryStage = primaryStage;
        //esta.variableGlobal = variableLocal
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginView();
        primaryStage.show();
    }
    
    public static void main(String[] args) throws Exception {
        launch();
    }
}