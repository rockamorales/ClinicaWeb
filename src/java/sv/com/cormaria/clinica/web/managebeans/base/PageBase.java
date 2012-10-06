package sv.com.cormaria.clinica.web.managebeans.base;

import java.io.Serializable;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import sv.com.cormaria.clinica.web.managebeans.sessions.ClinicaSessionBean;


public class PageBase implements Serializable{
    public ClinicaSessionBean getSessionBean(){
      ClinicaSessionBean vuiSession = (ClinicaSessionBean) this.getBean("#{clinicaSessionBean}", ClinicaSessionBean.class);
      return vuiSession;
    }
    
    public Object getBean(String expression, Class<?> type){
      FacesContext ctx = FacesContext.getCurrentInstance();
      Application app = ctx.getApplication();
      return app.getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(),expression,type).getValue(FacesContext.getCurrentInstance().getELContext());
    }
    
    public void setValue(String expression, Class<?> type, Object value){
    	  FacesContext ctx = FacesContext.getCurrentInstance();
    	  Application app = ctx.getApplication();
    	  app.getExpressionFactory().createValueExpression(FacesContext.getCurrentInstance().getELContext(),expression,type).setValue(FacesContext.getCurrentInstance().getELContext(), value);
    }
    
    public void addError(String message, String detail){
    	FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
    	FacesContext.getCurrentInstance().addMessage(null, message1);
    }
    public void addWarn(String message, String detail){
    	FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_WARN, message, detail);
    	FacesContext.getCurrentInstance().addMessage(null, message1);
    }
    public void addInfo(String message, String detail){
    	FacesMessage message1 = new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail);
    	FacesContext.getCurrentInstance().addMessage(null, message1);
    }

    public void renderResponse(){
    	FacesContext.getCurrentInstance().renderResponse();
    }
    
}
