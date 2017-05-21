/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaalessandramc;

import entidades.Colaborador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Grupo;
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
public class ColaboradorJpaController implements Serializable{
    public ColaboradorJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Colaborador colaborador) throws PreexistingEntityException, Exception{
        if (colaborador.getGrupoList() == null){
            colaborador.setGrupoList(new ArrayList<Grupo>());
        }
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : colaborador.getGrupoList()){
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getMatriculaGrupo());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            colaborador.setGrupoList(attachedGrupoList);
            em.persist(colaborador);
            for (Grupo grupoListGrupo : colaborador.getGrupoList()){
                Colaborador oldMatriculaColaboradorOfGrupoListGrupo = grupoListGrupo.getMatriculaColaborador();
                grupoListGrupo.setMatriculaColaborador(colaborador);
                grupoListGrupo = em.merge(grupoListGrupo);
                if (oldMatriculaColaboradorOfGrupoListGrupo != null){
                    oldMatriculaColaboradorOfGrupoListGrupo.getGrupoList().remove(grupoListGrupo);
                    oldMatriculaColaboradorOfGrupoListGrupo = em.merge(oldMatriculaColaboradorOfGrupoListGrupo);
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            if (findColaborador(colaborador.getMatriculaColaborador()) != null){
                throw new PreexistingEntityException("Colaborador " + colaborador + " already exists.", ex);
            }
            throw ex;
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Colaborador colaborador) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Colaborador persistentColaborador = em.find(Colaborador.class, colaborador.getMatriculaColaborador());
            List<Grupo> grupoListOld = persistentColaborador.getGrupoList();
            List<Grupo> grupoListNew = colaborador.getGrupoList();
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew){
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getMatriculaGrupo());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            colaborador.setGrupoList(grupoListNew);
            colaborador = em.merge(colaborador);
            for (Grupo grupoListOldGrupo : grupoListOld){
                if (!grupoListNew.contains(grupoListOldGrupo)){
                    grupoListOldGrupo.setMatriculaColaborador(null);
                    grupoListOldGrupo = em.merge(grupoListOldGrupo);
                }
            }
            for (Grupo grupoListNewGrupo : grupoListNew){
                if (!grupoListOld.contains(grupoListNewGrupo)){
                    Colaborador oldMatriculaColaboradorOfGrupoListNewGrupo = grupoListNewGrupo.getMatriculaColaborador();
                    grupoListNewGrupo.setMatriculaColaborador(colaborador);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                    if (oldMatriculaColaboradorOfGrupoListNewGrupo != null && !oldMatriculaColaboradorOfGrupoListNewGrupo.equals(colaborador)){
                        oldMatriculaColaboradorOfGrupoListNewGrupo.getGrupoList().remove(grupoListNewGrupo);
                        oldMatriculaColaboradorOfGrupoListNewGrupo = em.merge(oldMatriculaColaboradorOfGrupoListNewGrupo);
                    }
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                String id = colaborador.getMatriculaColaborador();
                if (findColaborador(id) == null){
                    throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.");
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
            Colaborador colaborador;
            try{
                colaborador = em.getReference(Colaborador.class, id);
                colaborador.getMatriculaColaborador();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The colaborador with id " + id + " no longer exists.", enfe);
            }
            List<Grupo> grupoList = colaborador.getGrupoList();
            for (Grupo grupoListGrupo : grupoList){
                grupoListGrupo.setMatriculaColaborador(null);
                grupoListGrupo = em.merge(grupoListGrupo);
            }
            em.remove(colaborador);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Colaborador> findColaboradorEntities(){
        return findColaboradorEntities(true, -1, -1);
    }

    public List<Colaborador> findColaboradorEntities(int maxResults, int firstResult){
        return findColaboradorEntities(false, maxResults, firstResult);
    }

    private List<Colaborador> findColaboradorEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Colaborador.class));
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

    public Colaborador findColaborador(String id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Colaborador.class, id);
        }finally{
            em.close();
        }
    }

    public int getColaboradorCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Colaborador> rt = cq.from(Colaborador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }

}
