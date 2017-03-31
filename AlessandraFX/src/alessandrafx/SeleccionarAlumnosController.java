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
    private ListView<Alumno> listaAlumnosInscritos;
    @FXML
    private Button botonDescartar;
    @FXML
    private Button botonBorrar;
    @FXML
    private Button botonGuardar;
    
    private ScreensController myController;
    private VentanaControlGrupoController padre;
    private ArrayList<Listagrupo> listaAlumnos;

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
        this.padre = (VentanaControlGrupoController) myController.getScreenPadre();
        listarAlumnosDisponiblesParaAgregar();
    }

    public void listarAlumnosDisponiblesParaAgregar() {
        SistemaAleMC sistema = new SistemaAleMC();
        ArrayList<Alumno> listaAlumnos = new ArrayList<>();
        
        listaAlumnos = sistema.getTodosAlumnosActivos();
        ObservableList listaNombreAlumnos = FXCollections.observableArrayList(listaAlumnos);
        listaAlumnosDisponibles.setItems(listaNombreAlumnos);

        ObservableList listaAlumnosSeleccionados = FXCollections.observableArrayList();
        listaAlumnosInscritos.setItems(listaAlumnosSeleccionados);
        
        ArrayList<Alumno> listaAlumnosRecibidos = padre.getAlumnos();
        if (!listaAlumnosRecibidos.isEmpty()){
            for (Alumno alumno : (ArrayList<Alumno>) listaAlumnosRecibidos) {
                listaNombreAlumnos.remove(alumno);
                listaAlumnosSeleccionados.add(alumno);
            }
        }else{
            System.out.println("asdfg");          
        }
        
        if (padre.isGrupoRecibido()){
            botonGuardar.setText("Guardar cambios");
            botonBorrar.setVisible(true);            
        }else{
            botonGuardar.setText("Registrar");
            botonBorrar.setVisible(false);           
        }

        //clic para agregar alumnos inscritos
        listaAlumnosDisponibles.setOnMouseClicked((Event event) -> {
            Alumno alumnoSeleccionado = (Alumno) listaAlumnosDisponibles.getSelectionModel().getSelectedItems().get(0);
            if (alumnoSeleccionado != null) {
                listaAlumnosSeleccionados.add(alumnoSeleccionado);
                listaNombreAlumnos.remove(listaAlumnosDisponibles.getSelectionModel().getSelectedItem());
            }
        });
        //Clic para quitar alumnos de los inscritos
        listaAlumnosInscritos.setOnMouseClicked((Event event) -> {
            Alumno alumnoSeleccionado = (Alumno) listaAlumnosInscritos.getSelectionModel().getSelectedItems().get(0);
            if (alumnoSeleccionado != null) {
                listaNombreAlumnos.add(alumnoSeleccionado);
                listaAlumnosSeleccionados.remove(listaAlumnosInscritos.getSelectionModel().getSelectedItem());
            }
        });

    }

    private List<Alumno> pasarList() throws DatoFaltante {
        List<Alumno> listaAlumnosInsc = new ArrayList<>();
        for (Alumno alumno : listaAlumnosInscritos.getItems()) {
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
            List<Alumno> listaAlumnos = pasarList();
            padre.guardarGrupo(listaAlumnos);
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
        padre.borrarVentana();
    }

    @FXML
    private void botonBorrarPresionado(MouseEvent event) {
        padre.borrarVentana();
    }

}
