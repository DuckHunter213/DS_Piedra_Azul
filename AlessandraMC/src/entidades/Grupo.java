/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DARKENSES
 */
@Entity
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByNombre", query = "SELECT g FROM Grupo g WHERE g.nombre = :nombre")
    , @NamedQuery(name = "Grupo.findByEstado", query = "SELECT g FROM Grupo g WHERE g.estado = :estado")
    , @NamedQuery(name = "Grupo.findByPrecio", query = "SELECT g FROM Grupo g WHERE g.precio = :precio")
    , @NamedQuery(name = "Grupo.findByMatriculaColaborador", query = "SELECT g FROM Grupo g WHERE g.matriculaColaborador = :matriculaColaborador")
    , @NamedQuery(name = "Grupo.findByMatriculaColaboradorActivos", query = "SELECT g FROM Grupo g WHERE g.matriculaColaborador = :matriculaColaborador AND g.estado = :estado")
    , @NamedQuery(name = "Grupo.findByMatriculaGrupo", query = "SELECT g FROM Grupo g WHERE g.matriculaGrupo = :matriculaGrupo")})
public class Grupo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "precio")
    private String precio;
    @Id
    @Basic(optional = false)
    @Column(name = "matriculaGrupo")
    private String matriculaGrupo;
    @OneToMany(mappedBy = "matriculaGrupo")
    private List<Horario> horarioList;
    @JoinColumn(name = "matriculaColaborador", referencedColumnName = "matriculaColaborador")
    @ManyToOne
    private Colaborador matriculaColaborador;
    @OneToMany(mappedBy = "matriculaGrupo")
    private List<Listagrupo> listagrupoList;

    public Grupo(){
    }

    public Grupo(String matriculaGrupo){
        this.matriculaGrupo = matriculaGrupo;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Boolean getEstado(){
        return estado;
    }

    public void setEstado(Boolean estado){
        this.estado = estado;
    }

    public String getPrecio(){
        return precio;
    }

    public void setPrecio(String precio){
        this.precio = precio;
    }

    public String getMatriculaGrupo(){
        return matriculaGrupo;
    }

    public void setMatriculaGrupo(String matriculaGrupo){
        this.matriculaGrupo = matriculaGrupo;
    }

    @XmlTransient
    public List<Horario> getHorarioList(){
        return horarioList;
    }

    public void setHorarioList(List<Horario> horarioList){
        this.horarioList = horarioList;
    }

    public Colaborador getMatriculaColaborador(){
        return matriculaColaborador;
    }

    public void setMatriculaColaborador(Colaborador matriculaColaborador){
        this.matriculaColaborador = matriculaColaborador;
    }

    @XmlTransient
    public List<Listagrupo> getListagrupoList(){
        return listagrupoList;
    }

    public void setListagrupoList(List<Listagrupo> listagrupoList){
        this.listagrupoList = listagrupoList;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (matriculaGrupo != null ? matriculaGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)){
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.matriculaGrupo == null && other.matriculaGrupo != null) || (this.matriculaGrupo != null && !this.matriculaGrupo.equals(other.matriculaGrupo))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return nombre + " [" + matriculaGrupo + "]";
    }

}
