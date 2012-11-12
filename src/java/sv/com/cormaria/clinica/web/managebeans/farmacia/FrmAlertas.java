package sv.com.cormaria.clinica.web.managebeans.farmacia;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.event.ActionEvent;

import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.farmacia.TblHistorialAlerta;
import sv.com.cormaria.servicios.facades.farmacia.TblHistorialAlertaFacadeLocal;


@ManagedBean
@ViewScoped
public class FrmAlertas extends PageBase{
    @EJB
    TblHistorialAlertaFacadeLocal alertaLocal;

    private List<TblHistorialAlerta> alertasList = new ArrayList<TblHistorialAlerta>();

    private HtmlInputText alertasid = new HtmlInputText();
    private Long selectedAlertaId;
    private String filtroAlertas = "todos";

    public FrmAlertas(){
    }
    
    public Long getSelectedAlertaId() {
        return selectedAlertaId;
    }

    public String getFiltroAlertas() {
        return filtroAlertas;
    }

    public void setFiltroAlertas(String filtroAlertas) {
        this.filtroAlertas = filtroAlertas;
    }

    public void setSelectedAlertaId(Long selectedAlertaId) {
        this.selectedAlertaId = selectedAlertaId;
    }

    public HtmlInputText getAlertasid() {
        return alertasid;
    }

    public void setAlertasid(HtmlInputText alertasid) {
        this.alertasid = alertasid;
    }
    
    public void refrescarAlertas(){
        this.alertasList.clear();
    }

    public List<TblHistorialAlerta> getAlertasList() {
        if (alertasList.isEmpty()){
            try{
                if(filtroAlertas.equals("todos")){
                   alertasList = alertaLocal.findAll();
                }else if(filtroAlertas.equals("activos")){
                   alertasList = alertaLocal.findActive();
                 }else{alertasList = alertaLocal.findInactive();
                  }
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return alertasList;
    }

    public void setAlertasList(List<TblHistorialAlerta> alertasList) {
        this.alertasList = alertasList;
    }	

    public void deactive(ActionEvent ae){
        try{
            System.out.println("Desactivando Alerta");
            UIDataTable datatable = (UIDataTable)ae.getComponent().getParent().getParent().getParent();
            TblHistorialAlerta alertaData = (TblHistorialAlerta)datatable.getRowData();
            System.out.println("alertaData: "+alertaData.getNumAlerta());
            alertaLocal.deactive(alertaData);
            this.getAlertasList().clear();
            this.addInfo("El registro ha sido desactivado", "El registro ha sido desactivado");
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
}