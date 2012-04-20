package sv.com.cormaria.clinica.web.managebeans.consultasmedicas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;

/**
 *
 * @author GERARDO
 */
@ManagedBean
@RequestScoped
public class FrmSigVitales extends PageBase {
private TblConsultas tblConsultas = new TblConsultas();
    @EJB
    private TblConsultasFacadeLocal tblConsultasFacade;
    
    private List<TblConsultas> tblConsultasList= new ArrayList<TblConsultas>();

    public TblConsultas getTblConsultas() {
        return tblConsultas;
    }

    public void setTblConsultas(TblConsultas tblConsultas) {
        this.tblConsultas = tblConsultas;
    }

    public List<TblConsultas> getTblConsultasList() {
        if (tblConsultasList.isEmpty()){
          try{
            tblConsultasList = tblConsultasFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return tblConsultasList;
    }

    public void setTblConsultasList(List<TblConsultas> tblConsultasList) {
        this.tblConsultasList = tblConsultasList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblConsultas = (TblConsultas) table.getRowData();
            tblConsultasFacade.remove(tblConsultas);
            tblConsultasList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    /** Creates a new instance of FrmSigVitales */
    public FrmSigVitales() {
    }
}
