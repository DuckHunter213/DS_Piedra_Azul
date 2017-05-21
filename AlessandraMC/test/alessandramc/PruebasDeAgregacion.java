package alessandramc;

import entidades.Alumno;
import entidades.Colaborador;
import entidades.Horario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DARKENSES
 */
public class PruebasDeAgregacion{

    @Test
    public void testAgregarColabordor(){
        System.out.println("agregarColabordor");
        String apellidoMaterno = "Alejandre";
        String apellidoPaterno = "Gomez";
        String calle = "Melchor Musguit";
        String colonia = "Revolucion";
        String correo = "gomez.alejandre@gmail.com";
        String nombre = "Luis Fernando";
        String titulo = "Ingeniero en Danzas Indanzables";
        String numero = "7";
        String telefono = "2282229579";
        Date fecha = new Date();
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        boolean result = instance.agregarColabordor(apellidoMaterno, apellidoPaterno, calle, colonia, correo, nombre, titulo, numero, telefono, fecha);
        assertEquals(expResult, result);
    }

    @Test
    public void testAgregarPromocion(){
        System.out.println("agregarPromocion");
        String cantidadCupones = "4400";
        String nombre = "Primeros 4400";
        String porcentajeDescuento = "100";
        boolean tipo = true;
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        boolean result = instance.agregarPromocion(cantidadCupones, nombre, porcentajeDescuento, tipo);
        assertEquals(expResult, result);
    }

    @Test
    public void testCrearGrupo(){
        System.out.println("crearGrupo");
        SistemaAleMC instance = new SistemaAleMC();
        String nombre = "Danza Arabe Alluh Balbar";
        String precio = "1000";
        List<Horario> listaHorarios = new ArrayList<>();
        Horario horario = new Horario();
        horario.setDia("Lunes");
        horario.setHoraInicio("13:00");
        horario.setHoraFin("14:00");
        horario.setSalon("104");
        listaHorarios.add(horario);
        Horario horario2 = new Horario();
        horario2.setDia("Lunes");
        horario2.setHoraInicio("13:00");
        horario2.setHoraFin("14:00");
        horario2.setSalon("104");
        listaHorarios.add(horario2);
        List<Alumno> listaAlumnos = new ArrayList<>();
        ArrayList<Alumno> esperado = instance.buscarAlumno("Luis Fernando");
        listaAlumnos.add(esperado.get(0));
        ArrayList<Colaborador> resultado = instance.buscarColaborador("Luis Fernando");
        Colaborador colaborador = resultado.get(0);

        boolean expResult = true;
        boolean result = instance.crearGrupo(nombre, precio, listaHorarios, listaAlumnos, colaborador);
        assertEquals(expResult, result);
    }

    /**
     * Test of agregarAlumno method, of class SistemaAleMC.
     */
    @Test
    public void testAgregarAlumno(){
        System.out.println("agregarAlumno");
        String apellidoMaterno = "Alejandre";
        String apellidoPaterno = "Gomez";
        String calle = "Melchor Musguit";
        String colonia = "Revolucion";
        String correo = "gomez.alejandre@gmail.com";
        String nombre = "Luis Fernando";
        String nombreTutor = "Alma Rosa Alejandre Vicencio";
        String numero = "7";
        String telefono = "2282229579";
        String telefonoTutor = "2281114099";
        Date fecha = new Date(1995 - 1900, 10, 23);
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        boolean result = instance.agregarAlumno(apellidoMaterno, apellidoPaterno, calle, colonia, correo, nombre, nombreTutor, numero, telefono, telefonoTutor, fecha);
        assertEquals(expResult, result);
    }

    @Test
    public void testAgregarCapital(){
        System.out.println("agregarCapital");
        String monto = "100";
        String motivo = "2 Sillas";
        Character tipo = 'S';
        SistemaAleMC instance = new SistemaAleMC();
        boolean expResult = true;
        boolean result = instance.agregarCapital(monto, motivo, tipo);
        assertEquals(expResult, result);
    }

    
}
