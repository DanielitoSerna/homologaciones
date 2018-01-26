package co.com.homologacionesu.entidades;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Objetivo: Administrar universidad
 * @author dsernama
 */
@Entity
@Table(name = "tbl_universidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUniversidad.findAll", query = "SELECT t FROM TblUniversidad t"),
    @NamedQuery(name = "TblUniversidad.findByIdUniversidad", 
            query = "SELECT t FROM TblUniversidad t WHERE t.idUniversidad = :idUniversidad"),
    @NamedQuery(name = "TblUniversidad.findByNombreUniversidad", 
            query = "SELECT t FROM TblUniversidad t WHERE t.nombreUniversidad = :nombreUniversidad"),
    @NamedQuery(name = "TblUniversidad.findByAcreditada", 
            query = "SELECT t FROM TblUniversidad t WHERE t.acreditada = :acreditada"),
    @NamedQuery(name = "TblUniversidad.findByNit", 
            query = "SELECT t FROM TblUniversidad t WHERE t.nit = :nit")})
public class TblUniversidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_universidad")
    private Integer idUniversidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_universidad")
    private String nombreUniversidad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "acreditada")
    private String acreditada;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nit")
    private String nit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universidadOrigen")
    private List<TblHomologacion> tblHomologacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "universidadDestino")
    private List<TblHomologacion> tblHomologacionList1;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false)
    private TblEstado idEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUniversidad")
    private List<TblProgramas> tblProgramasList;

    /**
     * 
     */
    public TblUniversidad() {
    }

    /**
     * 
     * @param idUniversidad 
     */
    public TblUniversidad(Integer idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    /**
     * 
     * @param idUniversidad
     * @param nombreUniversidad
     * @param acreditada
     * @param nit 
     */
    public TblUniversidad(Integer idUniversidad, String nombreUniversidad, 
            String acreditada, String nit) {
        this.idUniversidad = idUniversidad;
        this.nombreUniversidad = nombreUniversidad;
        this.acreditada = acreditada;
        this.nit = nit;
    }

    /**
     * 
     * @return 
     */
    public Integer getIdUniversidad() {
        return idUniversidad;
    }

    /**
     * 
     * @param idUniversidad 
     */
    public void setIdUniversidad(Integer idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    /**
     * 
     * @return 
     */
    public String getNombreUniversidad() {
        return nombreUniversidad;
    }

    /**
     * 
     * @param nombreUniversidad 
     */
    public void setNombreUniversidad(String nombreUniversidad) {
        this.nombreUniversidad = nombreUniversidad;
    }

    /**
     * 
     * @return 
     */
    public String getAcreditada() {
        return acreditada;
    }

    /**
     * 
     * @param acreditada 
     */
    public void setAcreditada(String acreditada) {
        this.acreditada = acreditada;
    }

    /**
     * 
     * @return 
     */
    public String getNit() {
        return nit;
    }

    /**
     * 
     * @param nit 
     */
    public void setNit(String nit) {
        this.nit = nit;
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
    @XmlTransient
    public List<TblProgramas> getTblProgramasList() {
        return tblProgramasList;
    }

    /**
     * 
     * @param tblProgramasList 
     */
    public void setTblProgramasList(List<TblProgramas> tblProgramasList) {
        this.tblProgramasList = tblProgramasList;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUniversidad != null ? idUniversidad.hashCode() : 0);
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
        if (!(object instanceof TblUniversidad)) {
            return false;
        }
        TblUniversidad other = (TblUniversidad) object;
        if ((this.idUniversidad == null && other.idUniversidad != null) || (this.idUniversidad != null && !this.idUniversidad.equals(other.idUniversidad))) {
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
        return "co.com.homologacionesu.entidades.TblUniversidad[ idUniversidad=" + idUniversidad + " ]";
    }
    
}
