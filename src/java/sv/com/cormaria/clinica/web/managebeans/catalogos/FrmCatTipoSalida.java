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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoSalida;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoSalidaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoSalida")
@RequestScoped
public class FrmCatTipoSalida extends PageBase {
    private CatTipoSalida catTipoSalida = new CatTipoSalida();
    @EJB
    private CatTipoSalidaFacadeLocal catTipoSalidaFacade;
    
    private List<CatTipoSalida> catTipoSalidaList= new ArrayList<CatTipoSalida>();

    public CatTipoSalida getCatTipoSalida() {
        return catTipoSalida;
    }

    public void setCatTipoSalida(CatTipoSalida catTipoSalida) {
        this.catTipoSalida = catTipoSalida;
    }

    public List<CatTipoSalida> getCatTipoSalidaList() {
        if (catTipoSalidaList.isEmpty()){
          try{
            catTipoSalidaList = catTipoSalidaFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoSalidaList;
    }

    public void setCatTipoSalidaList(List<CatTipoSalida> catTipoSalidaList) {
        this.catTipoSalidaList = catTipoSalidaList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoSalida = (CatTipoSalida) table.getRowData();
            catTipoSalidaFacade.remove(catTipoSalida);
            catTipoSalidaList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
