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
import sv.com.cormaria.servicios.entidades.catalogos.CatRubro;
import sv.com.cormaria.servicios.facades.catalogos.CatRubroFacadeLocal;

/**
 *
 * @author Claudia
 */

@ManagedBean (name = "frmCatRubro")
@RequestScoped
public class FrmCatRubro extends PageBase {
    private CatRubro catRubro = new CatRubro();
    @EJB
    private CatRubroFacadeLocal catRubroFacade;
    
    private List<CatRubro> catRubroList= new ArrayList<CatRubro>();

    public CatRubro getCatRubro() {
        return catRubro;
    }

    public void setCatRubro(CatRubro catRubro) {
        this.catRubro = catRubro;
    }

    public List<CatRubro> getCatRubroList() {
        if (catRubroList.isEmpty()){
          try{
            catRubroList = catRubroFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catRubroList;
    }

    public void setCatRubroList(List<CatRubro> catRubroList) {
        this.catRubroList = catRubroList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catRubro = (CatRubro) table.getRowData();
            catRubroFacade.remove(catRubro);
            catRubroList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}


