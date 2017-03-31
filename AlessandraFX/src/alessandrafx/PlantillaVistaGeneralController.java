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
import entidades.Persona;
import entidades.Promociones;
import util.ControlledScreen;
import util.ScreensController;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import util.FXGenerico;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class PlantillaVistaGeneralController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;

    public String getLabel1() {
        return label1.getText();
    }

    public String getLabel2() {
        return label2.getText();
    }

    public String getLabel3() {
        return label3.getText();
    }

    public String getLabel4() {
        return label4.getText();
    }

    public String getLabel5() {
        return label5.getText();
    }

    public String getLabel6() {
        return label6.getText();
    }

    public String getLabel7() {
        return label7.getText();
    }

    private ScreensController myController;
    private VistaControlGenericoController padre;
    private Grupo grupo;
    private Alumno alumno;
    private Colaborador colaborador;
    private Promociones promocion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ScreensController mainContainer = new ScreensController();

    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public Promociones getPromocion() {
        return promocion;
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent;
        this.padre = (VistaControlGenericoController) myController.getScreenPadre();
        padre.setearPlantilla(this);
    }

    @FXML
    private void elementoClic(MouseEvent event) {
        if (promocion != null) {

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Vas a borrar esa promoción");
            alerta.setHeaderText(null);
            alerta.setContentText("No es posible editar la promoción, en cambio se borrará, ¿desea continuar?");

            try {
                Optional<ButtonType> respuesta = alerta.showAndWait();
                if (respuesta.get() == ButtonType.OK) {
                    SistemaAleMC sistema = new SistemaAleMC();
                    sistema.desabilitarPromocion(promocion);
                    padre.padre.irAControlPromociones(new ActionEvent());
                } else {
                    System.out.println("error");
                }
            } catch (Exception e) {
                System.out.println("El cuadro fue rechazado");
            }

        } else {
            padre.recibirElementoClic(this);
        }
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
        label1.setText(grupo.getNombre());
        label2.setText(grupo.getMatriculaColaborador().getNombre() + " " + grupo.getMatriculaColaborador().getApellidoPaterno() + " " + grupo.getMatriculaColaborador().getApellidoMaterno());
        label3.setText(grupo.getMatriculaGrupo());
        label4.setText("$" + grupo.getPrecio());
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
        label1.setText(this.alumno.getNombre() + " " + this.alumno.getApellidoPaterno() + " " + this.alumno.getApellidoMaterno());
        label2.setText(this.alumno.getCalle() + " " + this.alumno.getColonia() + " " + this.alumno.getNumero());
        label3.setText(this.alumno.getCorreo());
        label4.setText(this.alumno.getTelefono());
        label5.setText(this.alumno.getFechaNacimiento().toString());
        label6.setText(this.alumno.getNombreTutor());
        label7.setText(this.alumno.getTelefonoTutor());
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
        label1.setText(this.colaborador.getNombre() + " " + this.colaborador.getApellidoPaterno() + " " + this.colaborador.getApellidoMaterno());
        label2.setText(this.colaborador.getCalle() + " " + this.colaborador.getColonia() + " " + this.colaborador.getNumero());
        label3.setText(this.colaborador.getMatriculaColaborador());
        label4.setText(this.colaborador.getTelefono());
        label5.setText(this.colaborador.getFechaNacimiento().toString());
        label6.setText(this.colaborador.getTitulo());
    }

    public void setPromociones(Promociones promocion) {
        this.promocion = promocion;
        label1.setText("Nombre de la promoción: " + this.promocion.getNombre());
        if (this.promocion.getTipo() == true) {
            label2.setText("Tipo de cupón: Inscripcion");
        } else {
            label2.setText("Tipo de cupón: Mensualidad");
        }
        label3.setText("Porcentaje de descuento" + this.promocion.getPorcentajeDescuento());
        label4.setText("Cupones disponibles" + this.promocion.getCantidadCupones());
        label5.setVisible(false);
        label6.setVisible(false);
        label7.setVisible(false);
    }

}
