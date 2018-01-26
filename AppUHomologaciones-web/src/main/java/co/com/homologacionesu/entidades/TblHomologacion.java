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
 * Objetivo: Generar homologación 
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

    /**
     * 
     */
    public TblHomologacion() {
    }

    /**
     * 
     * @param idHomologa 
     */
    public TblHomologacion(Integer idHomologa) {
        this.idHomologa = idHomologa;
    }

    /**
     * 
     * @return 
     */
    public Integer getIdHomologa() {
        return idHomologa;
    }

    /**
     * 
     * @param idHomologa 
     */
    public void setIdHomologa(Integer idHomologa) {
        this.idHomologa = idHomologa;
    }

    /**
     * 
     * @return 
     */
    public TblMaterias getMateriaOrigen() {
        return materiaOrigen;
    }

    /**
     * 
     * @param materiaOrigen 
     */
    public void setMateriaOrigen(TblMaterias materiaOrigen) {
        this.materiaOrigen = materiaOrigen;
    }

    /**
     * 
     * @return 
     */
    public TblMaterias getMateriaDestino() {
        return materiaDestino;
    }

    /**
     * 
     * @param materiaDestino 
     */
    public void setMateriaDestino(TblMaterias materiaDestino) {
        this.materiaDestino = materiaDestino;
    }

    /**
     * 
     * @return 
     */
    public TblProgramas getProgramaDestino() {
        return programaDestino;
    }

    /**
     * 
     * @param programaDestino 
     */
    public void setProgramaDestino(TblProgramas programaDestino) {
        this.programaDestino = programaDestino;
    }

    /**
     * 
     * @return 
     */
    public TblProgramas getProgramaOrigen() {
        return programaOrigen;
    }

    /**
     * 
     * @param programaOrigen 
     */
    public void setProgramaOrigen(TblProgramas programaOrigen) {
        this.programaOrigen = programaOrigen;
    }

    /**
     * 
     * @return 
     */
    public TblUniversidad getUniversidadOrigen() {
        return universidadOrigen;
    }

    /**
     * 
     * @param universidadOrigen 
     */
    public void setUniversidadOrigen(TblUniversidad universidadOrigen) {
        this.universidadOrigen = universidadOrigen;
    }

    /**
     * 
     * @return 
     */
    public TblUniversidad getUniversidadDestino() {
        return universidadDestino;
    }

    /**
     * 
     * @param universidadDestino 
     */
    public void setUniversidadDestino(TblUniversidad universidadDestino) {
        this.universidadDestino = universidadDestino;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHomologa != null ? idHomologa.hashCode() : 0);
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
        if (!(object instanceof TblHomologacion)) {
            return false;
        }
        TblHomologacion other = (TblHomologacion) object;
        if ((this.idHomologa == null && other.idHomologa != null) || (this.idHomologa != null && !this.idHomologa.equals(other.idHomologa))) {
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
        return "co.com.homologacionesu.entidades.TblHomologacion[ idHomologa=" + idHomologa + " ]";
    }
    
}
