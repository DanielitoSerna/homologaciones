package co.com.homologacionesu.beans;

import co.com.homologacionesu.entidades.TblUsuario;
import co.com.homologacionesu.jpacontroller.TblUsuarioJpaController;
import co.com.homologacionesu.util.JPAFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -2152389656664659476L;
    private String nombre;
    private String clave;
    private boolean logeado = false;
    private TblUsuarioJpaController tblUsuarioJpaController;
    private boolean todo = false;

    /**
     * 
     * @return 
     */
    public boolean isTodo() {
        return todo;
    }

    /**
     * 
     * @param todo 
     */
    public void setTodo(boolean todo) {
        this.todo = todo;
    }

    /**
     * 
     * @return 
     */
    public boolean estaLogeado() {
        return logeado;
    }

    /**
     * 
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * 
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * Descripción: Método que permite ingresar a la aplicación con usuario y 
     * contraseña
     * @param actionEvent
     * @throws IOException 
     */
    public void login(ActionEvent actionEvent) throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        
        tblUsuarioJpaController = new TblUsuarioJpaController(JPAFactory.getFACTORY());
        if((nombre != null && !nombre.isEmpty()) 
                && (clave != null && !clave.isEmpty())){
            try{
                TblUsuario  tblUsuario = tblUsuarioJpaController.findTblUsuario(nombre, clave);
                if(tblUsuario != null){
                    if(tblUsuario.getCodRol().getNombreRol().equals("Administrador")){
                        logeado = true;
                        todo = true;
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@.", nombre);
                    }else{
                        logeado = true;
                        todo = false;
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@.", nombre);
                    }
                }else{
                    logeado = false;
                    todo = false;
                    msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Usuario y/o Contraseña inválidos. Por favor verifique.", "");
                }
            }catch(Exception ex){
                logeado = false;
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error iniciando sesión.", "");
            }
        }else{
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Usuario y contraseña no pueden ser vacíos.", "");
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("estaLogeado", logeado);
        if (logeado) {
            context.addCallbackParam("view", "vw/menu.xhtml");
        }
    }

    /**
     * Descripción: Método que permite cerrar sesión
     * @param actionEvent 
     */
    public void logout(ActionEvent actionEvent) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        logeado = false;
    }
    
    /**
     * Descripción: Método que permite cerrar sesión
     * @param actionEvent 
     */
    public void logoutPrueba(ActionEvent acitonEvent) {
        ExternalContext ctx
                = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath
                = ((ServletContext) ctx.getContext()).getContextPath();

            // Usar el contexto de JSF para invalidar la sesión,
            // NO EL DE SERVLETS (nada de HttpServletRequest)
            ((HttpSession) ctx.getSession(false)).invalidate();

        try {
            // Redirección de nuevo con el contexto de JSF,
            // si se usa una HttpServletResponse fallará.
            // Sin embargo, como ya está fuera del ciclo de vida 
            // de JSF se debe usar la ruta completa -_-U
            ctx.redirect(ctxPath + "/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
