/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.administracion;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.RequisicionesDataModel;
import sv.com.cormaria.servicios.criteria.RequisicionesSearchCriteria;
import sv.com.cormaria.servicios.entidades.administracion.TblRequisiciones;
import sv.com.cormaria.servicios.entidades.catalogos.CatAreas;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoRequisicion;
import sv.com.cormaria.servicios.facades.administracion.TblRequisicionesFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatAreasFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoRequisicionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean(name = "frmTblRequisiciones")
@ViewScoped
public class FrmTblRequisiciones extends PageBase {
    private TblRequisiciones tblRequisiciones = new TblRequisiciones();

    @EJB
    private TblRequisicionesFacadeLocal tblRequisicionesFacade;

    @EJB
    private CatAreasFacadeLocal catAreasFacade;
    
    @EJB
    private CatTipoRequisicionFacadeLocal catTipoReqFacade;
    
        
    private List<CatAreas> areasList = new ArrayList<CatAreas>();
    private List<CatTipoRequisicion> catTipoReqList = new ArrayList<CatTipoRequisicion>();

    public List<CatAreas> getAreasList() {
        if (areasList.isEmpty()){
            try{
                areasList = catAreasFacade.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return areasList;
    }

    public void setAreasList(List<CatAreas> areasList) {
        this.areasList = areasList;
    }

    public List<CatTipoRequisicion> getCatTipoReqList() {
        if (catTipoReqList.isEmpty()){
            try{
                catTipoReqList = catTipoReqFacade.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        
        }
        return catTipoReqList;
    }

    public void setCatTipoReqList(List<CatTipoRequisicion> catTipoReqList) {
        this.catTipoReqList = catTipoReqList;
    }

    public TblRequisiciones getTblRequisiciones() {
        return tblRequisiciones;
    }

    public void setTblRequisiciones(TblRequisiciones tblRequisiciones) {
        this.tblRequisiciones = tblRequisiciones;
    }


    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblRequisiciones = (TblRequisiciones) table.getRowData();
            tblRequisicionesFacade.remove(tblRequisiciones);
            RequisicionesDataModel dataModel = (RequisicionesDataModel) this.getBean("#{requisicionesDataModel}", RequisicionesDataModel.class);
            dataModel.clear();
            addInfo("Se ha Eliminado la Requisicion con Exito", "Se ha Eliminado la Requisicion con Exito");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void buscar(ActionEvent ae){
        RequisicionesDataModel dataModel = (RequisicionesDataModel) this.getBean("#{requisicionesDataModel}", RequisicionesDataModel.class);
        dataModel.clear();
    }
}
