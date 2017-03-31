/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaalessandramc;

import entidades.Alumno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Listagrupo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaalessandramc.exceptions.NonexistentEntityException;
import jpaalessandramc.exceptions.PreexistingEntityException;

/**
 *
 * @author DARKENSES
 */
public class AlumnoJpaController implements Serializable{
    public AlumnoJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) throws PreexistingEntityException, Exception{
        if (alumno.getListagrupoList() == null){
            alumno.setListagrupoList(new ArrayList<Listagrupo>());
        }
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            List<Listagrupo> attachedListagrupoList = new ArrayList<Listagrupo>();
            for (Listagrupo listagrupoListListagrupoToAttach : alumno.getListagrupoList()){
                listagrupoListListagrupoToAttach = em.getReference(listagrupoListListagrupoToAttach.getClass(), listagrupoListListagrupoToAttach.getFolioListaGrupo());
                attachedListagrupoList.add(listagrupoListListagrupoToAttach);
            }
            alumno.setListagrupoList(attachedListagrupoList);
            em.persist(alumno);
            for (Listagrupo listagrupoListListagrupo : alumno.getListagrupoList()){
                Alumno oldMatriculaAlumnoOfListagrupoListListagrupo = listagrupoListListagrupo.getMatriculaAlumno();
                listagrupoListListagrupo.setMatriculaAlumno(alumno);
                listagrupoListListagrupo = em.merge(listagrupoListListagrupo);
                if (oldMatriculaAlumnoOfListagrupoListListagrupo != null){
                    oldMatriculaAlumnoOfListagrupoListListagrupo.getListagrupoList().remove(listagrupoListListagrupo);
                    oldMatriculaAlumnoOfListagrupoListListagrupo = em.merge(oldMatriculaAlumnoOfListagrupoListListagrupo);
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            if (findAlumno(alumno.getMatriculaAlumno()) != null){
                throw new PreexistingEntityException("Alumno " + alumno + " already exists.", ex);
            }
            throw ex;
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getMatriculaAlumno());
            List<Listagrupo> listagrupoListOld = persistentAlumno.getListagrupoList();
            List<Listagrupo> listagrupoListNew = alumno.getListagrupoList();
            List<Listagrupo> attachedListagrupoListNew = new ArrayList<Listagrupo>();
            for (Listagrupo listagrupoListNewListagrupoToAttach : listagrupoListNew){
                listagrupoListNewListagrupoToAttach = em.getReference(listagrupoListNewListagrupoToAttach.getClass(), listagrupoListNewListagrupoToAttach.getFolioListaGrupo());
                attachedListagrupoListNew.add(listagrupoListNewListagrupoToAttach);
            }
            listagrupoListNew = attachedListagrupoListNew;
            alumno.setListagrupoList(listagrupoListNew);
            alumno = em.merge(alumno);
            for (Listagrupo listagrupoListOldListagrupo : listagrupoListOld){
                if (!listagrupoListNew.contains(listagrupoListOldListagrupo)){
                    listagrupoListOldListagrupo.setMatriculaAlumno(null);
                    listagrupoListOldListagrupo = em.merge(listagrupoListOldListagrupo);
                }
            }
            for (Listagrupo listagrupoListNewListagrupo : listagrupoListNew){
                if (!listagrupoListOld.contains(listagrupoListNewListagrupo)){
                    Alumno oldMatriculaAlumnoOfListagrupoListNewListagrupo = listagrupoListNewListagrupo.getMatriculaAlumno();
                    listagrupoListNewListagrupo.setMatriculaAlumno(alumno);
                    listagrupoListNewListagrupo = em.merge(listagrupoListNewListagrupo);
                    if (oldMatriculaAlumnoOfListagrupoListNewListagrupo != null && !oldMatriculaAlumnoOfListagrupoListNewListagrupo.equals(alumno)){
                        oldMatriculaAlumnoOfListagrupoListNewListagrupo.getListagrupoList().remove(listagrupoListNewListagrupo);
                        oldMatriculaAlumnoOfListagrupoListNewListagrupo = em.merge(oldMatriculaAlumnoOfListagrupoListNewListagrupo);
                    }
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                String id = alumno.getMatriculaAlumno();
                if (findAlumno(id) == null){
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno alumno;
            try{
                alumno = em.getReference(Alumno.class, id);
                alumno.getMatriculaAlumno();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            List<Listagrupo> listagrupoList = alumno.getListagrupoList();
            for (Listagrupo listagrupoListListagrupo : listagrupoList){
                listagrupoListListagrupo.setMatriculaAlumno(null);
                listagrupoListListagrupo = em.merge(listagrupoListListagrupo);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities(){
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult){
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
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

    public Alumno findAlumno(String id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Alumno.class, id);
        }finally{
            em.close();
        }
    }

    public int getAlumnoCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }
    
}
