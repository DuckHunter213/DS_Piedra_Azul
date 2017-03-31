package alessandramc;

import entidades.Alumno;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaalessandramc.AlumnoJpaController;
import jpaalessandramc.exceptions.NonexistentEntityException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class AlumnoCRUDTest{

    public AlumnoCRUDTest(){
    }

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    String matriculaAlumno = Util.generarMatricula();
    AlumnoJpaController alumnoBD = new AlumnoJpaController(entityManagerFactory);

    @Before
    public void setUp(){
        Alumno alumno = new Alumno();
        alumno.setApellidoMaterno("Alejandre");
        alumno.setApellidoPaterno("Gomez");
        alumno.setCalle("Melchor Musguit");
        alumno.setColonia("Revolucion");
        alumno.setCorreo("gomez.alejandre@gmail.com");
        alumno.setEstado(Boolean.TRUE);
        Date fechaNacimiento = new Date();
        alumno.setFechaInscripcion(fechaNacimiento);
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setMatriculaAlumno(matriculaAlumno);
        alumno.setNombre("Luis Fernando");
        alumno.setNombreTutor("Victor Manuel Gomez Alejandre");
        alumno.setNumero("7");
        alumno.setTelefono("2282229579");
        alumno.setTelefonoTutor("2281114099");
        
        try{
            alumnoBD.create(alumno);
        }catch (Exception ex){
            Logger.getLogger(AlumnoCRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testEditarAlumno(){
        System.out.println("editarAlumno");
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        Date fechaNacimiento = new Date();
        boolean result = instance.editarAlumno("Alejandre", "Gomez", "Buena Vista", "Buena Vista", "gomez.alejandre@gmail.com", "Luis Fernando", "Alma Rosa", "6", "2282229579", "2281114099", fechaNacimiento, matriculaAlumno);
        assertEquals(expResult, result);
    }

    @Test
    public void testDesabilitarAlumno(){
        System.out.println("desabilitarAlumno");
        SistemaAleMC instance = new SistemaAleMC();
        Alumno alumno = instance.buscarAlumnoMatricula(matriculaAlumno);
        boolean expResult = true;
        boolean result = instance.desabilitarAlumno(alumno);
        assertEquals(expResult, result);
    }

    @Test
    public void testHabilitarAlumno(){
        System.out.println("habilitarAlumno");
        SistemaAleMC instance = new SistemaAleMC();
        Alumno alumno = instance.buscarAlumnoMatricula(matriculaAlumno);
        boolean expResult = true;
        boolean result = instance.habilitarAlumno(alumno);
        assertEquals(expResult, result);
    }

    @After
    public void tearDown(){
        try{
            alumnoBD.destroy(matriculaAlumno);
        }catch (NonexistentEntityException ex){
            Logger.getLogger(ColaboradorCRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
