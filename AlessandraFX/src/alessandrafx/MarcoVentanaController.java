/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import entidades.Grupo;
import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class MarcoVentanaController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private Text tituloSistema;
    @FXML
    private Button menuBotonAsistencia;
    @FXML
    private Button menuBotonIngresos;
    @FXML
    private Button menuBotonEgresos;
    @FXML
    private Button menuBotonReportes;
    @FXML
    private Button menuBotonNotificaciones;
    @FXML
    private Button menuBotonConfiguracion;
    @FXML
    private BorderPane panelPrincipal;
    @FXML
    private Pane panelContenedor;
    @FXML
    private Label tituloSeccion;
    @FXML
    private HBox menuSecciones;
    @FXML
    private MenuItem botonControlGrupo;

    public Pane root;
    private ScreensController myController;
    private String accion, tipoCRUD;
    private PlantillaVistaGeneralController plantillaSeleccionada;
    //public static final String MAIN_SCREEN = "main";
    public static final String PATH_INICIAL = "/vistasFXML/";
    public static final String VISTA_CONTROL_GENERICO = PATH_INICIAL + "VistaControlGenerico.fxml";
    public static final String VENTANA_GRUPO = PATH_INICIAL + "VentanaControlGrupo.fxml";
    public static final String VENTANA_PERSONA = PATH_INICIAL + "VentanaControlPersona.fxml";
    public static final String VENTANA_PROMOCION = PATH_INICIAL + "VentanaControlPromocion.fxml";
    public static final String MAIN_SCREEN_FXML = PATH_INICIAL + "MarcoVentanas.fxml";
    //public static final String POKER_SCREEN = "poker"; 
    //public static final String POKER_SCREEN_FXML = "poker.fxml"; 
    //public static final String ROULETTE_SCREEN = "roulette"; 
    //public static final String ROULETTE_SCREEN_FXML = "roulette.fxml";
    @FXML
    private MenuItem botonControlAlumnos;
    @FXML
    private MenuItem botonControlColaboradores;
    @FXML
    private MenuItem botonControlPromociones;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        root = (Pane) this.panelContenedor;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Item");

        Menu submenu = new Menu("Submenu");
        submenu.getItems().add(new CheckMenuItem("Item 1"));
        submenu.getItems().add(new CheckMenuItem("Item 2"));
        submenu.getItems().add(new CheckMenuItem("Item 3"));

        menu.getItems().add(submenu);

        menuBar.prefWidthProperty().bind(myController.widthProperty());
        menuBar.getMenus().add(menu);
        menuSecciones.getChildren().add(menuBar);
    }

    //VistaControlGenerico quiere decir que lo que recibe es un algo que llevará CRUD y usa plantillas,
    //Si fuera otra cosa, podría ser por ejemplo, pase de asistencia.
    public void recibirControlHijo(FXGenerico hijo, String clase) {
        if (clase.equals("VistaControlGenerico")) {
            VistaControlGenericoController hijoTipo = (VistaControlGenericoController) hijo;
            hijoTipo.setTipoControl(tipoCRUD);
        }
    }

    public void nuevoRegistroGenerico() {
        accion = "nuevo";
        cambiarAVentana();
    }

    public void cambiarVistaElementoSeleccionado(PlantillaVistaGeneralController elemento) {
        accion = "actualizar";
        plantillaSeleccionada = elemento;
        cambiarAVentana();
    }
    
    //Instancia la plantilla del CRUD elegido o el nuevo elemento del CRUD elegido
    public void cambiarAVentana(){
        ScreensController mainContainer = new ScreensController();
        if (tipoCRUD.equals("grupo")) {
            mainContainer.loadScreen(MarcoVentanaController.VENTANA_GRUPO, MarcoVentanaController.VENTANA_GRUPO, this);
            mainContainer.setScreen(MarcoVentanaController.VENTANA_GRUPO);
        } else if (tipoCRUD.equals("alumno") || tipoCRUD.equals("colaborador")) {
            mainContainer.loadScreen(MarcoVentanaController.VENTANA_PERSONA, MarcoVentanaController.VENTANA_PERSONA, this);
            mainContainer.setScreen(MarcoVentanaController.VENTANA_PERSONA);
        }else if (tipoCRUD.equals("promocion")){
            mainContainer.loadScreen(MarcoVentanaController.VENTANA_PROMOCION, MarcoVentanaController.VENTANA_PROMOCION, this);
            mainContainer.setScreen(MarcoVentanaController.VENTANA_PROMOCION);
        }
        root.getChildren().clear();
        root.getChildren().add(mainContainer);        
    }
    
    public PlantillaVistaGeneralController tipoAccion() {
        if (accion.equals("actualizar")) {
            tituloSeccion.setText("Control > " + plantillaSeleccionada.getLabel1());
            return plantillaSeleccionada;
        } else {
            tituloSeccion.setText("Control > Nuevo " + tipoCRUD);
            return new PlantillaVistaGeneralController();
        }
    }
    
    public String recuperarTipoPersona(){
        return this.tipoCRUD;
    }

    @FXML
    public void irAControlAlumnos(ActionEvent event) {
        tipoCRUD = "alumno";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de alumnos");
    }
    
    @FXML
    public void irAControlColaboradores(ActionEvent event) {    
        tipoCRUD = "colaborador";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de colaborador");
    }

    @FXML
    public void irAControlGrupo(ActionEvent event) {
        tipoCRUD = "grupo";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de grupos");
    }
    
    @FXML
    public void irAControlPromociones(ActionEvent event) {
        tipoCRUD = "promocion";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de promociones");
    }
    
    public void crearVistaGenerica(){
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MarcoVentanaController.VISTA_CONTROL_GENERICO, MarcoVentanaController.VISTA_CONTROL_GENERICO, this);
        mainContainer.setScreen(MarcoVentanaController.VISTA_CONTROL_GENERICO);
        root.getChildren().clear();
        root.getChildren().add(mainContainer);        
    }

}
