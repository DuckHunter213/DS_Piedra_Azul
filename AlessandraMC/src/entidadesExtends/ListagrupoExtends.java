package entidadesExtends;

import alessandramc.Util;
import entidades.Alumno;
import entidades.Grupo;
import entidades.Listagrupo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author DARKENSES
 */
public class ListagrupoExtends extends Listagrupo{

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    public ArrayList<Listagrupo> regresarListaAlumnoGrupo(Grupo grupo, Alumno alumno){
        Query query = entityManager.createNamedQuery("Listagrupo.findByInscritosInactivos").setParameter("matriculaGrupo", grupo).setParameter("matriculaAlumno", alumno).setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    /**
     * Regresa a los alumnos inscritos de una determinado grupo solo regresara a
     * los que en la lista esten como activos
     *
     * @param grupo grupo al que se le desea conocer los alumnos inscritos
     * @return arraylist con alumnos inscritos
     */
    public ArrayList<Alumno> getAlumnosInscritosActivos(Grupo grupo){
        ArrayList<Alumno> listaAlumnosArray = new ArrayList<>();

        Query query = entityManager.createNamedQuery("Listagrupo.findByAlumnosActivos").setParameter("matriculaGrupo", grupo).setParameter("estado", true);
        List<Listagrupo> resultado = query.getResultList();

        for (Listagrupo listagrupo : resultado){
            if (listagrupo.getMatriculaGrupo().equals(grupo)){
                listaAlumnosArray.add(listagrupo.getMatriculaAlumno());
            }
        }
        return listaAlumnosArray;
    }

}
