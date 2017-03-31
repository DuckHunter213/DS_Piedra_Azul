package entidades;

import entidades.Alumno;
import entidades.Asistencia;
import entidades.Grupo;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-03-30T18:47:16")
@StaticMetamodel(Listagrupo.class)
public class Listagrupo_ { 

    public static volatile SingularAttribute<Listagrupo, Boolean> estado;
    public static volatile SingularAttribute<Listagrupo, Grupo> matriculaGrupo;
    public static volatile SingularAttribute<Listagrupo, Asistencia> folioAsistencia;
    public static volatile SingularAttribute<Listagrupo, Alumno> matriculaAlumno;
    public static volatile SingularAttribute<Listagrupo, Integer> folioListaGrupo;

}