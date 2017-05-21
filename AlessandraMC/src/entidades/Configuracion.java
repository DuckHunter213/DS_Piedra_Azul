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
@Table(name = "configuracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configuracion.findAll", query = "SELECT c FROM Configuracion c")
    , @NamedQuery(name = "Configuracion.findByCostoAnual", query = "SELECT c FROM Configuracion c WHERE c.costoAnual = :costoAnual")
    , @NamedQuery(name = "Configuracion.findByPorcentajePenalizacion", query = "SELECT c FROM Configuracion c WHERE c.porcentajePenalizacion = :porcentajePenalizacion")
    , @NamedQuery(name = "Configuracion.findByPorcentajePagoColaborador", query = "SELECT c FROM Configuracion c WHERE c.porcentajePagoColaborador = :porcentajePagoColaborador")
    , @NamedQuery(name = "Configuracion.findByFolioConfigracion", query = "SELECT c FROM Configuracion c WHERE c.folioConfigracion = :folioConfigracion")
    , @NamedQuery(name = "Configuracion.findByFechaDeEdicion", query = "SELECT c FROM Configuracion c WHERE c.fechaDeEdicion = :fechaDeEdicion")
    , @NamedQuery(name = "Configuracion.findByEstado", query = "SELECT c FROM Configuracion c WHERE c.estado = :estado")})
public class Configuracion implements Serializable{
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "costoAnual")
    private int costoAnual;
    @Basic(optional = false)
    @Column(name = "porcentajePenalizacion")
    private String porcentajePenalizacion;
    @Basic(optional = false)
    @Column(name = "porcentajePagoColaborador")
    private String porcentajePagoColaborador;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folioConfigracion")
    private Integer folioConfigracion;
    @Basic(optional = false)
    @Column(name = "fechaDeEdicion")
    @Temporal(TemporalType.DATE)
    private Date fechaDeEdicion;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;

    public Configuracion(){
    }

    public Configuracion(Integer folioConfigracion){
        this.folioConfigracion = folioConfigracion;
    }

    public Configuracion(Integer folioConfigracion, int costoAnual, String porcentajePenalizacion, String porcentajePagoColaborador, Date fechaDeEdicion, boolean estado){
        this.folioConfigracion = folioConfigracion;
        this.costoAnual = costoAnual;
        this.porcentajePenalizacion = porcentajePenalizacion;
        this.porcentajePagoColaborador = porcentajePagoColaborador;
        this.fechaDeEdicion = fechaDeEdicion;
        this.estado = estado;
    }

    public int getCostoAnual(){
        return costoAnual;
    }

    public void setCostoAnual(int costoAnual){
        this.costoAnual = costoAnual;
    }

    public String getPorcentajePenalizacion(){
        return porcentajePenalizacion;
    }

    public void setPorcentajePenalizacion(String porcentajePenalizacion){
        this.porcentajePenalizacion = porcentajePenalizacion;
    }

    public String getPorcentajePagoColaborador(){
        return porcentajePagoColaborador;
    }

    public void setPorcentajePagoColaborador(String porcentajePagoColaborador){
        this.porcentajePagoColaborador = porcentajePagoColaborador;
    }

    public Integer getFolioConfigracion(){
        return folioConfigracion;
    }

    public void setFolioConfigracion(Integer folioConfigracion){
        this.folioConfigracion = folioConfigracion;
    }

    public Date getFechaDeEdicion(){
        return fechaDeEdicion;
    }

    public void setFechaDeEdicion(Date fechaDeEdicion){
        this.fechaDeEdicion = fechaDeEdicion;
    }

    public boolean getEstado(){
        return estado;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (folioConfigracion != null ? folioConfigracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuracion)){
            return false;
        }
        Configuracion other = (Configuracion) object;
        if ((this.folioConfigracion == null && other.folioConfigracion != null) || (this.folioConfigracion != null && !this.folioConfigracion.equals(other.folioConfigracion))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "entidades.Configuracion[ folioConfigracion=" + folioConfigracion + " ]";
    }

}
