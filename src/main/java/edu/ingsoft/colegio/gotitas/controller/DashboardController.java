package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import main.java.edu.ingsoft.colegio.gotitas.service.DashBoardService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class DashboardController implements Initializable {
    private DashBoardService dashboardService;
    private SceneManager sceneManager;

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> tvColumnIdEstudiante;
    @FXML
    private TableColumn<Estudiante, String> tvColumnNombreEstudiante;
    @FXML
    private TableColumn<Estudiante, String> tvColumnApellidoEstudiante;
    @FXML
    private TableColumn<Estudiante, String> tvColumnCorreo;
    @FXML
    private TableColumn<Estudiante, String> tvColumnSeccion;
    @FXML
    private TableColumn<Estudiante, String> tvColumnCurso;
    @FXML
    private TableColumn<Estudiante, String> tvColumnNombreDocente;
    @FXML
    private TableColumn<Estudiante, String> tvColumnApellidoDocente;
    
    public DashboardController(DashBoardService dashboardService, SceneManager sceneManager){
        this.dashboardService = dashboardService;
        this.sceneManager = sceneManager;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        handleLoadTableStudent();    
    }
    
    private void handleLoadTableStudent(){
        tvColumnIdEstudiante.setCellValueFactory(new PropertyValueFactory<>("idEstudiante"));
        tvColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tvColumnApellidoEstudiante.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tvColumnCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        tvColumnSeccion.setCellValueFactory(new PropertyValueFactory<>("nombreSeccion"));
        tvColumnCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        tvColumnNombreDocente.setCellValueFactory(new PropertyValueFactory<>("nombreDocente"));
        tvColumnApellidoDocente.setCellValueFactory(new PropertyValueFactory<>("apellidoDocente"));
        tvEstudiantes.setItems(dashboardService.listStudent());
    }
}
