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
@Table(name = "horario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h")
    , @NamedQuery(name = "Horario.findByDia", query = "SELECT h FROM Horario h WHERE h.dia = :dia")
    , @NamedQuery(name = "Horario.findByMatriculaGrupo", query = "SELECT h FROM Horario h WHERE h.matriculaGrupo = :matriculaGrupo")
    , @NamedQuery(name = "Horario.findByHoraInicio", query = "SELECT h FROM Horario h WHERE h.horaInicio = :horaInicio")
    , @NamedQuery(name = "Horario.findByHoraFin", query = "SELECT h FROM Horario h WHERE h.horaFin = :horaFin")
    , @NamedQuery(name = "Horario.findBySalon", query = "SELECT h FROM Horario h WHERE h.salon = :salon")
    , @NamedQuery(name = "Horario.findByFolioHorario", query = "SELECT h FROM Horario h WHERE h.folioHorario = :folioHorario")})
public class Horario implements Serializable{
    private static final long serialVersionUID = 1L;
    @Column(name = "dia")
    private String dia;
    @Column(name = "horaInicio")
    private String horaInicio;
    @Column(name = "horaFin")
    private String horaFin;
    @Column(name = "salon")
    private String salon;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folioHorario")
    private Integer folioHorario;
    @JoinColumn(name = "matriculaGrupo", referencedColumnName = "matriculaGrupo")
    @ManyToOne
    private Grupo matriculaGrupo;

    public Horario(){
    }

    public Horario(Integer folioHorario){
        this.folioHorario = folioHorario;
    }

    public String getDia(){
        return dia;
    }

    public void setDia(String dia){
        this.dia = dia;
    }

    public String getHoraInicio(){
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio){
        this.horaInicio = horaInicio;
    }

    public String getHoraFin(){
        return horaFin;
    }

    public void setHoraFin(String horaFin){
        this.horaFin = horaFin;
    }

    public String getSalon(){
        return salon;
    }

    public void setSalon(String salon){
        this.salon = salon;
    }

    public Integer getFolioHorario(){
        return folioHorario;
    }

    public void setFolioHorario(Integer folioHorario){
        this.folioHorario = folioHorario;
    }

    public Grupo getMatriculaGrupo(){
        return matriculaGrupo;
    }

    public void setMatriculaGrupo(Grupo matriculaGrupo){
        this.matriculaGrupo = matriculaGrupo;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (folioHorario != null ? folioHorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)){
            return false;
        }
        Horario other = (Horario) object;
        if ((this.folioHorario == null && other.folioHorario != null) || (this.folioHorario != null && !this.folioHorario.equals(other.folioHorario))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "entidades.Horario[ folioHorario=" + folioHorario + " ]";
    }
    
}
