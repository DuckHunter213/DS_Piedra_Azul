package alessandramc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Luis Fernando Gomez Alejandre
 */
public class Util{

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
     *
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
     *
     * @param lista
     * @return
     */
    public static List castArrayToList(ArrayList lista){
        List resultado = new ArrayList<>();
        for (Object object : lista){
            resultado.add(object);
        }
        return resultado;
    }

    /**
     * Metodo que calcula que fecha es mayor primero compara la primera fecha
     * con la segunda
     *
     * @param fechaHoy fecha de inicio
     * @param fechaSiguientePago fecha a comparar
     * @return
     * <blockquote><pre>
     *     * Menores (-1)
     *     * Mayores (1)
     *     * Iguales (0)
     * </pre></blockquote>
     *
     */
    public static int compararFechas(Date fechaHoy, Date fechaSiguientePago){
        if (fechaHoy.before(fechaSiguientePago)){
            return -1;
        }else{
            return 1;
        }
    }

    public static String fechaToString(Date date){
        String dia = String.valueOf(date.getDate());
        if (dia.length() == 1){
            dia = "0" + dia;
        }
        String mes = String.valueOf((date.getMonth() + 1));
        if (mes.length() == 1){
            mes = "0" + mes;
        }

        return dia + "/" + mes + "/" + String.valueOf(date.getYear() + 1900);
    }

    public static String convertirMesToString(int month){
        switch (month){
            case 0:
                return "enero";
            case 1:
                return "febrero";
            case 2:
                return "marzo";
            case 3:
                return "abril";
            case 4:
                return "mayo";
            case 5:
                return "junio";
            case 6:
                return "julio";
            case 7:
                return "agosto";
            case 8:
                return "septiembre";
            case 9:
                return "octubre";
            case 10:
                return "novimbre";
            case 11:
                return "diciembre";
            default:
                return "Sin periodo";
        }
    }

    public static int diasAtraso(Date fechaUltimoPago){
        return (int) numeroDias(fechaUltimoPago, new Date());
    }

    public static long numeroDias(Date fecha1, Date fecha2){
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

        Calendar calendar = new GregorianCalendar((fecha2.getYear() + 1900), fecha2.getMonth(), fecha2.getDate());
        java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

        long diferencia = (fecha1.getTime() - fecha.getTime()) / MILLSECS_PER_DAY;
        return diferencia;
    }

}
