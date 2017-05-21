/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandramc;

import entidades.Alumno;
import entidades.Grupo;
import entidades.Promociones;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaalessandramc.PromocionesJpaController;
import jpaalessandramc.exceptions.NonexistentEntityException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DARKENSES
 */
public class PromocionTest{
    
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    PromocionesJpaController promocionBD = new PromocionesJpaController(entityManagerFactory);

    @BeforeClass
    public static void setUpClass(){
    }

    @Before
    public void setUp(){
    }

    @Test
    public void testAgregarPromocionAnualidad(){
        System.out.println("agregarPromocion");
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        boolean result = instance.agregarPromocion("100", "Anualidad", "50", false);
        assertEquals(expResult, result);
    }

    @Test
    public void testAgregarPromocionMensualidad(){
        System.out.println("agregarPromocion");
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        boolean result = instance.agregarPromocion("100", "Mensualidad", "50", true);
        assertEquals(expResult, result);
    }

    @Test
    public void testDesabilitarPromocion(){
        System.out.println("desabilitarPromocionMensualidad");
        Promociones promocion = null;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        ArrayList<Promociones> resultado = instance.getTodasPromocionesActivas();
        for (Promociones promociones : resultado){
            if (promociones.getNombre().equals("Mensualidad")){
                promocion = promociones;
            }
            break;
        }
        boolean result = instance.desabilitarPromocion(promocion);
        try{
            promocionBD.destroy(promocion.getFolioPromocion());
        }catch (NonexistentEntityException ex){
            Logger.getLogger(PromocionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, result);
    }

    @Test
    public void testDesabilitarPromocionAnualidad(){
        System.out.println("desabilitarPromocionAnualidad");
        Promociones promocion = null;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        ArrayList<Promociones> resultado = instance.getTodasPromocionesActivas();
        for (Promociones promociones : resultado){
            if (promociones.getNombre().equals("Anualidad")){
                promocion = promociones;
            }
            break;
        }

        boolean result = instance.desabilitarPromocion(promocion);
        assertEquals(expResult, result);
    }

    @AfterClass
    public static void tearDownClass(){
    }

}
