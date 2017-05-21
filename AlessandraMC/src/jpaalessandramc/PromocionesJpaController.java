/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaalessandramc;

import entidades.Promociones;
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
public class PromocionesJpaController implements Serializable{
    public PromocionesJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Promociones promociones){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(promociones);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Promociones promociones) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            promociones = em.merge(promociones);
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Integer id = promociones.getFolioPromocion();
                if (findPromociones(id) == null){
                    throw new NonexistentEntityException("The promociones with id " + id + " no longer exists.");
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
            Promociones promociones;
            try{
                promociones = em.getReference(Promociones.class, id);
                promociones.getFolioPromocion();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The promociones with id " + id + " no longer exists.", enfe);
            }
            em.remove(promociones);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Promociones> findPromocionesEntities(){
        return findPromocionesEntities(true, -1, -1);
    }

    public List<Promociones> findPromocionesEntities(int maxResults, int firstResult){
        return findPromocionesEntities(false, maxResults, firstResult);
    }

    private List<Promociones> findPromocionesEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Promociones.class));
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

    public Promociones findPromociones(Integer id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Promociones.class, id);
        }finally{
            em.close();
        }
    }

    public int getPromocionesCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Promociones> rt = cq.from(Promociones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }

}
