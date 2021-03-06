package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblHomologacion;
import co.com.homologacionesu.entidades.TblMaterias;
import co.com.homologacionesu.entidades.TblProgramas;
import co.com.homologacionesu.entidades.TblUniversidad;
import co.com.homologacionesu.jpacontroller.TblHomologacionJpaController;
import co.com.homologacionesu.jpacontroller.TblMateriasJpaController;
import co.com.homologacionesu.jpacontroller.TblProgramasJpaController;
import co.com.homologacionesu.jpacontroller.TblUniversidadJpaController;
import co.com.homologacionesu.jpacontroller.exceptions.RollbackFailureException;
import co.com.homologacionesu.util.JPAFactory;
import co.com.homologacionesu.util.JSFUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;

/**
 * Objetivo: Gestionar homologación referente a la información previamente 
 * parametrizada y teniendo en cuenta criterios de búsqueda.
 * @author dsernama
 */
@ManagedBean
@ViewScoped
public class HomologacionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer universidadOrigen;
    private Integer universidadDestino;
    private Integer programaOrigen;
    private Integer programaDestino;
    private Integer materiaOrigen;
    private Integer materiaDestino;
    private List<TblUniversidad> tblUniversidads;
    private List<TblProgramas> tblProgramases;
    private List<TblProgramas> tblProgramases2;
    private List<TblMaterias> tblMateriases;
    private List<TblMaterias> tblMateriases2;
    private TblUniversidadJpaController tblUniversidadJpaController;
    private TblMateriasJpaController tblMateriasJpaController;
    private TblProgramasJpaController tblProgramasJpaController;
    private TblHomologacionJpaController tblHomologacionJpaController;
    private List<TblHomologacion> tblHomologacions;
    private Boolean habilitarCodigo;
    private Boolean habilitarBoton;
    private TblHomologacion tblHomologacion;

    /**
     * 
     * @return 
     */
    public List<TblProgramas> getTblProgramases2() {
        return tblProgramases2;
    }

    /**
     * 
     * @param tblProgramases2 
     */
    public void setTblProgramases2(List<TblProgramas> tblProgramases2) {
        this.tblProgramases2 = tblProgramases2;
    }

    /**
     * 
     * @return 
     */
    public List<TblMaterias> getTblMateriases2() {
        return tblMateriases2;
    }

    /**
     * 
     * @param tblMateriases2 
     */
    public void setTblMateriases2(List<TblMaterias> tblMateriases2) {
        this.tblMateriases2 = tblMateriases2;
    }

    /**
     * 
     * @return 
     */
    public TblHomologacion getTblHomologacion() {
        return tblHomologacion;
    }

    /**
     * 
     * @param tblHomologacion 
     */
    public void setTblHomologacion(TblHomologacion tblHomologacion) {
        this.tblHomologacion = tblHomologacion;
    }

    /**
     * 
     * @return 
     */
    public Integer getUniversidadOrigen() {
        return universidadOrigen;
    }

    /**
     * 
     * @param universidadOrigen 
     */
    public void setUniversidadOrigen(Integer universidadOrigen) {
        this.universidadOrigen = universidadOrigen;
    }

    /**
     * 
     * @return 
     */
    public Integer getUniversidadDestino() {
        return universidadDestino;
    }

    /**
     * 
     * @param universidadDestino 
     */
    public void setUniversidadDestino(Integer universidadDestino) {
        this.universidadDestino = universidadDestino;
    }

    /**
     * 
     * @return 
     */
    public Integer getProgramaOrigen() {
        return programaOrigen;
    }

    /**
     * 
     * @param programaOrigen 
     */
    public void setProgramaOrigen(Integer programaOrigen) {
        this.programaOrigen = programaOrigen;
    }

    /**
     * 
     * @return 
     */
    public Integer getProgramaDestino() {
        return programaDestino;
    }

    /**
     * 
     * @param programaDestino 
     */
    public void setProgramaDestino(Integer programaDestino) {
        this.programaDestino = programaDestino;
    }

    /**
     * 
     * @return 
     */
    public Integer getMateriaOrigen() {
        return materiaOrigen;
    }

    /**
     * 
     * @param materiaOrigen 
     */
    public void setMateriaOrigen(Integer materiaOrigen) {
        this.materiaOrigen = materiaOrigen;
    }

    /**
     * 
     * @return 
     */
    public Integer getMateriaDestino() {
        return materiaDestino;
    }

    /**
     * 
     * @param materiaDestino 
     */
    public void setMateriaDestino(Integer materiaDestino) {
        this.materiaDestino = materiaDestino;
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
    public List<TblMaterias> getTblMateriases() {
        return tblMateriases;
    }

    /**
     * 
     * @param tblMateriases 
     */
    public void setTblMateriases(List<TblMaterias> tblMateriases) {
        this.tblMateriases = tblMateriases;
    }

    /**
     * 
     * @return 
     */
    public List<TblHomologacion> getTblHomologacions() {
        return tblHomologacions;
    }

    /**
     * 
     * @param tblHomologacions 
     */
    public void setTblHomologacions(List<TblHomologacion> tblHomologacions) {
        this.tblHomologacions = tblHomologacions;
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
     * Descripción: Método que permite cargar una lista de información 
     * previamente guardada, dando la posibilidad de que sea editada o eliminada
     * según el caso
     */
    @PostConstruct
    public void init() {
        tblUniversidadJpaController
                = new TblUniversidadJpaController(JPAFactory.getFACTORY());
        tblHomologacionJpaController
                = new TblHomologacionJpaController(JPAFactory.getFACTORY());
        tblUniversidads = tblUniversidadJpaController.findTblUniversidadEntities();
        tblHomologacions = new ArrayList<>();
        List<TblHomologacion> homologacions = tblHomologacionJpaController
                .findTblHomologacionEntities();
        setHabilitarBoton(Boolean.FALSE);
        if (homologacions != null) {
            for (TblHomologacion homologacion : homologacions) {
                tblHomologacions.add(homologacion);
            }
        }
    }

    /**
     * Descripción: Método que permite guardar la información 
     */
    public void guardar() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        tblHomologacionJpaController
                = new TblHomologacionJpaController(JPAFactory.getFACTORY());
        tblUniversidadJpaController
                = new TblUniversidadJpaController(JPAFactory.getFACTORY());
        tblMateriasJpaController
                = new TblMateriasJpaController(JPAFactory.getFACTORY());
        tblProgramasJpaController
                = new TblProgramasJpaController(JPAFactory.getFACTORY());
        TblHomologacion tblHomologacionIn = new TblHomologacion();
        if ((universidadDestino != null) && (universidadOrigen != null)
                && (programaDestino != null) && (programaOrigen != null)
                && (materiaDestino != null) && (materiaOrigen != null)) {
            TblMaterias tblMateriasOrigen = tblMateriasJpaController
                    .findTblMaterias(materiaOrigen);
            TblMaterias tblMateriasDestino = tblMateriasJpaController
                    .findTblMaterias(materiaDestino);
            TblUniversidad tblUniversidadOrigen = tblUniversidadJpaController
                    .findTblUniversidad(universidadOrigen);
            TblUniversidad tblUniversidadDestino = tblUniversidadJpaController
                    .findTblUniversidad(universidadDestino);
            TblProgramas tblProgramasOrigen = tblProgramasJpaController
                    .findTblProgramas(programaOrigen);
            TblProgramas tblProgramasDestino = tblProgramasJpaController
                    .findTblProgramas(programaDestino);
            tblHomologacionIn.setMateriaDestino(tblMateriasDestino);
            tblHomologacionIn.setMateriaOrigen(tblMateriasOrigen);
            tblHomologacionIn.setUniversidadOrigen(tblUniversidadOrigen);
            tblHomologacionIn.setUniversidadDestino(tblUniversidadDestino);
            tblHomologacionIn.setProgramaOrigen(tblProgramasOrigen);
            tblHomologacionIn.setProgramaDestino(tblProgramasDestino);
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
            try {
                tblHomologacionJpaController.create(tblHomologacionIn);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Creado Correctamente", "");
                tblHomologacions.add(tblHomologacionIn);
                setMateriaDestino(null);
                setMateriaOrigen(null);
                setProgramaOrigen(null);
                setProgramaDestino(null);
                setUniversidadOrigen(null);
                setUniversidadDestino(null);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(HomologacionBean.class.getName()).log(Level.SEVERE, null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al crear", "");
            } catch (Exception ex) {
                Logger.getLogger(HomologacionBean.class.getName()).log(Level.SEVERE, null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al crear", "");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Favor ingesar información en todos los campos", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/homologacion.xhtml");
    }

    /**
     * Descripción: Método que permite seleccionar un objeto para cargarlo
     * en los diferentes componentes (texto,listas,radio,check)
     */
    public void seleccionarObjeto() {
        universidadOrigen = (getTblHomologacion().getUniversidadOrigen().getIdUniversidad() != null
                ? getTblHomologacion().getUniversidadOrigen().getIdUniversidad() : 0);
        universidadDestino = (getTblHomologacion().getUniversidadDestino().getIdUniversidad() != null
                ? getTblHomologacion().getUniversidadDestino().getIdUniversidad() : 0);
        programaOrigen = (getTblHomologacion().getProgramaOrigen().getIdPrograma() != null
                ? getTblHomologacion().getProgramaOrigen().getIdPrograma() : 0);
        programaDestino = (getTblHomologacion().getProgramaDestino().getIdPrograma() != null
                ? getTblHomologacion().getProgramaDestino().getIdPrograma() : 0);
        materiaOrigen = (getTblHomologacion().getMateriaOrigen().getIdMateria() != null
                ? getTblHomologacion().getMateriaOrigen().getIdMateria() : 0);
        materiaDestino = (getTblHomologacion().getMateriaDestino().getIdMateria() != null
                ? getTblHomologacion().getMateriaDestino().getIdMateria() : 0);
        filtrarProgramaUniDestino();
        filtrarProgramaUniOrigen();
        filtrarMateriaProgDestino();
        filtrarMateriaProgOrigen();
        setHabilitarCodigo(Boolean.FALSE);
        setHabilitarBoton(Boolean.TRUE);
    }

    /**
     * Descripción: Método que permite modificar un objeto previamente
     * seleccionado
     * @param actionEvent 
     */
    public void modificar(ActionEvent actionEvent) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        tblHomologacionJpaController
                = new TblHomologacionJpaController(JPAFactory.getFACTORY());
        tblUniversidadJpaController
                = new TblUniversidadJpaController(JPAFactory.getFACTORY());
        tblMateriasJpaController
                = new TblMateriasJpaController(JPAFactory.getFACTORY());
        tblProgramasJpaController
                = new TblProgramasJpaController(JPAFactory.getFACTORY());
        TblHomologacion tblHomologacionIn = new TblHomologacion();
        if ((universidadDestino != null) && (universidadOrigen != null)
                && (programaDestino != null) && (programaOrigen != null)
                && (materiaDestino != null) && (materiaOrigen != null)) {
            TblMaterias tblMateriasOrigen = tblMateriasJpaController
                    .findTblMaterias(materiaOrigen);
            TblMaterias tblMateriasDestino = tblMateriasJpaController
                    .findTblMaterias(materiaDestino);
            TblUniversidad tblUniversidadOrigen = tblUniversidadJpaController
                    .findTblUniversidad(universidadOrigen);
            TblUniversidad tblUniversidadDestino = tblUniversidadJpaController
                    .findTblUniversidad(universidadDestino);
            TblProgramas tblProgramasOrigen = tblProgramasJpaController
                    .findTblProgramas(programaOrigen);
            TblProgramas tblProgramasDestino = tblProgramasJpaController
                    .findTblProgramas(programaDestino);
            tblHomologacionIn.setMateriaDestino(tblMateriasDestino);
            tblHomologacionIn.setMateriaOrigen(tblMateriasOrigen);
            tblHomologacionIn.setProgramaOrigen(tblProgramasOrigen);
            tblHomologacionIn.setProgramaDestino(tblProgramasDestino);
            tblHomologacionIn.setUniversidadOrigen(tblUniversidadOrigen);
            tblHomologacionIn.setUniversidadDestino(tblUniversidadDestino);
            tblHomologacionIn.setIdHomologa(tblHomologacion.getIdHomologa());

            try {
                tblHomologacionJpaController.edit(tblHomologacionIn);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Modificado Correctamente", "");
                tblHomologacions.remove(tblHomologacion);
                tblHomologacions.add(tblHomologacionIn);
                setMateriaDestino(null);
                setMateriaOrigen(null);
                setProgramaOrigen(null);
                setProgramaDestino(null);
                setUniversidadOrigen(null);
                setUniversidadDestino(null);
                setHabilitarCodigo(Boolean.TRUE);
                setHabilitarBoton(Boolean.FALSE);
            } catch (RollbackFailureException ex) {
                Logger.getLogger(HomologacionBean.class.getName()).log(Level.SEVERE, null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al modificar", "");
            } catch (Exception ex) {
                Logger.getLogger(HomologacionBean.class.getName()).log(Level.SEVERE, null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al modificar", "");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Favor ingesar información en todos los campos", "");
        }

        context.addCallbackParam("view", "vw/homologacion.xhtml");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Descripción: Método que permite buscar registros
     */
    public void filtrarProgramaUniOrigen() {
        RequestContext context = RequestContext.getCurrentInstance();
        tblProgramasJpaController
                = new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblUniversidadJpaController
                = new TblUniversidadJpaController(JPAFactory.getFACTORY());
        TblUniversidad tblUniversidad2 = tblUniversidadJpaController
                .findTblUniversidad(universidadOrigen);
        tblProgramases = new ArrayList<>();
        tblProgramases = tblProgramasJpaController.consultarPorUniversidad(tblUniversidad2);
        context.addCallbackParam("view", "vw/homologacion.xhtml");
    }

    /**
     * Descripción: Método que permite buscar registros
     */
    public void filtrarProgramaUniDestino() {
        RequestContext context = RequestContext.getCurrentInstance();
        tblProgramasJpaController
                = new TblProgramasJpaController(JPAFactory.getFACTORY());
        tblUniversidadJpaController
                = new TblUniversidadJpaController(JPAFactory.getFACTORY());
        TblUniversidad tblUniversidad2 = tblUniversidadJpaController
                .findTblUniversidad(universidadDestino);
        tblProgramases2 = new ArrayList<>();
        tblProgramases2 = tblProgramasJpaController.consultarPorUniversidad(tblUniversidad2);
        context.addCallbackParam("view", "vw/homologacion.xhtml");
    }

    /**
     * Descripción: Método que permite buscar registros
     */
    public void filtrarMateriaProgOrigen() {
        RequestContext context = RequestContext.getCurrentInstance();
        tblMateriasJpaController
                = new TblMateriasJpaController(JPAFactory.getFACTORY());
        tblProgramasJpaController
                = new TblProgramasJpaController(JPAFactory.getFACTORY());
        TblProgramas tblProgramas = tblProgramasJpaController.findTblProgramas(programaOrigen);
        tblMateriases = new ArrayList<>();
        tblMateriases = tblMateriasJpaController.consultarPorpPrograma(tblProgramas);
        context.addCallbackParam("view", "vw/homologacion.xhtml");
    }

    /**
     * Descripción: Método que permite buscar registros
     */
    public void filtrarMateriaProgDestino() {
        RequestContext context = RequestContext.getCurrentInstance();
        tblMateriasJpaController
                = new TblMateriasJpaController(JPAFactory.getFACTORY());
        tblProgramasJpaController
                = new TblProgramasJpaController(JPAFactory.getFACTORY());
        TblProgramas tblProgramas = tblProgramasJpaController.findTblProgramas(programaDestino);
        tblMateriases2 = new ArrayList<>();
        tblMateriases2 = tblMateriasJpaController.consultarPorpPrograma(tblProgramas);
        context.addCallbackParam("view", "vw/homologacion.xhtml");
    }

    /**
     * Descripción: 
     * @param i
     * @return 
     */
    public List<TblHomologacion> getTblHomologacions(int i) {
        if (i == 1) {
            buscarHomologaciones();
        }
        return tblHomologacions;
    }

    /**
     * Descripción:. 
     */
    public void buscar() {
        RequestContext context = RequestContext.getCurrentInstance();
        setHabilitarBoton(Boolean.TRUE);
        context.addCallbackParam("view", "vw/Busqueda.xhtml");
    }

    /**
     * Descripción: Método que permite buscar una homologación con los 
     * parámetros anteriormente seleccionados
     */
    public void buscarHomologaciones() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        tblHomologacionJpaController
                = new TblHomologacionJpaController(JPAFactory.getFACTORY());
        tblUniversidadJpaController
                = new TblUniversidadJpaController(JPAFactory.getFACTORY());
        tblMateriasJpaController
                = new TblMateriasJpaController(JPAFactory.getFACTORY());
        tblProgramasJpaController
                = new TblProgramasJpaController(JPAFactory.getFACTORY());
        TblHomologacion tblHomologacionIn = new TblHomologacion();
        if ((universidadDestino != null) && (universidadOrigen != null)
                && (programaDestino != null) && (programaOrigen != null)) {
            TblUniversidad tblUniversidadOrigen = tblUniversidadJpaController
                    .findTblUniversidad(universidadOrigen);
            TblUniversidad tblUniversidadDestino = tblUniversidadJpaController
                    .findTblUniversidad(universidadDestino);
            TblProgramas tblProgramasOrigen = tblProgramasJpaController
                    .findTblProgramas(programaOrigen);
            TblProgramas tblProgramasDestino = tblProgramasJpaController
                    .findTblProgramas(programaDestino);

            tblHomologacionIn.setUniversidadOrigen(tblUniversidadOrigen);
            tblHomologacionIn.setUniversidadDestino(tblUniversidadDestino);
            tblHomologacionIn.setProgramaOrigen(tblProgramasOrigen);
            tblHomologacionIn.setProgramaDestino(tblProgramasDestino);
            setHabilitarCodigo(Boolean.TRUE);
            setHabilitarBoton(Boolean.FALSE);
            try {
                tblHomologacions.clear();
                tblHomologacions.addAll(tblHomologacionJpaController
                        .findTblHomologacionEntitiesBusqueda(true, -1, -1, tblHomologacionIn));
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "paso", "");
            } catch (Exception ex) {
                Logger.getLogger(HomologacionBean.class.getName()).log(Level.SEVERE, null, ex);
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al crear", "");
            }
        } else {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Favor ingesar información en todos los campos", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("view", "vw/Busqueda.xhtml");
    }

    /**
     * Descripción: Método que permite generar archivo excel como artefacto 
     * para tener en cuenta en el proceso de homologación
     * @param actionEvent 
     */
    public void generarReporte(ActionEvent actionEvent) {
        setHabilitarBoton(Boolean.TRUE);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reporte");
        XSSFCellStyle style = workbook.createCellStyle();

        Row encabezado = sheet.createRow(0);
        Cell celda1 = encabezado.createCell(0);
        celda1.setCellValue("Universidad Origen");
        Cell celda2 = encabezado.createCell(1);
        celda2.setCellValue("Universidad Destino");
        Cell celda3 = encabezado.createCell(2);
        celda3.setCellValue("Programa Origen");
        Cell celda4 = encabezado.createCell(3);
        celda4.setCellValue("Programa Destino");
        Cell celda5 = encabezado.createCell(4);
        celda5.setCellValue("Materia Origen");
        Cell celda6 = encabezado.createCell(5);
        celda6.setCellValue("Materia Destino");

        for (int i = 0; i < tblHomologacions.size(); i++) {
            Row fila = sheet.createRow(i + 1);

            TblHomologacion tblHomologacion2 = tblHomologacions.get(i);

            Cell dato1 = fila.createCell(0);
            dato1.setCellStyle(style);
            dato1.setCellValue(tblHomologacion2.getUniversidadOrigen().getNombreUniversidad());

            Cell dato2 = fila.createCell(1);
            dato2.setCellStyle(style);
            dato2.setCellValue(tblHomologacion2.getUniversidadDestino().getNombreUniversidad());

            Cell dato3 = fila.createCell(2);
            dato3.setCellStyle(style);
            dato3.setCellValue(tblHomologacion2.getProgramaOrigen().getNombrePrograma());

            Cell dato4 = fila.createCell(3);
            dato4.setCellStyle(style);
            dato4.setCellValue(tblHomologacion2.getProgramaDestino().getNombrePrograma());

            Cell dato5 = fila.createCell(4);
            dato5.setCellStyle(style);
            dato5.setCellValue(tblHomologacion2.getMateriaOrigen().getNombreMateria());

            Cell dato6 = fila.createCell(5);
            dato6.setCellStyle(style);
            dato6.setCellValue(tblHomologacion2.getMateriaDestino().getNombreMateria());

            java.util.Date fecha = JSFUtil.sumarRestarDiasFecha(new Date(), 0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
            String s = dateFormat.format(fecha);
            int anio = Integer.parseInt(s.substring(0, 2));
            int mes = Integer.parseInt(s.substring(3, 5));
            int dia = Integer.parseInt(s.substring(6, 8));
            String ruta = "C:\\Users\\HOMOLOGACION - " + anio + "" + mes + "" + dia + ".xlsx";
            File archivo = new File(ruta);
            FileOutputStream out;
            try {
                out = new FileOutputStream(archivo);
                workbook.write(out);
                out.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HomologacionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HomologacionBean.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle style1;
        HSSFFont headerFont;
        headerFont = JSFUtil.createFont(HSSFColor.WHITE.index,
                (short) 12, true, wb);
        style1 = JSFUtil.createStyle(headerFont, HSSFCellStyle.ALIGN_CENTER,
                HSSFColor.RED.index, true, HSSFColor.WHITE.index, wb);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(style1);
        }
    }

}
