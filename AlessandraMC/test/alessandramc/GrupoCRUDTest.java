package alessandramc;

import entidades.Alumno;
import entidades.Colaborador;
import entidades.Grupo;
import entidades.Horario;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaalessandramc.AlumnoJpaController;
import jpaalessandramc.ColaboradorJpaController;
import jpaalessandramc.GrupoJpaController;
import jpaalessandramc.exceptions.IllegalOrphanException;
import jpaalessandramc.exceptions.NonexistentEntityException;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GrupoCRUDTest{

    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    static String matriculaGrupo = Util.generarMatriculaGrupo();
    static GrupoJpaController grupoBD = new GrupoJpaController(entityManagerFactory);
    static AlumnoJpaController alumnoBD = new AlumnoJpaController(entityManagerFactory);
    static String matriculaColaborador = Util.generarMatricula();
    static ColaboradorJpaController colaboradorBD = new ColaboradorJpaController(entityManagerFactory);

    private static ArrayList<Alumno> listaAlumnos;
    private static Colaborador colaborador;
    private static ArrayList<Horario> listaHorarios;

    public static ArrayList<Alumno> hacerAlumnos(){
        Alumno alumno = new Alumno();
        listaAlumnos = new ArrayList<>();
        Date date = new Date();
        alumno.setMatriculaAlumno("Prueba");
        alumno.setNombre("Prueba");
        alumno.setApellidoPaterno("Prueba");
        alumno.setApellidoMaterno("Prueba");
        alumno.setFechaNacimiento(date);
        alumno.setCalle("Prueba");
        alumno.setNumero("Prueba");
        alumno.setColonia("Prueba");
        alumno.setCorreo("Prueba");
        alumno.setTelefono("Prueba");
        alumno.setEstado(Boolean.TRUE);
        alumno.setFechaInscripcion(date);
        int diaAlumno = date.getDate();
        if (diaAlumno == 29 || diaAlumno == 30 || diaAlumno == 31){
            diaAlumno = 28;
        }
        date.setDate(diaAlumno);
        alumno.setUltimaFechaPago(date);
        alumno.setNombreTutor("Prueba");
        alumno.setTelefonoTutor("Prueba");
        try{
            alumnoBD.create(alumno);
            listaAlumnos.add(alumno);
        }catch (Exception ex){
            Logger.getLogger(AlumnoCRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaAlumnos;
    }

    public static void hacerHorario(){
        listaHorarios = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            Horario horario = new Horario();
            horario.setDia("Prueba");
            horario.setFolioHorario(i);
            horario.setHoraFin("13:00");
            horario.setHoraInicio("12:00");
            horario.setSalon("102");
            listaHorarios.add(horario);
        }
    }

    public static void hacerColaborador(){
        colaborador = new Colaborador();
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
        }

    }

    @BeforeClass
    public static void setUpClass(){
        listaAlumnos = hacerAlumnos();
        hacerColaborador();
        hacerHorario();

        System.out.println("testHacerGrupo");
        Grupo grupo = new Grupo();
        grupo.setNombre("Prueba");
        grupo.setPrecio("100");
        grupo.setMatriculaColaborador(colaborador);
        grupo.setMatriculaGrupo(matriculaGrupo);
        grupo.setEstado(Boolean.TRUE);

        try{
            grupoBD.create(grupo);
        }catch (Exception ex){
            Logger.getLogger(GrupoCRUDTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testDesabilitarGrupo(){
        System.out.println("desabilitarGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        ArrayList<Grupo> grupo = instance.buscarGrupo("Prueba");
        boolean result = instance.desabilitarGrupo(grupo.get(0));
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    @Test
    public void testHabilitarGrupo(){
        System.out.println("habilitarGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        ArrayList<Grupo> grupo = instance.buscarGrupo("Prueba");
        boolean result = instance.habilitarGrupo(grupo.get(0));
        boolean expResult = true;
        assertEquals(expResult, result);
    }
    @Test
    public void testEditarGrupo(){
        System.out.println("editarGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
        ArrayList<Grupo> grupo = instance.buscarGrupo("Prueba");
        boolean result = instance.editarGrupo("Prueba", "1000", listaHorarios, listaAlumnos, alumnos, colaborador, grupo.get(0).getMatriculaGrupo());
        assertEquals(expResult, result);
    }

    @After
    public void tearDown(){

    }

}
