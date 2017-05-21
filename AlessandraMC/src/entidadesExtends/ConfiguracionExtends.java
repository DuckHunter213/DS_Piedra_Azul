package entidadesExtends;

import entidades.Configuracion;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaalessandramc.ConfiguracionJpaController;

public class ConfiguracionExtends extends Configuracion{
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private ConfiguracionJpaController configuracionBD = new ConfiguracionJpaController(entityManagerFactory);

    public boolean nuevaConfiguracion(int costoAnual, String porcentajePagoColaborador, String porcentajePenalizacion){
        Query query = entityManager.createNamedQuery("Configuracion.findByEstado").setParameter("estado", true);
        List<Configuracion> result = query.getResultList();
        if (!result.isEmpty()){
            result.get(0).setEstado(Boolean.FALSE);
            try{
                configuracionBD.edit(result.get(0));
            }catch (Exception ex){
                Logger.getLogger(ConfiguracionExtends.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Configuracion configuracion = new Configuracion();
        configuracion.setCostoAnual(costoAnual);
        configuracion.setEstado(Boolean.TRUE);
        configuracion.setFechaDeEdicion(new Date());
        configuracion.setPorcentajePagoColaborador(porcentajePagoColaborador);
        configuracion.setPorcentajePenalizacion(porcentajePenalizacion);
        try{
            configuracionBD.create(configuracion);
            return true;
        }catch (Exception e){

        }
        return false;
    }

    public Configuracion getConfiguracion(){
        Query query = entityManager.createNamedQuery("Configuracion.findByEstado").setParameter("estado", true);
        return (Configuracion) query.getResultList().get(0);
    }

}
