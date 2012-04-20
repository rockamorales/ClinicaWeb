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
import sv.com.cormaria.servicios.entidades.catalogos.CatBancos;
import sv.com.cormaria.servicios.facades.catalogos.CatBancosFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatBancos")
@RequestScoped
public class FrmCatBancos extends PageBase {
    private CatBancos catBancos = new CatBancos();
    @EJB
    private CatBancosFacadeLocal catBancosFacade;
    
    private List<CatBancos> catBancosList= new ArrayList<CatBancos>();

    public CatBancos getCatBancos() {
        return catBancos;
    }

    public void setCatBancos(CatBancos catBancos) {
        this.catBancos = catBancos;
    }

    public List<CatBancos> getCatBancosList() {
        if (catBancosList.isEmpty()){
          try{
            catBancosList = catBancosFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catBancosList;
    }

    public void setCatBancosList(List<CatBancos> catBancosList) {
        this.catBancosList = catBancosList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catBancos = (CatBancos) table.getRowData();
            catBancosFacade.remove(catBancos);
            catBancosList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}