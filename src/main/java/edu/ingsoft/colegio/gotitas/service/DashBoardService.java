package main.java.edu.ingsoft.colegio.gotitas.service;

import javafx.collections.ObservableList;
import main.java.edu.ingsoft.colegio.gotitas.model.Estudiante;
import main.java.edu.ingsoft.colegio.gotitas.repository.DashBoardRepository;
import main.java.edu.ingsoft.colegio.gotitas.repository.EstudianteRepository;

public class DashBoardService {

    private EstudianteRepository estudianteRepository;

    public DashBoardService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public ObservableList<Estudiante> listStudent(){
        if (estudianteRepository.findAll() == null) {
            throw new RuntimeException("Sin datos que mostrar");
        } else {
            return estudianteRepository.findAll();
        }
    }
}
