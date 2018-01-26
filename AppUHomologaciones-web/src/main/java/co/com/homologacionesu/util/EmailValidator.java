package co.com.homologacionesu.util;

import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.primefaces.validate.ClientValidator;

/**
 * Objetivo: Validar email
 * @author dsernama
 */
@FacesValidator("custom.emailValidator")
public class EmailValidator implements Validator, ClientValidator {
 
    private Pattern pattern;
  
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  
    /**
     * 
     */
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
 
    /**
     * Descripción. Validar formato de correo electrónico
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException 
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null) {
            return;
        }
         
        if(!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de validación", 
                        value + " No es un correo válido. "));
        }
    }
 
    /**
     * 
     * @return 
     */
    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }
 
    /**
     * 
     * @return 
     */
    @Override
    public String getValidatorId() {
        return "custom.emailValidator";
    }
     
}