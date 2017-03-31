package alessandramc;

import entidades.Alumno;
import entidades.Grupo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Luis Fernando Gomez Alejandre
 */
public class Util{
    
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AlessandraMCPU");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    /**
     * Esta clase genera un identificador que tiene formato YYYMMDDHHmmSS se
     * (año, mes, día, hora, minuto y segundo). Se eligió ésta estructura pues
     * garantiza que sean datos únicos e imposibles de recrear debido a su
     * exactitud, además, otorga información extra de su registro
     *
     * @return se regresa un String del identificador ya generado
     */
    public static String generarMatricula(){
        Date fecha = new Date();
        SimpleDateFormat formateadorDeFecha = new SimpleDateFormat("yyyMMddHHmmss");
        String matricula = formateadorDeFecha.format(fecha);
        matricula = (String) matricula.subSequence(1, matricula.length());
        return matricula;
    }
    /**
     * Esta clase genera un identificador que tiene formato "G" YYYMMDDHHmmSS se
     * (año, mes, día, hora, minuto y segundo). Se eligió ésta estructura pues
     * garantiza que sean datos únicos e imposibles de recrear debido a su
     * exactitud, además, otorga información extra de su registro
     *
     * @return se regresa un String del identificador ya generado
     */
    public static String generarMatriculaGrupo(){
        Date fecha = new Date();
        SimpleDateFormat formateadorDeFecha = new SimpleDateFormat("yyyMMddHHmmss");
        String matricula = formateadorDeFecha.format(fecha);
        matricula = (String) matricula.subSequence(1, matricula.length());
        String cadenaFinal = "G";
        return cadenaFinal.concat(matricula);
    }
    
    /**
     * Pase de una list a una arraylist
     * @param lista
     * @return
     */
    public static ArrayList castListToArray(List lista){
        ArrayList resultado = new ArrayList<>();
        for (Object object : lista){
            resultado.add(object);
        }
        return resultado;
    }
    /**
     * Pase de un arraylist a una list
     * @param lista
     * @return
     */
    public static ArrayList castArrayToList(List lista){
        ArrayList resultado = new ArrayList<>();
        for (Object object : lista){
            resultado.add(object);
        }
        return resultado;
    }
}
