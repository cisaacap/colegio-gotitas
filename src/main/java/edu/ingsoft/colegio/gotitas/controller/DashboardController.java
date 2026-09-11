package main.java.edu.ingsoft.colegio.gotitas.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import main.java.edu.ingsoft.colegio.gotitas.service.DashBoardService;
import main.java.edu.ingsoft.colegio.gotitas.util.SceneManager;

public class DashboardController implements Initializable {

    private final DashBoardService dashboardService;
    private final SceneManager sceneManager;

    @FXML
    private TableView<Estudiante> tvEstudiantes;

    // Cambiado de String a Integer para coincidir con la propiedad de Estudiante
    @FXML
    private TableColumn<Estudiante, Integer> tvColumnIdEstudiante;
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

    @FXML
    private Button btnCrear;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCerrarSesion;

    public DashboardController(DashBoardService dashboardService, SceneManager sceneManager) {
        this.dashboardService = dashboardService;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTableColumns();
        handleLoadTableStudent();
    }

    private void configureTableColumns() {
        tvColumnIdEstudiante.setCellValueFactory(new PropertyValueFactory<>("idEstudiante"));
        tvColumnNombreEstudiante.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tvColumnApellidoEstudiante.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tvColumnCorreo.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));
        tvColumnSeccion.setCellValueFactory(new PropertyValueFactory<>("nombreSeccion"));
        tvColumnCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        tvColumnNombreDocente.setCellValueFactory(new PropertyValueFactory<>("nombreDocente"));
        tvColumnApellidoDocente.setCellValueFactory(new PropertyValueFactory<>("apellidoDocente"));
    }

    private void handleLoadTableStudent() {
        try {
            tvEstudiantes.setItems(dashboardService.listStudent());
        } catch (RuntimeException e) {
            tvEstudiantes.getItems().clear();
            sceneManager.showInfoAlert("Información", "Tabla Estudiantes", e.getMessage(), Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void handleCrear() {
        try {
            // Abre el formulario modal para registrar un nuevo estudiante
            // sceneManager.showEstudianteFormView(null);
            handleLoadTableStudent(); // Recarga la tabla tras guardar
        } catch (Exception e) {
            sceneManager.showInfoAlert("Error", "Error al abrir el formulario", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleActualizar() {
        Estudiante seleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            sceneManager.showInfoAlert("Atención", "Selección requerida", "Por favor, seleccione un estudiante de la tabla para editar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Abre el formulario modal pasando la instancia seleccionada
            // sceneManager.showEstudianteFormView(seleccionado);
            handleLoadTableStudent(); // Recarga la tabla
        } catch (Exception e) {
            sceneManager.showInfoAlert("Error", "Error al actualizar datos", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleEliminar() {
        Estudiante seleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            sceneManager.showInfoAlert("Atención", "Selección requerida", "Debe seleccionar un estudiante para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        // Confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar al estudiante "
                + seleccionado.getNombre() + " " + seleccionado.getApellido() + "?");

        Optional<ButtonType> result = confirmacion.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Pasa el int primitivo directo al método
                boolean eliminado = dashboardService.deleteStudent(seleccionado.getIdEstudiante());
                if (eliminado) {
                    sceneManager.showInfoAlert("Éxito", "Estudiante eliminado", "El registro se ha eliminado correctamente.", Alert.AlertType.INFORMATION);
                    handleLoadTableStudent();
                }
            } catch (Exception e) {
                sceneManager.showInfoAlert("Error", "No se pudo eliminar", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleCerrarSesion() {
        try {
            sceneManager.showLoginView();
        } catch (Exception e) {
            sceneManager.showInfoAlert("Error", "No se pudo cerrar sesión", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
