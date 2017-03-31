/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import Excepciones.DatoFaltante;
import alessandramc.SistemaAleMC;
import entidades.Alumno;
import entidades.Colaborador;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
public class VentanaControlPersonaController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellidoPaterno;
    @FXML
    private TextField campoApellidoMaterno;
    @FXML
    private DatePicker campoFechaNacimiento;
    @FXML
    private TextField campoCorreo;
    @FXML
    private TextField campoCalle;
    @FXML
    private TextField campoNumeroCasa;
    @FXML
    private TextField campoColonia;
    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoVariable;
    @FXML
    private Button botonGuardar;
    @FXML
    private Button botonCancelar;
    @FXML
    private Label etiquetaVariable;
    @FXML
    private Label etiquetaVariable2;
    @FXML
    private TextField campoVariable2;
    @FXML
    private Button botonBorrar;
    @FXML
    private Label labelCorreo;

    private ScreensController myController;
    private MarcoVentanaController padre;
    private PlantillaVistaGeneralController plantillaSeleccionada;
    private SistemaAleMC sistema;
    private Alumno alumno;
    private Colaborador colaborador;
    private boolean recibido = false;

    private String apMat;
    private String apPat;
    private String calle;
    private String colonia;
    private String correo;
    private String nombre;
    private String nombreTutor;
    private String numero;
    private String telefono;
    private String telefonoTutor;
    private String titulo;

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
        botonBorrar.setVisible(false);
        try {
            if (!plantillaSeleccionada.getLabel1().isEmpty()) {
                botonBorrar.setVisible(true);
                recibido = true;
                if (plantillaSeleccionada.getAlumno() != null) {
                    this.botonGuardar.setText("Guardar alumno");
                    this.etiquetaVariable.setText("Tutor");
                    this.etiquetaVariable2.setText("Teléfono tutor");
                    alumno = plantillaSeleccionada.getAlumno();
                    setearAlumnoRecibido();
                } else {
                    this.campoCorreo.setVisible(false);
                    this.labelCorreo.setVisible(false);
                    this.botonGuardar.setText("Guardar colaborador");
                    this.etiquetaVariable.setText("título");
                    this.etiquetaVariable2.setVisible(false);
                    this.campoVariable2.setVisible(false);
                    colaborador = plantillaSeleccionada.getColaborador();
                    setearColaboradorRecibido();
                }
            }
        } catch (Exception e) {

        }

        String tipoPersona = padre.recuperarTipoPersona();
        if (tipoPersona.equals("alumno")) {
            this.botonGuardar.setText("Guardar alumno");
            this.etiquetaVariable.setText("Tutor");
            this.etiquetaVariable2.setText("Teléfono tutor");
        } else {
            this.botonGuardar.setText("Guardar colaborador");
            this.etiquetaVariable.setText("título");
        }

    }

    private void setearAlumnoRecibido() {
        this.campoApellidoMaterno.setText(alumno.getApellidoMaterno());
        this.campoApellidoPaterno.setText(alumno.getApellidoPaterno());
        this.campoCalle.setText(alumno.getCalle());
        this.campoColonia.setText(alumno.getColonia());
        this.campoCorreo.setText(alumno.getCorreo());
        LocalDate fecha = LocalDate.of(alumno.getFechaNacimiento().getYear() + 1900, alumno.getFechaNacimiento().getMonth(), alumno.getFechaNacimiento().getDay() + 19);
        this.campoFechaNacimiento.setValue(fecha);
        this.campoNombre.setText(alumno.getNombre());
        this.campoNumeroCasa.setText(alumno.getNumero());
        this.campoTelefono.setText(alumno.getTelefono());
        this.campoVariable.setText(alumno.getNombreTutor());
        this.etiquetaVariable.setText("Tutor");
        this.campoVariable2.setText(alumno.getTelefonoTutor());
        this.etiquetaVariable2.setText("Teléfono tutor");

    }

    private void setearColaboradorRecibido() {
        this.campoApellidoMaterno.setText(colaborador.getApellidoMaterno());
        this.campoApellidoPaterno.setText(colaborador.getApellidoPaterno());
        this.campoCalle.setText(colaborador.getCalle());
        this.campoColonia.setText(colaborador.getColonia());
        //this.campoCorreo.setText(colaborador.getCorreo());
        this.campoCorreo.setVisible(false);
        this.labelCorreo.setVisible(false);
        LocalDate fecha = LocalDate.of(colaborador.getFechaNacimiento().getYear() + 1900, colaborador.getFechaNacimiento().getMonth(), colaborador.getFechaNacimiento().getDay() + 19);
        this.campoFechaNacimiento.setValue(fecha);
        this.campoNombre.setText(colaborador.getNombre());
        this.campoNumeroCasa.setText(colaborador.getNumero());
        this.campoTelefono.setText(colaborador.getTelefono());
        this.campoVariable.setText(colaborador.getTitulo());
        this.etiquetaVariable.setText("Título");
        this.campoVariable2.setVisible(false);
        this.etiquetaVariable2.setVisible(false);
    }

    public void validarDatos() throws DatoFaltante {
        try {
            apMat = campoApellidoMaterno.getText();
            if (apMat.equals("")) {
                throw new DatoFaltante("Faltan datos", "El apellido materno está vacio");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "El apellido materno es incorrecto");
        }
        try {
            apPat = campoApellidoPaterno.getText();
            if (apPat.equals("")) {
                throw new DatoFaltante("Faltan datos", "El apellido paterno está vacio");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "El apellido paterno es incorrecto");
        }
        try {
            calle = campoCalle.getText();
            if (calle.equals("")) {
                throw new DatoFaltante("Faltan datos", "La calle está vacia");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "La calle es incorrecta");
        }
        try {
            colonia = campoColonia.getText();
            if (apMat.equals("")) {
                throw new DatoFaltante("Faltan datos", "La colonia está vacia");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "La colonia es incorrecta");
        }
        try {
            nombre = campoNombre.getText();
            if (nombre.equals("")) {
                throw new DatoFaltante("Faltan datos", "El nombre está vacio");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "El nombre es incorrecto");
        }
        try {
            numero = campoNumeroCasa.getText();
            if (numero.equals("")) {
                throw new DatoFaltante("Faltan datos", "El número de casa está vacio");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "El número de casa es incorrecto");
        }
        try {
            telefono = campoTelefono.getText();
            if (telefono.equals("")) {
                throw new DatoFaltante("Faltan datos", "El teléfono está vacio");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "El teléfono es incorrecto");
        }
    }

    public void validarDatosAlumno() throws DatoFaltante {
        try {
            nombreTutor = campoVariable.getText();
            if (nombreTutor.equals("")) {
                throw new DatoFaltante("Dato Faltante", "El nombre del tutor es incorrecto");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Dato Faltante", "El nombre del tutor está vacio");
        }
        try {
            correo = campoCorreo.getText();
            if (correo.equals("")) {
                throw new DatoFaltante("Faltan datos", "El correo está vacio");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Faltan datos", "El correo es incorrecto");
        }
        try {
            telefonoTutor = campoVariable2.getText();
            if (telefonoTutor.equals("")) {
                throw new DatoFaltante("Dato Faltante", "El teléfono del tutor es incorrecto");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Dato Faltante", "El nombre del tutor está vacio");
        }
    }

    public void validarDatosColaborador() throws DatoFaltante {
        try {
            titulo = campoVariable.getText();
            if (titulo.equals("")) {
                throw new DatoFaltante("Dato Faltante", "El título es incorrecto");
            }
        } catch (Exception e) {
            throw new DatoFaltante("Dato Faltante", "El título está vacio");
        }

    }

    @FXML
    private void botonGuardarPresionado(MouseEvent event) {
        String tipoPersona = padre.recuperarTipoPersona();
        if (tipoPersona.equals("alumno")) {
            try {
                validarDatos();
                validarDatosAlumno();

                LocalDate localDate = campoFechaNacimiento.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date fecha = Date.from(instant);

                if (!recibido) {
                    sistema.agregarAlumno(apMat, apPat, calle, colonia, correo, nombre, nombreTutor, numero, telefono, telefonoTutor, fecha);
                } else {
                    sistema.editarAlumno(apMat, apPat, calle, colonia, correo, nombre, nombreTutor, numero, telefono, telefonoTutor, fecha, alumno.getMatriculaAlumno());
                }

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Todo en orden");
                alerta.setHeaderText(null);
                alerta.setContentText("Acabamos de realizar los cambios ;) ");
                alerta.showAndWait();

                this.padre.irAControlAlumnos(new ActionEvent());

            } catch (DatoFaltante e) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle(e.getMessage());
                alerta.setHeaderText(null);
                alerta.setContentText(e.getDescripcion());
                alerta.showAndWait();
            } catch (Exception e) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle(e.getMessage());
                alerta.setHeaderText(null);
                alerta.setContentText(e.getLocalizedMessage());
                alerta.showAndWait();
            }
        } else if (tipoPersona.equals("colaborador")) {
            try {
                validarDatos();
                validarDatosColaborador();

                LocalDate localDate = campoFechaNacimiento.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date fecha = Date.from(instant);

                if (recibido) {
                    sistema.agregarColabordor(apMat, apPat, calle, colonia, correo, nombre, titulo, numero, telefono, fecha);
                } else {
                    sistema.editarColaborador(apMat, apPat, calle, colonia, correo, nombre, titulo, numero, telefono, fecha, colaborador.getMatriculaColaborador());
                }

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Todo en orden");
                alerta.setHeaderText(null);
                alerta.setContentText("Acabamos de realizar los cambios ;) ");
                alerta.showAndWait();

                this.padre.irAControlColaboradores(new ActionEvent());

            } catch (DatoFaltante e) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle(e.getMessage());
                alerta.setHeaderText(null);
                alerta.setContentText(e.getDescripcion());
                alerta.showAndWait();
            } catch (Exception e) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle(e.getMessage());
                alerta.setHeaderText(null);
                alerta.setContentText(e.getLocalizedMessage());
                alerta.showAndWait();
            }
        }
    }

    @FXML
    private void botonCancelarPresionado(MouseEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¿Seguro?");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que quiere descartar los cambios?");

        try {
            Optional<ButtonType> respuesta = alerta.showAndWait();
            if (respuesta.get() == ButtonType.OK) {
                if (this.alumno != null) {
                    this.padre.irAControlAlumnos(new ActionEvent());
                } else if (this.colaborador != null) {
                    this.padre.irAControlColaboradores(new ActionEvent());
                }
            } else {
                System.out.println("error");
            }
        } catch (Exception e) {
            System.out.println("El cuadro fue rechazado");
        }
    }

    @FXML
    private void botonBorrarPresionado(MouseEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¿Seguro?");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que quiere borrarlo?");

        try {
            Optional<ButtonType> respuesta = alerta.showAndWait();
            if (respuesta.get() == ButtonType.OK) {
                if (this.alumno != null) {
                    sistema.desabilitarAlumno(alumno);
                    this.padre.irAControlAlumnos(new ActionEvent());
                } else if (this.colaborador != null) {
                    sistema.desabilitarColaborador(colaborador);
                    this.padre.irAControlColaboradores(new ActionEvent());
                }
                Alert alerta2 = new Alert(Alert.AlertType.INFORMATION);
                alerta2.setTitle("Dato borrado");
                alerta2.setHeaderText(null);
                alerta2.setContentText("La operación fue exitosa ;)");
                alerta2.showAndWait();
            } else {
                System.out.println("error");
            }
        } catch (Exception e) {
            System.out.println("El cuadro fue rechazado");
        }
    }

}
