package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblEstado;
import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblPlanPrograma;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.jpacontroller.TblEstadoJpaController;
import co.com.homologacionesu.jpacontroller.TblPlanProgramaJpaController;
import co.com.homologacionesu.jpacontroller.TblProgramasJpaController;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import co.com.homologacionesu.util.JPAFactory;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.primefaces.event.SelectEvent;

/**
 * Objetivo: Administrar las acciones del módulo de planes
 * @author daserna
 */
@ManagedBean
@ViewScoped
public class PlanesBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombrePlan;
    private Date fechaInicio;
    private Date fechaVigencia;
    private Integer programa;
    private Long estado;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;
    private List<TblProgramas> tblProgramases;
    private TblProgramasJpaController tblProgramasJpaController;
    private TblEstadoJpaController tblEstadoJpaController;
    private TblPlanProgramaJpaController tblPlanProgramaJpaController;
    private List<TblPlanPrograma> tblPlanProgramas;

    /**
     * 
     * @return 
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * 
     * @param codigo 
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * 
     * @return 
     */
    public String getNombrePlan() {
        return nombrePlan;
    }

    /**
     * 
     * @param nombrePlan 
     */
    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    /**
     * 
     * @return 
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * 
     * @param fechaInicio 
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * 
     * @return 
     */
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    /**
     * 
     * @param fechaVigencia 
     */
    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    /**
     * 
     * @return 
     */
    public Integer getPrograma() {
        return programa;
    }

    /**
     * 
     * @param programa 
     */
    public void setPrograma(Integer programa) {
        this.programa = programa;
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
     * Descripción: Método que permite captura la fecha del calendario
     * @param event 
     */
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
    
    /**
     * Descripción: Método que permite cargar información una vez se ingrese a 
     * la vista
     */
    @PostConstruct
    public void init(){
        tblProgramasJpaController = 
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblProgramases = tblProgramasJpaController.findTblProgramasEntities();
        tblPlanProgramaJpaController =
                new TblPlanProgramaJpaController(JPAFactory.getFACTORY());
        
        List<TblPlanPrograma> planProgramas = tblPlanProgramaJpaController.findTblPlanProgramaEntities();
        tblPlanProgramas = new ArrayList<>();
        for (TblPlanPrograma planPrograma : planProgramas) {
            if(!planPrograma.getIdEstado().getDescripcion().equals("INACTIVO")){
                tblPlanProgramas.add(planPrograma);
            }
        }
        
        setHabilitarCodigo(Boolean.TRUE);
        setHabilitarBoton(Boolean.FALSE);
    }
    
    /**
     * Descripción: Método que permite guardar información del plan
     * @param actionEvent 
     */
    public void guardar(ActionEvent actionEvent){
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        estado = new Long(1);
        tblEstadoJpaController = 
                new TblEstadoJpaController(JPAFactory.getFACTORY());
        TblEstado tblEstado = tblEstadoJpaController.findTblEstado(estado);
        tblProgramasJpaController =
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblPlanProgramaJpaController =
                new TblPlanProgramaJpaController(JPAFactory.getFACTORY());
        TblPlanPrograma tblPlanPrograma = new TblPlanPrograma();
        
        if(programa != null){
            TblProgramas tblProgramas = tblProgramasJpaController.findTblProgramas(programa);
            if(codigo != null && !codigo.isEmpty() && nombrePlan != null 
                    && !nombrePlan.isEmpty() && fechaInicio != null 
                    && fechaVigencia != null){
                tblPlanPrograma.setCodigoPlan(codigo);
                tblPlanPrograma.setNombrePlan(nombrePlan);
                tblPlanPrograma.setFechaInicio(fechaInicio);
                tblPlanPrograma.setFechaVigencia(fechaVigencia);
                tblPlanPrograma.setIdEstado(tblEstado);
                tblPlanPrograma.setIdPrograma(tblProgramas);
                
                try {
                    tblPlanProgramaJpaController.create(tblPlanPrograma);
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                        "Creado Correctamente", "");
                    setCodigo("");
                    setNombrePlan("");
                    setPrograma(null);
                    setFechaInicio(null);
                    setFechaVigencia(null);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(PlanesBean.class.getName()).log(Level.SEVERE, null, ex);
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
        context.addCallbackParam("view", "vw/planes.xhtml");
    }
}
