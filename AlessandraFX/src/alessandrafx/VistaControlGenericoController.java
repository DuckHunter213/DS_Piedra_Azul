/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import alessandramc.SistemaAleMC;
import entidades.Alumno;
import entidades.Colaborador;
import entidades.Grupo;
import entidades.Promociones;
import util.ControlledScreen;
import util.ScreensController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import util.FXGenerico;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class VistaControlGenericoController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private Pane PanelControlGenerico;
    @FXML
    private Label textoControlDe;
    @FXML
    private FlowPane ScrollPanelInterno;
    @FXML
    private HBox panelBotonera;

    public static final String PATH_INICIAL = "/vistasFXML/";
    public static final String PLANTILLA_VISTA_GENERAL = PATH_INICIAL + "PlantillaVistaGeneral.fxml";
    private ArrayList<PlantillaVistaGeneralController> listaPlantillas;
    private List<Grupo> grupos;
    private List<Alumno> alumnos;
    private List<Colaborador> colaboradores;
    private List<Promociones> promociones;
    private Button botonAgregarElemento;
    private ScreensController myController;
    public MarcoVentanaController padre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ScreensController mainContainer = new ScreensController();
        botonAgregarElemento = new Button();
        this.panelBotonera.getChildren().add(botonAgregarElemento);
        botonAgregarElemento.addEventHandler(ActionEvent.ACTION, event -> clic());
    }
    
    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        this.padre = (MarcoVentanaController) myController.getScreenPadre();
        enviarTipoAPadre();
    }

    public void setTipoControl(String tipoObjeto) {
        grupos = new ArrayList<>();
        alumnos = new ArrayList<>();
        colaboradores = new ArrayList<>();
        listaPlantillas = new ArrayList<>();
        promociones = new ArrayList<>();
        SistemaAleMC sistema = new SistemaAleMC();
        switch (tipoObjeto) {
            case "grupo":
                grupos = sistema.getTodosGruposActivos();
                setGrupo();
                break;
            case "alumno":
                alumnos = sistema.getTodosAlumnosActivos();
                setAlumnos();
                break;
            case "colaborador":
                colaboradores = sistema.getTodosColaboradoresActivos();
                setColaboradores();
                break;
            case "promocion":
                promociones = sistema.getTodasPromocionesActivas();
                setPromociones();
                break;
            default:
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Cargamos mal algo");
                alerta.setHeaderText(null);
                alerta.setContentText("Que pena, algo hemos hecho mal aquí y el botón es incorrecto");
                alerta.showAndWait();
                break;
        }
    }
    
    //<editor-fold defaultstate="collapse" desc="Sobre setear el tipo de objetido de las plantillas">

    //Método exclisivo para Grupos, no debe abarcar ningún otro tipo de objeto, se usa junto a setearPlantilla
    private void setGrupo() {
        botonAgregarElemento.setText("Agregar grupo");
        this.textoControlDe.setText("Control de grupos");
        if (grupos.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Empezamos");
            alerta.setHeaderText(null);
            alerta.setContentText("El sistema es nuevo, prueba añadiendo algunos grupos ;) ");
            alerta.showAndWait();
        } else {
            ScreensController mainContainer;
            for (Grupo grupo : grupos) {
                mainContainer = new ScreensController();
                mainContainer.loadScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, this);
                mainContainer.setScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL);
                FlowPane root = (FlowPane) this.ScrollPanelInterno;
                root.getChildren().add(mainContainer);
            }
        }
    }

    //Método exclisivo para Alumnos, no debe abarcar ningún otro tipo de objeto, se usa junto a setearPlantilla
    private void setAlumnos() {
        botonAgregarElemento.setText("Agregar alumno");
        this.textoControlDe.setText("Control de alumnos");
        if (alumnos.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Empezamos");
            alerta.setHeaderText(null);
            alerta.setContentText("El sistema es nuevo, prueba añadiendo algunos alumnos ;) ");
            alerta.showAndWait();
        } else {
            ScreensController mainContainer;
            for (Alumno alumno : alumnos) {
                mainContainer = new ScreensController();
                mainContainer.loadScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, this);
                mainContainer.setScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL);
                FlowPane root = (FlowPane) this.ScrollPanelInterno;
                root.getChildren().add(mainContainer);
            }
        }
    }

    //Método exclisivo para Alumnos, no debe abarcar ningún otro tipo de objeto, se usa junto a setearPlantilla
    private void setColaboradores() {
        botonAgregarElemento.setText("Agregar colaborador");
        this.textoControlDe.setText("Control de Colaborador");
        if (colaboradores.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Empezamos");
            alerta.setHeaderText(null);
            alerta.setContentText("El sistema es nuevo, prueba añadiendo algunos colaboradores ;) ");
            alerta.showAndWait();
        } else {
            ScreensController mainContainer;
            for (Colaborador colaborador : colaboradores) {
                mainContainer = new ScreensController();
                mainContainer.loadScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, this);
                mainContainer.setScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL);
                FlowPane root = (FlowPane) this.ScrollPanelInterno;
                root.getChildren().add(mainContainer);
            }
        }
    }
    
    //Método exclisivo para Promocion, no debe abarcar ningún otro tipo de objeto, se usa junto a setearPlantilla
    private void setPromociones() {
        botonAgregarElemento.setText("Agregar promoción");
        this.textoControlDe.setText("Control de Promociones");
        if (promociones.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Empezamos");
            alerta.setHeaderText(null);
            alerta.setContentText("El sistema es nuevo, prueba añadiendo algunas promociones ;) ");
            alerta.showAndWait();
        } else {
            ScreensController mainContainer;
            for (Promociones promocion : promociones) {
                mainContainer = new ScreensController();
                mainContainer.loadScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, VistaControlGenericoController.PLANTILLA_VISTA_GENERAL, this);
                mainContainer.setScreen(VistaControlGenericoController.PLANTILLA_VISTA_GENERAL);
                this.ScrollPanelInterno.getChildren().add(mainContainer);
            }
        }
    }
    
    //setear grupo por pantalla
    public void setearPlantilla(PlantillaVistaGeneralController plantilla){
        listaPlantillas.add(plantilla);
        if (!grupos.isEmpty())
            plantilla.setGrupo(grupos.get(listaPlantillas.size()-1));
        if (!alumnos.isEmpty())
            plantilla.setAlumno(alumnos.get(listaPlantillas.size()-1));
        if (!colaboradores.isEmpty())
            plantilla.setColaborador(colaboradores.get(listaPlantillas.size()-1));
        if (!promociones.isEmpty())
            plantilla.setPromociones(promociones.get(listaPlantillas.size()-1));
    }
    
    //</editor-fold>
    
    
    //public void set
    public void recibirElementoClic(PlantillaVistaGeneralController elemento) {
        padre.cambiarVistaElementoSeleccionado(elemento);
    }

    //VistaControlGenerico quiere decir que este elemento, hijo de su padre, es un VistaController y debe tratar diferente
    //a que si fuera un hijo de otro tipo, por ejemplo, pase de asistencia
    public void enviarTipoAPadre() {
        padre.recibirControlHijo(this, "VistaControlGenerico");
    }

    public void clic() {
        padre.nuevoRegistroGenerico();
    }

}
