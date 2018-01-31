package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.jpacontroller.TblProgramasJpaController;
import co.com.homologacionesu.util.JPAFactory;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 * Objetivo: Administrar las acciones del módulo de planes
 * @author daserna
 */
@ManagedBean
@ViewScoped
public class PlanesBean implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Long codigo;
    private String nombrePlan;
    private Date fechaInicio;
    private Date fechaVigencia;
    private Integer programa;
    private Long estado;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;
    private List<TblProgramas> tblProgramases;
    private TblProgramasJpaController tblProgramasJpaController;

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
    
    @PostConstruct
    public void init(){
        tblProgramasJpaController = 
                new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblProgramases = tblProgramasJpaController.findTblProgramasEntities();
        
        setHabilitarCodigo(Boolean.TRUE);
        setHabilitarBoton(Boolean.FALSE);
    }
}
