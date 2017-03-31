package entidades;

import entidades.Listagrupo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T18:47:16")
@StaticMetamodel(Alumno.class)
public class Alumno_ { 

    public static volatile SingularAttribute<Alumno, String> apellidoPaterno;
    public static volatile SingularAttribute<Alumno, Boolean> estado;
    public static volatile SingularAttribute<Alumno, String> nombreTutor;
    public static volatile SingularAttribute<Alumno, String> numero;
    public static volatile SingularAttribute<Alumno, Date> fechaNacimiento;
    public static volatile SingularAttribute<Alumno, String> calle;
    public static volatile SingularAttribute<Alumno, String> nombre;
    public static volatile SingularAttribute<Alumno, Date> fechaInscripcion;
    public static volatile SingularAttribute<Alumno, String> apellidoMaterno;
    public static volatile SingularAttribute<Alumno, String> colonia;
    public static volatile ListAttribute<Alumno, Listagrupo> listagrupoList;
    public static volatile SingularAttribute<Alumno, String> correo;
    public static volatile SingularAttribute<Alumno, String> matriculaAlumno;
    public static volatile SingularAttribute<Alumno, String> telefonoTutor;
    public static volatile SingularAttribute<Alumno, String> telefono;

}