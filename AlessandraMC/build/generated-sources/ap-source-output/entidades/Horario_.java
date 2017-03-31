package entidades;

import entidades.Grupo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T18:47:16")
@StaticMetamodel(Horario.class)
public class Horario_ { 

    public static volatile SingularAttribute<Horario, String> horaFin;
    public static volatile SingularAttribute<Horario, Grupo> matriculaGrupo;
    public static volatile SingularAttribute<Horario, Integer> folioHorario;
    public static volatile SingularAttribute<Horario, String> dia;
    public static volatile SingularAttribute<Horario, String> horaInicio;
    public static volatile SingularAttribute<Horario, String> salon;

}