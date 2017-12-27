/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.entidades;

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
 * @author dsernama
 */
@Entity
@Table(name = "tbl_homologacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblHomologacion.findAll", 
            query = "SELECT t FROM TblHomologacion t"),
    @NamedQuery(name = "TblHomologacion.findByIdHomologa", 
            query = "SELECT t FROM TblHomologacion t WHERE t.idHomologa = :idHomologa"),
    @NamedQuery(name = "TblHomologacion.findByUniversidaCarrera", 
            query = "SELECT t FROM TblHomologacion t "
            + "WHERE  t.universidadOrigen =:universidadOrigen AND "
            + "t.universidadDestino=:universidadDestino and "
            + "t.programaOrigen=:programaOrigen AND t.programaDestino=:programaDestino")})
public class TblHomologacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_homologa")
    private Integer idHomologa;
    @JoinColumn(name = "materia_origen", referencedColumnName = "id_materia")
    @ManyToOne(optional = false)
    private TblMaterias materiaOrigen;
    @JoinColumn(name = "materia_destino", referencedColumnName = "id_materia")
    @ManyToOne(optional = false)
    private TblMaterias materiaDestino;
    @JoinColumn(name = "programa_destino", referencedColumnName = "id_programa")
    @ManyToOne(optional = false)
    private TblProgramas programaDestino;
    @JoinColumn(name = "programa_origen", referencedColumnName = "id_programa")
    @ManyToOne(optional = false)
    private TblProgramas programaOrigen;
    @JoinColumn(name = "universidad_origen", referencedColumnName = "id_universidad")
    @ManyToOne(optional = false)
    private TblUniversidad universidadOrigen;
    @JoinColumn(name = "universidad_destino", referencedColumnName = "id_universidad")
    @ManyToOne(optional = false)
    private TblUniversidad universidadDestino;

    public TblHomologacion() {
    }

    public TblHomologacion(Integer idHomologa) {
        this.idHomologa = idHomologa;
    }

    public Integer getIdHomologa() {
        return idHomologa;
    }

    public void setIdHomologa(Integer idHomologa) {
        this.idHomologa = idHomologa;
    }

    public TblMaterias getMateriaOrigen() {
        return materiaOrigen;
    }

    public void setMateriaOrigen(TblMaterias materiaOrigen) {
        this.materiaOrigen = materiaOrigen;
    }

    public TblMaterias getMateriaDestino() {
        return materiaDestino;
    }

    public void setMateriaDestino(TblMaterias materiaDestino) {
        this.materiaDestino = materiaDestino;
    }

    public TblProgramas getProgramaDestino() {
        return programaDestino;
    }

    public void setProgramaDestino(TblProgramas programaDestino) {
        this.programaDestino = programaDestino;
    }

    public TblProgramas getProgramaOrigen() {
        return programaOrigen;
    }

    public void setProgramaOrigen(TblProgramas programaOrigen) {
        this.programaOrigen = programaOrigen;
    }

    public TblUniversidad getUniversidadOrigen() {
        return universidadOrigen;
    }

    public void setUniversidadOrigen(TblUniversidad universidadOrigen) {
        this.universidadOrigen = universidadOrigen;
    }

    public TblUniversidad getUniversidadDestino() {
        return universidadDestino;
    }

    public void setUniversidadDestino(TblUniversidad universidadDestino) {
        this.universidadDestino = universidadDestino;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHomologa != null ? idHomologa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblHomologacion)) {
            return false;
        }
        TblHomologacion other = (TblHomologacion) object;
        if ((this.idHomologa == null && other.idHomologa != null) || (this.idHomologa != null && !this.idHomologa.equals(other.idHomologa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.homologacionesu.entidades.TblHomologacion[ idHomologa=" + idHomologa + " ]";
    }
    
}
