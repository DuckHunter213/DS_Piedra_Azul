/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import alessandramc.SistemaAleMC;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entidades.Alumno;
import entidades.Capital;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

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
    private String accion, tipoVentana;
    private PlantillaVistaGeneralController plantillaSeleccionada;
    //public static final String MAIN_SCREEN = "main";
    public static final String PATH_INICIAL = "/vistasFXML/";
    public static final String VISTA_CONTROL_GENERICO = PATH_INICIAL + "VistaControlGenerico.fxml";
    public static final String VENTANA_GRUPO = PATH_INICIAL + "VentanaControlGrupo.fxml";
    public static final String VENTANA_PASE_LISTA = PATH_INICIAL + "VentanaPaseLista.fxml";
    public static final String VENTANA_PERSONA = PATH_INICIAL + "VentanaControlPersona.fxml";
    public static final String VENTANA_PROMOCION = PATH_INICIAL + "VentanaControlPromocion.fxml";
    public static final String VENTANA_CONFIGURACION = PATH_INICIAL + "VentanaConfiguracion.fxml";
    public static final String VENTANA_INGRESOS = PATH_INICIAL + "VentanaIngreso.fxml";
    public static final String VENTANA_EGRESOS = PATH_INICIAL + "VentanaEgreso.fxml";
    public static final String MAIN_SCREEN_FXML = PATH_INICIAL + "MarcoVentanas.fxml";
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
            hijoTipo.setTipoControl(tipoVentana);
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
    public void cambiarAVentana() {
        verificarEstadoMensualidad();
        ScreensController mainContainer = new ScreensController();
        if (tipoVentana.equals("grupo")) {
            mainContainer.loadScreen(MarcoVentanaController.VENTANA_GRUPO, MarcoVentanaController.VENTANA_GRUPO, this);
            mainContainer.setScreen(MarcoVentanaController.VENTANA_GRUPO);
        } else if (tipoVentana.equals("alumno") || tipoVentana.equals("colaborador")) {
            mainContainer.loadScreen(MarcoVentanaController.VENTANA_PERSONA, MarcoVentanaController.VENTANA_PERSONA, this);
            mainContainer.setScreen(MarcoVentanaController.VENTANA_PERSONA);
        } else if (tipoVentana.equals("promocion")) {
            mainContainer.loadScreen(MarcoVentanaController.VENTANA_PROMOCION, MarcoVentanaController.VENTANA_PROMOCION, this);
            mainContainer.setScreen(MarcoVentanaController.VENTANA_PROMOCION);
        } else if (tipoVentana.equals("paseLista")) {
            //Nueva ventana para asistencia
            mainContainer.loadScreen(MarcoVentanaController.VENTANA_PASE_LISTA, MarcoVentanaController.VENTANA_PASE_LISTA, this);
            mainContainer.setScreen(MarcoVentanaController.VENTANA_PASE_LISTA);
        }
        root.getChildren().clear();
        root.getChildren().add(mainContainer);
    }

    public PlantillaVistaGeneralController tipoAccion() {
        if (accion.equals("actualizar")) {
            tituloSeccion.setText("Control > " + plantillaSeleccionada.getLabel1());
            return plantillaSeleccionada;
        } else {
            tituloSeccion.setText("Control > Nuevo " + tipoVentana);
            return new PlantillaVistaGeneralController();
        }
    }

    public String recuperarTipoPersona() {
        return this.tipoVentana;
    }

    @FXML
    public void irAControlAlumnos(ActionEvent event) {
        tipoVentana = "alumno";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de alumnos");
    }

    @FXML
    public void irAControlColaboradores(ActionEvent event) {
        tipoVentana = "colaborador";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de colaborador");
    }

    @FXML
    public void irAControlGrupo(ActionEvent event) {
        tipoVentana = "grupo";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de grupos");
    }

    @FXML
    public void irAControlPromociones(ActionEvent event) {
        tipoVentana = "promocion";
        crearVistaGenerica();
        tituloSeccion.setText("Control > Control de promociones");
    }

    @FXML
    public void irAAsistencia(MouseEvent event) {
        tipoVentana = "paseLista";
        crearVistaGenerica();
        tituloSeccion.setText("Pase de Asistencia");
    }

    public void crearVistaGenerica() {
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MarcoVentanaController.VISTA_CONTROL_GENERICO, MarcoVentanaController.VISTA_CONTROL_GENERICO, this);
        mainContainer.setScreen(MarcoVentanaController.VISTA_CONTROL_GENERICO);
        root.getChildren().clear();
        root.getChildren().add(mainContainer);
    }

    @FXML
    public void irAControl(MouseEvent event) {
        tipoVentana = "configuracion";
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MarcoVentanaController.VENTANA_CONFIGURACION, MarcoVentanaController.VENTANA_CONFIGURACION, this);
        mainContainer.setScreen(MarcoVentanaController.VENTANA_CONFIGURACION);
        root.getChildren().clear();
        root.getChildren().add(mainContainer);
        tituloSeccion.setText("Configuración general del sistema");
    }

    @FXML
    public void irAIngresos(MouseEvent event) {
        tipoVentana = "ingresos";
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MarcoVentanaController.VENTANA_INGRESOS, MarcoVentanaController.VENTANA_INGRESOS, this);
        mainContainer.setScreen(MarcoVentanaController.VENTANA_INGRESOS);
        root.getChildren().clear();
        root.getChildren().add(mainContainer);
        tituloSeccion.setText("Configuración general del sistema");
        verificarEstadoMensualidad();
    }

    @FXML
    public void irAEgresos(MouseEvent event) {
        tipoVentana = "egresos";
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MarcoVentanaController.VENTANA_EGRESOS, MarcoVentanaController.VENTANA_EGRESOS, this);
        mainContainer.setScreen(MarcoVentanaController.VENTANA_EGRESOS);
        root.getChildren().clear();
        root.getChildren().add(mainContainer);
        tituloSeccion.setText("Configuración general del sistema");
    }

    @FXML
    private void generarReporteMenusal(MouseEvent event) {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("Reporte.pdf");
            File file = fileChooser.showSaveDialog(null);
            file.getParentFile().mkdir();

            Document document = new Document();
            try {
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(VentanaEgresoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (DocumentException ex) {
                Logger.getLogger(VentanaEgresoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            document.open();
            PdfPTable table = new PdfPTable(3);
            PdfPCell cell = new PdfPCell(new Phrase("Factura emitida el " + new Date()));
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            SistemaAleMC sistema = new SistemaAleMC();
            Double balanceMonetario = 0.0;

            //Para salidas de dinero
            ArrayList<Capital> capitales = sistema.getRegistrosCapitalTipo('S');
            cell = new PdfPCell(new Phrase("Gastos y pagos realizados"));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //Agrega los registros de salidas de dinero
            Double totalEgreso = 0.0;
            if (capitales.isEmpty()) {
                cell = new PdfPCell(new Phrase(" --- "));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(" --- "));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }
            for (Capital capital : capitales) {
                cell = new PdfPCell(new Phrase(capital.getMotivo().toString()));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(capital.getMonto().toString()));
                balanceMonetario -= Double.valueOf(capital.getMonto());
                totalEgreso -= Double.valueOf(capital.getMonto());
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }
            cell = new PdfPCell(new Phrase("TOTAL DE EGRESOS"));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(totalEgreso.toString()));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            //Para Pago de mensualidades
            capitales = sistema.getRegistrosCapitalTipo('M');
            cell = new PdfPCell(new Phrase("Pagos de mensualidad recibidos"));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //Agrega los registros de pago de mensualidades de los alumnos
            Double totalIngresoMen = 0.0;
            if (capitales.isEmpty()) {
                cell = new PdfPCell(new Phrase(" --- "));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(" --- "));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }
            for (Capital capital : capitales) {
                cell = new PdfPCell(new Phrase(capital.getMotivo().toString()));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(capital.getMonto().toString()));
                balanceMonetario += Double.valueOf(capital.getMonto());
                totalIngresoMen += Double.valueOf(capital.getMonto());
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }
            cell = new PdfPCell(new Phrase("TOTAL DE MENSUALIDADES"));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(totalIngresoMen.toString()));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            //Para Pago de anualidades
            capitales = sistema.getRegistrosCapitalTipo('A');
            cell = new PdfPCell(new Phrase("Pagos de anualidades recibidos"));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //Agrega los registros de pago de anualidades de los alumnos
            Double totalIngresoAnu = 0.0;
            if (capitales.isEmpty()) {
                cell = new PdfPCell(new Phrase(" --- "));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(" --- "));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }
            for (Capital capital : capitales) {
                cell = new PdfPCell(new Phrase(capital.getMotivo().toString()));
                cell.setColspan(2);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(capital.getMonto().toString()));
                balanceMonetario += Double.valueOf(capital.getMonto());
                totalIngresoAnu += Double.valueOf(capital.getMonto());
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                table.addCell(cell);
            }
            cell = new PdfPCell(new Phrase("TOTAL DE ANUALIDADES"));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(totalIngresoAnu.toString()));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            //PARA EL BALANCE MONETARIO
            capitales = sistema.getRegistrosCapitalTipo('A');
            cell = new PdfPCell(new Phrase("Total"));
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setColspan(3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            //Agrega la celda del total monetario
            if (balanceMonetario == (Double) 0.0) {
                cell = new PdfPCell(new Phrase("No hubieron movimientos este mes"));
            } else {
                cell = new PdfPCell(new Phrase(balanceMonetario.toString()));
            }
            cell.setColspan(3);
            cell.setBackgroundColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            try {
                document.add(table);
            } catch (DocumentException ex) {
                Logger.getLogger(VentanaEgresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.close();
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Corre a ver tu PDF!");
            alerta.setHeaderText(null);
            alerta.setContentText("El PDF ha sido generado, puedes revisarlo");
            alerta.showAndWait();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Lo sentimos");
            alerta.setHeaderText(null);
            alerta.setContentText("La ubicación seleccionada no es válida y por tanto, no se generará el PDF");
            alerta.showAndWait();
        }
    }

    private void verificarEstadoMensualidad() {
        SistemaAleMC sistema = new SistemaAleMC();
        ArrayList<Alumno> result = sistema.getTodosAlumnosActivos();
        for (Alumno alumno : result) {
            int valor = sistema.estadoMensualidad(alumno);
            if (valor == 1) {
                String monto = sistema.calcularPagoMensualidad(alumno);
                sistema.guardarAdeudoAlumno(alumno, monto);
            }
        }

    }
}
