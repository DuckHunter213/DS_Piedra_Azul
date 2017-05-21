/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alessandramc;

import entidades.Alumno;
import entidades.Grupo;
import entidades.Promociones;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DARKENSES
 */
public class PlantillaPruebas{

    public PlantillaPruebas(){
    }

    @BeforeClass
    public static void setUpClass(){
    }

    @Before
    public void setUp(){
    }

    @Test
    public void testAgregarAsistencia(){
        System.out.println("agregarAsistencia");
        Integer folioListaGrupo = null;
        List listagrupoList = null;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = false;
//        boolean result = instance.agregarAsistencia(folioListaGrupo, listagrupoList);
        //assertEquals(expResult, result);
        fail("Esta prueba no ha sido terminada");
    }

    @Test
    public void testDesabilitarPromocion(){
        System.out.println("desabilitarPromocion");
        Promociones promocion = null;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = false;
        boolean result = instance.desabilitarPromocion(promocion);
        assertEquals(expResult, result);
        fail("Esta prueba no ha sido terminada");
    }

    @Test
    public void testDesabilitarAlumno(){
        System.out.println("desabilitarAlumno");
        Alumno alumno = null;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = false;
        boolean result = instance.desabilitarAlumno(alumno);
        assertEquals(expResult, result);
        fail("Esta prueba no ha sido terminada");
    }

    @Test
    public void testDesabilitarGrupo(){
        System.out.println("desabilitarGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        Grupo grupo = null;
        boolean expResult = true;
        boolean result = instance.desabilitarGrupo(grupo);
        assertEquals(expResult, result);
    }

    @Test
    public void testEditarAlumno(){
        System.out.println("editarAlumno");
        Alumno alumno = null;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = false;
        //boolean result = instance.editarAlumno(alumno);
        //assertEquals(expResult, result);
        //fail("Esta prueba no ha sido terminada");
    }

    @Test
    public void testEditarGrupo(){
        System.out.println("editarGrupo");
        Grupo grupo = null;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = false;
        //        boolean result = instance.editarGrupo(grupo);
        //assertEquals(expResult, result);
        fail("Esta prueba no ha sido terminada");
    }

    @AfterClass
    public static void tearDownClass(){
    }

    @After
    public void tearDown(){
    }

}
