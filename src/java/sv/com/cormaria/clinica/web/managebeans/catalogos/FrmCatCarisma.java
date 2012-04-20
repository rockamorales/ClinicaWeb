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
import sv.com.cormaria.servicios.entidades.catalogos.CatCarisma;
import sv.com.cormaria.servicios.facades.catalogos.CatCarismaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatCarisma")
@RequestScoped
public class FrmCatCarisma extends PageBase {
    private CatCarisma catCarisma = new CatCarisma();
    @EJB
    private CatCarismaFacadeLocal catCarismaFacade;
    
    private List<CatCarisma> catCarismaList= new ArrayList<CatCarisma>();

    public CatCarisma getCatCarisma() {
        return catCarisma;
    }

    public void setCatCarisma(CatCarisma catCarisma) {
        this.catCarisma = catCarisma;
    }

    public List<CatCarisma> getCatCarismaList() {
        if (catCarismaList.isEmpty()){
          try{
            catCarismaList = catCarismaFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catCarismaList;
    }

    public void setCatCarismaList(List<CatCarisma> catCarismaList) {
        this.catCarismaList = catCarismaList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catCarisma = (CatCarisma) table.getRowData();
            catCarismaFacade.remove(catCarisma);
            catCarismaList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
