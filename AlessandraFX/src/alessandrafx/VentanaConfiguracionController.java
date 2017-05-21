/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import Excepciones.DatoFaltante;
import alessandramc.SistemaAleMC;
import entidades.Configuracion;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class VentanaConfiguracionController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private TextField textBoxCostoAnual;
    @FXML
    private TextField textBoxPenalizacion;
    @FXML
    private TextField textBoxPagoColaborador;
    @FXML
    private Button botonCancelar;
    @FXML
    private Button botonGuardar;

    private SistemaAleMC sistema;
    private ScreensController myController;
    private MarcoVentanaController padre;
    private int costoAnual;
    private int penalizacion;
    private int pagoColaborador;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sistema = new SistemaAleMC();
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        padre = (MarcoVentanaController) myController.getScreenPadre();
        ponerDatos();
    }

    private void ponerDatos() {
        Configuracion configuracion = sistema.getConfiguracion();
        textBoxCostoAnual.setText(String.valueOf(configuracion.getCostoAnual()));
        textBoxPagoColaborador.setText(String.valueOf(configuracion.getPorcentajePagoColaborador()));
        textBoxPenalizacion.setText(String.valueOf(configuracion.getPorcentajePenalizacion()));
    }

    @FXML
    private void clicCancelar(MouseEvent event) {
        padre.irAControl(event);
    }

    private void recuperarDatos() throws DatoFaltante {
        try {
            costoAnual = Integer.parseInt(textBoxCostoAnual.getText());
            if (costoAnual < 0 && costoAnual > 10000) {
                throw new DatoFaltante("Faltan datos", "El costo anual está vacio");
            }
        } catch (NumberFormatException e) {
            throw new DatoFaltante("Faltan datos", "El costo anual es incorrecto");
        }
        try {
            penalizacion = Integer.parseInt(textBoxPenalizacion.getText());
            if (penalizacion < 0 && penalizacion > 100) {
                throw new DatoFaltante("Faltan datos", "El porcentaje de penalización está vacio");
            }
        } catch (NumberFormatException e) {
            throw new DatoFaltante("Faltan datos", "El porcentaje de penalización es incorrecto");
        }
        try {
            pagoColaborador = Integer.parseInt(textBoxPagoColaborador.getText());
            if (pagoColaborador < 0 && pagoColaborador >= 100) {
                throw new DatoFaltante("Faltan datos", "La cantidad de pago a colaborador está vacia");
            }
        } catch (NumberFormatException e) {
            throw new DatoFaltante("Faltan datos", "La cantidad de pago a colaborador es incorrecta");
        }
    }

    @FXML
    private void clicGuardar(MouseEvent event) {
        try {
            recuperarDatos();
            sistema.agregarConfiguracion(String.valueOf(penalizacion),String.valueOf(pagoColaborador), costoAnual);
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Éxito");
            alerta.setHeaderText(null);
            alerta.setContentText("Los cambios han sido registrados");
            alerta.showAndWait();
            padre.irAControl(event);
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
