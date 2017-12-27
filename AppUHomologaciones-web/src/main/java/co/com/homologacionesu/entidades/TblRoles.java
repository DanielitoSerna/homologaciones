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
 *
 * @author dsernama
 */
@Entity
@Table(name = "tbl_roles")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblRoles.findAll", query = "SELECT t FROM TblRoles t"),
    @NamedQuery(name = "TblRoles.findByCodRol", query = "SELECT t FROM TblRoles t WHERE t.codRol = :codRol"),
    @NamedQuery(name = "TblRoles.findByNombreRol", query = "SELECT t FROM TblRoles t WHERE t.nombreRol = :nombreRol"),
    @NamedQuery(name = "TblRoles.findByDescripcion", query = "SELECT t FROM TblRoles t WHERE t.descripcion = :descripcion")})
public class TblRoles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_rol")
    private Long codRol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nombre_rol")
    private String nombreRol;
    @Size(max = 200)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false)
    private TblEstado idEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codRol")
    private List<TblUsuario> tblUsuarioList;

    public TblRoles() {
    }

    public TblRoles(Long codRol) {
        this.codRol = codRol;
    }

    public TblRoles(Long codRol, String nombreRol) {
        this.codRol = codRol;
        this.nombreRol = nombreRol;
    }

    public Long getCodRol() {
        return codRol;
    }

    public void setCodRol(Long codRol) {
        this.codRol = codRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TblEstado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TblEstado idEstado) {
        this.idEstado = idEstado;
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
        hash += (codRol != null ? codRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblRoles)) {
            return false;
        }
        TblRoles other = (TblRoles) object;
        if ((this.codRol == null && other.codRol != null) || (this.codRol != null && !this.codRol.equals(other.codRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.homologacionesu.entidades.TblRoles[ codRol=" + codRol + " ]";
    }
    
}
