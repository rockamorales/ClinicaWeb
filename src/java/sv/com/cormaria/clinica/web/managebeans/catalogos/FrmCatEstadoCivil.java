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
import sv.com.cormaria.servicios.entidades.catalogos.CatEstadoCivil;
import sv.com.cormaria.servicios.facades.catalogos.CatEstadoCivilFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatEstadoCivil")
@RequestScoped
public class FrmCatEstadoCivil extends PageBase {
    private CatEstadoCivil catEstadoCivil = new CatEstadoCivil();
    @EJB
    private CatEstadoCivilFacadeLocal catEstadoCivilFacade;
    
    private List<CatEstadoCivil> catEstadoCivilList= new ArrayList<CatEstadoCivil>();

    public CatEstadoCivil getCatEstadoCivil() {
        return catEstadoCivil;
    }

    public void setCatEstadoCivil(CatEstadoCivil catEstadoCivil) {
        this.catEstadoCivil = catEstadoCivil;
    }

    public List<CatEstadoCivil> getCatEstadoCivilList() {
        if (catEstadoCivilList.isEmpty()){
          try{
            catEstadoCivilList = catEstadoCivilFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catEstadoCivilList;
    }

    public void setCatEstadoCivilList(List<CatEstadoCivil> catEstadoCivilList) {
        this.catEstadoCivilList = catEstadoCivilList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catEstadoCivil = (CatEstadoCivil) table.getRowData();
            catEstadoCivilFacade.remove(catEstadoCivil);
            catEstadoCivilList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
