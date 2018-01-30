package co.com.homologacionesu.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Objetivo: Manipular data mediante la clase
 * @author Daniel Serna 
 */
@Entity
@Table(name = "tbl_plan_programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblPlanPrograma.findAll", 
            query = "SELECT t FROM TblPlanPrograma t")
    , @NamedQuery(name = "TblPlanPrograma.findByIdPlanPrograma", 
            query = "SELECT t FROM TblPlanPrograma t WHERE t.idPlanPrograma = :idPlanPrograma")
    , @NamedQuery(name = "TblPlanPrograma.findByCodigoPlan", 
            query = "SELECT t FROM TblPlanPrograma t WHERE t.codigoPlan = :codigoPlan")
    , @NamedQuery(name = "TblPlanPrograma.findByNombrePlan", 
            query = "SELECT t FROM TblPlanPrograma t WHERE t.nombrePlan = :nombrePlan")
    , @NamedQuery(name = "TblPlanPrograma.findByFechaInicio", 
            query = "SELECT t FROM TblPlanPrograma t WHERE t.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "TblPlanPrograma.findByFechaVigencia", 
            query = "SELECT t FROM TblPlanPrograma t WHERE t.fechaVigencia = :fechaVigencia")})
public class TblPlanPrograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plan_programa")
    private Integer idPlanPrograma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo_plan")
    private String codigoPlan;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_plan")
    private String nombrePlan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fecha_vigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false)
    private TblEstado idEstado;
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    @ManyToOne(optional = false)
    private TblProgramas idPrograma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planOrigen")
    private List<TblHomologacion> tblHomologacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planDestino")
    private List<TblHomologacion> tblHomologacionList1;

    /**
     * 
     */
    public TblPlanPrograma() {
    }

    /**
     * 
     * @param idPlanPrograma 
     */
    public TblPlanPrograma(Integer idPlanPrograma) {
        this.idPlanPrograma = idPlanPrograma;
    }

    /**
     * 
     * @param idPlanPrograma
     * @param codigoPlan
     * @param nombrePlan
     * @param fechaInicio 
     */
    public TblPlanPrograma(Integer idPlanPrograma, String codigoPlan, 
            String nombrePlan, Date fechaInicio) {
        this.idPlanPrograma = idPlanPrograma;
        this.codigoPlan = codigoPlan;
        this.nombrePlan = nombrePlan;
        this.fechaInicio = fechaInicio;
    }

    /**
     * 
     * @return 
     */
    public Integer getIdPlanPrograma() {
        return idPlanPrograma;
    }

    /**
     * 
     * @param idPlanPrograma 
     */
    public void setIdPlanPrograma(Integer idPlanPrograma) {
        this.idPlanPrograma = idPlanPrograma;
    }

    /**
     * 
     * @return 
     */
    public String getCodigoPlan() {
        return codigoPlan;
    }

    /**
     * 
     * @param codigoPlan 
     */
    public void setCodigoPlan(String codigoPlan) {
        this.codigoPlan = codigoPlan;
    }

    /**
     * 
     * @return 
     */
    public String getNombrePlan() {
        return nombrePlan;
    }

    /**
     * 
     * @param nombrePlan 
     */
    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    /**
     * 
     * @return 
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * 
     * @param fechaInicio 
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * 
     * @return 
     */
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    /**
     * 
     * @param fechaVigencia 
     */
    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    /**
     * 
     * @return 
     */
    public TblEstado getIdEstado() {
        return idEstado;
    }

    /**
     * 
     * @param idEstado 
     */
    public void setIdEstado(TblEstado idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * 
     * @return 
     */
    public TblProgramas getIdPrograma() {
        return idPrograma;
    }

    /**
     * 
     * @param idPrograma 
     */
    public void setIdPrograma(TblProgramas idPrograma) {
        this.idPrograma = idPrograma;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanPrograma != null ? idPlanPrograma.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @param object
     * @return 
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblPlanPrograma)) {
            return false;
        }
        TblPlanPrograma other = (TblPlanPrograma) object;
        if ((this.idPlanPrograma == null && other.idPlanPrograma != null) || (this.idPlanPrograma != null && !this.idPlanPrograma.equals(other.idPlanPrograma))) {
            return false;
        }
        return true;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "co.com.homologacionesu.entidades.TblPlanPrograma[ idPlanPrograma=" + idPlanPrograma + " ]";
    }

    /**
     * 
     * @return 
     */
    @XmlTransient
    public List<TblHomologacion> getTblHomologacionList() {
        return tblHomologacionList;
    }

    /**
     * 
     * @param tblHomologacionList 
     */
    public void setTblHomologacionList(List<TblHomologacion> tblHomologacionList) {
        this.tblHomologacionList = tblHomologacionList;
    }

    /**
     * 
     * @return 
     */
    @XmlTransient
    public List<TblHomologacion> getTblHomologacionList1() {
        return tblHomologacionList1;
    }

    /**
     * 
     * @param tblHomologacionList1 
     */
    public void setTblHomologacionList1(List<TblHomologacion> tblHomologacionList1) {
        this.tblHomologacionList1 = tblHomologacionList1;
    }
    
}
