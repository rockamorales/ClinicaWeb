/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoRequisicion;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoRequisicionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoRequisicion")
@RequestScoped
public class FrmCatTipoRequisicion extends PageBase {
    private CatTipoRequisicion catTipoRequisicion = new CatTipoRequisicion();
    @EJB
    private CatTipoRequisicionFacadeLocal catTipoRequisicionFacade;
    
    private List<CatTipoRequisicion> catTipoRequisicionList= new ArrayList<CatTipoRequisicion>();

    public CatTipoRequisicion getCatTipoRequisicion() {
        return catTipoRequisicion;
    }

    public void setCatTipoRequisicion(CatTipoRequisicion catTipoRequisicion) {
        this.catTipoRequisicion = catTipoRequisicion;
    }

    public List<CatTipoRequisicion> getCatTipoRequisicionList() {
        if (catTipoRequisicionList.isEmpty()){
          try{
            catTipoRequisicionList = catTipoRequisicionFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoRequisicionList;
    }

    public void setCatTipoRequisicionList(List<CatTipoRequisicion> catTipoRequisicionList) {
        this.catTipoRequisicionList = catTipoRequisicionList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoRequisicion = (CatTipoRequisicion) table.getRowData();
            catTipoRequisicionFacade.remove(catTipoRequisicion);
            catTipoRequisicionList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
