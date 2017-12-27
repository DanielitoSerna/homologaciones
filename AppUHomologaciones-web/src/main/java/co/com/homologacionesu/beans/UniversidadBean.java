/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.jpacontroller.TblEstadoJpaController;
import co.com.homologacionesu.jpacontroller.TblUniversidadJpaController;
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
public class UniversidadBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String nombreUniversidad;
    private String nit;
    private Long estado;
    private Boolean acreditada;
    private TblEstadoJpaController tblEstadoJpaController;
    private TblUniversidadJpaController  tblUniversidadJpaController;
    private List<TblUniversidad> tblUniversidads;
    private TblUniversidad tblUniversidad;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;

    public String getNombreUniversidad() {
        return nombreUniversidad;
    }

    public void setNombreUniversidad(String nombreUniversidad) {
        this.nombreUniversidad = nombreUniversidad;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Boolean getAcreditada() {
        return acreditada;
    }

    public void setAcreditada(Boolean acreditada) {
        this.acreditada = acreditada;
    }

    public TblEstadoJpaController getTblEstadoJpaController() {
        return tblEstadoJpaController;
    }

    public void setTblEstadoJpaController(TblEstadoJpaController tblEstadoJpaController) {
        this.tblEstadoJpaController = tblEstadoJpaController;
    }

    public TblUniversidadJpaController getTblUniversidadJpaController() {
        return tblUniversidadJpaController;
    }

    public void setTblUniversidadJpaController(TblUniversidadJpaController tblUniversidadJpaController) {
        this.tblUniversidadJpaController = tblUniversidadJpaController;
    }

    public List<TblUniversidad> getTblUniversidads() {
        return tblUniversidads;
    }

    public void setTblUniversidads(List<TblUniversidad> tblUniversidads) {
        this.tblUniversidads = tblUniversidads;
    }

    public TblUniversidad getTblUniversidad() {
        return tblUniversidad;
    }

    public void setTblUniversidad(TblUniversidad tblUniversidad) {
        this.tblUniversidad = tblUniversidad;
    }

    public Boolean getHabilitarCodigo() {
        return habilitarCodigo;
    }

    public void setHabilitarCodigo(Boolean habilitarCodigo) {
        this.habilitarCodigo = habilitarCodigo;
    }

    public Boolean getHabilitarBoton() {
        return habilitarBoton;
    }

    public void setHabilitarBoton(Boolean habilitarBoton) {
        this.habilitarBoton = habilitarBoton;
    }
    
    @PostConstruct
    public void init(){
        tblUniversidadJpaController = new TblUniversidadJpaController(JPAFactory.getFACTORY());
        tblUniversidads = new ArrayList<>();
        List<TblUniversidad> universidads = tblUniversidadJpaController.findTblUniversidadEntities();
        if(universidads.size()>0){
            for (TblUniversidad universidad : universidads) {
                if(!universidad.getIdEstado().getDescripcion().equals("INACTIVO")){
                    tblUniversidads.add(universidad);
                }
            }
        }
        setHabilitarCodigo(Boolean.TRUE);
        setHabilitarBoton(Boolean.FALSE);
    }
    
    public void guardar(ActionEvent actionEvent){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblUniversidadJpaController = 
                new TblUniversidadJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        TblUniversidad tblUniversidadIn = new TblUniversidad();
        if((nombreUniversidad != null && !nombreUniversidad.isEmpty()) 
                && (acreditada != null)
                && (nit != null && !nit.isEmpty())){
            tblUniversidadIn.setIdEstado(tblEstado);
            tblUniversidadIn.setNit(nit);
            tblUniversidadIn.setNombreUniversidad(nombreUniversidad);
            if(acreditada){
                tblUniversidadIn.setAcreditada("Si");
            }else{
                tblUniversidadIn.setAcreditada("No");
            }
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
            try {
                tblUniversidadJpaController.create(tblUniversidadIn);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Creado Correctamente", "");
                setNit("");
                setNombreUniversidad("");
                setAcreditada(null);
                tblUniversidads.add(tblUniversidadIn);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(UniversidadBean.class.getName()).log(Level.SEVERE, null, ex);
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
            } catch (Exception ex) {
                Logger.getLogger(UniversidadBean.class.getName()).log(Level.SEVERE, null, ex);
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/universidades.xhtml");
    }
    
    public void seleccionarObjeto() {
        nombreUniversidad = (getTblUniversidad().getNombreUniversidad()!= null
                    ? getTblUniversidad().getNombreUniversidad() : "");
        nit = (getTblUniversidad().getNit()!= null
                    ? getTblUniversidad().getNit(): "");
        if(getTblUniversidad().getAcreditada()!= null){
            if(getTblUniversidad().getAcreditada().equals("Si")){
                acreditada = Boolean.TRUE;
            }else{
                acreditada = Boolean.FALSE;
            }
        }else{
            acreditada = Boolean.FALSE;
        }
        setHabilitarCodigo(Boolean.FALSE);
        setHabilitarBoton(Boolean.TRUE);
    }
    
    public void modificar(ActionEvent actionEvent){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblUniversidadJpaController = 
                new TblUniversidadJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        TblUniversidad tblUniversidadIn = new TblUniversidad();
        if((nombreUniversidad != null && !nombreUniversidad.isEmpty()) 
                && (acreditada != null)
                && (nit != null && !nit.isEmpty())){
            tblUniversidadIn.setIdEstado(tblEstado);
            tblUniversidadIn.setNit(nit);
            tblUniversidadIn.setIdUniversidad(tblUniversidad.getIdUniversidad());
            tblUniversidadIn.setNombreUniversidad(nombreUniversidad);
            if(acreditada){
                tblUniversidadIn.setAcreditada("Si");
            }else{
                tblUniversidadIn.setAcreditada("No");
            }
            tblUniversidadIn.setTblHomologacionList(new ArrayList<TblHomologacion>());
            tblUniversidadIn.setTblHomologacionList1(new ArrayList<TblHomologacion>());
            tblUniversidadIn.setTblProgramasList(new ArrayList<TblProgramas>());
            try {
                tblUniversidadJpaController.edit(tblUniversidadIn);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Modificado Correctamente", "");
                setNit("");
                setNombreUniversidad("");
                setAcreditada(null);
                setHabilitarCodigo(Boolean.TRUE);
                setHabilitarBoton(Boolean.FALSE);
                tblUniversidads.remove(tblUniversidad);
                tblUniversidads.add(tblUniversidadIn);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(UniversidadBean.class.getName()).log(Level.SEVERE, null, ex);
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
            } catch (Exception ex) {
                Logger.getLogger(UniversidadBean.class.getName()).log(Level.SEVERE, null, ex);
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/universidades.xhtml");
    }
    
    public void eliminar(ActionEvent actionEvent){
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();
        estado = new Long(2);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        
        tblUniversidad.setIdEstado(tblEstado);
        
        try {
            tblUniversidadJpaController.edit(tblUniversidad);
            setNit("");
            setNombreUniversidad("");
            setAcreditada(null);
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Eliminado Correctamente", "");
            tblUniversidads.remove(tblUniversidad);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UniversidadBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UniversidadBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        } catch (Exception ex) {
            Logger.getLogger(UniversidadBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/universidades.xhtml");
    }
}
