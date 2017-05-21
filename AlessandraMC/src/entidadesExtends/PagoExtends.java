package entidadesExtends;

import alessandramc.Util;
import entidades.Pago;
import entidades.Alumno;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaalessandramc.PagoJpaController;

public class PagoExtends extends Pago{

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    PagoJpaController pagoBD = new PagoJpaController(entityManagerFactory);

    public ArrayList<Pago> getMansualidadesActivas(){
        Query query = entityManager.createNamedQuery("Pago.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Pago> getMansualidadesActivas(Alumno alumno){
        Query query = entityManager.createNamedQuery("Pago.findByAlumno").setParameter("matriculaAlumno", alumno);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Pago> getMansualidadesActivas(String criterio){
        Query query = entityManager.createNamedQuery("Pago.findByTipo").setParameter("tipo", criterio);
        return Util.castListToArray(query.getResultList());
    }

    public boolean agregarAdeudo(String monto, String periodo, Alumno matriculaAlumno){
        Pago pago = new Pago();

        pago.setEstado(true);
        pago.setMatriculaAlumno(matriculaAlumno);
        pago.setMonto(monto);
        pago.setPeriodo(periodo);
        pago.setTipo("m");

        try{
            pagoBD.create(pago);
            return true;
        }catch (Exception ex){
            Logger.getLogger(PagoExtends.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public boolean quitarAdeudo(Alumno alumno, String criterio){
        Query query = entityManager.createNamedQuery("Pago.findByAlumnoAdeudos").setParameter("matriculaAlumno", alumno).setParameter("tipo", criterio);
        ArrayList<Pago> pagos = Util.castListToArray(query.getResultList());
        for (Pago pago : pagos){
            pago.setEstado(Boolean.FALSE);
            try{
                pagoBD.edit(pago);
                return true;
            }catch (Exception ex){
                Logger.getLogger(PagoExtends.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public ArrayList<Pago> getAdeudosActivos(Alumno alumno, String criterio){
        Query query = entityManager.createNamedQuery("Pago.findByAlumnoAdeudosActivos").setParameter("matriculaAlumno", alumno).setParameter("tipo", criterio).setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

}
