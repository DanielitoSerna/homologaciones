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
 *
 * @author dsernama
 */
@Entity
@Table(name = "tbl_materias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblMaterias.findAll", query = "SELECT t FROM TblMaterias t"),
    @NamedQuery(name = "TblMaterias.findByIdMateria", 
            query = "SELECT t FROM TblMaterias t WHERE t.idMateria = :idMateria"),
    @NamedQuery(name = "TblMaterias.findByCodigoInterno", 
            query = "SELECT t FROM TblMaterias t WHERE t.codigoInterno = :codigoInterno"),
    @NamedQuery(name = "TblMaterias.findByNombreMateria", 
            query = "SELECT t FROM TblMaterias t WHERE t.nombreMateria = :nombreMateria"),
    @NamedQuery(name = "TblMaterias.findByPrograma",
            query = "SELECT t FROM TblMaterias t WHERE t.idPrograma = :idPrograma")})
public class TblMaterias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_materia")
    private Integer idMateria;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codigo_interno")
    private String codigoInterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "nombre_materia")
    private String nombreMateria;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false)
    private TblEstado idEstado;
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    @ManyToOne(optional = false)
    private TblProgramas idPrograma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materiaOrigen")
    private List<TblHomologacion> tblHomologacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materiaDestino")
    private List<TblHomologacion> tblHomologacionList1;

    public TblMaterias() {
    }

    public TblMaterias(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public TblMaterias(Integer idMateria, String codigoInterno, String nombreMateria) {
        this.idMateria = idMateria;
        this.codigoInterno = codigoInterno;
        this.nombreMateria = nombreMateria;
    }

    public Integer getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Integer idMateria) {
        this.idMateria = idMateria;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public TblEstado getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(TblEstado idEstado) {
        this.idEstado = idEstado;
    }

    public TblProgramas getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(TblProgramas idPrograma) {
        this.idPrograma = idPrograma;
    }

    @XmlTransient
    public List<TblHomologacion> getTblHomologacionList() {
        return tblHomologacionList;
    }

    public void setTblHomologacionList(List<TblHomologacion> tblHomologacionList) {
        this.tblHomologacionList = tblHomologacionList;
    }

    @XmlTransient
    public List<TblHomologacion> getTblHomologacionList1() {
        return tblHomologacionList1;
    }

    public void setTblHomologacionList1(List<TblHomologacion> tblHomologacionList1) {
        this.tblHomologacionList1 = tblHomologacionList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMateria != null ? idMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblMaterias)) {
            return false;
        }
        TblMaterias other = (TblMaterias) object;
        if ((this.idMateria == null && other.idMateria != null) || (this.idMateria != null && !this.idMateria.equals(other.idMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.homologacionesu.entidades.TblMaterias[ idMateria=" + idMateria + " ]";
    }
    
}
