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
import sv.com.cormaria.servicios.entidades.catalogos.CatParentescoResponsable;
import sv.com.cormaria.servicios.facades.catalogos.CatParentescoResponsableFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatParentescoResponsable")
@RequestScoped
public class FrmCatParentescoResponsable extends PageBase {
    private CatParentescoResponsable catParentescoResponsable = new CatParentescoResponsable();
    @EJB
    private CatParentescoResponsableFacadeLocal catParentescoResponsableFacade;
    
    private List<CatParentescoResponsable> catParentescoResponsableList= new ArrayList<CatParentescoResponsable>();

    public CatParentescoResponsable getCatParentescoResponsable() {
        return catParentescoResponsable;
    }

    public void setCatParentescoResponsable(CatParentescoResponsable catParentescoResponsable) {
        this.catParentescoResponsable = catParentescoResponsable;
    }

    public List<CatParentescoResponsable> getCatParentescoResponsableList() {
        if (catParentescoResponsableList.isEmpty()){
          try{
            catParentescoResponsableList = catParentescoResponsableFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catParentescoResponsableList;
    }

    public void setCatParentescoResponsableList(List<CatParentescoResponsable> catParentescoResponsableList) {
        this.catParentescoResponsableList = catParentescoResponsableList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catParentescoResponsable = (CatParentescoResponsable) table.getRowData();
            catParentescoResponsableFacade.remove(catParentescoResponsable);
            catParentescoResponsableList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
