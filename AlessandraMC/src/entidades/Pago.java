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
@Table(name = "pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pago.findAll", query = "SELECT p FROM Pago p")
    , @NamedQuery(name = "Pago.findByFolioPago", query = "SELECT p FROM Pago p WHERE p.folioPago = :folioPago")
    , @NamedQuery(name = "Pago.findByMonto", query = "SELECT p FROM Pago p WHERE p.monto = :monto")
    , @NamedQuery(name = "Pago.findByPeriodo", query = "SELECT p FROM Pago p WHERE p.periodo = :periodo")
    , @NamedQuery(name = "Pago.findByAlumno", query = "SELECT p FROM Pago p WHERE p.matriculaAlumno = :matriculaAlumno")
    , @NamedQuery(name = "Pago.findByTipo", query = "SELECT p FROM Pago p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Pago.findByAlumnoAdeudos", query = "SELECT p FROM Pago p WHERE p.matriculaAlumno = :matriculaAlumno AND p.tipo = :tipo")
    , @NamedQuery(name = "Pago.findByAlumnoAdeudosActivos", query = "SELECT p FROM Pago p WHERE p.matriculaAlumno = :matriculaAlumno AND p.tipo = :tipo AND p.estado = :estado")
    , @NamedQuery(name = "Pago.findByEstado", query = "SELECT p FROM Pago p WHERE p.estado = :estado")})
public class Pago implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "folioPago")
    private Integer folioPago;
    @Basic(optional = false)
    @Column(name = "monto")
    private String monto;
    @Basic(optional = false)
    @Column(name = "periodo")
    private String periodo;
    @Basic(optional = false)
    @Column(name = "estado")
    private boolean estado;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @JoinColumn(name = "matriculaAlumno", referencedColumnName = "matriculaAlumno")
    @ManyToOne(optional = false)
    private Alumno matriculaAlumno;

    public Pago(){
    }

    public Pago(Integer folioPago){
        this.folioPago = folioPago;
    }

    public Pago(Integer folioPago, String monto, String periodo, boolean estado, String tipo){
        this.folioPago = folioPago;
        this.monto = monto;
        this.periodo = periodo;
        this.estado = estado;
        this.tipo = tipo;
    }

    public Integer getFolioPago(){
        return folioPago;
    }

    public void setFolioPago(Integer folioPago){
        this.folioPago = folioPago;
    }

    public String getMonto(){
        return monto;
    }

    public void setMonto(String monto){
        this.monto = monto;
    }

    public String getPeriodo(){
        return periodo;
    }

    public void setPeriodo(String periodo){
        this.periodo = periodo;
    }

    public boolean getEstado(){
        return estado;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public Alumno getMatriculaAlumno(){
        return matriculaAlumno;
    }

    public void setMatriculaAlumno(Alumno matriculaAlumno){
        this.matriculaAlumno = matriculaAlumno;
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += (folioPago != null ? folioPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pago)){
            return false;
        }
        Pago other = (Pago) object;
        if ((this.folioPago == null && other.folioPago != null) || (this.folioPago != null && !this.folioPago.equals(other.folioPago))){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return "entidades.Pago[ folioPago=" + folioPago + " ]";
    }

}
