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
import entidades.Alumno;
import entidades.Pago;
import entidades.Promociones;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class VentanaIngresoController extends FXGenerico implements Initializable, ControlledScreen {

    private SistemaAleMC sistema;
    private ScreensController myController;
    private MarcoVentanaController padre;
    private int total = 0;
    private ArrayList<Alumno> alumnos;
    private Alumno alumnoSeleccionado;
    private Promociones promocionElegidaMensualidad;
    private Promociones promocionElegidaAnualidad;
    private boolean mensualidadSeleccionada;

    @FXML
    private TextField textFieldMensualidadActual;
    @FXML
    private TextField textFieldMensualidadesAtrasadas;
    @FXML
    private Button botonCancelar;
    @FXML
    private Button botonGuardar;
    @FXML
    private Label labelPromocion;
    @FXML
    private Label textFieldTotalMensualidad;
    @FXML
    private ComboBox<String> comboBoxAlumnoAnualidad;
    @FXML
    private TextField textFieldAnualidadActual;
    @FXML
    private TextField textFieldAnualidadesAtrasadas;
    @FXML
    private Label labelPromocion1;
    @FXML
    private Label textFieldTotalAnualidad;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab mensualidadTab;
    @FXML
    private Tab anualidadTab;
    @FXML
    private Label campoAlumno;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sistema = new SistemaAleMC();
        alumnos = new ArrayList<>();
        actualizarTextFieldMensualidades();
        actualizarTextFieldAnualidades();

        tabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) -> {
            if (newTab.getText().equals("Mensualidad")) {
                actualizarTextFieldMensualidades();
                mensualidadSeleccionada = true;
            } else {
                actualizarTextFieldAnualidades();
                mensualidadSeleccionada = false;
            }
        });

    }

    private void actualizarTextFieldMensualidades() {

        textFieldMensualidadActual.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                total = Integer.parseInt(textFieldMensualidadActual.getText())
                        + Integer.parseInt(textFieldMensualidadesAtrasadas.getText());
                textFieldTotalMensualidad.setText(String.valueOf(incluirPromocion()));
            } catch (NumberFormatException ex) {
                total = Integer.parseInt(textFieldMensualidadesAtrasadas.getText());
                textFieldTotalMensualidad.setText(String.valueOf(incluirPromocion()));
            }
        });
        textFieldMensualidadesAtrasadas.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                total = Integer.parseInt(textFieldMensualidadActual.getText())
                        + Integer.parseInt(textFieldMensualidadesAtrasadas.getText());
                textFieldTotalMensualidad.setText(String.valueOf(incluirPromocion()));
            } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                total = Integer.parseInt(textFieldMensualidadActual.getText());
                textFieldTotalMensualidad.setText(String.valueOf(incluirPromocion()));
            }
        });
    }

    private void actualizarTextFieldAnualidades() {
        alumnos = sistema.getTodosAlumnosActivos();
        ArrayList<String> alumnosS = new ArrayList<>();
        for (Alumno alumno : alumnos) {
            alumnosS.add(alumno.toString());
        }
        ObservableList alumnosObs = FXCollections.observableArrayList(alumnosS);
        comboBoxAlumnoAnualidad.setItems(alumnosObs);

        comboBoxAlumnoAnualidad.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            alumnos.stream().filter((alumno) -> (alumno.toString().equals(newValue))).forEach((alumno) -> {
                alumnoSeleccionado = alumno;
            });
            actualizarCalculoDeudaAnualidad();
        });

        textFieldAnualidadActual.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                total = Integer.parseInt(textFieldAnualidadActual.getText())
                        + Integer.parseInt(textFieldAnualidadesAtrasadas.getText());
                textFieldTotalAnualidad.setText(String.valueOf(incluirPromocion()));
            } catch (NumberFormatException ex) {
                total = Integer.parseInt(textFieldAnualidadesAtrasadas.getText());
                textFieldTotalAnualidad.setText(String.valueOf(incluirPromocion()));
            }
        });
        textFieldAnualidadesAtrasadas.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            try {
                total = Integer.parseInt(textFieldAnualidadActual.getText())
                        + Integer.parseInt(textFieldAnualidadesAtrasadas.getText());
                textFieldTotalAnualidad.setText(String.valueOf(incluirPromocion()));
            } catch (IndexOutOfBoundsException | NumberFormatException ex) {
                total = Integer.parseInt(textFieldAnualidadesAtrasadas.getText());
                textFieldTotalAnualidad.setText(String.valueOf(incluirPromocion()));
            }
        });
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        padre = (MarcoVentanaController) myController.getScreenPadre();
    }

    public void actualizarCalculoDeudaMensualidad() {
        int totalMensualidadAtrasada = 0;
        ArrayList<Pago> pagosActivos = null;
        if (alumnoSeleccionado != null) {
            pagosActivos = sistema.getAdeudosActivos(alumnoSeleccionado, "M");
        } else {
            pagosActivos = new ArrayList<>();
        }

        if (pagosActivos.size() > 1) {
            for (Pago pagoActivo : pagosActivos) {
                totalMensualidadAtrasada += Integer.parseInt(pagoActivo.getMonto());
            }
        } else {
            totalMensualidadAtrasada = 0;
        }
        try {
            String pagoMens = pagosActivos.get(0).getMonto();
            textFieldMensualidadActual.setText(pagoMens);
            textFieldMensualidadesAtrasadas.setText(String.valueOf(totalMensualidadAtrasada));
            total = Integer.parseInt(pagoMens) + totalMensualidadAtrasada;
            textFieldTotalMensualidad.setText(String.valueOf(total));
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException ex) {
            textFieldMensualidadActual.setText("0");
            textFieldMensualidadesAtrasadas.setText("0");
            textFieldTotalMensualidad.setText("0");
        }
    }

    public void actualizarCalculoDeudaAnualidad() {
        int totalAnualidadAtrasada = 0;
        ArrayList<Pago> pagosActivos = null;
        if (alumnoSeleccionado != null) {
            pagosActivos = sistema.getAdeudosActivos(alumnoSeleccionado, "A");
        } else {
            pagosActivos = new ArrayList<>();
        }

        if (pagosActivos.size() > 1) {
            for (Pago pagoActivo : pagosActivos) {
                totalAnualidadAtrasada += Integer.getInteger(pagoActivo.getMonto());
            }
        } else {
            totalAnualidadAtrasada = 0;
        }
        try {
            String pagoMens = pagosActivos.get(0).getMonto();
            textFieldAnualidadActual.setText(pagoMens);
            textFieldAnualidadesAtrasadas.setText(String.valueOf(totalAnualidadAtrasada));
            total = Integer.parseInt(pagoMens) + totalAnualidadAtrasada;
            textFieldTotalAnualidad.setText(String.valueOf(total));
        } catch (IndexOutOfBoundsException | NullPointerException | NumberFormatException ex) {
            textFieldAnualidadActual.setText("0");
            textFieldAnualidadesAtrasadas.setText("0");
            textFieldTotalAnualidad.setText("0");
        }
    }

    private double incluirPromocion() {
        if (promocionElegidaMensualidad != null) {
            Double totalConPromocion = total - (total * (Double.parseDouble(promocionElegidaMensualidad.getPorcentajeDescuento()) / 100));
            return totalConPromocion;
        } else if (promocionElegidaAnualidad != null) {
            Double totalConPromocion = total - (total * (Double.parseDouble(promocionElegidaAnualidad.getPorcentajeDescuento()) / 100));
            return totalConPromocion;
        } else {
            return total;
        }
    }

    @FXML
    private void clicCancelar(MouseEvent event) {
        padre.irAIngresos(event);
    }

    @FXML
    private void clicGuardar(MouseEvent event) {
        String pagoSugerido = "0.00", pagoRealizado = "0.00";
        if (promocionElegidaMensualidad != null) {
            if (!promocionElegidaMensualidad.getNombre().equals("Sin promoción")) {
                sistema.restarPromocion(promocionElegidaMensualidad);
            }
        }
        if (promocionElegidaAnualidad != null) {
            if (!promocionElegidaAnualidad.getNombre().equals("Sin promoción")) {
                sistema.restarPromocion(promocionElegidaAnualidad);
            }
        }
        if (alumnoSeleccionado != null) {
            try {
                if (mensualidadSeleccionada == true) {
                    sistema.quitarAdeudoAlumno(alumnoSeleccionado, "M");
                    pagoSugerido = String.valueOf(total);
                    pagoRealizado = textFieldTotalMensualidad.getText();
                } else {
                    sistema.quitarAdeudoAlumno(alumnoSeleccionado, "A");
                    pagoSugerido = String.valueOf(total);
                    pagoRealizado = textFieldTotalMensualidad.getText();
                }
                generarReportePago(pagoSugerido, pagoRealizado);
                sistema.agregarCapital(textFieldMensualidadActual.getText(), "Pago del alumno " + alumnoSeleccionado.getMatriculaAlumno(), 'm');

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Bien");
                alerta.setHeaderText(null);
                alerta.setContentText("Hemos registrado el pago ;)");
                alerta.showAndWait();
                padre.irAIngresos(event);
            } catch (DatoFaltante e) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle(e.getMessage());
                alerta.setHeaderText(null);
                alerta.setContentText(e.getDescripcion());
                alerta.showAndWait();
            }

        } else {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("X(");
            alerta.setHeaderText(null);
            alerta.setContentText("No has seleccionado al colaborador");
            alerta.showAndWait();
        }
    }

    @FXML
    private void clicBuscarPromocionMensualidad(MouseEvent event) {
        Dialog ventana = new Dialog();
        ventana.setTitle("Elige una promoción");
        ventana.setHeaderText("Elige una promoción");
        ventana.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        ventana.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //Crear promoción vacía
        Promociones promocionVacia = new Promociones();
        promocionVacia.setNombre("Sin promoción");
        promocionVacia.setCantidadCupones("1");
        promocionVacia.setFolioPromocion(Integer.getInteger("A001"));
        promocionVacia.setPorcentajeDescuento("0");
        promocionVacia.setTipo(Boolean.FALSE);
        //Creación de lista que irá dentro del tableView
        ArrayList<Promociones> listaPromociones = sistema.getPromociones(Boolean.TRUE);
        listaPromociones.add(0, promocionVacia);
        ObservableList listaPromocionesObs = FXCollections.observableArrayList(listaPromociones);
        //Creación del grid dónde irá el tableView
        GridPane panel = new GridPane();
        //Creación del TableView
        TableView tabla = new TableView();
        tabla.setMaxHeight(500);
        //Columna para el nombre
        TableColumn columnaNombre = new TableColumn();
        columnaNombre.setPrefWidth(200);
        columnaNombre.setText("Nombre de la promoción");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        //Columna para la cantidad
        TableColumn columnaCantidad = new TableColumn();
        columnaCantidad.setPrefWidth(100);
        columnaCantidad.setText("Disponibles");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadCupones"));
        //Columna para el porcentaje de descuento
        TableColumn columnaPorcentajeDescuento = new TableColumn();
        columnaPorcentajeDescuento.setPrefWidth(100);
        columnaPorcentajeDescuento.setText("Descuento(%)");
        columnaPorcentajeDescuento.setCellValueFactory(new PropertyValueFactory<>("porcentajeDescuento"));
        //Columna para el porcentaje de descuento
        TableColumn columnaTipo = new TableColumn();
        columnaTipo.setPrefWidth(100);
        columnaTipo.setText("Tipo");
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        //Columna para el porcentaje de descuento
        TableColumn columnaFolio = new TableColumn();
        columnaFolio.setPrefWidth(100);
        columnaFolio.setText("folio");
        columnaFolio.setCellValueFactory(new PropertyValueFactory<>("folioPromocion"));
        //Asigna la lista al TableView, este al grid y este a la ventana
        tabla.setItems(listaPromocionesObs);
        tabla.getColumns().addAll(columnaNombre, columnaCantidad, columnaPorcentajeDescuento, columnaTipo, columnaFolio);
        panel.getChildren().add(tabla);
        ventana.getDialogPane().setContent(panel);

        //clic para agregar alumnos inscritos
        tabla.getSelectionModel().selectedItemProperty().addListener((ObservableValue observableValue, Object oldValue, Object newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            Promociones promocion = (Promociones) newValue;
            labelPromocion.setText(promocion.getNombre());
            promocionElegidaMensualidad = (Promociones) newValue;
        });
        Optional<ButtonType> respuesta = ventana.showAndWait();
        //if (respuesta == )

        if (respuesta.get() == ButtonType.APPLY) {
            textFieldTotalMensualidad.setText(String.valueOf(incluirPromocion()));
        } else {
            labelPromocion.setText("Sin promoción");
        }

    }

    @FXML
    private void clicBuscarPromocionAnualidad(MouseEvent event) {
        Dialog ventana = new Dialog();
        ventana.setTitle("Elige una promoción");
        ventana.setHeaderText("Elige una promoción");
        ventana.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        ventana.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //Crear promoción vacía
        Promociones promocionVacia = new Promociones();
        promocionVacia.setNombre("Sin promoción");
        promocionVacia.setCantidadCupones("1");
        promocionVacia.setFolioPromocion(Integer.getInteger("A001"));
        promocionVacia.setPorcentajeDescuento("0");
        promocionVacia.setTipo(Boolean.TRUE);
        //Creación de lista que irá dentro del tableView
        ArrayList<Promociones> listaPromociones = sistema.getPromociones(Boolean.FALSE);
        listaPromociones.add(0, promocionVacia);
        ObservableList listaPromocionesObs = FXCollections.observableArrayList(listaPromociones);
        //Creación del grid dónde irá el tableView
        GridPane panel = new GridPane();
        //Creación del TableView
        TableView tabla = new TableView();
        tabla.setMaxHeight(500);
        //Columna para el nombre
        TableColumn columnaNombre = new TableColumn();
        columnaNombre.setPrefWidth(200);
        columnaNombre.setText("Nombre de la promoción");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        //Columna para la cantidad
        TableColumn columnaCantidad = new TableColumn();
        columnaCantidad.setPrefWidth(100);
        columnaCantidad.setText("Disponibles");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidadCupones"));
        //Columna para el porcentaje de descuento
        TableColumn columnaPorcentajeDescuento = new TableColumn();
        columnaPorcentajeDescuento.setPrefWidth(100);
        columnaPorcentajeDescuento.setText("Descuento(%)");
        columnaPorcentajeDescuento.setCellValueFactory(new PropertyValueFactory<>("porcentajeDescuento"));
        //Columna para el porcentaje de descuento
        TableColumn columnaTipo = new TableColumn();
        columnaTipo.setPrefWidth(100);
        columnaTipo.setText("Tipo");
        columnaTipo.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        //Columna para el porcentaje de descuento
        TableColumn columnaFolio = new TableColumn();
        columnaFolio.setPrefWidth(100);
        columnaFolio.setText("folio");
        columnaFolio.setCellValueFactory(new PropertyValueFactory<>("folioPromocion"));
        //Asigna la lista al TableView, este al grid y este a la ventana
        tabla.setItems(listaPromocionesObs);
        tabla.getColumns().addAll(columnaNombre, columnaCantidad, columnaPorcentajeDescuento, columnaTipo, columnaFolio);
        panel.getChildren().add(tabla);
        ventana.getDialogPane().setContent(panel);

        //clic para agregar alumnos inscritos
        tabla.getSelectionModel().selectedItemProperty().addListener((ObservableValue observableValue, Object oldValue, Object newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            Promociones promocion = (Promociones) newValue;
            labelPromocion.setText(promocion.getNombre());
            promocionElegidaAnualidad = (Promociones) newValue;
        });
        Optional<ButtonType> respuesta = ventana.showAndWait();

        if (respuesta.get() == ButtonType.APPLY) {
            textFieldTotalAnualidad.setText(String.valueOf(incluirPromocion()));
        } else {
            labelPromocion.setText("Sin promoción");
        }
    }

    public void generarReportePago(String pagoSugerido, String pagoRealizado) throws DatoFaltante {

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
            cell = new PdfPCell(new Phrase(pagoSugerido));
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            //Agrega el pago realizado
            cell = new PdfPCell(new Phrase("Pago realizado"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(pagoRealizado)));
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

    @FXML
    private void ClicSeleccionarAlumno(MouseEvent event) {
        //comboBoxAlumnoMensualidad.setItems(alumnosObs);
        Dialog ventana = new Dialog();
        ventana.setTitle("Selecciona un alumno");
        ventana.setHeaderText("Selecciona un alumno");
        ventana.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        ventana.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //Creación de lista que irá dentro del tableView
        alumnos = sistema.getTodosAlumnosActivos();
        ObservableList alumnosObs = FXCollections.observableArrayList(alumnos);
        //Creación del grid dónde irá el tableView
        GridPane panel = new GridPane();
        //Creación del TableView
        TableView tabla = new TableView();
        tabla.setMaxHeight(500);
        //Columna para el nombre
        TableColumn columnaNombre = new TableColumn();
        columnaNombre.setPrefWidth(100);
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
        columnaMatricula.setCellValueFactory(new PropertyValueFactory<>("matriculaAlumno"));
        //Columna para el porcentaje de descuento
        TableColumn columnaApMat = new TableColumn();
        columnaApMat.setPrefWidth(100);
        columnaApMat.setText("Apellido materno");
        columnaApMat.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        //Columna para el porcentaje de descuento
        TableColumn columnaTutor = new TableColumn();
        columnaTutor.setPrefWidth(100);
        columnaTutor.setText("Tutor");
        columnaTutor.setCellValueFactory(new PropertyValueFactory<>("nombreTutor"));
        //Asigna la lista al TableView, este al grid y este a la ventana
        tabla.setItems(alumnosObs);
        tabla.getColumns().addAll(columnaNombre, columnaApPat, columnaApMat, columnaMatricula, columnaTutor);
        panel.getChildren().add(tabla);
        ventana.getDialogPane().setContent(panel);

        //clic para agregar alumnos inscritos
        tabla.getSelectionModel().selectedItemProperty().addListener((ObservableValue observableValue, Object oldValue, Object newValue) -> {
            //Check whether item is selected and set value of selected item to Label
            alumnoSeleccionado = (Alumno) newValue;
            campoAlumno.setText(alumnoSeleccionado.toString());
        });
        Optional<ButtonType> respuesta = ventana.showAndWait();

        if (respuesta.get() == ButtonType.APPLY) {
        actualizarCalculoDeudaMensualidad();
        } else {
            labelPromocion.setText("No hay alumno seleccionado");
        }

    }

}
