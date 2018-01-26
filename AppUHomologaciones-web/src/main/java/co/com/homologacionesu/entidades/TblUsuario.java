package co.com.homologacionesu.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objetivo: Administrar usuarios
 * @author Daniel Serna
 */
@Entity
@Table(name = "tbl_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblUsuario.findAll", 
            query = "SELECT t FROM TblUsuario t"),
    @NamedQuery(name = "TblUsuario.findByUsuario", 
            query = "SELECT t FROM TblUsuario t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "TblUsuario.findByContrasena", 
            query = "SELECT t FROM TblUsuario t WHERE t.contrasena = :contrasena"),
    @NamedQuery(name = "TblUsuario.findByCorreo", 
            query = "SELECT t FROM TblUsuario t WHERE t.correo = :correo"),
    @NamedQuery(name = "TblUsuario.findByUsuarioContrasena", 
            query = "SELECT t FROM TblUsuario t WHERE t.usuario = :usuario and "
                    + "t.contrasena = :contrasena")})
public class TblUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "contrasena")
    private String contrasena;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "correo")
    private String correo;
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(optional = false)
    private TblEstado idEstado;
    @JoinColumn(name = "cod_rol", referencedColumnName = "cod_rol")
    @ManyToOne(optional = false)
    private TblRoles codRol;

    /**
     * 
     */
    public TblUsuario() {
    }

    /**
     * 
     * @param usuario 
     */
    public TblUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * 
     * @param usuario
     * @param contrasena
     * @param correo 
     */
    public TblUsuario(String usuario, String contrasena, String correo) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correo = correo;
    }

    /**
     * 
     * @return 
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * 
     * @param usuario 
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * 
     * @return 
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * 
     * @param contrasena 
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * 
     * @return 
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * 
     * @param correo 
     */
    public void setCorreo(String correo) {
        this.correo = correo;
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
    public TblRoles getCodRol() {
        return codRol;
    }

    /**
     * 
     * @param codRol 
     */
    public void setCodRol(TblRoles codRol) {
        this.codRol = codRol;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
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
        if (!(object instanceof TblUsuario)) {
            return false;
        }
        TblUsuario other = (TblUsuario) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
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
        return "co.com.homologacionesu.entidades.TblUsuario[ usuario=" + usuario + " ]";
    }
    
}
