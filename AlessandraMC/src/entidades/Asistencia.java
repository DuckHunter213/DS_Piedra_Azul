/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DARKENSES
 */
@Entity
@Table(name = "asistencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Asistencia.findAll", query = "SELECT a FROM Asistencia a")
    , @NamedQuery(name = "Asistencia.findByFolioAsistencia", query = "SELECT a FROM Asistencia a WHERE a.folioAsistencia = :folioAsistencia")
    , @NamedQuery(name = "Asistencia.findByFecha", query = "SELECT a FROM Asistencia a WHERE a.fecha = :fecha")})
public class Asistencia implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folioAsistencia")
    private Integer folioAsistencia;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "folioListaGrupo", referencedColumnName = "folioListaGrupo")
    @ManyToOne
    private Listagrupo folioListaGrupo;

    public Asistencia(){
    }

    public Asistencia(Integer folioAsistencia){
        this.folioAsistencia = folioAsistencia;
    }

    public Integer getFolioAsistencia(){
        return folioAsistencia;
    }

    public void setFolioAsistencia(Integer folioAsistencia){
        this.folioAsistencia = folioAsistencia;
    }

    public Date getFecha(){
        return fecha;
    }

    public void setFecha(Date fecha){
        this.fecha = fecha;
    }

    public Listagrupo getFolioListaGrupo(){
        return folioListaGrupo;
    }

    public void setFolioListaGrupo(Listagrupo folioListaGrupo){
        this.folioListaGrupo = folioListaGrupo;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (folioAsistencia != null ? folioAsistencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencia)){
            return false;
        }
        Asistencia other = (Asistencia) object;
        if ((this.folioAsistencia == null && other.folioAsistencia != null) || (this.folioAsistencia != null && !this.folioAsistencia.equals(other.folioAsistencia))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "entidades.Asistencia[ folioAsistencia=" + folioAsistencia + " ]";
    }

}
