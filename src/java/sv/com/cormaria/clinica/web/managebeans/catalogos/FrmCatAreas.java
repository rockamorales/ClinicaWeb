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
import sv.com.cormaria.servicios.entidades.catalogos.CatAreas;
import sv.com.cormaria.servicios.facades.catalogos.CatAreasFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatAreas")
@RequestScoped
public class FrmCatAreas extends PageBase {
    private CatAreas catAreas = new CatAreas();
    @EJB
    private CatAreasFacadeLocal catAreasFacade;
    
    private List<CatAreas> catAreasList= new ArrayList<CatAreas>();

    public CatAreas getCatAreas() {
        return catAreas;
    }

    public void setCatAreas(CatAreas catAreas) {
        this.catAreas = catAreas;
    }

    public List<CatAreas> getCatAreasList() {
        if (catAreasList.isEmpty()){
          try{
            catAreasList = catAreasFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catAreasList;
    }

    public void setCatAreasList(List<CatAreas> catAreasList) {
        this.catAreasList = catAreasList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catAreas = (CatAreas) table.getRowData();
            catAreasFacade.remove(catAreas);
            catAreasList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
