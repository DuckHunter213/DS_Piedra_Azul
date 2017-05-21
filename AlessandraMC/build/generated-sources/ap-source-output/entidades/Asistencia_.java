package entidades;

import entidades.Listagrupo;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T17:58:05")
@StaticMetamodel(Asistencia.class)
public class Asistencia_ { 

    public static volatile SingularAttribute<Asistencia, Date> fecha;
    public static volatile SingularAttribute<Asistencia, Integer> folioAsistencia;
    public static volatile SingularAttribute<Asistencia, Listagrupo> folioListaGrupo;

}