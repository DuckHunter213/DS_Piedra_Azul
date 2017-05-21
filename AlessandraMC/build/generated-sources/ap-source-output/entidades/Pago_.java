package entidades;

import entidades.Alumno;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T17:58:05")
@StaticMetamodel(Pago.class)
public class Pago_ { 

    public static volatile SingularAttribute<Pago, Boolean> estado;
    public static volatile SingularAttribute<Pago, String> tipo;
    public static volatile SingularAttribute<Pago, String> monto;
    public static volatile SingularAttribute<Pago, Integer> folioPago;
    public static volatile SingularAttribute<Pago, String> periodo;
    public static volatile SingularAttribute<Pago, Alumno> matriculaAlumno;

}