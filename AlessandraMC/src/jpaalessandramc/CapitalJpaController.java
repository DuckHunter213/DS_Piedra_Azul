/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaalessandramc;

import entidades.Capital;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import jpaalessandramc.exceptions.NonexistentEntityException;

/**
 *
 * @author DARKENSES
 */
public class CapitalJpaController implements Serializable{
    public CapitalJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Capital capital){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(capital);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Capital capital) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            capital = em.merge(capital);
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Integer id = capital.getFolioCapital();
                if (findCapital(id) == null){
                    throw new NonexistentEntityException("The capital with id " + id + " no longer exists.");
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
            Capital capital;
            try{
                capital = em.getReference(Capital.class, id);
                capital.getFolioCapital();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The capital with id " + id + " no longer exists.", enfe);
            }
            em.remove(capital);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Capital> findCapitalEntities(){
        return findCapitalEntities(true, -1, -1);
    }

    public List<Capital> findCapitalEntities(int maxResults, int firstResult){
        return findCapitalEntities(false, maxResults, firstResult);
    }

    private List<Capital> findCapitalEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Capital.class));
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

    public Capital findCapital(Integer id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Capital.class, id);
        }finally{
            em.close();
        }
    }

    public int getCapitalCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Capital> rt = cq.from(Capital.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }

}
