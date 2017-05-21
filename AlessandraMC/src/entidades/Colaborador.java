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
@Table(name = "colaborador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Colaborador.findAll", query = "SELECT c FROM Colaborador c")
    , @NamedQuery(name = "Colaborador.findByTitulo", query = "SELECT c FROM Colaborador c WHERE c.titulo = :titulo")
    , @NamedQuery(name = "Colaborador.findByMatriculaColaborador", query = "SELECT c FROM Colaborador c WHERE c.matriculaColaborador = :matriculaColaborador")
    , @NamedQuery(name = "Colaborador.findByNombre", query = "SELECT c FROM Colaborador c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Colaborador.findByApellidoPaterno", query = "SELECT c FROM Colaborador c WHERE c.apellidoPaterno = :apellidoPaterno")
    , @NamedQuery(name = "Colaborador.findByApellidoMaterno", query = "SELECT c FROM Colaborador c WHERE c.apellidoMaterno = :apellidoMaterno")
    , @NamedQuery(name = "Colaborador.findByCalle", query = "SELECT c FROM Colaborador c WHERE c.calle = :calle")
    , @NamedQuery(name = "Colaborador.findByFechaRegistro", query = "SELECT c FROM Colaborador c WHERE c.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Colaborador.findByColonia", query = "SELECT c FROM Colaborador c WHERE c.colonia = :colonia")
    , @NamedQuery(name = "Colaborador.findByTelefono", query = "SELECT c FROM Colaborador c WHERE c.telefono = :telefono")
    , @NamedQuery(name = "Colaborador.findByEstado", query = "SELECT c FROM Colaborador c WHERE c.estado = :estado")
    , @NamedQuery(name = "Colaborador.findByFechaNacimiento", query = "SELECT c FROM Colaborador c WHERE c.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Colaborador.findByNumero", query = "SELECT c FROM Colaborador c WHERE c.numero = :numero")})
public class Colaborador implements Serializable{
    private static final long serialVersionUID = 1L;
    @Column(name = "titulo")
    private String titulo;
    @Id
    @Basic(optional = false)
    @Column(name = "matriculaColaborador")
    private String matriculaColaborador;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidoPaterno")
    private String apellidoPaterno;
    @Basic(optional = false)
    @Column(name = "apellidoMaterno")
    private String apellidoMaterno;
    @Column(name = "calle")
    private String calle;
    @Basic(optional = false)
    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    @Basic(optional = false)
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    @Column(name = "colonia")
    private String colonia;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "fechaNacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "numero")
    private String numero;
    @OneToMany(mappedBy = "matriculaColaborador")
    private List<Grupo> grupoList;

    public Colaborador(){
    }

    public Colaborador(String matriculaColaborador){
        this.matriculaColaborador = matriculaColaborador;
    }

    public Colaborador(String matriculaColaborador, String nombre, String apellidoPaterno, String apellidoMaterno, Date fechaRegistro, Date fechaPago){
        this.matriculaColaborador = matriculaColaborador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaRegistro = fechaRegistro;
        this.fechaPago = fechaPago;
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public String getMatriculaColaborador(){
        return matriculaColaborador;
    }

    public void setMatriculaColaborador(String matriculaColaborador){
        this.matriculaColaborador = matriculaColaborador;
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

    public String getApellidoMaterno(){
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno){
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCalle(){
        return calle;
    }

    public void setCalle(String calle){
        this.calle = calle;
    }

    public Date getFechaRegistro(){
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro){
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaPago(){
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago){
        this.fechaPago = fechaPago;
    }

    public String getColonia(){
        return colonia;
    }

    public void setColonia(String colonia){
        this.colonia = colonia;
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

    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNumero(){
        return numero;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    @XmlTransient
    public List<Grupo> getGrupoList(){
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList){
        this.grupoList = grupoList;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (matriculaColaborador != null ? matriculaColaborador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Colaborador)){
            return false;
        }
        Colaborador other = (Colaborador) object;
        if ((this.matriculaColaborador == null && other.matriculaColaborador != null) || (this.matriculaColaborador != null && !this.matriculaColaborador.equals(other.matriculaColaborador))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return nombre + " " + apellidoPaterno + " " + apellidoMaterno + " [" + matriculaColaborador + "]";
    }

}
