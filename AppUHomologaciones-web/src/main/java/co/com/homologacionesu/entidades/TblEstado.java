package co.com.homologacionesu.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Objetivo: Entidad que maneja constantes que son usados dentro de la aplicación
 * @author Daniel Serna
 */
@Entity
@Table(name = "tbl_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblEstado.findAll", 
            query = "SELECT t FROM TblEstado t"),
    @NamedQuery(name = "TblEstado.findByIdEstado", 
            query = "SELECT t FROM TblEstado t WHERE t.idEstado = :idEstado"),
    @NamedQuery(name = "TblEstado.findByDescripcion", 
            query = "SELECT t FROM TblEstado t WHERE t.descripcion = :descripcion")})
public class TblEstado implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<TblPlanPrograma> tblPlanProgramaList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estado")
    private Long idEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<TblMaterias> tblMateriasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<TblRoles> tblRolesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<TblUniversidad> tblUniversidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<TblProgramas> tblProgramasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstado")
    private List<TblUsuario> tblUsuarioList;

    /**
     * 
     */
    public TblEstado() {
    }

    /**
     * 
     * @param idEstado 
     */
    public TblEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * 
     * @param idEstado
     * @param descripcion 
     */
    public TblEstado(Long idEstado, String descripcion) {
        this.idEstado = idEstado;
        this.descripcion = descripcion;
    }

    /**
     * 
     * @return 
     */
    public Long getIdEstado() {
        return idEstado;
    }

    /**
     * 
     * @param idEstado 
     */
    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    /**
     * 
     * @return 
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * 
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public List<TblRoles> getTblRolesList() {
        return tblRolesList;
    }

    /**
     * 
     * @param tblRolesList 
     */
    public void setTblRolesList(List<TblRoles> tblRolesList) {
        this.tblRolesList = tblRolesList;
    }

    /**
     * 
     * @return 
     */
    @XmlTransient
    public List<TblUniversidad> getTblUniversidadList() {
        return tblUniversidadList;
    }

    /**
     * 
     * @param tblUniversidadList 
     */
    public void setTblUniversidadList(List<TblUniversidad> tblUniversidadList) {
        this.tblUniversidadList = tblUniversidadList;
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
    @XmlTransient
    public List<TblUsuario> getTblUsuarioList() {
        return tblUsuarioList;
    }

    /**
     * 
     * @param tblUsuarioList 
     */
    public void setTblUsuarioList(List<TblUsuario> tblUsuarioList) {
        this.tblUsuarioList = tblUsuarioList;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
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
        if (!(object instanceof TblEstado)) {
            return false;
        }
        TblEstado other = (TblEstado) object;
        if ((this.idEstado == null && other.idEstado != null) || (this.idEstado != null && !this.idEstado.equals(other.idEstado))) {
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
        return "co.com.homologacionesu.entidades.TblEstado[ idEstado=" + idEstado + " ]";
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
