package entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T17:58:05")
@StaticMetamodel(Capital.class)
public class Capital_ { 

    public static volatile SingularAttribute<Capital, String> motivo;
    public static volatile SingularAttribute<Capital, Character> tipo;
    public static volatile SingularAttribute<Capital, String> monto;
    public static volatile SingularAttribute<Capital, Date> fechaCreacion;
    public static volatile SingularAttribute<Capital, Integer> folioCapital;

}