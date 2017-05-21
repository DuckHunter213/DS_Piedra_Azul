package alessandramc;

import entidades.Colaborador;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaalessandramc.ColaboradorJpaController;
import jpaalessandramc.exceptions.NonexistentEntityException;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author DARKENSES
 */
public class ColaboradorCRUDTest{

    public ColaboradorCRUDTest(){
    }

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    String matriculaColaborador = Util.generarMatricula();
    ColaboradorJpaController colaboradorBD = new ColaboradorJpaController(entityManagerFactory);

    @Before
    public void setUp(){
        Colaborador colaborador = new Colaborador();
        colaborador.setMatriculaColaborador(matriculaColaborador);
        colaborador.setNombre("Alma Rosa");
        colaborador.setApellidoMaterno("Alejandre");
        colaborador.setApellidoPaterno("Vicencio");
        Date fechaNacimiento = new Date();
        colaborador.setFechaNacimiento(fechaNacimiento);
        colaborador.setTitulo("Maestra en Danzas Danzables");
        colaborador.setCalle("Unidad Buena Vista");
        colaborador.setNumero("6F");
        colaborador.setColonia("Buena Vista");
        colaborador.setFechaRegistro(fechaNacimiento);
        colaborador.setFechaPago(fechaNacimiento);
        colaborador.setTelefono("2281125347");
        colaborador.setEstado(Boolean.TRUE);

        try{
            colaboradorBD.create(colaborador);
        }catch (Exception ex){
            System.err.println("Error al guardar al colaborador en la base de datos");
            ex.printStackTrace();
        }

    }

    @Test
    public void testEditarColaborador(){
        System.out.println("editarColaborador");
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        Date fechaNacimiento = new Date();
        boolean result = instance.editarColaborador("Vicencio", "Alejandre", "Melchor Musguit", "Revolución", "fgar_40_20@hotmail.com", "Alma Rosa", "Maestra en Danzas Danzables", "7", "2281125347", fechaNacimiento, matriculaColaborador);
        assertEquals(expResult, result);
    }

    @Test
    public void testDesabilitarColaborador(){
        System.out.println("desabilitarColaborador");
        SistemaAleMC instance = new SistemaAleMC();
        Colaborador colaborador = instance.buscarColaboradorMatricula(matriculaColaborador);
        boolean expResult = true;
        boolean result = instance.desabilitarColaborador(colaborador);
        assertEquals(expResult, result);
    }

    @Test
    public void testHabilitarColaborador(){
        System.out.println("habilitarColaborador");
        SistemaAleMC instance = new SistemaAleMC();
        Colaborador colaborador = instance.buscarColaboradorMatricula(matriculaColaborador);
        boolean expResult = true;
        boolean result = instance.habilitarColaborador(colaborador);
        assertEquals(expResult, result);
    }

    @After
    public void tearDown(){
        try{
            colaboradorBD.destroy(matriculaColaborador);
        }catch (NonexistentEntityException ex){
            Logger.getLogger(ColaboradorCRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
