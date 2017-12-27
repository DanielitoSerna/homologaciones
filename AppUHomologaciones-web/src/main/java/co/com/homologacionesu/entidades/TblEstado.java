/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author dsernama
 */
@Entity
@Table(name = "tbl_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblEstado.findAll", query = "SELECT t FROM TblEstado t"),
    @NamedQuery(name = "TblEstado.findByIdEstado", query = "SELECT t FROM TblEstado t WHERE t.idEstado = :idEstado"),
    @NamedQuery(name = "TblEstado.findByDescripcion", query = "SELECT t FROM TblEstado t WHERE t.descripcion = :descripcion")})
public class TblEstado implements Serializable {

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

    public TblEstado() {
    }

    public TblEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public TblEstado(Long idEstado, String descripcion) {
        this.idEstado = idEstado;
        this.descripcion = descripcion;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<TblMaterias> getTblMateriasList() {
        return tblMateriasList;
    }

    public void setTblMateriasList(List<TblMaterias> tblMateriasList) {
        this.tblMateriasList = tblMateriasList;
    }

    @XmlTransient
    public List<TblRoles> getTblRolesList() {
        return tblRolesList;
    }

    public void setTblRolesList(List<TblRoles> tblRolesList) {
        this.tblRolesList = tblRolesList;
    }

    @XmlTransient
    public List<TblUniversidad> getTblUniversidadList() {
        return tblUniversidadList;
    }

    public void setTblUniversidadList(List<TblUniversidad> tblUniversidadList) {
        this.tblUniversidadList = tblUniversidadList;
    }

    @XmlTransient
    public List<TblProgramas> getTblProgramasList() {
        return tblProgramasList;
    }

    public void setTblProgramasList(List<TblProgramas> tblProgramasList) {
        this.tblProgramasList = tblProgramasList;
    }

    @XmlTransient
    public List<TblUsuario> getTblUsuarioList() {
        return tblUsuarioList;
    }

    public void setTblUsuarioList(List<TblUsuario> tblUsuarioList) {
        this.tblUsuarioList = tblUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstado != null ? idEstado.hashCode() : 0);
        return hash;
    }

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

    @Override
    public String toString() {
        return "co.com.homologacionesu.entidades.TblEstado[ idEstado=" + idEstado + " ]";
    }
    
}
