package entidadesExtends;

import alessandramc.Util;
import entidades.Alumno;
import entidades.Asistencia;
import entidades.Grupo;
import entidades.Listagrupo;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaalessandramc.AsistenciaJpaController;

/**
 *
 * @author Luis Fernando Gomez Alejandre
 */
public class AsistenciaExtends extends Asistencia{

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    AsistenciaJpaController asistenciaBD = new AsistenciaJpaController(entityManagerFactory);

    public boolean agregarAsistencia(Grupo grupo, ArrayList<Alumno> listaAlumnos){
        for (Alumno listaAlumno : listaAlumnos){
            Query query = entityManager.createNamedQuery("Listagrupo.findByInscripcion").setParameter("matriculaGrupo", grupo).setParameter("matriculaAlumno", listaAlumno);
            ArrayList<Listagrupo> lista = Util.castListToArray(query.getResultList());

            for (Listagrupo listagrupo : lista){
                Asistencia asistencia = new Asistencia();
                asistencia.setFecha(new Date());
                asistencia.setFolioAsistencia(listagrupo.getFolioListaGrupo());
                asistencia.setFolioListaGrupo(listagrupo);
                try{
                    asistenciaBD.create(asistencia);
                    return true;
                }catch (Exception e){
                    System.err.println("Hubo un error en el pase de asistencia");
                }
            }
        }

        return false;
    }

    public ArrayList<Grupo> mostrarGruposSinLista(){
        GrupoExtends grupoExtends = new GrupoExtends();
        ArrayList<Grupo> listaGrupo = grupoExtends.getGruposPorDia();

        Query query = entityManager.createNamedQuery("Asistencia.findByFecha").setParameter("fecha", new Date());
        List<Asistencia> listaAsistencias = query.getResultList();

        for (int i = 0; i < listaAsistencias.size(); i++){
            for (int j = 0; j < listaGrupo.size(); j++){
                if (listaAsistencias.get(i).getFolioListaGrupo().getMatriculaGrupo().equals(listaGrupo.get(j))){
                    listaGrupo.remove(j);
                }
            }
        }

        return listaGrupo;
    }

}
