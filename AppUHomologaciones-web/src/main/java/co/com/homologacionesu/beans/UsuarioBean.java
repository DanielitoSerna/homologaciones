package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblRoles;
import co.com.homologacionesu.entidades.TblUsuario;
import co.com.homologacionesu.jpacontroller.TblEstadoJpaController;
import co.com.homologacionesu.jpacontroller.TblRolesJpaController;
import co.com.homologacionesu.jpacontroller.TblUsuarioJpaController;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import co.com.homologacionesu.util.JPAFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 * Objetivo: Administrar usuarios
 * @author dsernama
 */
@ManagedBean
@ViewScoped
public class UsuarioBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String nombreUsuario;
    private String clave;
    private String correo;
    private Long estado;
    private Long rol;
    private String claveRepetir;
    private TblEstadoJpaController tblEstadoJpaController;
    private TblRolesJpaController tblRolesJpaController;
    private TblUsuarioJpaController tblUsuarioJpaController;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;
    private List<TblUsuario> usuarios;
    private List<TblRoles> roleses;
    private TblUsuario tblUsuario;

    /**
     * 
     * @return 
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * 
     * @param nombreUsuario 
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * 
     * @return 
     */
    public String getClave() {
        return clave;
    }

    /**
     * 
     * @param clave 
     */
    public void setClave(String clave) {
        this.clave = clave;
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
    public Long getEstado() {
        return estado;
    }

    /**
     * 
     * @param estado 
     */
    public void setEstado(Long estado) {
        this.estado = estado;
    }

    /**
     * 
     * @return 
     */
    public Boolean getHabilitarCodigo() {
        return habilitarCodigo;
    }

    /**
     * 
     * @param habilitarCodigo 
     */
    public void setHabilitarCodigo(Boolean habilitarCodigo) {
        this.habilitarCodigo = habilitarCodigo;
    }

    /**
     * 
     * @return 
     */
    public Boolean getHabilitarBoton() {
        return habilitarBoton;
    }

    /**
     * 
     * @param habilitarBoton 
     */
    public void setHabilitarBoton(Boolean habilitarBoton) {
        this.habilitarBoton = habilitarBoton;
    }

    /**
     * 
     * @return 
     */
    public Long getRol() {
        return rol;
    }

    /**
     * 
     * @param rol 
     */
    public void setRol(Long rol) {
        this.rol = rol;
    }

    /**
     * 
     * @return 
     */
    public String getClaveRepetir() {
        return claveRepetir;
    }

    /**
     * 
     * @param claveRepetir 
     */
    public void setClaveRepetir(String claveRepetir) {
        this.claveRepetir = claveRepetir;
    }

    /**
     * 
     * @return 
     */
    public TblUsuario getTblUsuario() {
        return tblUsuario;
    }

    /**
     * 
     * @param tblUsuario 
     */
    public void setTblUsuario(TblUsuario tblUsuario) {
        this.tblUsuario = tblUsuario;
    }
    
    /**
     * 
     * @return 
     */
    public List<TblUsuario> getUsuarios() {
        return usuarios;
    }

    /**
     * 
     * @param usuarios 
     */
    public void setUsuarios(List<TblUsuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * 
     * @return 
     */
    public List<TblRoles> getRoleses() {
        return roleses;
    }

    /**
     * 
     * @param roleses 
     */
    public void setRoleses(List<TblRoles> roleses) {
        this.roleses = roleses;
    }
    
    /**
     * Metodo que se encarga de insanciar una lista de datos para presentar
     * una vez cargada la vista
     */
    @PostConstruct
    public void init(){
        tblUsuarioJpaController = new TblUsuarioJpaController(JPAFactory.getFACTORY());
        usuarios = new ArrayList<>();
        List<TblUsuario> tblUsuarios = tblUsuarioJpaController.findTblUsuarioEntities();
        if(tblUsuarios.size() >0){
            for (TblUsuario tblUsuario1 : tblUsuarios) {
                if(!tblUsuario1.getIdEstado().getDescripcion().equals("INACTIVO")){
                    usuarios.add(tblUsuario1);
                }
            }
        }
        tblRolesJpaController = new TblRolesJpaController(JPAFactory.getFACTORY());
        roleses = tblRolesJpaController.findTblRolesEntities();
        setHabilitarCodigo(Boolean.TRUE);
        setHabilitarBoton(Boolean.FALSE);
    }
    
    /**
     * Descripción: Método que permite guardar información
     * @param event 
     */
    public void guardar(ActionEvent event){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblRolesJpaController =
                new TblRolesJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblUsuarioJpaController =
                new TblUsuarioJpaController(JPAFactory.getFACTORY());
        TblUsuario tblUsuarioIn = new TblUsuario();
        if(rol != null){
            TblRoles tblRoles = tblRolesJpaController.findTblRoles(rol);
            if((nombreUsuario != null && !nombreUsuario.isEmpty())
                    &&(clave != null && !clave.isEmpty())
                    &&(claveRepetir != null && !claveRepetir.isEmpty())
                    &&(correo != null && !correo.isEmpty())
                    &&(rol != null)){
                if(clave.equals(claveRepetir)){
                    tblUsuarioIn.setCodRol(tblRoles);
                    tblUsuarioIn.setIdEstado(tblEstado);
                    tblUsuarioIn.setContrasena(clave);
                    tblUsuarioIn.setCorreo(correo);
                    tblUsuarioIn.setUsuario(nombreUsuario);
                    setHabilitarCodigo(Boolean.TRUE);
                    setHabilitarBoton(Boolean.FALSE);
                    try {
                        tblUsuarioJpaController.create(tblUsuarioIn);
                        usuarios.add(tblUsuarioIn);
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Creado Correctamente", "");
                        setNombreUsuario("");
                        setClave("");
                        setClaveRepetir("");
                        setRol(null);
                        setCorreo("");
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(UsuarioBean.class.getName())
                                .log(Level.SEVERE, null, ex);
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
                    } catch (Exception ex) {
                        Logger.getLogger(UsuarioBean.class.getName())
                                .log(Level.SEVERE, null, ex);
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
                    }
                }else{
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "La contraseña no coinicida. Verifique.", "");
                }
            }else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Debe seleccionar el rol", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/usuarios.xhtml");
    }
    
    /**
     * Descripción: Método que permite seleccionar un registro
     */
    public void seleccionarObjeto() {
        nombreUsuario = (getTblUsuario().getUsuario()!= null
                    ? getTblUsuario().getUsuario() : "");
        correo = (getTblUsuario().getCorreo()!= null
                    ? getTblUsuario().getCorreo(): "");
        clave = (getTblUsuario().getContrasena()!= null
                    ? getTblUsuario().getContrasena() : "");
        claveRepetir = (getTblUsuario().getContrasena()!= null
                    ? getTblUsuario().getContrasena() : "");
        rol = (getTblUsuario().getCodRol().getCodRol()!= null
                    ? getTblUsuario().getCodRol().getCodRol() : 0);
        setHabilitarCodigo(Boolean.FALSE);
        setHabilitarBoton(Boolean.TRUE);
    }
    
    /**
     * Descripción: Método que permite modificar información
     * @param actionEvent 
     */
    public void modificar(ActionEvent actionEvent){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblRolesJpaController =
                new TblRolesJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblUsuarioJpaController =
                new TblUsuarioJpaController(JPAFactory.getFACTORY());
        TblUsuario tblUsuarioIn = new TblUsuario();
        if(rol != null){
            TblRoles tblRoles = tblRolesJpaController.findTblRoles(rol);
            if((nombreUsuario != null && !nombreUsuario.isEmpty())
                    &&(clave != null && !clave.isEmpty())
                    &&(claveRepetir != null && !claveRepetir.isEmpty())
                    &&(correo != null && !correo.isEmpty())
                    &&(rol != null)){
                if(clave.equals(claveRepetir)){
                    tblUsuarioIn.setCodRol(tblRoles);
                    tblUsuarioIn.setIdEstado(tblEstado);
                    tblUsuarioIn.setContrasena(clave);
                    tblUsuarioIn.setCorreo(correo);
                    tblUsuarioIn.setUsuario(nombreUsuario);
                    setHabilitarCodigo(Boolean.TRUE);
                    setHabilitarBoton(Boolean.FALSE);
                    try {
                        tblUsuarioJpaController.edit(tblUsuarioIn);
                        usuarios.remove(tblUsuario);
                        usuarios.add(tblUsuarioIn);
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Modificado Correctamente", "");
                        setCorreo("");
                        setNombreUsuario("");
                        setRol(null);
                        setClave("");
                        setClaveRepetir("");
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(UsuarioBean.class.getName())
                                .log(Level.SEVERE, null, ex);
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
                    } catch (Exception ex) {
                        Logger.getLogger(UsuarioBean.class.getName())
                                .log(Level.SEVERE, null, ex);
                        msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
                    }
                }else{
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "La contraseña no coinicida. Verifique.", "");
                }
            }else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Debe seleccionar el rol", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/usuarios.xhtml");
    }
    
    /**
     * Descripción: Método que permite eliminar información
     * @param actionEvent 
     */
    public void eliminar(ActionEvent actionEvent){
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();
        estado = new Long(2);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblUsuarioJpaController =
                new TblUsuarioJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        
        tblUsuario.setIdEstado(tblEstado);
        try {
            tblUsuarioJpaController.edit(tblUsuario);
            usuarios.remove(tblUsuario);
            setNombreUsuario("");
            setCorreo("");
            setClave("");
            setClaveRepetir("");
            setRol(null);
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Eliminado Correctamente", "");
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/usuarios.xhtml");
    }
}
