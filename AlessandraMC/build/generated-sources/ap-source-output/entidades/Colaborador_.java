package entidades;

import entidades.Grupo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T17:58:05")
@StaticMetamodel(Colaborador.class)
public class Colaborador_ { 

    public static volatile SingularAttribute<Colaborador, String> apellidoPaterno;
    public static volatile ListAttribute<Colaborador, Grupo> grupoList;
    public static volatile SingularAttribute<Colaborador, Boolean> estado;
    public static volatile SingularAttribute<Colaborador, String> numero;
    public static volatile SingularAttribute<Colaborador, Date> fechaNacimiento;
    public static volatile SingularAttribute<Colaborador, Date> fechaRegistro;
    public static volatile SingularAttribute<Colaborador, String> calle;
    public static volatile SingularAttribute<Colaborador, String> titulo;
    public static volatile SingularAttribute<Colaborador, String> nombre;
    public static volatile SingularAttribute<Colaborador, Date> fechaPago;
    public static volatile SingularAttribute<Colaborador, String> apellidoMaterno;
    public static volatile SingularAttribute<Colaborador, String> colonia;
    public static volatile SingularAttribute<Colaborador, String> matriculaColaborador;
    public static volatile SingularAttribute<Colaborador, String> telefono;

}