/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DARKENSES
 */
@Entity
@Table(name = "listagrupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Listagrupo.findAll", query = "SELECT l FROM Listagrupo l")
    , @NamedQuery(name = "Listagrupo.findByFolioListaGrupo", query = "SELECT l FROM Listagrupo l WHERE l.folioListaGrupo = :folioListaGrupo")
    , @NamedQuery(name = "Listagrupo.findByInscripcion", query = "SELECT l FROM Listagrupo l WHERE l.matriculaGrupo = :matriculaGrupo AND l.matriculaAlumno = :matriculaAlumno")
    , @NamedQuery(name = "Listagrupo.findByAlumnosActivos", query = "SELECT l FROM Listagrupo l WHERE l.matriculaGrupo = :matriculaGrupo AND l.estado = :estado")
    , @NamedQuery(name = "Listagrupo.findByEstado", query = "SELECT l FROM Listagrupo l WHERE l.estado = :estado")})
public class Listagrupo implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folioListaGrupo")
    private Integer folioListaGrupo;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "matriculaGrupo", referencedColumnName = "matriculaGrupo")
    @ManyToOne
    private Grupo matriculaGrupo;
    @JoinColumn(name = "matriculaAlumno", referencedColumnName = "matriculaAlumno")
    @ManyToOne
    private Alumno matriculaAlumno;
    @JoinColumn(name = "folioAsistencia", referencedColumnName = "folioAsistencia")
    @ManyToOne
    private Asistencia folioAsistencia;

    public Listagrupo(){
    }

    public Listagrupo(Integer folioListaGrupo){
        this.folioListaGrupo = folioListaGrupo;
    }

    public Integer getFolioListaGrupo(){
        return folioListaGrupo;
    }

    public void setFolioListaGrupo(Integer folioListaGrupo){
        this.folioListaGrupo = folioListaGrupo;
    }

    public Boolean getEstado(){
        return estado;
    }

    public void setEstado(Boolean estado){
        this.estado = estado;
    }

    public Grupo getMatriculaGrupo(){
        return matriculaGrupo;
    }

    public void setMatriculaGrupo(Grupo matriculaGrupo){
        this.matriculaGrupo = matriculaGrupo;
    }

    public Alumno getMatriculaAlumno(){
        return matriculaAlumno;
    }

    public void setMatriculaAlumno(Alumno matriculaAlumno){
        this.matriculaAlumno = matriculaAlumno;
    }

    public Asistencia getFolioAsistencia(){
        return folioAsistencia;
    }

    public void setFolioAsistencia(Asistencia folioAsistencia){
        this.folioAsistencia = folioAsistencia;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (folioListaGrupo != null ? folioListaGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listagrupo)){
            return false;
        }
        Listagrupo other = (Listagrupo) object;
        if ((this.folioListaGrupo == null && other.folioListaGrupo != null) || (this.folioListaGrupo != null && !this.folioListaGrupo.equals(other.folioListaGrupo))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "entidades.Listagrupo[ folioListaGrupo=" + folioListaGrupo + " ]";
    }
    
}
