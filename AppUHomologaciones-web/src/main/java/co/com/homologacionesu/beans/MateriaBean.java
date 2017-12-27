/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.jpacontroller.TblEstadoJpaController;
import co.com.homologacionesu.jpacontroller.TblMateriasJpaController;
import co.com.homologacionesu.jpacontroller.TblProgramasJpaController;
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
public class MateriaBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String codigoInterno;
    private String nombreMateria;
    private Integer programaPertenece;
    private Long estado;
    private TblEstadoJpaController tblEstadoJpaController;
    private TblProgramasJpaController tblProgramasJpaController;
    private TblMateriasJpaController tblMateriasJpaController;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;
    private TblMaterias tblMaterias;
    private List<TblMaterias>  tblMateriases;
    private List<TblProgramas> tblProgramases;

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

    public Integer getProgramaPertenece() {
        return programaPertenece;
    }

    public void setProgramaPertenece(Integer programaPertenece) {
        this.programaPertenece = programaPertenece;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
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

    public TblMaterias getTblMaterias() {
        return tblMaterias;
    }

    public void setTblMaterias(TblMaterias tblMaterias) {
        this.tblMaterias = tblMaterias;
    }

    public List<TblMaterias> getTblMateriases() {
        return tblMateriases;
    }

    public void setTblMateriases(List<TblMaterias> tblMateriases) {
        this.tblMateriases = tblMateriases;
    }

    public List<TblProgramas> getTblProgramases() {
        return tblProgramases;
    }

    public void setTblProgramases(List<TblProgramas> tblProgramases) {
        this.tblProgramases = tblProgramases;
    }
    
    @PostConstruct
    public void init(){
        tblProgramasJpaController = 
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblProgramases = tblProgramasJpaController.findTblProgramasEntities();
        tblMateriasJpaController = 
                new TblMateriasJpaController(JPAFactory.getFACTORY());
        List<TblMaterias> materiases = tblMateriasJpaController.findTblMateriasEntities();
        tblMateriases = new  ArrayList<>();
        for (TblMaterias materiase : materiases) {
            if(!materiase.getIdEstado().getDescripcion().equals("INACTIVO")){
                tblMateriases.add(materiase);
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
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblProgramasJpaController =
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblMateriasJpaController = 
                new TblMateriasJpaController(JPAFactory.getFACTORY());
        TblMaterias tblMateriasIn = new TblMaterias();
        if(programaPertenece != null){
            TblProgramas tblProgramas = tblProgramasJpaController.findTblProgramas(programaPertenece);
            if((codigoInterno != null && !codigoInterno.isEmpty()
                    && nombreMateria != null && !nombreMateria.isEmpty())){
                tblMateriasIn.setCodigoInterno(codigoInterno);
                tblMateriasIn.setIdEstado(tblEstado);
                tblMateriasIn.setIdPrograma(tblProgramas);
                tblMateriasIn.setNombreMateria(nombreMateria);
                setHabilitarCodigo(Boolean.TRUE);
                setHabilitarBoton(Boolean.FALSE);
                try {
                    tblMateriasJpaController.create(tblMateriasIn);
                    tblMateriases.add(tblMateriasIn);
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Creado Correctamente", "");
                    setNombreMateria("");
                    setCodigoInterno("");
                    setProgramaPertenece(null);
                } catch (Exception ex) {
                    Logger.getLogger(MateriaBean.class.getName()).log(Level.SEVERE, null, ex);
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
                }
            }else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Debe seleccionar el campo Programa", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/materias.xhtml");
    }
    
    public void seleccionarObjeto() {
        nombreMateria = (getTblMaterias().getNombreMateria()!= null
                    ? getTblMaterias().getNombreMateria(): "");
        codigoInterno = (getTblMaterias().getCodigoInterno()!= null
                    ? getTblMaterias().getCodigoInterno(): "");
        programaPertenece = (getTblMaterias().getIdPrograma().getIdPrograma()!= null
                    ? getTblMaterias().getIdPrograma().getIdPrograma() : 0);
        setHabilitarCodigo(Boolean.FALSE);
        setHabilitarBoton(Boolean.TRUE);
    }
    
    public void modificar(ActionEvent actionEvent){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblProgramasJpaController =
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblMateriasJpaController = 
                new TblMateriasJpaController(JPAFactory.getFACTORY());
        TblMaterias tblMateriasIn = new TblMaterias();
        if(programaPertenece != null){
            TblProgramas tblProgramas = tblProgramasJpaController.findTblProgramas(programaPertenece);
            if((codigoInterno != null && !codigoInterno.isEmpty()
                    && nombreMateria != null && !nombreMateria.isEmpty())){
                tblMateriasIn.setCodigoInterno(codigoInterno);
                tblMateriasIn.setIdEstado(tblEstado);
                tblMateriasIn.setIdPrograma(tblProgramas);
                tblMateriasIn.setNombreMateria(nombreMateria);
                tblMateriasIn.setIdMateria(tblMaterias.getIdMateria());
                tblMateriasIn.setTblHomologacionList(new ArrayList<TblHomologacion>());
                tblMateriasIn.setTblHomologacionList1(new ArrayList<TblHomologacion>());
                try {
                    tblMateriasJpaController.edit(tblMateriasIn);
                    tblMateriases.remove(tblMaterias);
                    tblMateriases.add(tblMateriasIn);
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Modificado Correctamente", "");
                    setNombreMateria("");
                    setCodigoInterno("");
                    setHabilitarCodigo(Boolean.TRUE);
                setHabilitarBoton(Boolean.FALSE);
                } catch (Exception ex) {
                    Logger.getLogger(MateriaBean.class.getName()).log(Level.SEVERE, null, ex);
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
                }
            }else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Debe seleccionar el campo Programa", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/materias.xhtml");
    }
    
    public void eliminar(ActionEvent actionEvent){
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();
        estado = new Long(2);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblMateriasJpaController = 
                new TblMateriasJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblMaterias.setIdEstado(tblEstado);
        try {
            tblMateriasJpaController.edit(tblMaterias);
            tblMateriases.remove(tblMaterias);
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Eliminado Correctamente", "");
            setCodigoInterno("");
            setNombreMateria("");
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(MateriaBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        } catch (RollbackFailureException ex) {
            Logger.getLogger(MateriaBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        } catch (Exception ex) {
            Logger.getLogger(MateriaBean.class.getName()).log(Level.SEVERE, null, ex);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al eliminar", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/materias.xhtml");
    }
            
}
