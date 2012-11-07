/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.colecturia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ComprobanteDonacionEmitidosDataModel;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ComprobanteDonacionPagadosDataModel;
import sv.com.cormaria.servicios.entidades.colecturia.TblComprobanteDonacion;
import sv.com.cormaria.servicios.facades.colecturia.TblComprobanteDonacionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class FrmAnulacionTblDonacionVoluntaria extends PageBase {
    private TblComprobanteDonacion cblComprobanteDonacion = new TblComprobanteDonacion();
    @EJB
    private TblComprobanteDonacionFacadeLocal cblComprobanteDonacionFacade;
    
    private List<TblComprobanteDonacion> cblComprobanteDonacionList= new ArrayList<TblComprobanteDonacion>();

    public TblComprobanteDonacion getTblComprobanteDonacion() {
        return cblComprobanteDonacion;
    }

    public void setTblComprobanteDonacion(TblComprobanteDonacion cblComprobanteDonacion) {
        this.cblComprobanteDonacion = cblComprobanteDonacion;
    }

    public List<TblComprobanteDonacion> getTblComprobanteDonacionList() {
        if (cblComprobanteDonacionList.isEmpty()){
          try{
            cblComprobanteDonacionList = cblComprobanteDonacionFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return cblComprobanteDonacionList;
    }

    public void setTblComprobanteDonacionList(List<TblComprobanteDonacion> cblComprobanteDonacionList) {
        this.cblComprobanteDonacionList = cblComprobanteDonacionList;
    }
    
    public Date getTodayStartDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public Date getTodayEndDate(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            cblComprobanteDonacion = (TblComprobanteDonacion) table.getRowData();
            cblComprobanteDonacionFacade.anularComprobante(cblComprobanteDonacion.getNumComDonacion());
            ComprobanteDonacionPagadosDataModel dataModel = (ComprobanteDonacionPagadosDataModel) this.getBean("#{comprobanteDonacionPagadosDataModel}", ComprobanteDonacionPagadosDataModel.class);
            dataModel.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void buscar(ActionEvent ae){
        ComprobanteDonacionPagadosDataModel dataModel = (ComprobanteDonacionPagadosDataModel) this.getBean("#{comprobanteDonacionPagadosDataModel}", ComprobanteDonacionEmitidosDataModel.class);
        dataModel.clear();
    }
}
