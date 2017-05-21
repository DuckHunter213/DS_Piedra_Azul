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
import entidades.Alumno;
import entidades.Pago;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaalessandramc.exceptions.NonexistentEntityException;

/**
 *
 * @author DARKENSES
 */
public class PagoJpaController implements Serializable{
    public PagoJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Pago pago){
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno matriculaAlumno = pago.getMatriculaAlumno();
            if (matriculaAlumno != null){
                matriculaAlumno = em.getReference(matriculaAlumno.getClass(), matriculaAlumno.getMatriculaAlumno());
                pago.setMatriculaAlumno(matriculaAlumno);
            }
            em.persist(pago);
            if (matriculaAlumno != null){
                matriculaAlumno.getPagoList().add(pago);
                matriculaAlumno = em.merge(matriculaAlumno);
            }
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Pago pago) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Pago persistentPago = em.find(Pago.class, pago.getFolioPago());
            Alumno matriculaAlumnoOld = persistentPago.getMatriculaAlumno();
            Alumno matriculaAlumnoNew = pago.getMatriculaAlumno();
            if (matriculaAlumnoNew != null){
                matriculaAlumnoNew = em.getReference(matriculaAlumnoNew.getClass(), matriculaAlumnoNew.getMatriculaAlumno());
                pago.setMatriculaAlumno(matriculaAlumnoNew);
            }
            pago = em.merge(pago);
            if (matriculaAlumnoOld != null && !matriculaAlumnoOld.equals(matriculaAlumnoNew)){
                matriculaAlumnoOld.getPagoList().remove(pago);
                matriculaAlumnoOld = em.merge(matriculaAlumnoOld);
            }
            if (matriculaAlumnoNew != null && !matriculaAlumnoNew.equals(matriculaAlumnoOld)){
                matriculaAlumnoNew.getPagoList().add(pago);
                matriculaAlumnoNew = em.merge(matriculaAlumnoNew);
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                Integer id = pago.getFolioPago();
                if (findPago(id) == null){
                    throw new NonexistentEntityException("The pago with id " + id + " no longer exists.");
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
            Pago pago;
            try{
                pago = em.getReference(Pago.class, id);
                pago.getFolioPago();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The pago with id " + id + " no longer exists.", enfe);
            }
            Alumno matriculaAlumno = pago.getMatriculaAlumno();
            if (matriculaAlumno != null){
                matriculaAlumno.getPagoList().remove(pago);
                matriculaAlumno = em.merge(matriculaAlumno);
            }
            em.remove(pago);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Pago> findPagoEntities(){
        return findPagoEntities(true, -1, -1);
    }

    public List<Pago> findPagoEntities(int maxResults, int firstResult){
        return findPagoEntities(false, maxResults, firstResult);
    }

    private List<Pago> findPagoEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pago.class));
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

    public Pago findPago(Integer id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Pago.class, id);
        }finally{
            em.close();
        }
    }

    public int getPagoCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pago> rt = cq.from(Pago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }

}
