package entidadesExtends;

import alessandramc.SistemaAleMC;
import alessandramc.Util;
import entidades.Colaborador;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import jpaalessandramc.ColaboradorJpaController;
import jpaalessandramc.exceptions.NonexistentEntityException;

/**
 *
 * @author DARKENSES
 */
public class ColaboradorExtends extends Colaborador{
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    ColaboradorJpaController colaboradorBD = new ColaboradorJpaController(entityManagerFactory);

    public boolean habilitarColaborador(Colaborador colaborador){
        colaborador.setEstado(Boolean.TRUE);
        try{
            colaboradorBD.edit(colaborador);
            return true;
        }catch (NonexistentEntityException ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean desabilitarColaborador(Colaborador colaborador){
        colaborador.setEstado(Boolean.FALSE);
        try{
            colaboradorBD.edit(colaborador);
            return true;
        }catch (NonexistentEntityException ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean editarColaborador(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String titulo, String numero, String telefono, Date fecha, String matriculaColaborador){
        Colaborador colaborador = buscarColaboradorMatricula(matriculaColaborador);
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setCalle(calle);
        colaborador.setColonia(colonia);
        colaborador.setEstado(Boolean.TRUE);
        colaborador.setFechaNacimiento(fecha);
        colaborador.setNombre(nombre);
        colaborador.setNumero(numero);
        colaborador.setTelefono(telefono);
        colaborador.setTitulo(titulo);
        try{
            colaboradorBD.edit(colaborador);
            return true;
        }catch (Exception ex){
            System.err.println("Hubo un error");
        }
        return false;
    }

    public boolean agregarColabordor(String apellidoMaterno, String apellidoPaterno, String calle, String colonia, String correo, String nombre, String titulo, String numero, String telefono, Date fecha){
        Colaborador colaborador = new Colaborador();
        colaborador.setApellidoMaterno(apellidoMaterno);
        colaborador.setApellidoPaterno(apellidoPaterno);
        colaborador.setCalle(calle);
        colaborador.setColonia(colonia);
        colaborador.setNumero(numero);
        colaborador.setEstado(Boolean.TRUE);
        colaborador.setFechaNacimiento(fecha);
        colaborador.setMatriculaColaborador(Util.generarMatricula());
        colaborador.setNombre(nombre);
        colaborador.setTelefono(telefono);
        colaborador.setTitulo(titulo);
        Date hoy = new Date();
        colaborador.setFechaRegistro(hoy);

        int dia = new Date().getDay();
        if (dia == 29 || dia == 30 || dia == 31){
            hoy.setDate(28);
            colaborador.setFechaPago(hoy);
        }else{
            colaborador.setFechaPago(hoy);
        }
        try{
            colaboradorBD.create(colaborador);
            return true;
        }catch (Exception ex){
            Logger.getLogger(SistemaAleMC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Colaborador buscarColaboradorMatricula(String matricula){
        Query query = entityManager.createNamedQuery("Colaborador.findByMatriculaColaborador").setParameter("matriculaColaborador", matricula);
        return (Colaborador) query.getResultList().get(0);
    }

    public ArrayList<Colaborador> buscarColaborador(String nombre){
        Query query = entityManager.createNamedQuery("Colaborador.findByNombre").setParameter("nombre", nombre);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Colaborador> getTodosColaboradoresActivos(){
        Query query = entityManager.createNamedQuery("Colaborador.findByEstado").setParameter("estado", true);
        return Util.castListToArray(query.getResultList());
    }

    public ArrayList<Colaborador> getTodosColaboradores(){
        return Util.castListToArray(colaboradorBD.findColaboradorEntities());
    }

}
