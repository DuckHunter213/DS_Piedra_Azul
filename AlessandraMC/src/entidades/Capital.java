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
@Table(name = "capital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Capital.findAll", query = "SELECT c FROM Capital c")
    , @NamedQuery(name = "Capital.findByMonto", query = "SELECT c FROM Capital c WHERE c.monto = :monto")
    , @NamedQuery(name = "Capital.findByMotivo", query = "SELECT c FROM Capital c WHERE c.motivo = :motivo")
    , @NamedQuery(name = "Capital.findByFechaCreacion", query = "SELECT c FROM Capital c WHERE c.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Capital.findByTipo", query = "SELECT c FROM Capital c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Capital.findByFolioCapital", query = "SELECT c FROM Capital c WHERE c.folioCapital = :folioCapital")})
public class Capital implements Serializable{
    private static final long serialVersionUID = 1L;
    @Column(name = "monto")
    private String monto;
    @Column(name = "motivo")
    private String motivo;
    @Column(name = "fechaCreacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "tipo")
    private Character tipo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folioCapital")
    private Integer folioCapital;

    public Capital(){
    }

    public Capital(Integer folioCapital){
        this.folioCapital = folioCapital;
    }

    public String getMonto(){
        return monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }

    public String getMotivo(){
        return motivo;
    }

    public void setMotivo(String motivo){
        this.motivo = motivo;
    }

    public Date getFechaCreacion(){
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion){
        this.fechaCreacion = fechaCreacion;
    }

    public Character getTipo(){
        return tipo;
    }

    public void setTipo(Character tipo){
        this.tipo = tipo;
    }

    public Integer getFolioCapital(){
        return folioCapital;
    }

    public void setFolioCapital(Integer folioCapital){
        this.folioCapital = folioCapital;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (folioCapital != null ? folioCapital.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Capital)){
            return false;
        }
        Capital other = (Capital) object;
        if ((this.folioCapital == null && other.folioCapital != null) || (this.folioCapital != null && !this.folioCapital.equals(other.folioCapital))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "entidades.Capital[ folioCapital=" + folioCapital + " ]";
    }
    
}
