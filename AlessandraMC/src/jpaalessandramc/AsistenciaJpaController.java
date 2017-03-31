/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaalessandramc;

import entidades.Asistencia;
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

/**
 *
 * @author DARKENSES
 */
public class AsistenciaJpaController implements Serializable{
    public AsistenciaJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia){
        if (asistencia.getListagrupoList() == null){
            asistencia.setListagrupoList(new ArrayList<Listagrupo>());
        }
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            List<Listagrupo> attachedListagrupoList = new ArrayList<Listagrupo>();
            for (Listagrupo listagrupoListListagrupoToAttach : asistencia.getListagrupoList()){
                listagrupoListListagrupoToAttach = em.getReference(listagrupoListListagrupoToAttach.getClass(), listagrupoListListagrupoToAttach.getFolioListaGrupo());
                attachedListagrupoList.add(listagrupoListListagrupoToAttach);
            }
            asistencia.setListagrupoList(attachedListagrupoList);
            em.persist(asistencia);
            for (Listagrupo listagrupoListListagrupo : asistencia.getListagrupoList()){
                Asistencia oldFolioAsistenciaOfListagrupoListListagrupo = listagrupoListListagrupo.getFolioAsistencia();
                listagrupoListListagrupo.setFolioAsistencia(asistencia);
                listagrupoListListagrupo = em.merge(listagrupoListListagrupo);
                if (oldFolioAsistenciaOfListagrupoListListagrupo != null){
                    oldFolioAsistenciaOfListagrupoListListagrupo.getListagrupoList().remove(listagrupoListListagrupo);
                    oldFolioAsistenciaOfListagrupoListListagrupo = em.merge(oldFolioAsistenciaOfListagrupoListListagrupo);
                }
            }
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Asistencia asistencia) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getFolioAsistencia());
            List<Listagrupo> listagrupoListOld = persistentAsistencia.getListagrupoList();
            List<Listagrupo> listagrupoListNew = asistencia.getListagrupoList();
            List<Listagrupo> attachedListagrupoListNew = new ArrayList<Listagrupo>();
            for (Listagrupo listagrupoListNewListagrupoToAttach : listagrupoListNew){
                listagrupoListNewListagrupoToAttach = em.getReference(listagrupoListNewListagrupoToAttach.getClass(), listagrupoListNewListagrupoToAttach.getFolioListaGrupo());
                attachedListagrupoListNew.add(listagrupoListNewListagrupoToAttach);
            }
            listagrupoListNew = attachedListagrupoListNew;
            asistencia.setListagrupoList(listagrupoListNew);
            asistencia = em.merge(asistencia);
            for (Listagrupo listagrupoListOldListagrupo : listagrupoListOld){
                if (!listagrupoListNew.contains(listagrupoListOldListagrupo)){
                    listagrupoListOldListagrupo.setFolioAsistencia(null);
                    listagrupoListOldListagrupo = em.merge(listagrupoListOldListagrupo);
                }
            }
            for (Listagrupo listagrupoListNewListagrupo : listagrupoListNew){
                if (!listagrupoListOld.contains(listagrupoListNewListagrupo)){
                    Asistencia oldFolioAsistenciaOfListagrupoListNewListagrupo = listagrupoListNewListagrupo.getFolioAsistencia();
                    listagrupoListNewListagrupo.setFolioAsistencia(asistencia);
                    listagrupoListNewListagrupo = em.merge(listagrupoListNewListagrupo);
                    if (oldFolioAsistenciaOfListagrupoListNewListagrupo != null && !oldFolioAsistenciaOfListagrupoListNewListagrupo.equals(asistencia)){
                        oldFolioAsistenciaOfListagrupoListNewListagrupo.getListagrupoList().remove(listagrupoListNewListagrupo);
                        oldFolioAsistenciaOfListagrupoListNewListagrupo = em.merge(oldFolioAsistenciaOfListagrupoListNewListagrupo);
                    }
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Integer id = asistencia.getFolioAsistencia();
                if (findAsistencia(id) == null){
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
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
            Asistencia asistencia;
            try{
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getFolioAsistencia();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            List<Listagrupo> listagrupoList = asistencia.getListagrupoList();
            for (Listagrupo listagrupoListListagrupo : listagrupoList){
                listagrupoListListagrupo.setFolioAsistencia(null);
                listagrupoListListagrupo = em.merge(listagrupoListListagrupo);
            }
            em.remove(asistencia);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Asistencia> findAsistenciaEntities(){
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult){
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(Integer id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Asistencia.class, id);
        }finally{
            em.close();
        }
    }

    public int getAsistenciaCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }
    
}
