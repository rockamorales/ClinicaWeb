/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.colecturia;

import java.util.ArrayList;
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
import sv.com.cormaria.servicios.entidades.colecturia.TblComprobanteDonacion;
import sv.com.cormaria.servicios.facades.colecturia.TblComprobanteDonacionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class FrmTblDonacionesVoluntarias extends PageBase {
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

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            cblComprobanteDonacion = (TblComprobanteDonacion) table.getRowData();
            cblComprobanteDonacionFacade.remove(cblComprobanteDonacion);
            cblComprobanteDonacionList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void buscar(ActionEvent ae){
        ComprobanteDonacionEmitidosDataModel dataModel = (ComprobanteDonacionEmitidosDataModel) this.getBean("#{comprobanteDonacionEmitidosDataModel}", ComprobanteDonacionEmitidosDataModel.class);
        dataModel.clear();
    }
}
