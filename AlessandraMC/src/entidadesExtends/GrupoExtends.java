package entidadesExtends;

import alessandramc.SistemaAleMC;
import alessandramc.Util;
import entidades.Alumno;
import entidades.Colaborador;
import entidades.Grupo;
import entidades.Horario;
import entidades.Listagrupo;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaalessandramc.GrupoJpaController;
import jpaalessandramc.HorarioJpaController;
import jpaalessandramc.ListagrupoJpaController;
import jpaalessandramc.exceptions.NonexistentEntityException;

/**
 *
 * @author DARKENSES
 */
public class GrupoExtends extends Grupo{
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    ListagrupoJpaController listagrupoBD = new ListagrupoJpaController(entityManagerFactory);
    GrupoJpaController grupoBD = new GrupoJpaController(entityManagerFactory);
    HorarioJpaController horarioBD = new HorarioJpaController(entityManagerFactory);

    public boolean desabilitarAlumnoGrupo(Grupo grupo, Alumno alumno){
        List<Listagrupo> resultado = listagrupoBD.findListagrupoEntities();
        for (Listagrupo listagrupo : resultado){
            if (listagrupo.getMatriculaGrupo().equals(grupo)){
                if (listagrupo.getMatriculaAlumno().equals(alumno)){
                    listagrupo.setEstado(Boolean.FALSE);
                    try{
                        listagrupoBD.edit(listagrupo);
                        return true;
                    }catch (Exception ex){
                    }
                }
            }
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
    public boolean agregarGrupo(String nombre, String precio, String matricula, List<Horario> listaHorarios){
        Grupo grupo = new Grupo();
        grupo.setEstado(Boolean.TRUE);
        grupo.setMatriculaGrupo(matricula);
        grupo.setNombre(nombre);
        grupo.setPrecio(precio);
        try{
            grupoBD.create(grupo);

            return true;
        }catch (Exception ex){
            System.err.println("Error en grupo");
        }
        return false;
    }

    public void agregarHorarioGrupo(Grupo grupo, List<Horario> listaHorarios){
        for (Horario horario : listaHorarios){
            horario.setMatriculaGrupo(grupo);
            horarioBD.create(horario);
        }
        grupo.setHorarioList(listaHorarios);
    }

    /**
     * Agrega la referencia del colaborador a un grupo
     *
     * @param grupo Grupo a donde se agregara el colaborador
     * @param colaborador El colaborador que se le asignara el grupo
     * @return
     */
    public boolean editarColaboradorGrupo(Grupo grupo, Colaborador colaborador){
        grupo.setMatriculaColaborador(colaborador);
        try{
            grupoBD.edit(grupo);
            return true;
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean agregarAlumnoGrupo(Grupo grupo, List<Alumno> listaAlumno){
        List<Listagrupo> listaGrupo = new ArrayList<>();
        while (!listaAlumno.isEmpty()){
            Listagrupo lista = new Listagrupo();
            lista.setMatriculaAlumno(listaAlumno.get(0));
            lista.setEstado(Boolean.TRUE);
            listagrupoBD.create(lista);
            listaGrupo.add(lista);
            listaAlumno.remove(0);
        }
        grupo.setListagrupoList(listaGrupo);
        try{
            grupoBD.edit(grupo);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public boolean habilitarGrupo(Grupo grupo){
        grupo.setEstado(Boolean.TRUE);
        try{
            grupoBD.edit(grupo);
            return true;
        }catch (NonexistentEntityException ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean desabilitarGrupo(Grupo grupo){
        grupo.setEstado(Boolean.FALSE);
        try{
            grupoBD.edit(grupo);
            return true;
        }catch (NonexistentEntityException ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean quitarReferenciaHorario(Horario horario, Grupo grupo){
        try{
            horarioBD.destroy(horario.getFolioHorario());
            return true;
        }catch (NonexistentEntityException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public boolean establecerReferenciaHorario(Horario horario, Grupo grupo){
        horarioBD.create(horario);
        return false;
    }

    public ArrayList<Grupo> getGruposPorDia(){
        GregorianCalendar gc = new GregorianCalendar();
        int diaSemana = gc.get(GregorianCalendar.DAY_OF_WEEK);
        String diaSemanaString;
        switch (diaSemana){
            case 1:
                diaSemanaString = "Domingo";
                break;
            case 2:
                diaSemanaString = "Lunes";
                break;
            case 3:
                diaSemanaString = "Martes";
                break;
            case 4:
                diaSemanaString = "Miércoles";
                break;
            case 5:
                diaSemanaString = "Jueves";
                break;
            case 6:
                diaSemanaString = "Viernes";
                break;
            case 7:
                diaSemanaString = "Sábado";
                break;
            default:
                diaSemanaString = "";
                break;
        }

        Query query = entityManager.createNamedQuery("Horario.findByDia").setParameter("dia", diaSemanaString);
        List<Horario> result = query.getResultList();
        ArrayList<Grupo> resultado = new ArrayList<>();
        for (Horario horario : result){
            resultado.add(horario.getMatriculaGrupo());
        }

        for (int i = 0; i < resultado.size(); i++){
            if (!resultado.get(0).getEstado()){
                resultado.remove(i);
            }
        }

        return Util.castListToArray(resultado);
    }

    public boolean validarHorarios(){

        if (true){
            return true;
        }
        return false;
    }

    public boolean editarGrupo(String nombre, String precio, List<Horario> listaHorarios, List<Alumno> listaAlumnos, ArrayList<Alumno> alumnosRetirados, Colaborador colaborador, String matricula){
        Grupo grupoEditado = new Grupo();
        Query query = entityManager.createNamedQuery("Grupo.findByMatriculaGrupo").setParameter("matriculaGrupo", matricula);
        Grupo grupoOriginal = (Grupo) query.getResultList().get(0);        
        grupoEditado.setListagrupoList(grupoOriginal.getListagrupoList());
        grupoEditado.setNombre(nombre);
        grupoEditado.setPrecio(precio);
        grupoEditado.setMatriculaColaborador(colaborador);
        grupoEditado.setMatriculaGrupo(matricula);
        grupoEditado.setEstado(Boolean.TRUE);

        List<Horario> listaHorarioOriginal = grupoOriginal.getHorarioList();

        for (Horario horario : listaHorarioOriginal){
            quitarReferenciaHorario(horario, grupoOriginal);
        }
        grupoEditado.setHorarioList(listaHorarios);
        for (Horario horario : listaHorarios){
            horario.setMatriculaGrupo(grupoEditado);
            horarioBD.create(horario);
        }

        editarListaAlumnoGrupo(Util.castListToArray(listaAlumnos), alumnosRetirados, grupoEditado);
        System.out.println("alessandramc.SistemaAleMC.editarGrupo()");

        return false;
    }

    public boolean editarListaAlumnoGrupo(ArrayList<Alumno> listaAlumnoNuevos, ArrayList<Alumno> listaAlumnoQuitados, Grupo grupo){
        List<Listagrupo> nuevaLista = grupo.getListagrupoList();

        for (Alumno alumnoQuitado : listaAlumnoQuitados){
            Query query = entityManager.createNamedQuery("Listagrupo.findByInscritosInactivos").setParameter("matriculaGrupo", grupo).setParameter("matriculaAlumno", alumnoQuitado).setParameter("estado", true);
            List<Listagrupo> consulta = query.getResultList();
            if (!consulta.isEmpty()){
                consulta.get(0).setEstado(Boolean.FALSE);
                try{
                    listagrupoBD.edit(consulta.get(0));
                }catch (Exception ex){
                    Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //Ocupa una busqueda que si conside con los alumnos que ya estaban ponlos en TRUE
        for (Alumno alumno : listaAlumnoNuevos){
            Query query = entityManager.createNamedQuery("Listagrupo.findByInscritosInactivos").setParameter("matriculaGrupo", grupo).setParameter("matriculaAlumno", alumno).setParameter("estado", false);
            List<Listagrupo> consulta = query.getResultList();
            if (!consulta.isEmpty()){
                consulta.get(0).setEstado(Boolean.TRUE);
                try{
                    listagrupoBD.edit(consulta.get(0));
                }catch (Exception ex){
                    Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Crea las referencias para alumnos nuevos
        for (Alumno alumno : listaAlumnoNuevos){
            Query query = entityManager.createNamedQuery("Listagrupo.findByInscritosInactivos").setParameter("matriculaGrupo", grupo).setParameter("matriculaAlumno", alumno).setParameter("estado", true);
            List<Listagrupo> consulta = query.getResultList();
            if (consulta.isEmpty()){
                Listagrupo listaGrupo = new Listagrupo();
                listaGrupo.setEstado(Boolean.TRUE);
                listaGrupo.setMatriculaAlumno(alumno);
                listaGrupo.setMatriculaGrupo(grupo);
                listagrupoBD.create(listaGrupo);
                nuevaLista.add(listaGrupo);
            }
        }
        grupo.setListagrupoList(nuevaLista);
        try{
            grupoBD.edit(grupo);
            return true;
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean crearGrupo(String nombre, String precio, List<Horario> listaHorarios, List<Alumno> listaAlumnos, Colaborador colaborador){
        try{
            String matricula = Util.generarMatriculaGrupo();
            agregarGrupo(nombre, precio, matricula, listaHorarios);
            Query query = entityManager.createNamedQuery("Grupo.findByMatriculaGrupo").setParameter("matriculaGrupo", matricula);
            Grupo grupo = (Grupo) query.getResultList().get(0);
            agregarHorarioGrupo(grupo, listaHorarios);
            editarColaboradorGrupo(grupo, colaborador);
            agregarAlumnoGrupo(grupo, listaAlumnos);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    public int contarAlumnos(Grupo grupo){
        Query query = entityManager.createNamedQuery("Listagrupo.findByAlumnosActivos").setParameter("matriculaGrupo", grupo).setParameter("estado", true);
        return query.getResultList().size();
    }

    /**
     * Método que regresa a todos los grupos solo los que estan habilitados
     *
     * @return arraylist con todos los grupos
     */
    public ArrayList<Grupo> getTodosGruposActivos(){
        Query query = entityManager.createNamedQuery("Grupo.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    public Grupo buscarGrupoMatricula(String matricula){
        Query query = entityManager.createNamedQuery("Grupo.findByMatriculaGrupo").setParameter("matriculaGrupo", matricula);
        return (Grupo) query.getResultList().get(0);
    }

    public ArrayList<Grupo> buscarGrupo(String nombre){
        Query query = entityManager.createNamedQuery("Grupo.findByNombre").setParameter("nombre", nombre);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Grupo> getTodosGrupos(){
        return Util.castListToArray(grupoBD.findGrupoEntities());
    }

    public ArrayList<Alumno> getAlumnosInscritos(Grupo grupo){
        return Util.castListToArray(listagrupoBD.findListagrupoEntities());
    }

}
