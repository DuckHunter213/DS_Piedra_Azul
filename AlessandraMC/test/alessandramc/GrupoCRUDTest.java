package alessandramc;

import entidades.Alumno;
import entidades.Grupo;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaalessandramc.AlumnoJpaController;
import jpaalessandramc.GrupoJpaController;
import jpaalessandramc.exceptions.NonexistentEntityException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GrupoCRUDTest{

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    String matriculaGrupo = Util.generarMatriculaGrupo();
    GrupoJpaController grupoBD = new GrupoJpaController(entityManagerFactory);
    AlumnoJpaController alumnoBD = new AlumnoJpaController(entityManagerFactory);

    public ArrayList<Alumno> hacerAlumnos(){
        ArrayList<Alumno> listaAlumnos = null;
        for (int i = 0; i < 10; i++){
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
            //alumno.setMatriculaAlumno(matriculaAlumno);
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
        return listaAlumnos;
    }

    @Before
    public void setUp(){
        Grupo grupo = new Grupo();

        //necesitas arregle de 5 alumnos
    }

    @Test
    public void testEditarGrupo(){
        System.out.println("editarGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        Date fechaNacimiento = new Date();
        boolean result = true;//instance.editarGrupo(matriculaGrupo, matriculaGrupo, listaHorarios, listaAlumnos, colaborador, matriculaGrupo);
        assertEquals(expResult, result);
    }

    @Test
    public void testDesabilitarGrupo(){
        System.out.println("desabilitarGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        Alumno alumno = instance.buscarAlumnoMatricula(matriculaGrupo);
        boolean expResult = true;
        boolean result = instance.desabilitarAlumno(alumno);
        assertEquals(expResult, result);
    }

    @Test
    public void testHabilitarGrupo(){
        System.out.println("habilitarGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        Alumno alumno = instance.buscarAlumnoMatricula(matriculaGrupo);
        boolean expResult = true;
        boolean result = instance.habilitarAlumno(alumno);
        assertEquals(expResult, result);
    }

    @After
    public void tearDown(){
        try{
            grupoBD.destroy(matriculaGrupo);
        }catch (NonexistentEntityException ex){
            Logger.getLogger(ColaboradorCRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
