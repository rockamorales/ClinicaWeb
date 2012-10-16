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
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblInstitucion;
import sv.com.cormaria.servicios.facades.administracion.TblInstitucionFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmTblInstitucion")
@RequestScoped
public class FrmTblInstitucion extends PageBase {
    private TblInstitucion tblInstitucion = new TblInstitucion();
    @EJB
    private TblInstitucionFacadeLocal tblInstitucionFacade;
    
    private List<TblInstitucion> tblInstitucionList= new ArrayList<TblInstitucion>();

    public TblInstitucion getTblInstitucion() {
        return tblInstitucion;
    }

    public void setTblInstitucion(TblInstitucion tblInstitucion) {
        this.tblInstitucion = tblInstitucion;
    }

    public List<TblInstitucion> getTblInstitucionList() {
        if (tblInstitucionList.isEmpty()){
          try{
            tblInstitucionList = tblInstitucionFacade.findActive();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return tblInstitucionList;
    }

    public void setTblInstitucionList(List<TblInstitucion> tblInstitucionList) {
        this.tblInstitucionList = tblInstitucionList;
    }

    public void desactivar(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            tblInstitucion = (TblInstitucion) table.getRowData();
            tblInstitucionFacade.desactivar(tblInstitucion);
            tblInstitucionList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
