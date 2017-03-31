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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DARKENSES
 */
@Entity
@Table(name = "promociones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promociones.findAll", query = "SELECT p FROM Promociones p")
    , @NamedQuery(name = "Promociones.findByFolioPromocion", query = "SELECT p FROM Promociones p WHERE p.folioPromocion = :folioPromocion")
    , @NamedQuery(name = "Promociones.findByNombre", query = "SELECT p FROM Promociones p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Promociones.findByTipo", query = "SELECT p FROM Promociones p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Promociones.findByPorcentajeDescuento", query = "SELECT p FROM Promociones p WHERE p.porcentajeDescuento = :porcentajeDescuento")
    , @NamedQuery(name = "Promociones.findByCantidadCupones", query = "SELECT p FROM Promociones p WHERE p.cantidadCupones = :cantidadCupones")})
public class Promociones implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folioPromocion")
    private Integer folioPromocion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "tipo")
    private Boolean tipo;
    @Column(name = "porcentajeDescuento")
    private String porcentajeDescuento;
    @Column(name = "cantidadCupones")
    private String cantidadCupones;

    public Promociones(){
    }

    public Promociones(Integer folioPromocion){
        this.folioPromocion = folioPromocion;
    }

    public Integer getFolioPromocion(){
        return folioPromocion;
    }

    public void setFolioPromocion(Integer folioPromocion){
        this.folioPromocion = folioPromocion;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public Boolean getTipo(){
        return tipo;
    }

    public void setTipo(Boolean tipo){
        this.tipo = tipo;
    }

    public String getPorcentajeDescuento(){
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(String porcentajeDescuento){
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public String getCantidadCupones(){
        return cantidadCupones;
    }

    public void setCantidadCupones(String cantidadCupones){
        this.cantidadCupones = cantidadCupones;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (folioPromocion != null ? folioPromocion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promociones)){
            return false;
        }
        Promociones other = (Promociones) object;
        if ((this.folioPromocion == null && other.folioPromocion != null) || (this.folioPromocion != null && !this.folioPromocion.equals(other.folioPromocion))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "entidades.Promociones[ folioPromocion=" + folioPromocion + " ]";
    }
    
}
