/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DARKENSES
 */
@Entity
@Table(name = "alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alumno.findAll", query = "SELECT a FROM Alumno a")
    , @NamedQuery(name = "Alumno.findByNombre", query = "SELECT a FROM Alumno a WHERE a.nombre = :nombre")
    , @NamedQuery(name = "Alumno.findByApellidoPaterno", query = "SELECT a FROM Alumno a WHERE a.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Alumno.findByNombreTutor", query = "SELECT a FROM Alumno a WHERE a.nombreTutor = :nombreTutor")
    , @NamedQuery(name = "Alumno.findByApellidoMaterno", query = "SELECT a FROM Alumno a WHERE a.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Alumno.findByTelefonoTutor", query = "SELECT a FROM Alumno a WHERE a.telefonoTutor = :telefonoTutor")
    , @NamedQuery(name = "Alumno.findByFechaNacimiento", query = "SELECT a FROM Alumno a WHERE a.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Alumno.findByCalle", query = "SELECT a FROM Alumno a WHERE a.calle = :calle")
    , @NamedQuery(name = "Alumno.findByNumero", query = "SELECT a FROM Alumno a WHERE a.numero = :numero")
    , @NamedQuery(name = "Alumno.findByColonia", query = "SELECT a FROM Alumno a WHERE a.colonia = :colonia")
    , @NamedQuery(name = "Alumno.findByCorreo", query = "SELECT a FROM Alumno a WHERE a.correo = :correo")
    , @NamedQuery(name = "Alumno.findByTelefono", query = "SELECT a FROM Alumno a WHERE a.telefono = :telefono")
    , @NamedQuery(name = "Alumno.findByEstado", query = "SELECT a FROM Alumno a WHERE a.estado = :estado")
    , @NamedQuery(name = "Alumno.findByMatriculaAlumno", query = "SELECT a FROM Alumno a WHERE a.matriculaAlumno = :matriculaAlumno")
    , @NamedQuery(name = "Alumno.findByFechaInscripcion", query = "SELECT a FROM Alumno a WHERE a.fechaInscripcion = :fechaInscripcion")})
public class Alumno extends Persona implements  Serializable{
    private static final long serialVersionUID = 1L;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Column(name = "nombreTutor")
    private String nombreTutor;
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Column(name = "telefonoTutor")
    private String telefonoTutor;
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numero")
    private String numero;
    @Column(name = "colonia")
    private String colonia;
    @Column(name = "correo")
    private String correo;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "estado")
    private Boolean estado;
    @Id
    @Basic(optional = false)
    @Column(name = "matriculaAlumno")
    private String matriculaAlumno;
    @Column(name = "fechaInscripcion")
    @Temporal(TemporalType.DATE)
    private Date fechaInscripcion;
    @OneToMany(mappedBy = "matriculaAlumno")
    private List<Listagrupo> listagrupoList;

    public Alumno(){
    }

    public Alumno(String matriculaAlumno){
        this.matriculaAlumno = matriculaAlumno;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getApellidoPaterno(){
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno){
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getNombreTutor(){
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor){
        this.nombreTutor = nombreTutor;
    }

    public String getApellidoMaterno(){
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno){
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefonoTutor(){
        return telefonoTutor;
    }

    public void setTelefonoTutor(String telefonoTutor){
        this.telefonoTutor = telefonoTutor;
    }

    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCalle(){
        return calle;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }

    public String getNumero(){
        return numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    public String getColonia(){
        return colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getTelefono(){
        return telefono;
    }

    public void setTelefono(String telefono){
        this.telefono = telefono;
    }

    public Boolean getEstado(){
        return estado;
    }

    public void setEstado(Boolean estado){
        this.estado = estado;
    }

    public String getMatriculaAlumno(){
        return matriculaAlumno;
    }

    public void setMatriculaAlumno(String matriculaAlumno){
        this.matriculaAlumno = matriculaAlumno;
    }

    public Date getFechaInscripcion(){
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion){
        this.fechaInscripcion = fechaInscripcion;
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
        hash += (matriculaAlumno != null ? matriculaAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alumno)){
            return false;
        }
        Alumno other = (Alumno) object;
        if ((this.matriculaAlumno == null && other.matriculaAlumno != null) || (this.matriculaAlumno != null && !this.matriculaAlumno.equals(other.matriculaAlumno))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno + " [" + matriculaAlumno + "]";
    }

    @Override
    public String getTipoPersona(){
        return "alumno";
    }
    
}
