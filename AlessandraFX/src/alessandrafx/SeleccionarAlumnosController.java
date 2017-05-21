/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;
import Excepciones.DatoFaltante;
import alessandramc.SistemaAleMC;
import entidades.Alumno;
import entidades.Listagrupo;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class SeleccionarAlumnosController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private ListView<Alumno> listaAlumnosDisponibles;
    @FXML
    private ListView<Alumno> listaAlumnosAgregados;
    @FXML
    private Button botonDescartar;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonGuardar;
    @FXML
    private Label labelAlumnosAgregados;
    @FXML
    private Label labelAlumnosDisponibles;

    private ScreensController myController;
    private VentanaControlGrupoController padre;
    private VentanaPaseListaController padreLista;
    private ArrayList<Listagrupo> listaAlumnos;
    private ArrayList<Alumno> listaAlumnosRetirados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ScreensController mainContainer = new ScreensController();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        if (myController.getScreenPadre() instanceof VentanaControlGrupoController) {
            this.padre = (VentanaControlGrupoController) myController.getScreenPadre();
        } else {
            this.padreLista = (VentanaPaseListaController) myController.getScreenPadre();
        }
        listarAlumnosDisponiblesParaAgregar();
    }

    public void listarAlumnosDisponiblesParaAgregar() {
        SistemaAleMC sistema = new SistemaAleMC();
        ArrayList<Alumno> listaAlumnos = new ArrayList<>();
        //Si la ventana es para editar grupo
        if (padre != null) {

            //Lista de los alumnos disponibles en el grupo
            listaAlumnos = sistema.getTodosAlumnosActivos();
            ObservableList listaAlumnosDisponiblesObsCol = FXCollections.observableArrayList(listaAlumnos);
            listaAlumnosDisponibles.setItems(listaAlumnosDisponiblesObsCol);

            listaAlumnosRetirados = new ArrayList<>();
            //Lista de alumnos que ya existian en el grupo
            ObservableList listaAlumnosInscritosObsCol = FXCollections.observableArrayList();
            listaAlumnosAgregados.setItems(listaAlumnosInscritosObsCol);

            //Revisa que no hayan alumnos inscritos que sea posible inscribir y los guarda en la lista de retirados
            ArrayList<Alumno> listaAlumnosRecibidos = padre.getAlumnos();
            if (!listaAlumnosRecibidos.isEmpty()) {
                for (Alumno alumno : (ArrayList<Alumno>) listaAlumnosRecibidos) {
                    listaAlumnosDisponiblesObsCol.remove(alumno);
                    listaAlumnosInscritosObsCol.add(alumno);
                }
            }

            //clic para agregar alumnos inscritos
            listaAlumnosDisponibles.setOnMouseClicked((Event event) -> {
                Alumno alumnoSeleccionado = (Alumno) listaAlumnosDisponibles.getSelectionModel().getSelectedItems().get(0);
                if (alumnoSeleccionado != null) {
                    listaAlumnosInscritosObsCol.add(alumnoSeleccionado);
                    listaAlumnosRetirados.remove(listaAlumnosDisponibles.getSelectionModel().getSelectedItem());
                    listaAlumnosDisponiblesObsCol.remove(listaAlumnosDisponibles.getSelectionModel().getSelectedItem());
                }
            });
            //Clic para quitar alumnos de los inscritos
            listaAlumnosAgregados.setOnMouseClicked((Event event) -> {
                Alumno alumnoSeleccionado = (Alumno) listaAlumnosAgregados.getSelectionModel().getSelectedItems().get(0);
                if (alumnoSeleccionado != null) {
                    listaAlumnosDisponiblesObsCol.add(alumnoSeleccionado);
                    listaAlumnosRetirados.add(listaAlumnosAgregados.getSelectionModel().getSelectedItem());
                    listaAlumnosInscritosObsCol.remove(listaAlumnosAgregados.getSelectionModel().getSelectedItem());
                }
            });

            //Cambia los textos de los botones
            if (padre.isGrupoRecibido()) {
                botonGuardar.setText("Guardar cambios");
                botonBorrar.setVisible(true);
            } else {
                botonGuardar.setText("Registrar");
                botonBorrar.setVisible(false);
            }
            //Si la ventana es para pasar lista
        } else {
            labelAlumnosAgregados.setText("Alumnos con asistencia");
            labelAlumnosDisponibles.setText("Alumnos en el grupo");

            //Lista de los alumnos disponibles en el grupo
            listaAlumnos = padreLista.getAlumnosInscritos();
            ObservableList listaAlumnosDisponiblesObsCol = FXCollections.observableArrayList(listaAlumnos);
            listaAlumnosDisponibles.setItems(listaAlumnosDisponiblesObsCol);

            //Lista de alumnos que ya existian en el grupo
            ObservableList listaAlumnosInscritosObsCol = FXCollections.observableArrayList();
            listaAlumnosAgregados.setItems(listaAlumnosInscritosObsCol);

            System.out.println("Without error");

            //clic para agregar alumnos
            listaAlumnosDisponibles.setOnMouseClicked((Event event) -> {
                Alumno alumnoSeleccionado = (Alumno) listaAlumnosDisponibles.getSelectionModel().getSelectedItems().get(0);
                if (alumnoSeleccionado != null) {
                    listaAlumnosDisponiblesObsCol.remove(listaAlumnosDisponibles.getSelectionModel().getSelectedItem());
                    listaAlumnosInscritosObsCol.add(alumnoSeleccionado);
                }
            });
            //Clic para quitar alumnos de los agregados
            listaAlumnosAgregados.setOnMouseClicked((Event event) -> {
                Alumno alumnoSeleccionado = (Alumno) listaAlumnosAgregados.getSelectionModel().getSelectedItems().get(0);
                if (alumnoSeleccionado != null) {
                    listaAlumnosDisponiblesObsCol.add(alumnoSeleccionado);
                    listaAlumnosInscritosObsCol.remove(listaAlumnosAgregados.getSelectionModel().getSelectedItem());
                }
            });

            //Cambia los textos de los botones
            botonGuardar.setText("Guardar Pase de lista");
            botonBorrar.setVisible(false);
            botonBorrar.setText("Cancelar");
        }

    }

    private List<Alumno> recuperarListaInscritos() throws DatoFaltante {
        List<Alumno> listaAlumnosInsc = new ArrayList<>();
        for (Alumno alumno : listaAlumnosAgregados.getItems()) {
            listaAlumnosInsc.add(alumno);
        }
        if (listaAlumnosInsc.size() == 0) {
            throw new DatoFaltante("Ups, No hay alumnos seleccionados", "Faltan datos");
        }
        return listaAlumnosInsc;
    }

    @FXML
    private void botonGuardarPresionado(MouseEvent event) {
        try {
            if (padre != null) {
                List<Alumno> listaAlumnos = recuperarListaInscritos();
                padre.guardarGrupo(listaAlumnos, listaAlumnosRetirados);
            } else {
                List<Alumno> listaAlumnos = recuperarListaInscritos();
                padreLista.guardarAsistencia(listaAlumnos);
            }
        } catch (DatoFaltante e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle(e.getDescripcion());
            alerta.setHeaderText(null);
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void botonDescartarPresionado(MouseEvent event) {
        if (padre != null)
            padre.borrarVentana();
        else
            padreLista.borrarVentana();
    }

    @FXML
    private void botonBorrarPresionado(MouseEvent event) {
        padre.borrarVentana();
    }

}
