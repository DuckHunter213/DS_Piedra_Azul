package alessandramc;

import entidades.Capital;
import entidades.Colaborador;
import entidades.Horario;
import entidades.Promociones;
import entidades.Asistencia;
import entidades.Alumno;
import entidades.Grupo;
import entidades.Listagrupo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import jpaalessandramc.*;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import jpaalessandramc.exceptions.NonexistentEntityException;
import javax.persistence.Query;

/**
 *
 * @author Luis Fernando Gomez Alejandre
 */
public class SistemaAleMC {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    AlumnoJpaController alumnoBD = new AlumnoJpaController(entityManagerFactory);
    ColaboradorJpaController colaboradorBD = new ColaboradorJpaController(entityManagerFactory);
    GrupoJpaController grupoBD = new GrupoJpaController(entityManagerFactory);
    HorarioJpaController horarioBD = new HorarioJpaController(entityManagerFactory);
    ListagrupoJpaController listagrupoBD = new ListagrupoJpaController(entityManagerFactory);
    AsistenciaJpaController asistenciaBD = new AsistenciaJpaController(entityManagerFactory);
    CapitalJpaController capitalBD = new CapitalJpaController(entityManagerFactory);
    PromocionesJpaController promocionBD = new PromocionesJpaController(entityManagerFactory);

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
    public boolean agregarAlumno(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String nombreTutor, String numero, String telefono, String telefonoTutor, Date fecha) {
        Alumno alumno = new Alumno();
        alumno.setApellidoMaterno(apellidoMaterno);
        alumno.setApellidoPaterno(apellidoPaterno);
        alumno.setCalle(calle);
        alumno.setNumero(numero);
        alumno.setColonia(colonia);
        alumno.setCorreo(correo);
        alumno.setEstado(Boolean.TRUE);
        alumno.setFechaNacimiento(fecha);
        alumno.setMatriculaAlumno(Util.generarMatricula());
        alumno.setNombre(nombre);
        alumno.setNombreTutor(nombreTutor);
        alumno.setTelefono(telefono);
        alumno.setTelefonoTutor(telefonoTutor);

        try {
            alumnoBD.create(alumno);
            return true;
        } catch (Exception ex) {
            System.err.println("Ha ocurrido al agregar un alumno");
        }
        return false;
    }

    /**
     *
     * @param cantidadCupones
     * @param nombre
     * @param porcentajeDescuento
     * @return
     */
    public boolean agregarPromocion(String cantidadCupones, String nombre, String porcentajeDescuento, boolean tipo) {
        Promociones promocion = new Promociones();
        promocion.setCantidadCupones(cantidadCupones);
        promocion.setNombre(nombre);
        promocion.setPorcentajeDescuento(porcentajeDescuento);
        promocion.setTipo(tipo);
        try {
            promocionBD.create(promocion);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
    public boolean crearGrupo(String nombre, String precio, List<Horario> listaHorarios, List<Alumno> listaAlumnos, Colaborador colaborador) {
        try {
            String matricula = Util.generarMatriculaGrupo();
            agregarGrupo(nombre, precio, matricula, listaHorarios);
            Grupo grupo = buscarGrupoMatricula(matricula);
            editarColaboradorGrupo(grupo, colaborador);
            agregarAlumnoGrupo(grupo, listaAlumnos);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
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
    public boolean agregarColabordor(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String titulo, String numero, String telefono, Date fecha) {
        Colaborador colaborador = new Colaborador();
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setCalle(calle);
        colaborador.setColonia(colonia);
        colaborador.setNumero(numero);
        colaborador.setEstado(Boolean.TRUE);
        colaborador.setFechaNacimiento(fecha);
        colaborador.setMatriculaColaborador(Util.generarMatricula());
        colaborador.setNombre(nombre);
        colaborador.setTelefono(telefono);
        colaborador.setTitulo(titulo);

        try {
            colaboradorBD.create(colaborador);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Version flexible para agregar grupos sin datos
     *
     * @param nombre
     * @param precio
     * @param matricula
     * @param listaHorarios
     * @return
     */
    private boolean agregarGrupo(String nombre, String precio, String matricula, List<Horario> listaHorarios) {
        Grupo grupo = new Grupo();
        grupo.setEstado(Boolean.TRUE);
        grupo.setMatriculaGrupo(matricula);
        grupo.setNombre(nombre);
        grupo.setPrecio(precio);
        try {
            grupoBD.create(grupo);
            for (Horario h : listaHorarios) {
                h.setMatriculaGrupo(grupo);
                horarioBD.create(h);
            }
            return true;
        } catch (Exception ex) {
            System.err.println("Error en grupo");
        }
        return false;
    }

    private boolean agregarReferenciaHorario(Horario horario, Grupo grupo) {
        horarioBD.create(horario);
        return false;
    }

    /**
     * Agrega la referencia del colaborador a un grupo
     *
     * @param grupo Grupo a donde se agregara el colaborador
     * @param colaborador El colaborador que se le asignara el grupo
     * @return
     */
    public boolean editarColaboradorGrupo(Grupo grupo, Colaborador colaborador) {
        grupo.setMatriculaColaborador(colaborador);
        try {
            grupoBD.edit(grupo);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param monto
     * @param motivo
     * @param tipo
     * @return
     */
    public boolean agregarCapital(String monto, String motivo, Character tipo) {
        Capital capital = new Capital();
        Date fechaCreacion = new Date();
        capital.setFechaCreacion(fechaCreacion);
        capital.setMonto(monto);
        capital.setMotivo(motivo);
        capital.setTipo(tipo);

        try {
            capitalBD.create(capital);
            return true;
        } catch (Exception e) {
            //TODO
        }
        return false;
    }

    /**
     * Agregar una asistencia el método no ha sido probado
     *
     * @param folioListaGrupo
     * @param listagrupoList
     * @return
     */
    public boolean agregarAsistencia(Integer folioListaGrupo, List listagrupoList) {
        Asistencia asistencia = new Asistencia();
        Date fecha = new Date();
        asistencia.setFecha(fecha);
        asistencia.setFolioAsistencia(folioListaGrupo);
        asistencia.setListagrupoList(listagrupoList);

        try {
            asistenciaBD.create(asistencia);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Agrega los alumnos a un grupo determinado siempre y cuando las
     * referencias del grupo esten vacias
     *
     * @param grupo
     * @param listaAlumno
     * @return
     */
    private boolean agregarAlumnoGrupo(Grupo grupo, List<Alumno> listaAlumno) {
        List<Listagrupo> listaGrupo = new ArrayList<>();
        while (!listaAlumno.isEmpty()) {
            Listagrupo lista = new Listagrupo();
            lista.setMatriculaAlumno(listaAlumno.get(0));
            lista.setEstado(Boolean.TRUE);
            listagrupoBD.create(lista);
            listaGrupo.add(lista);
            listaAlumno.remove(0);
        }
        grupo.setListagrupoList(listaGrupo);
        try {
            grupoBD.edit(grupo);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean editarListaAlumnoGrupo(Grupo grupoEditado, List<Alumno> listaAlumno, Grupo grupoOriginal) {
        List<Listagrupo> listaGrupo = new ArrayList<>();
        List<Listagrupo> resultado = new ArrayList<>();
        for (int i = 0; i < listaAlumno.size(); i++) {
            Query query = entityManager.createNamedQuery("Listagrupo.findByInscripcion").setParameter("matriculaGrupo", grupoOriginal).setParameter("matriculaAlumno", listaAlumno.get(i));
            List<Listagrupo> consulta = query.getResultList();

            while (!consulta.isEmpty()) {
                resultado.add(consulta.get(0));
                consulta.remove(0);
            }
        }

        ArrayList<Listagrupo> listaAnterior = new ArrayList<>();
        for (int i = 0; i < resultado.size(); i++) {
            resultado.get(i).setEstado(Boolean.FALSE);
            listaAnterior.add(resultado.get(i));
        }

        for (int i = 0; i < listaAlumno.size(); i++) {
            for (int j = 0; j < resultado.size(); j++) {
                if (listaAlumno.get(i).equals(resultado.get(j).getMatriculaAlumno())) {
                    resultado.get(j).setEstado(Boolean.TRUE);
                    listaGrupo.add(resultado.get(j));
                    try {
                        listagrupoBD.edit(resultado.get(i));
                        listaAlumno.remove(j);
                    } catch (Exception ex) {
                        Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        while (!listaAlumno.isEmpty()) {
            Listagrupo lista = new Listagrupo();
            lista.setEstado(Boolean.TRUE);
            lista.setMatriculaAlumno(listaAlumno.get(0));
            lista.setMatriculaGrupo(grupoEditado);
            listaGrupo.add(lista);
            try {
                listagrupoBD.create(lista);
            } catch (Exception ex) {
                Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
            }
            listaAlumno.remove(0);
        }
        //Checar este metodo para que no deje referencias vacias
        for (int i = 0; i < listaGrupo.size(); i++) {
            for (int j = 0; j < listaAnterior.size(); j++) {
                if (listaGrupo.get(i).getMatriculaAlumno().equals(listaAnterior.get(j).getMatriculaAlumno())) {
                    listaAnterior.remove(j);
                    break;
                }
            }
        }
        for (Listagrupo listagrupo : listaAnterior) {
            System.out.println(listagrupo.toString());
        }

        while (!listaAnterior.isEmpty()) {
            listaGrupo.add(listaAnterior.get(0));
            listaAnterior.remove(0);
        }

        grupoEditado.setListagrupoList(listaGrupo);
        try {
            grupoBD.edit(grupoEditado);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param apellidoMaterno
     * @param apellidoPaterno
     * @param calle
     * @param colonia
     * @param alumno
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
    public boolean editarAlumno(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String nombreTutor, String numero, String telefono, String telefonoTutor, Date fechaNacimiento, String matriculaAlumno) {
        Alumno alumno = buscarAlumnoMatricula(matriculaAlumno);
        alumno.setNombre(nombre);
        alumno.setApellidoPaterno(apellidoPaterno);
        alumno.setApellidoMaterno(apellidoMaterno);
        alumno.setCalle(calle);
        alumno.setColonia(colonia);
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setNombreTutor(nombreTutor);
        alumno.setTelefono(telefono);
        alumno.setTelefonoTutor(telefonoTutor);
        try {
            alumnoBD.edit(alumno);
            return true;
        } catch (NonexistentEntityException ex) {
            System.err.println("No existe el usuario en la base de datos");
        } catch (Exception ex) {
            System.err.println("Hubo un error");
        }
        return false;
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
    public boolean editarColaborador(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String titulo, String numero, String telefono, Date fecha, String matriculaColaborador) {
        Colaborador colaborador = buscarColaboradorMatricula(matriculaColaborador);
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setCalle(calle);
        colaborador.setColonia(colonia);
        colaborador.setEstado(Boolean.TRUE);
        colaborador.setFechaNacimiento(fecha);
        colaborador.setNombre(nombre);
        colaborador.setNumero(numero);
        colaborador.setTelefono(telefono);
        colaborador.setTitulo(titulo);
        try {
            colaboradorBD.edit(colaborador);
            return true;
        } catch (Exception ex) {
            System.err.println("Hubo un error");
        }
        return false;
    }

    /**
     * Edita el grupo esto solo es para los datos, no funciona en habilitar y
     * deshabilitar
     *
     * @param nombre nombre del grupo
     * @param precio precio del grupo
     * @param listaAlumnos lista de alumnos
     * @param listaHorarios lista de horarios
     * @param matricula matricula del grupo
     * @param colaborador referecia del colaborador
     * @return
     */
    public boolean editarGrupo(String nombre, String precio, List<Horario> listaHorarios, List<Alumno> listaAlumnos, Colaborador colaborador, String matricula) {
        Grupo grupoEditado = new Grupo();
        Grupo grupoOriginal = buscarGrupoMatricula(matricula);

        grupoEditado.setNombre(nombre);
        grupoEditado.setPrecio(precio);
        grupoEditado.setMatriculaColaborador(colaborador);
        grupoEditado.setMatriculaGrupo(matricula);
        grupoEditado.setEstado(Boolean.TRUE);

        List<Horario> listaHorarioOriginal = grupoOriginal.getHorarioList();

        for (Horario horario : listaHorarioOriginal) {
            quitarReferenciaHorario(horario, grupoOriginal);
        }
        grupoEditado.setHorarioList(listaHorarios);
        for (Horario horario : listaHorarios) {
            horario.setMatriculaGrupo(grupoEditado);
            agregarReferenciaHorario(horario, grupoEditado);
        }
        for (int i = 0; i < listaAlumnos.size(); i++) {
            quitarReferenciaAlumno(grupoEditado, listaAlumnos.get(i));
            editarListaAlumnoGrupo(grupoEditado, listaAlumnos, grupoOriginal);
        }

        try {
            grupoBD.edit(grupoEditado);
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Busquedas">
    /**
     *
     * @param nombre
     * @return
     */
    public ArrayList<Colaborador> buscarColaborador(String nombre) {
        Query query = entityManager.createNamedQuery("Colaborador.findByNombre").setParameter("nombre", nombre);
        return Util.castListToArray(query.getResultList());
    }

    /**
     *
     * @param nombre
     * @return
     */
    public ArrayList<Alumno> buscarAlumno(String nombre) {
        Query query = entityManager.createNamedQuery("Alumno.findByNombre").setParameter("nombre", nombre);
        return Util.castListToArray(query.getResultList());
    }

    /**
     *
     * @param nombre
     * @return
     */
    public ArrayList<Grupo> buscarGrupo(String nombre) {
        Query query = entityManager.createNamedQuery("Grupo.findByNombre").setParameter("nombre", nombre);
        return Util.castListToArray(query.getResultList());
    }

    /**
     * Buscar a un grupo
     *
     * @param matricula criterio de busqueda del método
     * @return regresa la referencia de un grupo
     */
    public Grupo buscarGrupoMatricula(String matricula) {
        Query query = entityManager.createNamedQuery("Grupo.findByMatriculaGrupo").setParameter("matriculaGrupo", matricula);
        List<Grupo> resultado = query.getResultList();
        return resultado.get(0);
    }

    /**
     * Buscar a un grupo
     *
     * @param matricula criterio de busqueda del método
     * @return regresa la referencia de un grupo
     */
    public Alumno buscarAlumnoMatricula(String matricula) {
        Query query = entityManager.createNamedQuery("Alumno.findByMatriculaAlumno").setParameter("matriculaAlumno", matricula);
        List<Alumno> resultado = query.getResultList();
        return resultado.get(0);
    }

    /**
     * Buscar a un grupo
     *
     * @param matricula criterio de busqueda del método
     * @return regresa la referencia de un grupo
     */
    public Colaborador buscarColaboradorMatricula(String matricula) {
        Query query = entityManager.createNamedQuery("Colaborador.findByMatriculaColaborador").setParameter("matriculaColaborador", matricula);
        List<Colaborador> resultado = query.getResultList();
        return resultado.get(0);
    }
    //</editor-fold>

    public ArrayList<Promociones> getTodasPromocionesActivas() {
        Query query = entityManager.createNamedQuery("Promociones.findAll");
        List<Promociones> result = query.getResultList();
        ArrayList<Promociones> listaResultado = new ArrayList<>();
        for (Promociones promocion : result) {
            if (Integer.parseInt(promocion.getCantidadCupones()) != 0) {
                listaResultado.add(promocion);
            }
        }
        return listaResultado;
    }

    private boolean quitarReferenciaHorario(Horario horario, Grupo grupo) {
        try {
            horarioBD.destroy(horario.getFolioHorario());
            return true;
        } catch (NonexistentEntityException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private boolean quitarReferenciaAlumno(Grupo grupo, Alumno alumno) {
        Query query = entityManager.createNamedQuery("Listagrupo.findByInscripcion").setParameter("matriculaGrupo", grupo).setParameter("matriculaAlumno", alumno);
        ArrayList<Listagrupo> lista = Util.castListToArray(query.getResultList());
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setEstado(Boolean.FALSE);
            try {
                listagrupoBD.edit(lista.get(i));
            } catch (Exception ex) {
                Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SistemaAleMC ale = new SistemaAleMC();

        Grupo grupo = ale.buscarGrupo("Danza Arabe Alluh Balbar").get(0);
        Alumno alumno = ale.alumnoBD.findAlumno("0170329211817");
        ArrayList listaAlumnos = new ArrayList<>();
        listaAlumnos.add(alumno);
        System.err.println("Cambios");

        ArrayList<Horario> listaHorarios = new ArrayList<>();
        Horario horario = new Horario();
        horario.setDia("Lunes");
        horario.setHoraFin("Fin");
        horario.setHoraInicio("Inicio");
        horario.setSalon("108");
        listaHorarios.add(horario);
        ale.editarGrupo(grupo.getNombre(), grupo.getPrecio(), listaHorarios, listaAlumnos, grupo.getMatriculaColaborador(), grupo.getMatriculaGrupo());
    }

    //<editor-fold defaultstate="collapsed" desc="Deshabilitar Habilitar">
    /**
     * Pone el valor de una promocion en 0, no es posible editarlo despues
     *
     * @param promocion referencia a la promoción a desabilitar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarPromocion(Promociones promocion) {
        promocion.setCantidadCupones("0");
        try {
            promocionBD.edit(promocion);
            return true;
        } catch (Exception ex) {

        }
        return false;
    }

    /**
     * Dehabilita el alumno dado
     *
     * @param alumno referencia al alumno a desabilitar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarAlumno(Alumno alumno) {
        alumno.setEstado(Boolean.FALSE);
        try {
            alumnoBD.edit(alumno);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Colaborador que sera descativado
     *
     * @param colaborador referencia del colaborador a desactivar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarColaborador(Colaborador colaborador) {
        colaborador.setEstado(Boolean.FALSE);
        try {
            colaboradorBD.edit(colaborador);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Deshabilitar un grupo evita que aparezca en ciertos métodos
     *
     * @param grupo referencia del grupo a deshabilitar
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarGrupo(Grupo grupo) {
        grupo.setEstado(Boolean.FALSE);
        try {
            grupoBD.edit(grupo);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Habilita al colaborador referenciado
     *
     * @param colaborador
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean habilitarColaborador(Colaborador colaborador) {
        colaborador.setEstado(Boolean.TRUE);
        try {
            colaboradorBD.edit(colaborador);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param grupo
     * @return
     */
    public boolean habilitarGrupo(Grupo grupo) {
        grupo.setEstado(Boolean.TRUE);
        try {
            grupoBD.edit(grupo);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param alumno
     * @return
     */
    public boolean habilitarAlumno(Alumno alumno) {
        alumno.setEstado(Boolean.TRUE);
        try {
            alumnoBD.edit(alumno);
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Deshabilitar alumno de un grupo
     *
     * @param grupo referencia del grupo a deshabilitar
     * @param alumno alumno que sera desabilitado del grupo
     * @return regresa True en caso de poder desabilitarlo y False en caso de no
     * poder
     */
    public boolean desabilitarAlumnoGrupo(Grupo grupo, Alumno alumno) {
        List<Listagrupo> resultado = listagrupoBD.findListagrupoEntities();
        for (Listagrupo listagrupo : resultado) {
            if (listagrupo.getMatriculaGrupo().equals(grupo)) {
                if (listagrupo.getMatriculaAlumno().equals(alumno)) {
                    listagrupo.setEstado(Boolean.FALSE);
                    try {
                        listagrupoBD.edit(listagrupo);
                        return true;
                    } catch (Exception ex) {
                    }
                }
            }
        }
        return false;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Ciertos Métodos Get y Set">
    /**
     * Método que regreasa a todos los alumnos inscritos de la base de datos sin
     * diferenciar que esten activos
     *
     * @return arraylist de alumnos
     */
    public ArrayList<Alumno> getTodosAlumnos() {
        return Util.castListToArray(alumnoBD.findAlumnoEntities());
    }

    public ArrayList<Alumno> getTodosAlumnosActivos() {
        Query query = entityManager.createNamedQuery("Alumno.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    /**
     * Método que regresa a todos los colaboradores sin diferenciar si estan
     * activos
     *
     * @return arraylist de colaboradores
     */
    public ArrayList<Colaborador> getTodosColaboradores() {
        return Util.castListToArray(colaboradorBD.findColaboradorEntities());
    }

    public ArrayList<Colaborador> getTodosColaboradoresActivos() {
        Query query = entityManager.createNamedQuery("Colaborador.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    /**
     * Método que regresa a todos los grupos no importa si estan habilitados o
     * no
     *
     * @return arraylist con todos los grupos
     */
    public ArrayList<Grupo> getTodosGrupos() {
        return Util.castListToArray(grupoBD.findGrupoEntities());
    }

    /**
     * Método que regresa a todos los grupos solo los que estan habilitados
     *
     * @return arraylist con todos los grupos
     */
    public ArrayList<Grupo> getTodosGruposActivos() {
        Query query = entityManager.createNamedQuery("Grupo.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    /**
     * Regresa a los alumnos inscritos de una determinado grupo incluye a los
     * inctivos
     *
     * @param grupo grupo al que se le desea conocer los alumnos inscritos
     * @return arraylist con alumnos inscritos
     */
    public ArrayList<Alumno> getAlumnosInscritos(Grupo grupo) {
        ArrayList<Alumno> listaAlumnosArray = new ArrayList<>();
        List<Listagrupo> resultado = listagrupoBD.findListagrupoEntities();

        for (Listagrupo listagrupo : resultado) {
            if (listagrupo.getMatriculaGrupo().equals(grupo)) {
                listaAlumnosArray.add(listagrupo.getMatriculaAlumno());
            }
        }

        return listaAlumnosArray;
    }

    /**
     * Regresa a los alumnos inscritos de una determinado grupo solo regresara a
     * los que en la lista esten como activos
     *
     * @param grupo grupo al que se le desea conocer los alumnos inscritos
     * @return arraylist con alumnos inscritos
     */
    public ArrayList<Alumno> getAlumnosInscritosActivos(Grupo grupo) {
        ArrayList<Alumno> listaAlumnosArray = new ArrayList<>();

        Query query = entityManager.createNamedQuery("Listagrupo.findByAlumnosActivos").setParameter("matriculaGrupo", grupo).setParameter("estado", true);
        List<Listagrupo> resultado = query.getResultList();

        for (Listagrupo listagrupo : resultado) {
            if (listagrupo.getMatriculaGrupo().equals(grupo)) {
                listaAlumnosArray.add(listagrupo.getMatriculaAlumno());
            }
        }

        return listaAlumnosArray;
    }
    //</editor-fold>

}
