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
import sv.com.cormaria.servicios.entidades.catalogos.CatProfesiones;
import sv.com.cormaria.servicios.facades.catalogos.CatProfesionesFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatProfesiones")
@RequestScoped
public class FrmCatProfesiones extends PageBase {
    private CatProfesiones catProfesiones = new CatProfesiones();
    @EJB
    private CatProfesionesFacadeLocal catProfesionesFacade;
    
    private List<CatProfesiones> catProfesionesList= new ArrayList<CatProfesiones>();

    public CatProfesiones getCatProfesiones() {
        return catProfesiones;
    }

    public void setCatProfesiones(CatProfesiones catProfesiones) {
        this.catProfesiones = catProfesiones;
    }

    public List<CatProfesiones> getCatProfesionesList() {
        if (catProfesionesList.isEmpty()){
          try{
            catProfesionesList = catProfesionesFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catProfesionesList;
    }

    public void setCatProfesionesList(List<CatProfesiones> catProfesionesList) {
        this.catProfesionesList = catProfesionesList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catProfesiones = (CatProfesiones) table.getRowData();
            catProfesionesFacade.remove(catProfesiones);
            catProfesionesList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
