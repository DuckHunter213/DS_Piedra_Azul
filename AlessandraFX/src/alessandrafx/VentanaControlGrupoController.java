/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandrafx;

import util.ControlledScreen;
import util.FXGenerico;
import util.ScreensController;
import Excepciones.DatoFaltante;
import Excepciones.HorarioIncorrecto;
import alessandramc.SistemaAleMC;
import entidades.Alumno;
import entidades.Colaborador;
import entidades.Grupo;
import entidades.Horario;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author gerar
 */
public class VentanaControlGrupoController extends FXGenerico implements Initializable, ControlledScreen {

    @FXML
    private TextField textFieldNombreCurso;
    @FXML
    private ComboBox<Colaborador> listaColaboradores;
    @FXML
    private CheckBox checkBoxLunes;
    @FXML
    private Spinner<String> horaInicioLunes;
    @FXML
    private Spinner<String> horaFinLunes;
    @FXML
    private CheckBox checkBoxMartes;
    @FXML
    private Spinner<String> horaInicioMartes;
    @FXML
    private Spinner<String> horaFinMartes;
    @FXML
    private CheckBox checkBoxMiercoles;
    @FXML
    private Spinner<String> horaInicioMiercoles;
    @FXML
    private Spinner<String> horaFinMiercoles;
    @FXML
    private CheckBox checkBoxJueves;
    @FXML
    private Spinner<String> horaInicioJueves;
    @FXML
    private Spinner<String> horaFinJueves;
    @FXML
    private CheckBox checkBoxViernes;
    @FXML
    private Spinner<String> horaInicioViernes;
    @FXML
    private Spinner<String> horaFinViernes;
    @FXML
    private CheckBox checkBoxSabado;
    @FXML
    private Spinner<String> horaInicioSabado;
    @FXML
    private Spinner<String> horaFinSabado;
    @FXML
    private Pane panelSeleccionAlumnos;
    @FXML
    private Pane contenedor;
    @FXML
    private TextField textFieldPrecioCurso;
    @FXML
    private TextField textFieldSalonLunes;
    @FXML
    private TextField textFieldSalonMartes;
    @FXML
    private TextField textFieldSalonMiercoles;
    @FXML
    private TextField textFieldSalonJueves;
    @FXML
    private TextField textFieldSalonViernes;
    @FXML
    private TextField textFieldSalonSabado;

    private ScreensController myController;
    private MarcoVentanaController padre;
    private PlantillaVistaGeneralController plantillaSeleccionada;
    private SistemaAleMC sistema;
    private Grupo grupoRegistrado;
    private boolean grupoRecibido = false;
    public static final String PATH_INICIAL = "/vistasFXML/";
    public static final String SELECCIONAR_ALUMNOS_FXML = PATH_INICIAL + "SeleccionarAlumnos.fxml";
    public static final String VENTANA_CONTROL_GRUPO_FXML = PATH_INICIAL + "VentanaControlGrupo.fxml";
    public static final int TIEMPO_MINIMO_CLASE = 30;
    public static final int TIEMPO_MAXIMO_CLASE = 150;

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
        try {
            if (!plantillaSeleccionada.getLabel1().isEmpty()) {
                grupoRegistrado = plantillaSeleccionada.getGrupo();
                grupoRecibido = true;
            } else {
                grupoRecibido = false;
            }

        } catch (Exception e) {

        }

        //Agrega la sección de alumnos
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(VentanaControlGrupoController.SELECCIONAR_ALUMNOS_FXML, VentanaControlGrupoController.SELECCIONAR_ALUMNOS_FXML, this);
        mainContainer.setScreen(VentanaControlGrupoController.SELECCIONAR_ALUMNOS_FXML);

        Pane root = (Pane) this.panelSeleccionAlumnos;
        root.getChildren().add(mainContainer);

        //Crea el combobox con los colaboradores disponibles
        List<Colaborador> listaColaboradoresBD = sistema.getTodosColaboradores();
        ObservableList listaObsColaboradores = FXCollections.observableArrayList(listaColaboradoresBD);
        listaColaboradores.setItems(listaObsColaboradores);

        //Crea las horas que estarán disponibles en el spinner
        ArrayList<String> listaHoras = new ArrayList<>();
        int indice = 0;
        for (int i = 8; i <= 20; i++) {
            for (int j = 0; j <= 45; j += 15) {
                if (j != 0) {
                    if (i < 10) {
                        listaHoras.add("0" + i + ":" + j);
                    } else {
                        listaHoras.add(i + ":" + j);
                    }
                } else if (i < 10) {
                    listaHoras.add("0" + i + ":0" + j);
                } else {
                    listaHoras.add(i + ":0" + j);
                }
                indice++;
            }
        }
        System.err.println("Buen seteado ;)");
        ObservableList listaObsHoras = FXCollections.observableArrayList(listaHoras);
        setListaSpinners(listaObsHoras);
        if (grupoRegistrado != null) {
            setearGrupoRecibido();
        }
    }

    public ArrayList<Alumno> getAlumnos() {
        ArrayList<Alumno> alumnos = sistema.getAlumnosInscritosActivos(grupoRegistrado);
        System.err.println("ASDADSF");
        return alumnos;
    }

    private void setearGrupoRecibido() {
        textFieldNombreCurso.setText(grupoRegistrado.getNombre());
        textFieldPrecioCurso.setText(grupoRegistrado.getPrecio());
        listaColaboradores.getSelectionModel().select(grupoRegistrado.getMatriculaColaborador());
        setearHorariosRegistrados();
    }

    private void setearHorariosRegistrados() {
        List<Horario> horariosList = grupoRegistrado.getHorarioList();
        ArrayList<Horario> horarios = new ArrayList<>();
        for (Horario horario : horariosList) {
            horarios.add(horario);
        }

        for (Horario horario : horarios) {
            String nombreDia = horario.getDia().toLowerCase();
            int horaInicio = Integer.parseInt(horario.getHoraInicio().substring(0, 1));
            int horaFin = Integer.parseInt(horario.getHoraFin().substring(0, 1));
            if (horaInicio < 10 && horaInicio > 3) {
                horario.setHoraInicio("0" + horario.getHoraInicio());
            }
            if (horaFin < 10 && horaFin > 3) {
                horario.setHoraFin("0" + horario.getHoraFin());
            }
            if (nombreDia.equals("lunes")) {
                checkBoxLunes.setSelected(true);
                horaInicioLunes.getValueFactory().setValue(horario.getHoraInicio());
                horaFinLunes.getValueFactory().setValue(horario.getHoraFin());
                textFieldSalonLunes.setText(horario.getSalon());
            } else if (nombreDia.equals("martes")) {
                checkBoxMartes.setSelected(true);
                horaInicioMartes.getValueFactory().setValue(horario.getHoraInicio());
                horaFinMartes.getValueFactory().setValue(horario.getHoraFin());
                textFieldSalonMartes.setText(horario.getSalon());
            } else if (nombreDia.equals("miércoles")) {
                checkBoxMiercoles.setSelected(true);
                horaInicioMiercoles.getValueFactory().setValue(horario.getHoraInicio());
                horaFinMiercoles.getValueFactory().setValue(horario.getHoraFin());
                textFieldSalonMiercoles.setText(horario.getSalon());
            } else if (nombreDia.equals("jueves")) {
                checkBoxJueves.setSelected(true);
                horaInicioJueves.getValueFactory().setValue(horario.getHoraInicio());
                horaFinJueves.getValueFactory().setValue(horario.getHoraFin());
                textFieldSalonJueves.setText(horario.getSalon());
            } else if (nombreDia.equals("viernes")) {
                checkBoxViernes.setSelected(true);
                horaInicioViernes.getValueFactory().setValue(horario.getHoraInicio());
                horaFinViernes.getValueFactory().setValue(horario.getHoraFin());
                textFieldSalonViernes.setText(horario.getSalon());
            } else if (nombreDia.equals("sábado")) {
                checkBoxSabado.setSelected(true);
                horaInicioSabado.getValueFactory().setValue(horario.getHoraInicio());
                horaFinSabado.getValueFactory().setValue(horario.getHoraFin());
                textFieldSalonSabado.setText(horario.getSalon());
            }
        }
    }

    public void guardarGrupo(List<Alumno> listaAlumno) {
        try {
            //los parametros se setean antes
            String nombreCurso = getNombre();
            String precioCurso = getPrecio();
            ArrayList<Horario> listaHorario = armarHorario();
            Colaborador colaboradorCurso = getColaborador();
            if (this.grupoRegistrado != null) {
                sistema.editarGrupo(nombreCurso, precioCurso, listaHorario, listaAlumno, colaboradorCurso, grupoRegistrado.getMatriculaGrupo());
            } else {
                sistema.crearGrupo(nombreCurso, precioCurso, listaHorario, listaAlumno, colaboradorCurso);
            }
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("grupo guardado");
            alerta.setHeaderText(null);
            alerta.setContentText("El grupo ha sido registrado con éxito");
            alerta.showAndWait();
            this.padre.root.getChildren().clear();

            this.padre.irAControlGrupo(new ActionEvent());
        } catch (HorarioIncorrecto | DatoFaltante e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle(e.getMessage());
            alerta.setHeaderText(null);
            alerta.setContentText(e.getDescripcion());
            alerta.showAndWait();
        }
    }

    public void borrarVentana() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("¿Seguro?");
        alerta.setHeaderText(null);
        alerta.setContentText("¿Está seguro que quiere hacerlo?");

        try {
            Optional<ButtonType> respuesta = alerta.showAndWait();
            if (respuesta.get() == ButtonType.OK) {
                if (this.grupoRegistrado != null) {
                    sistema.desabilitarGrupo(grupoRegistrado);
                    this.padre.root.getChildren().clear();
                } else {
                    this.padre.root.getChildren().clear();
                }
            } else {
                System.out.println("error");
            }
        } catch (Exception e) {
            System.out.println("El cuadro fue rechazado");
        }
    }

    private void setListaSpinners(ObservableList<String> listaObsHoras) {
        ArrayList<Spinner> listaSpinners = new ArrayList<>();
        listaSpinners.add(horaInicioLunes);
        listaSpinners.add(horaFinLunes);
        listaSpinners.add(horaInicioMartes);
        listaSpinners.add(horaFinMartes);
        listaSpinners.add(horaInicioMiercoles);
        listaSpinners.add(horaFinMiercoles);
        listaSpinners.add(horaInicioJueves);
        listaSpinners.add(horaFinJueves);
        listaSpinners.add(horaInicioViernes);
        listaSpinners.add(horaFinViernes);
        listaSpinners.add(horaInicioSabado);
        listaSpinners.add(horaFinSabado);

        for (Spinner listaSpinner : listaSpinners) {
            SpinnerValueFactory factory = new SpinnerValueFactory.ListSpinnerValueFactory<String>(listaObsHoras);
            listaSpinner.setValueFactory(factory);
        }
    }

    private String obtenerMinutos(String cadena) {
        StringBuffer sb = new StringBuffer(cadena);
        sb = sb.reverse();
        String cadenaAux = sb.toString().substring(0, 2);
        sb = new StringBuffer(cadenaAux);
        return String.valueOf(sb.reverse());
    }

    public ArrayList<Horario> armarHorario() throws DatoFaltante, HorarioIncorrecto {
        ArrayList<Horario> listaHorario = new ArrayList<>();
        int horaInicio, horaFin, minutoInicio, minutoFin, tiempoInicio, tiempoFin;
        if (checkBoxLunes.isSelected()) {
            minutoInicio = Integer.parseInt(obtenerMinutos(horaInicioLunes.getValue()));
            minutoFin = Integer.parseInt(obtenerMinutos(horaFinLunes.getValue()));
            horaInicio = Integer.parseInt(horaInicioLunes.getValue().substring(0, 2));
            horaFin = Integer.parseInt(horaFinLunes.getValue().substring(0, 2));
            tiempoInicio = (horaInicio * 60) + minutoInicio;
            tiempoFin = (horaFin * 60) + minutoFin;
            if (tiempoInicio + 60 <= tiempoFin && tiempoInicio + 150 >= tiempoFin) {
                Horario horario = new Horario();
                horario.setDia("Lunes");
                horario.setSalon(textFieldSalonLunes.getText());
                horario.setHoraInicio(horaInicioLunes.getValue());
                horario.setHoraFin(horaFinLunes.getValue());
                listaHorario.add(horario);
            } else {
                throw new HorarioIncorrecto("error en el horario", "El horario del lunes es incorrecto");
            }
        }
        if (checkBoxMartes.isSelected()) {
            minutoInicio = Integer.parseInt(obtenerMinutos(horaInicioMartes.getValue()));
            minutoFin = Integer.parseInt(obtenerMinutos(horaFinMartes.getValue()));
            horaInicio = Integer.parseInt(horaInicioMartes.getValue().substring(0, 2));
            horaFin = Integer.parseInt(horaFinMartes.getValue().substring(0, 2));
            tiempoInicio = (horaInicio * 60) + minutoInicio;
            tiempoFin = (horaFin * 60) + minutoFin;
            if (tiempoInicio + 60 <= tiempoFin && tiempoInicio + 150 >= tiempoFin) {
                Horario horario = new Horario();
                horario.setDia("Martes");
                horario.setSalon(textFieldSalonMartes.getText());
                horario.setHoraInicio(horaInicioMartes.getValue());
                horario.setHoraFin(horaFinMartes.getValue());
                listaHorario.add(horario);
            } else {
                throw new HorarioIncorrecto("error en el horario", "El horario del martes es incorrecto");
            }
        }
        if (checkBoxMiercoles.isSelected()) {
            minutoInicio = Integer.parseInt(obtenerMinutos(horaInicioMiercoles.getValue()));
            minutoFin = Integer.parseInt(obtenerMinutos(horaFinMiercoles.getValue()));
            horaInicio = Integer.parseInt(horaInicioMiercoles.getValue().substring(0, 2));
            horaFin = Integer.parseInt(horaFinMiercoles.getValue().substring(0, 2));
            tiempoInicio = (horaInicio * 60) + minutoInicio;
            tiempoFin = (horaFin * 60) + minutoFin;
            if (tiempoInicio + 60 <= tiempoFin && tiempoInicio + 150 >= tiempoFin) {
                Horario horario = new Horario();
                horario.setDia("Miércoles");
                horario.setSalon(textFieldSalonMiercoles.getText());
                horario.setHoraInicio(horaInicioMiercoles.getValue());
                horario.setHoraFin(horaFinMiercoles.getValue());
                listaHorario.add(horario);
            } else {
                throw new HorarioIncorrecto("error en el horario", "El horario del miércoles es incorrecto");
            }
        }
        if (checkBoxJueves.isSelected()) {
            minutoInicio = Integer.parseInt(obtenerMinutos(horaInicioJueves.getValue()));
            minutoFin = Integer.parseInt(obtenerMinutos(horaFinJueves.getValue()));
            horaInicio = Integer.parseInt(horaInicioJueves.getValue().substring(0, 2));
            horaFin = Integer.parseInt(horaFinJueves.getValue().substring(0, 2));
            tiempoInicio = (horaInicio * 60) + minutoInicio;
            tiempoFin = (horaFin * 60) + minutoFin;
            if (tiempoInicio + 60 <= tiempoFin && tiempoInicio + 150 >= tiempoFin) {
                Horario horario = new Horario();
                horario.setDia("Jueves");
                horario.setSalon(textFieldSalonJueves.getText());
                horario.setHoraInicio(horaInicioJueves.getValue());
                horario.setHoraFin(horaFinJueves.getValue());
                listaHorario.add(horario);
            } else {
                throw new HorarioIncorrecto("error en el horario", "El horario del jueves es incorrecto");
            }
        }
        if (checkBoxViernes.isSelected()) {
            minutoInicio = Integer.parseInt(obtenerMinutos(horaInicioViernes.getValue()));
            minutoFin = Integer.parseInt(obtenerMinutos(horaFinViernes.getValue()));
            horaInicio = Integer.parseInt(horaInicioViernes.getValue().substring(0, 2));
            horaFin = Integer.parseInt(horaFinViernes.getValue().substring(0, 2));
            tiempoInicio = (horaInicio * 60) + minutoInicio;
            tiempoFin = (horaFin * 60) + minutoFin;
            if (tiempoInicio + 60 <= tiempoFin && tiempoInicio + 150 >= tiempoFin) {
                Horario horario = new Horario();
                horario.setDia("Viernes");
                horario.setSalon(textFieldSalonViernes.getText());
                horario.setHoraInicio(horaInicioViernes.getValue());
                horario.setHoraFin(horaFinViernes.getValue());
                listaHorario.add(horario);
            } else {
                throw new HorarioIncorrecto("error en el horario", "El horario del viernes es incorrecto");
            }
        }
        if (checkBoxSabado.isSelected()) {
            minutoInicio = Integer.parseInt(obtenerMinutos(horaInicioSabado.getValue()));
            minutoFin = Integer.parseInt(obtenerMinutos(horaFinSabado.getValue()));
            horaInicio = Integer.parseInt(horaInicioSabado.getValue().substring(0, 2));
            horaFin = Integer.parseInt(horaFinSabado.getValue().substring(0, 2));
            tiempoInicio = (horaInicio * 60) + minutoInicio;
            tiempoFin = (horaFin * 60) + minutoFin;
            if (tiempoInicio + 60 <= tiempoFin && tiempoInicio + 150 >= tiempoFin) {
                Horario horario = new Horario();
                horario.setDia("Sábado");
                horario.setSalon(textFieldSalonSabado.getText());
                horario.setHoraInicio(horaInicioSabado.getValue());
                horario.setHoraFin(horaFinSabado.getValue());
                listaHorario.add(horario);
            } else {
                throw new HorarioIncorrecto("error en el horario", "El horario del sábado es incorrecto");
            }
        }

        if (listaHorario.isEmpty()) {
            throw new DatoFaltante("Faltan datos", "Ups, el horario debe ser de al menos un día");
        }

        return listaHorario;
    }

    private String getPrecio() throws DatoFaltante {
        String precio = textFieldPrecioCurso.getText();
        if (precio.equals("")) {
            throw new DatoFaltante("Faltan datos", "Ups, el precio debe ser colocado también");
        }
        return precio;
    }

    private String getNombre() throws DatoFaltante {
        String nombreCurso = textFieldNombreCurso.getText();
        if (nombreCurso.equals("")) {
            throw new DatoFaltante("Faltan datos", "Ups, el nombre del curso debe ser colocado también");
        }
        return nombreCurso;
    }

    private Colaborador getColaborador() throws DatoFaltante {
        Colaborador colaborador = listaColaboradores.getValue();
        if (colaborador == null) {
            throw new DatoFaltante("Faltan datos", "Ups, el colaborador debe ser colocado también");
        }
        return colaborador;
    }

    public void desabilitarGrupo() {
        sistema.desabilitarGrupo(grupoRegistrado);
        this.padre.irAControlGrupo(new ActionEvent());
    }

    public boolean isGrupoRecibido() {
        return grupoRecibido;
    }
}
