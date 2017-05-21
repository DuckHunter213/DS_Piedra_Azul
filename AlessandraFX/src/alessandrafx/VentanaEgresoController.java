/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import Excepciones.DatoFaltante;
import alessandramc.SistemaAleMC;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entidades.Colaborador;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class VentanaEgresoController extends FXGenerico implements Initializable, ControlledScreen {

    private SistemaAleMC sistema;
    private ScreensController myController;
    private MarcoVentanaController padre;
    private Colaborador colaboradorElegido;
    private int pagoSugerido;

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab mensualidadTab;
    @FXML
    private Button botonSeleccionarColaborador;
    @FXML
    private TextField textFieldPagoColaborador;
    @FXML
    private Button botonCancelarPagoColaborador;
    @FXML
    private Button botonGuardarPagoColaborador;
    @FXML
    private Tab anualidadTab;
    @FXML
    private TextField textFieldMontoGasto;
    @FXML
    private TextArea textFieldMotivoGasto;
    @FXML
    private Button botonCancelarGasto;
    @FXML
    private Button botonGuardarGasto;
    @FXML
    private Label labelNombreColaborador;
    @FXML
    private Label labelPagoIdeal;

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
    }

    @FXML
    private void seleccionarColaborador(MouseEvent event) {
        Dialog ventana = new Dialog();
        ventana.setTitle("Selecciona un colaborador");
        ventana.setHeaderText("Selecciona un colaborador");
        ventana.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        ventana.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //Creación de lista que irá dentro del tableView
        ArrayList<Colaborador> listaColaboradores = sistema.getTodosColaboradoresActivos();
        ObservableList listaColaboradoresObs = FXCollections.observableArrayList(listaColaboradores);
        //Creación del grid dónde irá el tableView
        GridPane panel = new GridPane();
        //Creación del TableView
        TableView tabla = new TableView();
        tabla.setMaxHeight(500);
        //Columna para el nombre
        TableColumn columnaNombre = new TableColumn();
        columnaNombre.setPrefWidth(150);
        columnaNombre.setText("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        //Columna para la cantidad
        TableColumn columnaApPat = new TableColumn();
        columnaApPat.setPrefWidth(100);
        columnaApPat.setText("Apellido paterno");
        columnaApPat.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        //Columna para el porcentaje de descuento
        TableColumn columnaMatricula = new TableColumn();
        columnaMatricula.setPrefWidth(100);
        columnaMatricula.setText("Matrícula");
        columnaMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaColaborador"));
        //Columna para el porcentaje de descuento
        TableColumn columnaTelefono = new TableColumn();
        columnaTelefono.setPrefWidth(100);
        columnaTelefono.setText("teléfono");
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        //Columna para el porcentaje de descuento
        TableColumn columnaTitulo = new TableColumn();
        columnaTitulo.setPrefWidth(100);
        columnaTitulo.setText("Título");
        columnaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        //Asigna la lista al TableView, este al grid y este a la ventana
        tabla.setItems(listaColaboradoresObs);
        tabla.getColumns().addAll(columnaNombre, columnaApPat, columnaMatricula, columnaTelefono, columnaTitulo);
        panel.getChildren().add(tabla);
        ventana.getDialogPane().setContent(panel);

        //clic para agregar alumnos inscritos
        tabla.getSelectionModel().selectedItemProperty().addListener((ObservableValue observableValue, Object oldValue, Object newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            Colaborador promocion = (Colaborador) newValue;
            labelNombreColaborador.setText(promocion.getNombre());
            colaboradorElegido = (Colaborador) newValue;
            labelPagoIdeal.setText(sistema.calcularPagoColaborador(colaboradorElegido));
            textFieldPagoColaborador.setText(sistema.calcularPagoColaboradorPeriodo(colaboradorElegido));
        });
        ventana.showAndWait();
    }

    @FXML
    private void clicCancelarPagoColaborador(MouseEvent event) {
        padre.irAEgresos(event);
    }

    private int recuperarPagoColaborador() throws DatoFaltante {
        int pagoColaborador = 0;
        try {
            pagoColaborador = Integer.parseInt(textFieldPagoColaborador.getText());
        } catch (Exception e) {
            throw new DatoFaltante("Dato erroneo", "Parece que algo diferente al dinero no es una forma de pago aquí");
        }
        return pagoColaborador;
    }

    private int recuperarTotalGasto() throws DatoFaltante {
        int montoPagado = 0;
        try {
            montoPagado = Integer.parseInt(textFieldMontoGasto.getText());
        } catch (Exception e) {
            throw new DatoFaltante("Dato erroneo", "Parece que algo diferente al dinero no es una forma de pago aquí");
        }
        return montoPagado;
    }

    private String recuperarMotivoGasto() throws DatoFaltante {
        String motivoGasto = "";
        try {
            motivoGasto = textFieldMotivoGasto.getText();
        } catch (Exception e) {
            throw new DatoFaltante("Dato erroneo", "Algo no está bien, menos caracteres por favor");
        }
        if (motivoGasto.length() > 560) {
            throw new DatoFaltante("Dato erroneo", "Algo no está bien, menos caracteres por favor");
        }
        return motivoGasto;
    }

    @FXML
    private void clicGuardarPagoColaborador(MouseEvent event) {
        try {
            int pagoColaborador = recuperarPagoColaborador();
            String motivo = "Al colaborador " + colaboradorElegido.toString()
                    + " se la ha hecho un pago de " + pagoColaborador + " en la fecha "
                    + new Date();
            generarReportePagoColaborador(pagoColaborador, pagoSugerido);
            //S es salida de capital
            sistema.agregarCapital(String.valueOf(pagoColaborador), motivo, 'S');
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Pago registrado");
            alerta.setHeaderText(null);
            alerta.setContentText("Parece que alguien irá con dinero a casa ;)");
            alerta.showAndWait();
            padre.irAEgresos(event);
        } catch (DatoFaltante e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle(e.getMessage());
            alerta.setHeaderText(null);
            alerta.setContentText(e.getDescripcion());
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Lo sentimos");
            alerta.setHeaderText(null);
            alerta.setContentText("Parece que tenemos problemas con la BD :s");
            alerta.showAndWait();
        }
    }

    @FXML
    private void clicCancelarOtroGasto(MouseEvent event) {
        padre.irAEgresos(event);
    }

    @FXML
    private void clicGuardarOtroGasto(MouseEvent event) {
        try {
            int montoPagado = recuperarTotalGasto();
            String motivoGasto = recuperarMotivoGasto();
            generarReporteOtroGasto(montoPagado, motivoGasto);
            //S es salida de capital
            sistema.agregarCapital(String.valueOf(montoPagado), motivoGasto, 'S');
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Pago registrado");
            alerta.setHeaderText(null);
            alerta.setContentText("El gasto se ha registrado ;)");
            alerta.showAndWait();

            padre.irAEgresos(event);
        } catch (DatoFaltante e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle(e.getMessage());
            alerta.setHeaderText(null);
            alerta.setContentText(e.getDescripcion());
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Lo sentimos");
            alerta.setHeaderText(null);
            alerta.setContentText("Parece que tenemos problemas con la BD :s");
            alerta.showAndWait();
        }
    }

    private void generarReportePagoColaborador(int pagoColaborador, int pagoSugerido) throws DatoFaltante {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("facturaNueva.pdf");
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

            //Agrega el pago IDEAL
            cell = new PdfPCell(new Phrase("Pago sugerido recibido"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Pago sugerido"));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            //Agrega el pago realizado
            cell = new PdfPCell(new Phrase("Pago realizado"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(pagoColaborador)));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            try {
                document.add(table);
            } catch (DocumentException ex) {
                Logger.getLogger(VentanaEgresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.close();

        } catch (Exception e) {
            throw new DatoFaltante("ubicación no válida", "La ubicación que has escogido no es válida");
        }

    }

    private void generarReporteOtroGasto(int cantidad, String motivo) throws DatoFaltante {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("facturaHecha.pdf");
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

            //Agrega el monto pagado
            cell = new PdfPCell(new Phrase("Pago realizado"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(cantidad)));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            //Agrega el motivo del pago
            cell = new PdfPCell(new Phrase("Motivo: \n" + motivo));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell.setColspan(3);
            table.addCell(cell);
            try {
                document.add(table);
            } catch (DocumentException ex) {
                Logger.getLogger(VentanaEgresoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.close();

        } catch (Exception e) {
            throw new DatoFaltante("ubicación no válida", "La ubicación que has escogido no es válida");
        }

    }

}
