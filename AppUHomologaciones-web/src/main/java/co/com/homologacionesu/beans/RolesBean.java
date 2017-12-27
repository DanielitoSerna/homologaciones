/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblRoles;
import co.com.homologacionesu.jpacontroller.TblEstadoJpaController;
import co.com.homologacionesu.jpacontroller.TblRolesJpaController;
import co.com.homologacionesu.jpacontroller.exceptions.NonexistentEntityException;
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
 *
 * @author dsernama
 */
@ManagedBean
@ViewScoped
public class RolesBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long codigo;
    private String descripcionRol;
    private String nombreRol;
    private Long estado;
    private TblEstadoJpaController tblEstadoJpaController;
    private TblRolesJpaController tblRolesJpaController;
    private List<TblRoles> roleses;
    private TblRoles tblRoles;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;

    /**
     * 
     * @return 
     */
    public Long getCodigo() {
        return codigo;
    }

    /**
     * 
     * @param codigo 
     */
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    /**
     * 
     * @return 
     */
    public String getDescripcionRol() {
        return descripcionRol;
    }

    /**
     * 
     * @param descripcionRol 
     */
    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    /**
     * 
     * @return 
     */
    public String getNombreRol() {
        return nombreRol;
    }

    /**
     * 
     * @param nombreRol 
     */
    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
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
     * 
     * @return 
     */
    public TblRoles getTblRoles() {
        return tblRoles;
    }

    /**
     * 
     * @param tblRoles 
     */
    public void setTblRoles(TblRoles tblRoles) {
        this.tblRoles = tblRoles;
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
     * Metodo que se encarga de insanciar una lista de datos para presentar
     * una vez cargada la vista
     */
    @PostConstruct
    public void init(){
        tblRolesJpaController = new TblRolesJpaController(JPAFactory.getFACTORY());
        List<TblRoles> tblRoleses = tblRolesJpaController.findTblRolesEntities();
        roleses = new ArrayList<>();
        if(tblRoleses.size()>0){
            for (TblRoles tblRolese : tblRoleses) {
                if(!tblRolese.getIdEstado().getDescripcion().equals("INACTIVO")){
                    roleses.add(tblRolese);
                }
            }
        }
        setHabilitarCodigo(Boolean.TRUE);
        setHabilitarBoton(Boolean.FALSE);
    }

    /**
     * Metodo que permite guardar la información diligenciada en el formulario
     * de roles
     * @param actionEvent 
     */
    public void guardar(ActionEvent actionEvent) {
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblRolesJpaController =
                new TblRolesJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        TblRoles tblRolesIn = new TblRoles();

        if ((codigo != null)
                && (nombreRol != null && !nombreRol.isEmpty())
                && (descripcionRol != null && !descripcionRol.isEmpty())) {
            tblRolesIn.setCodRol(codigo);
            tblRolesIn.setNombreRol(nombreRol);
            tblRolesIn.setDescripcion(descripcionRol);
            tblRolesIn.setIdEstado(tblEstado);
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
            try {
                tblRolesJpaController.create(tblRolesIn);
                roleses.add(tblRolesIn);
                setCodigo(null);
                setDescripcionRol("");
                setNombreRol("");
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Creado Correctamente", "");
            } catch (RollbackFailureException ex) {
                Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, 
                        null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
            } catch (Exception ex) {
                Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, 
                        null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
            }

        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingresar información en todos los "
                    + "campos", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/roles.xhtml");
    }
    
    /**
     * Método que permite obtener un registro de la lista para poder editar o
     * eliminar
     */
    public void seleccionarObjeto() {
        codigo = (getTblRoles().getCodRol()!= null
                    ? getTblRoles().getCodRol() : 0);
        nombreRol = (getTblRoles().getNombreRol()!= null
                    ? getTblRoles().getNombreRol() : "");
        descripcionRol = (getTblRoles().getDescripcion()!= null
                    ? getTblRoles().getDescripcion() : "");
        setHabilitarCodigo(Boolean.FALSE);
        setHabilitarBoton(Boolean.TRUE);
    }

    /**
     * Metodo que se encarga de modificar los registros
     * @param event 
     */
    public void modifiar(ActionEvent event){
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblRolesJpaController =
                new TblRolesJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        
        if ((codigo != null)
                && (nombreRol != null && !nombreRol.isEmpty())
                && (descripcionRol != null && !descripcionRol.isEmpty())) {
            tblRoles.setCodRol(codigo);
            tblRoles.setNombreRol(nombreRol);
            tblRoles.setDescripcion(descripcionRol);
            tblRoles.setIdEstado(tblEstado);
            try {
                tblRolesJpaController.edit(tblRoles);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Modificado Correctamente", "");
                setHabilitarCodigo(Boolean.TRUE);
                setHabilitarBoton(Boolean.FALSE);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, 
                        null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
            } catch (Exception ex) {
                Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, 
                        null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingresar información en los "
                    + "campos código y nombre del rol", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/roles.xhtml");
//        JSFUtil.redirectPage("/vw/roles.xhtml");
    }
    
    public void eliminar(ActionEvent event){
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();
        estado = new Long(2);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblRolesJpaController =
                new TblRolesJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        
        tblRoles.setIdEstado(tblEstado);
        try {
            tblRolesJpaController.edit(tblRoles);
            roleses.remove(tblRoles);
            setCodigo(null);
            setDescripcionRol("");
            setNombreRol("");
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Eliminado Correctamente", "");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        } catch (RollbackFailureException ex) {
            Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        } catch (Exception ex) {
            Logger.getLogger(RolesBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/roles.xhtml");
    }
}
