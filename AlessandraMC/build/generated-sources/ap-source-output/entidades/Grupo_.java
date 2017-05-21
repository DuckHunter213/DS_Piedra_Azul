package entidades;

import entidades.Colaborador;
import entidades.Horario;
import entidades.Listagrupo;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-18T17:58:05")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile SingularAttribute<Grupo, Colaborador> matriculaColaborador;
    public static volatile SingularAttribute<Grupo, Boolean> estado;
    public static volatile SingularAttribute<Grupo, String> precio;
    public static volatile ListAttribute<Grupo, Listagrupo> listagrupoList;
    public static volatile SingularAttribute<Grupo, String> matriculaGrupo;
    public static volatile SingularAttribute<Grupo, String> nombre;
    public static volatile ListAttribute<Grupo, Horario> horarioList;

}