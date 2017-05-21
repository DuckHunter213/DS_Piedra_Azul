package entidadesExtends;

import alessandramc.SistemaAleMC;
import alessandramc.Util;
import entidades.Alumno;
import entidades.Capital;
import entidades.Colaborador;
import entidades.Grupo;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaalessandramc.AlumnoJpaController;
import jpaalessandramc.CapitalJpaController;
import jpaalessandramc.exceptions.NonexistentEntityException;

public class CapitalExtends extends Capital{
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    CapitalJpaController capitalBD = new CapitalJpaController(entityManagerFactory);
    ListagrupoExtends listagrupoExtends = new ListagrupoExtends();
    PagoExtends pagoExtends = new PagoExtends();
    AlumnoJpaController alumnoBD = new AlumnoJpaController(entityManagerFactory);
    AlumnoExtends alumnoExtends = new AlumnoExtends();

    public boolean agregarCapital(String monto, String motivo, Character tipo){
        Capital capital = new Capital();
        Date fechaCreacion = new Date();
        capital.setFechaCreacion(fechaCreacion);
        capital.setMonto(monto);
        capital.setMotivo(motivo);
        capital.setTipo(tipo);

        try{
            capitalBD.create(capital);
            return true;
        }catch (Exception e){
            //TODO
        }
        return false;
    }

    public String calcularPagoColaborador(Colaborador colaborador){
        //2.- Buscar en que grupos esta
        Query query = entityManager.createNamedQuery("Grupo.findByMatriculaColaboradorActivos").setParameter("matriculaColaborador", colaborador).setParameter("estado", true);
        ArrayList<Grupo> ListaGrupos = Util.castListToArray(query.getResultList());
        //3.- Calcular el total con la cantidad de alumnos
        GrupoExtends grupoExtends = new GrupoExtends();
        int totalColaborador = 0;
        for (Grupo grupo : ListaGrupos){
            System.out.println(grupo.toString());
            int alumnosEnGrupo = grupoExtends.contarAlumnos(grupo);

            int totalGrupo = alumnosEnGrupo * Integer.parseInt(grupo.getPrecio());
            System.out.println("Alumnos en grupo: " + alumnosEnGrupo);
            System.out.println("Precio del grupo: " + grupo.getPrecio());
            System.out.println("Total del recaudo del grupo: " + totalGrupo);
            totalColaborador += totalGrupo;
        }

        return Integer.toString(totalColaborador);
    }

    public String calcularPagoColaboradorPeriodo(Colaborador colaborador){

        ArrayList<Capital> capitales = getRegistrosCapitalTipo('M');
        Date fechaUltimoPago = colaborador.getFechaPago();
        int dia = fechaUltimoPago.getDate();
        int mes = fechaUltimoPago.getMonth();
        int anio = fechaUltimoPago.getYear();
        Date fechaProximoPago;
        if (fechaUltimoPago.getMonth() == 11){
            mes = 0;
            fechaProximoPago = new Date(anio + 1, mes, dia);
        }else{
            fechaProximoPago = new Date(anio, mes + 1, dia);
        }
        int totalColaborador = 0;
        for (Capital capital : capitales){
            if (capital.getMotivo().contains("0170329211527")){
                if (Util.compararFechas(capital.getFechaCreacion(), fechaUltimoPago) != -1 && Util.compararFechas(capital.getFechaCreacion(), fechaProximoPago) != 1){
                    totalColaborador = totalColaborador + Integer.parseInt(capital.getMonto());
                }
            }
        }
        return Integer.toString(totalColaborador);
    }

    public ArrayList<Capital> getTodosRegistrosCapital(){
        Query query = entityManager.createNamedQuery("Capital.findAll");
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Capital> getRegistrosCapitalTipo(char tipo){
        Query query = entityManager.createNamedQuery("Capital.findByTipo").setParameter("tipo", tipo);
        return Util.castListToArray(query.getResultList());
    }

    public boolean guardarAdeudoAlumno(Alumno alumno, String monto){
        Date date = new Date();
        
        String mes = Util.convertirMesToString(date.getMonth());
        pagoExtends.agregarAdeudo(monto, mes, alumno);

        Date fechaSiguientePagoBD = alumno.getUltimaFechaPago();
        Date fechaSiguientePago = new Date();

        fechaSiguientePago.setDate(fechaSiguientePagoBD.getDate());
        fechaSiguientePago.setMonth(fechaSiguientePagoBD.getMonth());
        fechaSiguientePago.setYear(fechaSiguientePagoBD.getYear());

        System.out.println(Util.fechaToString(fechaSiguientePago));
        if (fechaSiguientePago.getMonth() == 11){
            fechaSiguientePago.setMonth(0);
            fechaSiguientePago.setYear((fechaSiguientePago.getYear() + 1));
        }else{
            fechaSiguientePago.setMonth((fechaSiguientePago.getMonth() + 1));
        }
        Alumno alumnoLocal = alumnoExtends.buscarAlumnoMatricula(alumno.getMatriculaAlumno());
        alumnoLocal.setUltimaFechaPago(fechaSiguientePago);
        try{
            alumnoBD.edit(alumnoLocal);
            return true;
        }catch (NonexistentEntityException ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

}
