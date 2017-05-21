package alessandramc;

import entidades.*;
import entidadesExtends.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Luis Fernando Gomez Alejandre
 */
public class SistemaAleMC{

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    private final ConfiguracionExtends configuracionExtends = new ConfiguracionExtends();
    private final AlumnoExtends alumnoExtends = new AlumnoExtends();
    private final ColaboradorExtends colaboradorExtends = new ColaboradorExtends();
    private final GrupoExtends grupoExtends = new GrupoExtends();
    private final ListagrupoExtends listagrupoExtends = new ListagrupoExtends();
    private final CapitalExtends capitalExtends = new CapitalExtends();
    private final PromocionesExtends promocionesExtends = new PromocionesExtends();
    private final PagoExtends pagoExtends = new PagoExtends();
    private final AsistenciaExtends asistenciaExtends = new AsistenciaExtends();

    /**
     *
     * @param apellidoMaterno
     * @param apellidoPaterno
     * @param calle
     * @param colonia
     * @param correo
     * @param nombre
     * @param nombreTutor
     * @param numero
     * @param telefono
     * @param telefonoTutor
     * @param fecha
     * @return
     */
    public boolean agregarAlumno(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String nombreTutor, String numero, String telefono, String telefonoTutor, Date fecha){
        return alumnoExtends.agregarAlumno(apellidoMaterno, apellidoPaterno, calle, colonia, correo, nombre, nombreTutor, numero, telefono, telefonoTutor, fecha);
    }

    /**
     *
     * @param grupo
     * @param listaAlumnos
     * @return
     */
    public boolean agregarAsistencia(Grupo grupo, ArrayList<Alumno> listaAlumnos){
        return asistenciaExtends.agregarAsistencia(grupo, listaAlumnos);
    }

    /**
     *
     * @param cantidadCupones
     * @param nombre
     * @param porcentajeDescuento
     * @param tipo
     * @return
     */
    public boolean agregarPromocion(String cantidadCupones, String nombre, String porcentajeDescuento, boolean tipo){
        return promocionesExtends.agregarPromocion(cantidadCupones, nombre, porcentajeDescuento, tipo);
    }

    /**
     * Método para crear todo el grupo utilizar solo para los nuevos grupos
     *
     * @param nombre
     * @param precio
     * @param listaHorarios
     * @param listaAlumnos
     * @param colaborador
     * @return Valores boleanos TRUE si pudo agregar a la base de datos
     */
    public boolean crearGrupo(String nombre, String precio, List<Horario> listaHorarios, List<Alumno> listaAlumnos, Colaborador colaborador){
        return grupoExtends.crearGrupo(nombre, precio, listaHorarios, listaAlumnos, colaborador);
    }

    /**
     * Agrega Colaboradores a la base de datos, utiizar solo en caos de que el
     * colabordador sea nuevo
     *
     * @param apellidoMaterno
     * @param apellidoPaterno
     * @param calle
     * @param colonia
     * @param correo
     * @param nombre
     * @param titulo
     * @param numero
     * @param telefono
     * @param fecha
     * @return
     */
    public boolean agregarColabordor(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String titulo, String numero, String telefono, Date fecha){
        return colaboradorExtends.agregarColabordor(apellidoMaterno, apellidoPaterno, calle, colonia, correo, nombre, titulo, numero, telefono, fecha);
    }

    /**
     * Agrega la referencia del colaborador a un grupo
     *
     * @param grupo Grupo a donde se agregara el colaborador
     * @param colaborador El colaborador que se le asignara el grupo
     * @return
     */
    public boolean editarColaboradorGrupo(Grupo grupo, Colaborador colaborador){
        return grupoExtends.editarColaboradorGrupo(grupo, colaborador);
    }

    /**
     *
     * @param monto
     * @param motivo
     * @param tipo
     * @return
     */
    public boolean agregarCapital(String monto, String motivo, Character tipo){
        return capitalExtends.agregarCapital(monto, motivo, tipo);
    }

    /**
     *
     * @param apellidoMaterno
     * @param apellidoPaterno
     * @param calle
     * @param colonia
     * @param nombre
     * @param nombreTutor
     * @param correo
     * @param telefono
     * @param telefonoTutor
     * @param fechaNacimiento
     * @param numero
     * @param matriculaAlumno
     * @return
     */
    public boolean editarAlumno(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String nombreTutor, String numero, String telefono, String telefonoTutor, Date fechaNacimiento, String matriculaAlumno){
        return alumnoExtends.editarAlumno(apellidoMaterno, apellidoPaterno, calle, colonia, correo, nombre, nombreTutor, numero, telefono, telefonoTutor, fechaNacimiento, matriculaAlumno);
    }

    /**
     *
     * @param apellidoMaterno
     * @param apellidoPaterno
     * @param colonia
     * @param correo
     * @param calle
     * @param matriculaColaborador
     * @param numero
     * @param nombre
     * @param fecha
     * @param telefono
     * @param titulo
     * @return
     */
    public boolean editarColaborador(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String titulo, String numero, String telefono, Date fecha, String matriculaColaborador){
        return colaboradorExtends.editarColaborador(apellidoMaterno, apellidoPaterno, calle, colonia, correo, nombre, titulo, numero, telefono, fecha, matriculaColaborador);
    }

    /**
     * Edita el grupo esto solo es para los datos, no funciona en habilitar y
     * deshabilitar
     *
     * @param nombre nombre del grupo
     * @param precio precio del grupo
     * @param listaAlumnos lista de alumnos
     * @param listaHorarios lista de horarios
     * @param alumnosRetirados
     * @param matricula matricula del grupo
     * @param colaborador referecia del colaborador
     * @return
     */
    public boolean editarGrupo(String nombre, String precio, List<Horario> listaHorarios, List<Alumno> listaAlumnos, ArrayList<Alumno> alumnosRetirados, Colaborador colaborador, String matricula){
        return grupoExtends.editarGrupo(nombre, precio, listaHorarios, listaAlumnos, alumnosRetirados, colaborador, matricula);
    }

    /**
     *
     * @param nombre
     * @return
     */
    public ArrayList<Colaborador> buscarColaborador(String nombre){
        return colaboradorExtends.buscarColaborador(nombre);
    }

    /**
     *
     * @param nombre
     * @return
     */
    public ArrayList<Alumno> buscarAlumno(String nombre){
        return alumnoExtends.buscarAlumno(nombre);
    }

    /**
     *
     * @param nombre
     * @return
     */
    public ArrayList<Grupo> buscarGrupo(String nombre){
        return grupoExtends.buscarGrupo(nombre);
    }

    /**
     * Buscar a un grupo
     *
     * @param matricula criterio de busqueda del método
     * @return regresa la referencia de un grupo
     */
    public Grupo buscarGrupoMatricula(String matricula){
        return grupoExtends.buscarGrupoMatricula(matricula);
    }

    /**
     * Buscar a un grupo
     *
     * @param matricula criterio de busqueda del método
     * @return regresa la referencia de un grupo
     */
    public Alumno buscarAlumnoMatricula(String matricula){
        return alumnoExtends.buscarAlumnoMatricula(matricula);
    }

    /**
     * Buscar a un grupo
     *
     * @param matricula criterio de busqueda del método
     * @return regresa la referencia de un grupo
     */
    public Colaborador buscarColaboradorMatricula(String matricula){
        return colaboradorExtends.buscarColaboradorMatricula(matricula);
    }

    /**
     *
     * @return
     */
    public ArrayList<Promociones> getTodasPromocionesActivas(){
        return promocionesExtends.getTodasPromocionesActivas();
    }

    /**
     * Pone el valor de una promocion en 0, no es posible editarlo despues
     *
     * @param promocion referencia a la promoción a desabilitar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarPromocion(Promociones promocion){
        return promocionesExtends.desabilitarPromocion(promocion);
    }

    /**
     * Dehabilita el alumno dado
     *
     * @param alumno referencia al alumno a desabilitar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarAlumno(Alumno alumno){
        return alumnoExtends.desabilitarAlumno(alumno);
    }

    /**
     * Colaborador que sera descativado
     *
     * @param colaborador referencia del colaborador a desactivar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarColaborador(Colaborador colaborador){
        return colaboradorExtends.desabilitarColaborador(colaborador);
    }

    /**
     * Deshabilitar un grupo evita que aparezca en ciertos métodos
     *
     * @param grupo referencia del grupo a deshabilitar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarGrupo(Grupo grupo){
        return grupoExtends.desabilitarGrupo(grupo);
    }

    /**
     * Habilita al colaborador referenciado
     *
     * @param colaborador
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean habilitarColaborador(Colaborador colaborador){
        return colaboradorExtends.habilitarColaborador(colaborador);
    }

    /**
     *
     * @param grupo
     * @return
     */
    public boolean habilitarGrupo(Grupo grupo){
        return grupoExtends.habilitarGrupo(grupo);
    }

    /**
     *
     * @param alumno
     * @return
     */
    public boolean habilitarAlumno(Alumno alumno){
        return alumnoExtends.habilitarAlumno(alumno);
    }

    /**
     * Deshabilitar alumno de un grupo
     *
     * @param grupo referencia del grupo a deshabilitar
     * @param alumno alumno que sera desabilitado del grupo
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarAlumnoGrupo(Grupo grupo, Alumno alumno){
        return grupoExtends.desabilitarAlumnoGrupo(grupo, alumno);
    }

    /**
     * Método que regreasa a todos los alumnos inscritos de la base de datos sin
     * diferenciar que esten activos
     *
     * @return arraylist de alumnos
     */
    public ArrayList<Alumno> getTodosAlumnos(){
        return alumnoExtends.getTodosAlumnos();
    }

    /**
     *
     * @return
     */
    public ArrayList<Alumno> getTodosAlumnosActivos(){
        return alumnoExtends.getTodosAlumnosActivos();
    }

    /**
     *
     * @return
     */
    public ArrayList<Alumno> getTodosAlumnosConDeuda(){
        return alumnoExtends.getTodosAlumnosConDeuda();
    }

    /**
     *
     * @param alumno
     * @param criterio
     * @return
     */
    public boolean quitarAdeudoAlumno(Alumno alumno, String criterio){
        return pagoExtends.quitarAdeudo(alumno, criterio);
    }

    /**
     * Método que regresa a todos los colaboradores sin diferenciar si estan
     * activos
     *
     * @return arraylist de colaboradores
     */
    public ArrayList<Colaborador> getTodosColaboradores(){
        return colaboradorExtends.getTodosColaboradores();
    }

    /**
     *
     * @return
     */
    public ArrayList<Colaborador> getTodosColaboradoresActivos(){
        return colaboradorExtends.getTodosColaboradoresActivos();
    }

    /**
     * Método que regresa a todos los grupos no importa si estan habilitados o
     * no
     *
     * @return arraylist con todos los grupos
     */
    public ArrayList<Grupo> getTodosGrupos(){
        return grupoExtends.getTodosGrupos();
    }

    /**
     * Método que regresa a todos los grupos solo los que estan habilitados
     *
     * @return arraylist con todos los grupos
     */
    public ArrayList<Grupo> getTodosGruposActivos(){
        return grupoExtends.getTodosGruposActivos();
    }

    /**
     * Regresa a los alumnos inscritos de una determinado grupo incluye a los
     * inctivos
     *
     * @param grupo grupo al que se le desea conocer los alumnos inscritos
     * @return arraylist con alumnos inscritos
     */
    public ArrayList<Alumno> getAlumnosInscritos(Grupo grupo){
        return grupoExtends.getAlumnosInscritos(grupo);
    }

    /**
     * Regresa a los alumnos inscritos de una determinado grupo solo regresara a
     * los que en la lista esten como activos
     *
     * @param grupo grupo al que se le desea conocer los alumnos inscritos
     * @return arraylist con alumnos inscritos
     */
    public ArrayList<Alumno> getAlumnosInscritosActivos(Grupo grupo){
        return listagrupoExtends.getAlumnosInscritosActivos(grupo);
    }

    /**
     *
     * @return
     */
    public ArrayList<Grupo> getGruposSinLista(){
        return asistenciaExtends.mostrarGruposSinLista();
    }

    /**
     *
     * @return
     */
    public ArrayList<Grupo> getGruposPorDia(){
        return grupoExtends.getGruposPorDia();
    }

    /**
     *
     * @param porcentajePenalizacion
     * @param porcentajePagoColaborador
     * @param costoAnual
     * @return
     */
    public boolean agregarConfiguracion(String porcentajePenalizacion, String porcentajePagoColaborador, int costoAnual){
        return configuracionExtends.nuevaConfiguracion(costoAnual, porcentajePagoColaborador, porcentajePenalizacion);
    }

    /**
     *
     * @return
     */
    public Configuracion getConfiguracion(){
        return configuracionExtends.getConfiguracion();
    }

    /**
     *
     * @param alumno
     * @return
     */
    public ArrayList<Pago> getMensualidadesActivas(Alumno alumno){
        return pagoExtends.getMansualidadesActivas(alumno);
    }

    /**
     *
     * @param tipo El tipo debera ser un string "a" para anualidad y "m" para
     * mensualidad
     * @return
     */
    public ArrayList<Pago> getAdeudosActivas(String tipo){
        return pagoExtends.getMansualidadesActivas(tipo);
    }

    /**
     *
     * @param criterio donde toma un valor boleano donde verdadero es anualidad
     * y falso mensualidad
     * @return
     */
    public ArrayList<Promociones> getPromociones(boolean criterio){
        return promocionesExtends.getPromociones(criterio);
    }

    /**
     *
     * @param monto
     * @param periodo
     * @param matriculaAlumno
     * @return
     */
    public boolean agregarAdeudo(String monto, String periodo, Alumno matriculaAlumno){
        return pagoExtends.agregarAdeudo(monto, periodo, matriculaAlumno);
    }

    /**
     *
     * @param alumno
     * @param criterio ocupar un String indicando ya se la anualidad "a" o una
     * "m" para mesualidad
     * @return
     */
    public ArrayList<Pago> getAdeudosActivos(Alumno alumno, String criterio){
        return pagoExtends.getAdeudosActivos(alumno, criterio);
    }

    /**
     *
     * @param promocion
     * @return
     */
    public boolean restarPromocion(Promociones promocion){
        return promocionesExtends.restarPromocion(promocion);
    }

    /**
     *
     * @param colaborador
     * @return
     */
    public String calcularPagoColaborador(Colaborador colaborador){
        return capitalExtends.calcularPagoColaborador(colaborador);
    }

    /**
     *
     * @param colaborador
     * @return
     */
    public String calcularPagoColaboradorPeriodo(Colaborador colaborador){
        return capitalExtends.calcularPagoColaboradorPeriodo(colaborador);
    }

    /**
     *
     * @return
     */
    public ArrayList<Capital> getTodosRegistrosCapital(){
        return capitalExtends.getTodosRegistrosCapital();
    }

    /**
     *
     * @param tipo
     * @return
     */
    public ArrayList<Capital> getRegistrosCapitalTipo(char tipo){
        return capitalExtends.getRegistrosCapitalTipo(tipo);
    }

    /**
     *
     * @param alumno
     * @return
     */
    public int estadoMensualidad(Alumno alumno){
        return alumnoExtends.estadoMensualidad(alumno);
    }

    /**
     *
     * @param alumno
     * @return
     */
    public String calcularPagoMensualidad(Alumno alumno){
        return alumnoExtends.calcularPagoMensualidad(alumno);
    }

    /**
     *
     * @param alumno
     * @param monto
     * @return
     */
    public boolean guardarAdeudoAlumno(Alumno alumno, String monto){
        return capitalExtends.guardarAdeudoAlumno(alumno, monto);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SistemaAleMC ale = new SistemaAleMC();
        GregorianCalendar gc = new GregorianCalendar();
        System.out.println(gc.get(GregorianCalendar.DAY_OF_WEEK));
//        ArrayList<Alumno> result = ale.getTodosAlumnosActivos();
//        Date date = new Date();
//        System.out.println(Util.fechaToString(date));
//        for (Alumno alumno : result){
//            System.out.print("Alumno: " + alumno.toString() + "\t\t ");
//            System.out.println(Util.diasAtraso(alumno.getUltimaFechaPago()));
//        }
    }

}
