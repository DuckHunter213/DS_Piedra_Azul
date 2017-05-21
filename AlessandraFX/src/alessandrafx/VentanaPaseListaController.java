/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import alessandramc.SistemaAleMC;
import entidades.Alumno;
import entidades.Grupo;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class VentanaPaseListaController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private Pane panelAlumnos;

    
    private ScreensController myController;
    private MarcoVentanaController padre;
    private PlantillaVistaGeneralController plantillaSeleccionada;
    private SistemaAleMC sistema;
    private Grupo grupoRegistrado;
    public static final String PATH_INICIAL = "/vistasFXML/";
    public static final String SELECCIONAR_ALUMNOS_FXML = PATH_INICIAL + "SeleccionarAlumnos.fxml";
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sistema = new SistemaAleMC();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        padre = (MarcoVentanaController) myController.getScreenPadre();
        plantillaSeleccionada = padre.tipoAccion();
        grupoRegistrado = plantillaSeleccionada.getGrupo();
        
        //Agrega la sección de alumnos
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(VentanaControlGrupoController.SELECCIONAR_ALUMNOS_FXML, VentanaControlGrupoController.SELECCIONAR_ALUMNOS_FXML, this);
        mainContainer.setScreen(VentanaControlGrupoController.SELECCIONAR_ALUMNOS_FXML);

        Pane root = (Pane) this.panelAlumnos;
        root.getChildren().add(mainContainer);
    }
    
    public void guardarAsistencia(List<Alumno> listaAlumno) {
        try {
            ArrayList<Alumno> lista = new ArrayList<>();
            for (Alumno alumno : listaAlumno) {
                lista.add(alumno);
            }
            sistema.agregarAsistencia(grupoRegistrado, lista);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Pase de lista registrado ;)");
            alerta.setHeaderText(null);
            alerta.setContentText("Hemos guardado la asistencia");
            alerta.showAndWait();
            this.padre.root.getChildren().clear();

            this.padre.irAControlGrupo(new ActionEvent());
        } catch (Exception e) {
//            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
//            alerta.setTitle(e.getMessage());
//            alerta.setHeaderText(null);
//            alerta.setContentText(e.getDescripcion());
//            alerta.showAndWait();
        }
    }    
    
    public ArrayList<Alumno> getAlumnosInscritos() {
        ArrayList<Alumno> alumnos = sistema.getAlumnosInscritosActivos(grupoRegistrado);
        return alumnos;
    }
    
    public void borrarVentana(){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¿Seguro?");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que quiere hacerlo?");
        
        try {
            Optional<ButtonType> respuesta = alerta.showAndWait();
            if (respuesta.get() == ButtonType.OK) {
                this.padre.root.getChildren().clear();
                this.padre.irAAsistencia(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.NONE, 0, true, true, true, true, true, true, true, true, true, true, null));
                
            } else {
                System.out.println("error");
            }
        } catch (Exception e) {
            System.out.println("El cuadro fue rechazado");
        }
    }
    
}
