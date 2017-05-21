/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import Excepciones.DatoFaltante;
import alessandramc.SistemaAleMC;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class VentanaControlPromocionController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private ToggleButton toggleMensualidad;
    @FXML
    private ToggleButton toggleAnualidad;
    @FXML
    private TextField campoDescuento;
    @FXML
    private TextField campoCantidad;
    @FXML
    private Button botonCancelar;
    @FXML
    private Button botonGuardar;
    @FXML
    private TextField campoNombrePromocion;

    private ScreensController myController;
    private MarcoVentanaController padre;
    private SistemaAleMC sistema;

    private String cantidadCupones;
    private String nombre;
    private String porcentajeDescuento;
    private boolean tipoCupon;

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
        toggleAnualidad.setSelected(true);
        //No hace falta pedir el tipo de acción al padre, siempre será una nueva        
    }

    @FXML
    private void clicCancelar(MouseEvent event) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¿Seguro?");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que quiere descartar los cambios?");
        alerta.showAndWait();

        try {
            Optional<ButtonType> respuesta = alerta.showAndWait();
            if (respuesta.get() == ButtonType.OK) {
                this.padre.root.getChildren().clear();
            } else {
                System.out.println("error");
            }
        } catch (Exception e) {
            System.out.println("El cuadro fue rechazado");
        }
    }

    public void recuperarDatos() throws DatoFaltante {
        try {
            cantidadCupones = campoCantidad.getText();
            if (cantidadCupones.equals("")) {
                throw new DatoFaltante("Faltan datos", "La cantidad de cupones está vacia");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "La cantidad de cupones está vacia");
        }
        try {
            nombre = campoNombrePromocion.getText();
            if (nombre.equals("")) {
                throw new DatoFaltante("Faltan datos", "La promoción debe tener un nombre");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "La promoción debe tener un nombre");
        }
        try {
            porcentajeDescuento = campoDescuento.getText();
            int valorDescuento = Integer.parseInt(porcentajeDescuento);

            if (valorDescuento < 1 || valorDescuento > 100) {
                throw new DatoFaltante("Faltan datos", "El porcentaje de descuento es inválido");
            }
        } catch (NumberFormatException | DatoFaltante e) {
            throw new DatoFaltante("Faltan datos", "El porcentaje de descuento está vacio");
        }
        try {
            tipoCupon = toggleAnualidad.isSelected() == true;
            if (toggleAnualidad.isSelected() == false && toggleMensualidad.isSelected() == false) {
                throw new DatoFaltante("Faltan datos", "El tipo de descuento es inválido");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "El tipo de descuento es invalido");
        }
    }

    @FXML
    private void clicGuardar(MouseEvent event) {
        try {
            recuperarDatos();
            sistema.agregarPromocion(cantidadCupones, nombre, porcentajeDescuento, tipoCupon);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText(null);
            alerta.setContentText("Tu promoción ha sido registrada");
            alerta.showAndWait();
            padre.irAControlPromociones(new ActionEvent());
        } catch (DatoFaltante e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle(e.getMessage());
            alerta.setHeaderText(null);
            alerta.setContentText(e.getDescripcion());
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Hemos tenido un problema con la Base de datos, pruebe más tarde");
            alerta.showAndWait();
        }
    }

}
