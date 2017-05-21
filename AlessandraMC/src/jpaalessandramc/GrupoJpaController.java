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
import entidades.Colaborador;
import entidades.Grupo;
import entidades.Horario;
import java.util.ArrayList;
import java.util.List;
import entidades.Listagrupo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpaalessandramc.exceptions.NonexistentEntityException;
import jpaalessandramc.exceptions.PreexistingEntityException;

/**
 *
 * @author DARKENSES
 */
public class GrupoJpaController implements Serializable{
    public GrupoJpaController(EntityManagerFactory emf){
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws PreexistingEntityException, Exception{
        if (grupo.getHorarioList() == null){
            grupo.setHorarioList(new ArrayList<Horario>());
        }
        if (grupo.getListagrupoList() == null){
            grupo.setListagrupoList(new ArrayList<Listagrupo>());
        }
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Colaborador matriculaColaborador = grupo.getMatriculaColaborador();
            if (matriculaColaborador != null){
                matriculaColaborador = em.getReference(matriculaColaborador.getClass(), matriculaColaborador.getMatriculaColaborador());
                grupo.setMatriculaColaborador(matriculaColaborador);
            }
            List<Horario> attachedHorarioList = new ArrayList<Horario>();
            for (Horario horarioListHorarioToAttach : grupo.getHorarioList()){
                horarioListHorarioToAttach = em.getReference(horarioListHorarioToAttach.getClass(), horarioListHorarioToAttach.getFolioHorario());
                attachedHorarioList.add(horarioListHorarioToAttach);
            }
            grupo.setHorarioList(attachedHorarioList);
            List<Listagrupo> attachedListagrupoList = new ArrayList<Listagrupo>();
            for (Listagrupo listagrupoListListagrupoToAttach : grupo.getListagrupoList()){
                listagrupoListListagrupoToAttach = em.getReference(listagrupoListListagrupoToAttach.getClass(), listagrupoListListagrupoToAttach.getFolioListaGrupo());
                attachedListagrupoList.add(listagrupoListListagrupoToAttach);
            }
            grupo.setListagrupoList(attachedListagrupoList);
            em.persist(grupo);
            if (matriculaColaborador != null){
                matriculaColaborador.getGrupoList().add(grupo);
                matriculaColaborador = em.merge(matriculaColaborador);
            }
            for (Horario horarioListHorario : grupo.getHorarioList()){
                Grupo oldMatriculaGrupoOfHorarioListHorario = horarioListHorario.getMatriculaGrupo();
                horarioListHorario.setMatriculaGrupo(grupo);
                horarioListHorario = em.merge(horarioListHorario);
                if (oldMatriculaGrupoOfHorarioListHorario != null){
                    oldMatriculaGrupoOfHorarioListHorario.getHorarioList().remove(horarioListHorario);
                    oldMatriculaGrupoOfHorarioListHorario = em.merge(oldMatriculaGrupoOfHorarioListHorario);
                }
            }
            for (Listagrupo listagrupoListListagrupo : grupo.getListagrupoList()){
                Grupo oldMatriculaGrupoOfListagrupoListListagrupo = listagrupoListListagrupo.getMatriculaGrupo();
                listagrupoListListagrupo.setMatriculaGrupo(grupo);
                listagrupoListListagrupo = em.merge(listagrupoListListagrupo);
                if (oldMatriculaGrupoOfListagrupoListListagrupo != null){
                    oldMatriculaGrupoOfListagrupoListListagrupo.getListagrupoList().remove(listagrupoListListagrupo);
                    oldMatriculaGrupoOfListagrupoListListagrupo = em.merge(oldMatriculaGrupoOfListagrupoListListagrupo);
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            if (findGrupo(grupo.getMatriculaGrupo()) != null){
                throw new PreexistingEntityException("Grupo " + grupo + " already exists.", ex);
            }
            throw ex;
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws NonexistentEntityException, Exception{
        EntityManager em = null;
        try{
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getMatriculaGrupo());
            Colaborador matriculaColaboradorOld = persistentGrupo.getMatriculaColaborador();
            Colaborador matriculaColaboradorNew = grupo.getMatriculaColaborador();
            List<Horario> horarioListOld = persistentGrupo.getHorarioList();
            List<Horario> horarioListNew = grupo.getHorarioList();
            List<Listagrupo> listagrupoListOld = persistentGrupo.getListagrupoList();
            List<Listagrupo> listagrupoListNew = grupo.getListagrupoList();
            if (matriculaColaboradorNew != null){
                matriculaColaboradorNew = em.getReference(matriculaColaboradorNew.getClass(), matriculaColaboradorNew.getMatriculaColaborador());
                grupo.setMatriculaColaborador(matriculaColaboradorNew);
            }
            List<Horario> attachedHorarioListNew = new ArrayList<Horario>();
            for (Horario horarioListNewHorarioToAttach : horarioListNew){
                horarioListNewHorarioToAttach = em.getReference(horarioListNewHorarioToAttach.getClass(), horarioListNewHorarioToAttach.getFolioHorario());
                attachedHorarioListNew.add(horarioListNewHorarioToAttach);
            }
            horarioListNew = attachedHorarioListNew;
            grupo.setHorarioList(horarioListNew);
            List<Listagrupo> attachedListagrupoListNew = new ArrayList<Listagrupo>();
            for (Listagrupo listagrupoListNewListagrupoToAttach : listagrupoListNew){
                listagrupoListNewListagrupoToAttach = em.getReference(listagrupoListNewListagrupoToAttach.getClass(), listagrupoListNewListagrupoToAttach.getFolioListaGrupo());
                attachedListagrupoListNew.add(listagrupoListNewListagrupoToAttach);
            }
            listagrupoListNew = attachedListagrupoListNew;
            grupo.setListagrupoList(listagrupoListNew);
            grupo = em.merge(grupo);
            if (matriculaColaboradorOld != null && !matriculaColaboradorOld.equals(matriculaColaboradorNew)){
                matriculaColaboradorOld.getGrupoList().remove(grupo);
                matriculaColaboradorOld = em.merge(matriculaColaboradorOld);
            }
            if (matriculaColaboradorNew != null && !matriculaColaboradorNew.equals(matriculaColaboradorOld)){
                matriculaColaboradorNew.getGrupoList().add(grupo);
                matriculaColaboradorNew = em.merge(matriculaColaboradorNew);
            }
            for (Horario horarioListOldHorario : horarioListOld){
                if (!horarioListNew.contains(horarioListOldHorario)){
                    horarioListOldHorario.setMatriculaGrupo(null);
                    horarioListOldHorario = em.merge(horarioListOldHorario);
                }
            }
            for (Horario horarioListNewHorario : horarioListNew){
                if (!horarioListOld.contains(horarioListNewHorario)){
                    Grupo oldMatriculaGrupoOfHorarioListNewHorario = horarioListNewHorario.getMatriculaGrupo();
                    horarioListNewHorario.setMatriculaGrupo(grupo);
                    horarioListNewHorario = em.merge(horarioListNewHorario);
                    if (oldMatriculaGrupoOfHorarioListNewHorario != null && !oldMatriculaGrupoOfHorarioListNewHorario.equals(grupo)){
                        oldMatriculaGrupoOfHorarioListNewHorario.getHorarioList().remove(horarioListNewHorario);
                        oldMatriculaGrupoOfHorarioListNewHorario = em.merge(oldMatriculaGrupoOfHorarioListNewHorario);
                    }
                }
            }
            for (Listagrupo listagrupoListOldListagrupo : listagrupoListOld){
                if (!listagrupoListNew.contains(listagrupoListOldListagrupo)){
                    listagrupoListOldListagrupo.setMatriculaGrupo(null);
                    listagrupoListOldListagrupo = em.merge(listagrupoListOldListagrupo);
                }
            }
            for (Listagrupo listagrupoListNewListagrupo : listagrupoListNew){
                if (!listagrupoListOld.contains(listagrupoListNewListagrupo)){
                    Grupo oldMatriculaGrupoOfListagrupoListNewListagrupo = listagrupoListNewListagrupo.getMatriculaGrupo();
                    listagrupoListNewListagrupo.setMatriculaGrupo(grupo);
                    listagrupoListNewListagrupo = em.merge(listagrupoListNewListagrupo);
                    if (oldMatriculaGrupoOfListagrupoListNewListagrupo != null && !oldMatriculaGrupoOfListagrupoListNewListagrupo.equals(grupo)){
                        oldMatriculaGrupoOfListagrupoListNewListagrupo.getListagrupoList().remove(listagrupoListNewListagrupo);
                        oldMatriculaGrupoOfListagrupoListNewListagrupo = em.merge(oldMatriculaGrupoOfListagrupoListNewListagrupo);
                    }
                }
            }
            em.getTransaction().commit();
        }catch (Exception ex){
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0){
                String id = grupo.getMatriculaGrupo();
                if (findGrupo(id) == null){
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try{
                grupo = em.getReference(Grupo.class, id);
                grupo.getMatriculaGrupo();
            }catch (EntityNotFoundException enfe){
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            Colaborador matriculaColaborador = grupo.getMatriculaColaborador();
            if (matriculaColaborador != null){
                matriculaColaborador.getGrupoList().remove(grupo);
                matriculaColaborador = em.merge(matriculaColaborador);
            }
            List<Horario> horarioList = grupo.getHorarioList();
            for (Horario horarioListHorario : horarioList){
                horarioListHorario.setMatriculaGrupo(null);
                horarioListHorario = em.merge(horarioListHorario);
            }
            List<Listagrupo> listagrupoList = grupo.getListagrupoList();
            for (Listagrupo listagrupoListListagrupo : listagrupoList){
                listagrupoListListagrupo.setMatriculaGrupo(null);
                listagrupoListListagrupo = em.merge(listagrupoListListagrupo);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        }finally{
            if (em != null){
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities(){
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult){
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(String id){
        EntityManager em = getEntityManager();
        try{
            return em.find(Grupo.class, id);
        }finally{
            em.close();
        }
    }

    public int getGrupoCount(){
        EntityManager em = getEntityManager();
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }finally{
            em.close();
        }
    }

}
