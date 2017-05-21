package entidadesExtends;

import alessandramc.SistemaAleMC;
import alessandramc.Util;
import entidades.Alumno;
import entidades.Listagrupo;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpaalessandramc.AlumnoJpaController;

public class AlumnoExtends extends Alumno{

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    AlumnoJpaController alumnoBD = new AlumnoJpaController(entityManagerFactory);

    public boolean desabilitarAlumno(Alumno alumno){
        alumno.setEstado(Boolean.FALSE);
        try{
            alumnoBD.edit(alumno);
            return true;
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean habilitarAlumno(Alumno alumno){
        alumno.setEstado(Boolean.TRUE);
        try{
            alumnoBD.edit(alumno);
            return true;
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean agregarAlumno(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String nombreTutor, String numero, String telefono, String telefonoTutor, Date fecha){
        Alumno alumno = new Alumno();
        alumno.setMatriculaAlumno(Util.generarMatricula());
        alumno.setNombre(nombre);
        alumno.setApellidoPaterno(apellidoPaterno);
        alumno.setApellidoMaterno(apellidoMaterno);
        alumno.setFechaNacimiento(fecha);
        alumno.setCalle(calle);
        alumno.setNumero(numero);
        alumno.setColonia(colonia);
        alumno.setCorreo(correo);
        alumno.setTelefono(telefono);
        alumno.setEstado(Boolean.TRUE);
        Date hoy = new Date();
        alumno.setFechaInscripcion(hoy);
        int diaAlumno = hoy.getDate();
        if (diaAlumno == 29 || diaAlumno == 30 || diaAlumno == 31){
            diaAlumno = 28;
        }
        hoy.setDate(diaAlumno);
        alumno.setUltimaFechaPago(hoy);
        alumno.setNombreTutor(nombreTutor);
        alumno.setTelefonoTutor(telefonoTutor);
        try{
            alumnoBD.create(alumno);
            return true;
        }catch (Exception ex){
            System.err.println("Ha ocurrido al agregar un alumno");
        }
        return false;
    }

    public boolean editarAlumno(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String nombreTutor, String numero, String telefono, String telefonoTutor, Date fechaNacimiento, String matriculaAlumno){
        SistemaAleMC sistema = new SistemaAleMC();
        Alumno alumno = sistema.buscarAlumnoMatricula(matriculaAlumno);
        alumno.setNombre(nombre);
        alumno.setApellidoPaterno(apellidoPaterno);
        alumno.setApellidoMaterno(apellidoMaterno);
        alumno.setCalle(calle);
        alumno.setColonia(colonia);
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setNombreTutor(nombreTutor);
        alumno.setTelefono(telefono);
        alumno.setTelefonoTutor(telefonoTutor);
        try{
            alumnoBD.edit(alumno);
            return true;
        }catch (Exception ex){
            System.err.println("Hubo un error");
        }
        return false;
    }

    public int estadoMensualidad(Alumno alumno){
        Date fechaHoy = new Date();
        Date fechaUltimoPago = alumno.getUltimaFechaPago();

        Date fechaSiguientePago = new Date();
        if (fechaHoy.getMonth() == 11){
            fechaSiguientePago.setDate(fechaUltimoPago.getDate());
            fechaSiguientePago.setMonth(0);
            fechaSiguientePago.setYear(fechaSiguientePago.getYear() + 1);
        }else{
            fechaSiguientePago.setMonth(fechaUltimoPago.getMonth() + 1);
            fechaSiguientePago.setDate(fechaUltimoPago.getDate());
        }
        System.out.println(alumno.toString() + " " + Util.fechaToString(fechaSiguientePago));
        return Util.compararFechas(fechaHoy, fechaSiguientePago);
    }

    public String calcularPagoMensualidad(Alumno alumno){
        Query query = entityManager.createNamedQuery("Listagrupo.findByAlumnoEstado").setParameter("estado", true).setParameter("matriculaAlumno", alumno);
        ArrayList<Listagrupo> grupos = Util.castListToArray(query.getResultList());
        int totalMensualidad = 0;
        for (Listagrupo grupo : grupos){
            totalMensualidad += Integer.parseInt(grupo.getMatriculaGrupo().getPrecio());
        }
        return String.valueOf(totalMensualidad);
    }

    public Alumno buscarAlumnoMatricula(String matricula){
        Query query = entityManager.createNamedQuery("Alumno.findByMatriculaAlumno").setParameter("matriculaAlumno", matricula);
        return (Alumno) query.getResultList().get(0);
    }

    public ArrayList<Alumno> buscarAlumno(String nombre){
        Query query = entityManager.createNamedQuery("Alumno.findByNombre").setParameter("nombre", nombre);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Alumno> getTodosAlumnosActivos(){
        Query query = entityManager.createNamedQuery("Alumno.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Alumno> getTodosAlumnosConDeuda(){
        Query query = entityManager.createNamedQuery("Pago.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }
    
    public ArrayList<Alumno> getTodosAlumnos(){
        return Util.castListToArray(alumnoBD.findAlumnoEntities());
    }


}
