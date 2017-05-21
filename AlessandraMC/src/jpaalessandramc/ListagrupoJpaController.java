/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaalessandramc;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Grupo;
import entidades.Alumno;
import entidades.Asistencia;
import entidades.Listagrupo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaalessandramc.exceptions.NonexistentEntityException;

/**
 *
 * @author DARKENSES
 */
public class ListagrupoJpaController implements Serializable{
    public ListagrupoJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Listagrupo listagrupo){
        if (listagrupo.getAsistenciaList() == null){
            listagrupo.setAsistenciaList(new ArrayList<Asistencia>());
        }
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo matriculaGrupo = listagrupo.getMatriculaGrupo();
            if (matriculaGrupo != null){
                matriculaGrupo = em.getReference(matriculaGrupo.getClass(), matriculaGrupo.getMatriculaGrupo());
                listagrupo.setMatriculaGrupo(matriculaGrupo);
            }
            Alumno matriculaAlumno = listagrupo.getMatriculaAlumno();
            if (matriculaAlumno != null){
                matriculaAlumno = em.getReference(matriculaAlumno.getClass(), matriculaAlumno.getMatriculaAlumno());
                listagrupo.setMatriculaAlumno(matriculaAlumno);
            }
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : listagrupo.getAsistenciaList()){
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getFolioAsistencia());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            listagrupo.setAsistenciaList(attachedAsistenciaList);
            em.persist(listagrupo);
            if (matriculaGrupo != null){
                matriculaGrupo.getListagrupoList().add(listagrupo);
                matriculaGrupo = em.merge(matriculaGrupo);
            }
            if (matriculaAlumno != null){
                matriculaAlumno.getListagrupoList().add(listagrupo);
                matriculaAlumno = em.merge(matriculaAlumno);
            }
            for (Asistencia asistenciaListAsistencia : listagrupo.getAsistenciaList()){
                Listagrupo oldFolioListaGrupoOfAsistenciaListAsistencia = asistenciaListAsistencia.getFolioListaGrupo();
                asistenciaListAsistencia.setFolioListaGrupo(listagrupo);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldFolioListaGrupoOfAsistenciaListAsistencia != null){
                    oldFolioListaGrupoOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldFolioListaGrupoOfAsistenciaListAsistencia = em.merge(oldFolioListaGrupoOfAsistenciaListAsistencia);
                }
            }
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Listagrupo listagrupo) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Listagrupo persistentListagrupo = em.find(Listagrupo.class, listagrupo.getFolioListaGrupo());
            Grupo matriculaGrupoOld = persistentListagrupo.getMatriculaGrupo();
            Grupo matriculaGrupoNew = listagrupo.getMatriculaGrupo();
            Alumno matriculaAlumnoOld = persistentListagrupo.getMatriculaAlumno();
            Alumno matriculaAlumnoNew = listagrupo.getMatriculaAlumno();
            List<Asistencia> asistenciaListOld = persistentListagrupo.getAsistenciaList();
            List<Asistencia> asistenciaListNew = listagrupo.getAsistenciaList();
            if (matriculaGrupoNew != null){
                matriculaGrupoNew = em.getReference(matriculaGrupoNew.getClass(), matriculaGrupoNew.getMatriculaGrupo());
                listagrupo.setMatriculaGrupo(matriculaGrupoNew);
            }
            if (matriculaAlumnoNew != null){
                matriculaAlumnoNew = em.getReference(matriculaAlumnoNew.getClass(), matriculaAlumnoNew.getMatriculaAlumno());
                listagrupo.setMatriculaAlumno(matriculaAlumnoNew);
            }
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew){
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getFolioAsistencia());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            listagrupo.setAsistenciaList(asistenciaListNew);
            listagrupo = em.merge(listagrupo);
            if (matriculaGrupoOld != null && !matriculaGrupoOld.equals(matriculaGrupoNew)){
                matriculaGrupoOld.getListagrupoList().remove(listagrupo);
                matriculaGrupoOld = em.merge(matriculaGrupoOld);
            }
            if (matriculaGrupoNew != null && !matriculaGrupoNew.equals(matriculaGrupoOld)){
                matriculaGrupoNew.getListagrupoList().add(listagrupo);
                matriculaGrupoNew = em.merge(matriculaGrupoNew);
            }
            if (matriculaAlumnoOld != null && !matriculaAlumnoOld.equals(matriculaAlumnoNew)){
                matriculaAlumnoOld.getListagrupoList().remove(listagrupo);
                matriculaAlumnoOld = em.merge(matriculaAlumnoOld);
            }
            if (matriculaAlumnoNew != null && !matriculaAlumnoNew.equals(matriculaAlumnoOld)){
                matriculaAlumnoNew.getListagrupoList().add(listagrupo);
                matriculaAlumnoNew = em.merge(matriculaAlumnoNew);
            }
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld){
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)){
                    asistenciaListOldAsistencia.setFolioListaGrupo(null);
                    asistenciaListOldAsistencia = em.merge(asistenciaListOldAsistencia);
                }
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew){
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)){
                    Listagrupo oldFolioListaGrupoOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getFolioListaGrupo();
                    asistenciaListNewAsistencia.setFolioListaGrupo(listagrupo);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldFolioListaGrupoOfAsistenciaListNewAsistencia != null && !oldFolioListaGrupoOfAsistenciaListNewAsistencia.equals(listagrupo)){
                        oldFolioListaGrupoOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldFolioListaGrupoOfAsistenciaListNewAsistencia = em.merge(oldFolioListaGrupoOfAsistenciaListNewAsistencia);
                    }
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Integer id = listagrupo.getFolioListaGrupo();
                if (findListagrupo(id) == null){
                    throw new NonexistentEntityException("The listagrupo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Listagrupo listagrupo;
            try{
                listagrupo = em.getReference(Listagrupo.class, id);
                listagrupo.getFolioListaGrupo();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The listagrupo with id " + id + " no longer exists.", enfe);
            }
            Grupo matriculaGrupo = listagrupo.getMatriculaGrupo();
            if (matriculaGrupo != null){
                matriculaGrupo.getListagrupoList().remove(listagrupo);
                matriculaGrupo = em.merge(matriculaGrupo);
            }
            Alumno matriculaAlumno = listagrupo.getMatriculaAlumno();
            if (matriculaAlumno != null){
                matriculaAlumno.getListagrupoList().remove(listagrupo);
                matriculaAlumno = em.merge(matriculaAlumno);
            }
            List<Asistencia> asistenciaList = listagrupo.getAsistenciaList();
            for (Asistencia asistenciaListAsistencia : asistenciaList){
                asistenciaListAsistencia.setFolioListaGrupo(null);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
            }
            em.remove(listagrupo);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Listagrupo> findListagrupoEntities(){
        return findListagrupoEntities(true, -1, -1);
    }

    public List<Listagrupo> findListagrupoEntities(int maxResults, int firstResult){
        return findListagrupoEntities(false, maxResults, firstResult);
    }

    private List<Listagrupo> findListagrupoEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Listagrupo.class));
            Query q = em.createQuery(cq);
            if (!all){
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }finally{
            em.close();
        }
    }

    public Listagrupo findListagrupo(Integer id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Listagrupo.class, id);
        }finally{
            em.close();
        }
    }

    public int getListagrupoCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Listagrupo> rt = cq.from(Listagrupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }

}
