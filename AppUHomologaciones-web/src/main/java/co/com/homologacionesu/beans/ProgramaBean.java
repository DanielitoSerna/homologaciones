package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.jpacontroller.TblEstadoJpaController;
import co.com.homologacionesu.jpacontroller.TblProgramasJpaController;
import co.com.homologacionesu.jpacontroller.TblUniversidadJpaController;
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
 * Objetivo: Administrar programas
 * @author dsernama
 */
@ManagedBean
@ViewScoped
public class ProgramaBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String codigoInterno;
    private String nombrePrograma;
    private Long estado;
    private Integer universidad;
    private TblEstadoJpaController tblEstadoJpaController;
    private TblUniversidadJpaController tblUniversidadJpaController;
    private TblProgramasJpaController tblProgramasJpaController;
    private TblProgramas tblProgramas;
    private List<TblProgramas> tblProgramases;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;
    private List<TblUniversidad> tblUniversidads;
    
    /**
     * 
     * @return 
     */
    public String getCodigoInterno() {
        return codigoInterno;
    }

    /**
     * 
     * @param codigoInterno 
     */
    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    /**
     * 
     * @return 
     */
    public String getNombrePrograma() {
        return nombrePrograma;
    }

    /**
     * 
     * @param nombrePrograma 
     */
    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
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
    public Integer getUniversidad() {
        return universidad;
    }

    /**
     * 
     * @param universidad 
     */
    public void setUniversidad(Integer universidad) {
        this.universidad = universidad;
    }

    /**
     * 
     * @return 
     */
    public TblProgramas getTblProgramas() {
        return tblProgramas;
    }

    /**
     * 
     * @param tblProgramas 
     */
    public void setTblProgramas(TblProgramas tblProgramas) {
        this.tblProgramas = tblProgramas;
    }

    /**
     * 
     * @return 
     */
    public List<TblProgramas> getTblProgramases() {
        return tblProgramases;
    }

    /**
     * 
     * @param tblProgramases 
     */
    public void setTblProgramases(List<TblProgramas> tblProgramases) {
        this.tblProgramases = tblProgramases;
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
    public List<TblUniversidad> getTblUniversidads() {
        return tblUniversidads;
    }

    /**
     * 
     * @param tblUniversidads 
     */
    public void setTblUniversidads(List<TblUniversidad> tblUniversidads) {
        this.tblUniversidads = tblUniversidads;
    }
    
    /**
     * Descripción: Método que carga la información al momento de ingresar a la
     * pantalla 
     */
    @PostConstruct
    public void init(){
        tblUniversidadJpaController = 
                new TblUniversidadJpaController(JPAFactory.getFACTORY());
        tblProgramasJpaController = 
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblUniversidads = tblUniversidadJpaController.findTblUniversidadEntities();
        tblProgramases = new ArrayList<>();
        List<TblProgramas> programases = tblProgramasJpaController.findTblProgramasEntities();
        
        for (TblProgramas programase : programases) {
            if(!programase.getIdEstado().getDescripcion().equals("INACTIVO")){
                tblProgramases.add(programase);
            }
        }
        
        setHabilitarCodigo(Boolean.TRUE);
        setHabilitarBoton(Boolean.FALSE);
    }
    
    /**
     * Descripción: Método que permite guardar la información
     */
    public void guardar(){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblUniversidadJpaController =
                new TblUniversidadJpaController(JPAFactory.getFACTORY());
        TblProgramas tblProgramasIn = new TblProgramas();
        if(universidad != null){
            TblUniversidad tblUniversidad = 
                    tblUniversidadJpaController.findTblUniversidad(universidad);
            if((codigoInterno != null && !codigoInterno.isEmpty())
                    &&(nombrePrograma != null && !nombrePrograma.isEmpty())
                    &&(universidad != null)){
                tblProgramasIn.setCodigoInterno(codigoInterno);
                tblProgramasIn.setNombrePrograma(nombrePrograma);
                tblProgramasIn.setIdEstado(tblEstado);
                tblProgramasIn.setIdUniversidad(tblUniversidad);
                setHabilitarCodigo(Boolean.TRUE);
                setHabilitarBoton(Boolean.FALSE);
                try {
                    tblProgramasJpaController.create(tblProgramasIn);
                    tblProgramases.add(tblProgramasIn);
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Creado Correctamente", "");
                    setCodigoInterno("");
                    setEstado(null);
                    setUniversidad(null);
                    setNombrePrograma("");
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(ProgramaBean.class.getName()).log(Level.SEVERE, null, ex);
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
                } catch (Exception ex) {
                    Logger.getLogger(ProgramaBean.class.getName()).log(Level.SEVERE, null, ex);
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al crear", "");
                }
            }else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Debe seleccionar el campo Universidad", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/programas.xhtml");
    }
    
    /**
     * Descripción: Método que permite seleccionar un registro
     */
    public void seleccionarObjeto() {
        nombrePrograma = (getTblProgramas().getNombrePrograma()!= null
                    ? getTblProgramas().getNombrePrograma() : "");
        codigoInterno = (getTblProgramas().getCodigoInterno()!= null
                    ? getTblProgramas().getCodigoInterno(): "");
        universidad = (getTblProgramas().getIdUniversidad().getIdUniversidad() != null
                    ? getTblProgramas().getIdUniversidad().getIdUniversidad() : 0);
        setHabilitarCodigo(Boolean.FALSE);
        setHabilitarBoton(Boolean.TRUE);
    }
    
    /**
     * Descripción: Método que permite modificar un registro anteriormente
     * seleccionado
     * @param actionEvent 
     */
    public void modificar(ActionEvent actionEvent){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblUniversidadJpaController =
                new TblUniversidadJpaController(JPAFactory.getFACTORY());
        TblProgramas tblProgramasIn = new TblProgramas();
        if(universidad != null){
            TblUniversidad tblUniversidad = 
                    tblUniversidadJpaController.findTblUniversidad(universidad);
            if((codigoInterno != null && !codigoInterno.isEmpty())
                    &&(nombrePrograma != null && !nombrePrograma.isEmpty())
                    &&(universidad != null)){
                tblProgramasIn.setCodigoInterno(codigoInterno);
                tblProgramasIn.setNombrePrograma(nombrePrograma);
                tblProgramasIn.setIdEstado(tblEstado);
                tblProgramasIn.setIdUniversidad(tblUniversidad);
                tblProgramasIn.setIdPrograma(tblProgramas.getIdPrograma());
                tblProgramasIn.setTblHomologacionList(new ArrayList<TblHomologacion>());
                tblProgramasIn.setTblMateriasList(new ArrayList<TblMaterias>());
                tblProgramasIn.setTblHomologacionList1(new ArrayList<TblHomologacion>());
                try {
                    tblProgramasJpaController.edit(tblProgramasIn);
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Modificado Correctamente", "");
                    setCodigoInterno("");
                    setEstado(null);
                    setUniversidad(null);
                    setNombrePrograma("");
                    setHabilitarCodigo(Boolean.TRUE);
                    setHabilitarBoton(Boolean.FALSE);
                    tblProgramases.remove(tblProgramas);
                    tblProgramases.add(tblProgramasIn);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(ProgramaBean.class.getName()).log(Level.SEVERE, null, ex);
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
                } catch (Exception ex) {
                    Logger.getLogger(ProgramaBean.class.getName()).log(Level.SEVERE, null, ex);
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error al modificar", "");
                }
            }else{
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Favor ingesar información en todos los campos", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Debe seleccionar el campo Universidad", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/programas.xhtml");
    }
    
    /**
     * Descripción: Método que permite eliminar un registro previamente 
     * seleccionado
     * @param actionEvent 
     */
    public void eliminar(ActionEvent actionEvent){
        FacesMessage msg = null;
        RequestContext context = RequestContext.getCurrentInstance();
        estado = new Long(2);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        tblProgramasJpaController =
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        
        tblProgramas.setIdEstado(tblEstado);
        try {
            tblProgramasJpaController.edit(tblProgramas);
            tblProgramases.remove(tblProgramas);
            setNombrePrograma("");
            setCodigoInterno("");
            setUniversidad(null);
            setEstado(null);
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
        context.addCallbackParam("view", "vw/programas.xhtml");
    }
}
