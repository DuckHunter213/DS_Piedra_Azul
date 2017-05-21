package entidadesExtends;

import alessandramc.SistemaAleMC;
import alessandramc.Util;
import entidades.Promociones;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaalessandramc.PromocionesJpaController;

/**
 *
 * @author Luis Fernando Gomez Alejandre
 */
public class PromocionesExtends extends Promociones{
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    PromocionesJpaController promocionBD = new PromocionesJpaController(entityManagerFactory);

    public boolean agregarPromocion(String cantidadCupones, String nombre, String porcentajeDescuento, boolean tipo){
        Promociones promocion = new Promociones();
        promocion.setCantidadCupones(cantidadCupones);
        promocion.setNombre(nombre);
        promocion.setPorcentajeDescuento(porcentajeDescuento);
        promocion.setTipo(tipo);
        try{
            promocionBD.create(promocion);
            return true;
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean desabilitarPromocion(Promociones promocion){
        promocion.setCantidadCupones("0");
        try{
            promocionBD.edit(promocion);
            return true;
        }catch (Exception ex){

        }
        return false;
    }

    public boolean restarPromocion(Promociones promocion){
        promocion.setCantidadCupones(String.valueOf(Integer.parseInt(promocion.getCantidadCupones()) - 1));
        try{
            promocionBD.edit(promocion);
            return true;
        }catch (Exception ex){
            System.err.println("Error en la base de datos con las promociones");
        }
        return false;
    }

    public ArrayList<Promociones> getPromociones(boolean criterio){
        Query query = entityManager.createNamedQuery("Promociones.findByTipo").setParameter("tipo", criterio);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Promociones> getTodasPromocionesActivas(){
        Query query = entityManager.createNamedQuery("Promociones.findAll");
        ArrayList<Promociones> result = Util.castListToArray(query.getResultList());
        for (int i = 0; i < result.size(); i++){
            if (result.get(i).getCantidadCupones().equals("0")){
                result.remove(i);
            }
        }
        return result;
    }

}
