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
 * Objetivo: Administrar programas
 * @author Daniel Serna
 */
@Entity
@Table(name = "tbl_programas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblProgramas.findAll", 
            query = "SELECT t FROM TblProgramas t"),
    @NamedQuery(name = "TblProgramas.findByIdPrograma", 
            query = "SELECT t FROM TblProgramas t WHERE t.idPrograma = :idPrograma"),
    @NamedQuery(name = "TblProgramas.findByCodigoInterno", 
            query = "SELECT t FROM TblProgramas t WHERE t.codigoInterno = :codigoInterno"),
    @NamedQuery(name = "TblProgramas.findByNombrePrograma", 
            query = "SELECT t FROM TblProgramas t WHERE t.nombrePrograma = :nombrePrograma"),
    @NamedQuery(name = "TblProgramas.findByUniversidad",
            query = "SELECT t FROM TblProgramas t WHERE t.idUniversidad = :idUniversidad")})
public class TblProgramas implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "facultad_pertenece")
    private String facultadPertenece;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrograma")
    private List<TblPlanPrograma> tblPlanProgramaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_programa")
    private Integer idPrograma;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo_interno")
    private String codigoInterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_programa")
    private String nombrePrograma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPrograma")
    private List<TblMaterias> tblMateriasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaDestino")
    private List<TblHomologacion> tblHomologacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaOrigen")
    private List<TblHomologacion> tblHomologacionList1;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false)
    private TblEstado idEstado;
    @JoinColumn(name = "id_universidad", referencedColumnName = "id_universidad")
    @ManyToOne(optional = false)
    private TblUniversidad idUniversidad;

    /**
     * 
     */
    public TblProgramas() {
    }

    /**
     * 
     * @param idPrograma 
     */
    public TblProgramas(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    /**
     * 
     * @param idPrograma
     * @param codigoInterno
     * @param nombrePrograma 
     */
    public TblProgramas(Integer idPrograma, String codigoInterno, String nombrePrograma) {
        this.idPrograma = idPrograma;
        this.codigoInterno = codigoInterno;
        this.nombrePrograma = nombrePrograma;
    }

    /**
     * 
     * @return 
     */
    public Integer getIdPrograma() {
        return idPrograma;
    }

    /**
     * 
     * @param idPrograma 
     */
    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    /**
     * 
     * @return 
     */
    public String getCodigoInterno() {
        return codigoInterno;
    }

    /**
     * 
     * @param codigoInterno 
     */
    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    /**
     * 
     * @return 
     */
    public String getNombrePrograma() {
        return nombrePrograma;
    }

    /**
     * 
     * @param nombrePrograma 
     */
    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    /**
     * 
     * @return 
     */
    @XmlTransient
    public List<TblMaterias> getTblMateriasList() {
        return tblMateriasList;
    }

    /**
     * 
     * @param tblMateriasList 
     */
    public void setTblMateriasList(List<TblMaterias> tblMateriasList) {
        this.tblMateriasList = tblMateriasList;
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
    public TblUniversidad getIdUniversidad() {
        return idUniversidad;
    }

    /**
     * 
     * @param idUniversidad 
     */
    public void setIdUniversidad(TblUniversidad idUniversidad) {
        this.idUniversidad = idUniversidad;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrograma != null ? idPrograma.hashCode() : 0);
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
        if (!(object instanceof TblProgramas)) {
            return false;
        }
        TblProgramas other = (TblProgramas) object;
        if ((this.idPrograma == null && other.idPrograma != null) || (this.idPrograma != null && !this.idPrograma.equals(other.idPrograma))) {
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
        return "co.com.homologacionesu.entidades.TblProgramas[ idPrograma=" + idPrograma + " ]";
    }

    /**
     * 
     * @return 
     */
    public String getFacultadPertenece() {
        return facultadPertenece;
    }

    /**
     * 
     * @param facultadPertenece 
     */
    public void setFacultadPertenece(String facultadPertenece) {
        this.facultadPertenece = facultadPertenece;
    }

    /**
     * 
     * @return 
     */
    @XmlTransient
    public List<TblPlanPrograma> getTblPlanProgramaList() {
        return tblPlanProgramaList;
    }

    /**
     * 
     * @param tblPlanProgramaList 
     */
    public void setTblPlanProgramaList(List<TblPlanPrograma> tblPlanProgramaList) {
        this.tblPlanProgramaList = tblPlanProgramaList;
    }
    
}
