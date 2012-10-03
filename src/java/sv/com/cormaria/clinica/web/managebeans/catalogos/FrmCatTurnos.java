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
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatTurnos;
import sv.com.cormaria.servicios.facades.catalogos.CatTurnosFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmCatTurnos")
@ViewScoped
public class FrmCatTurnos extends PageBase {
    private CatTurnos catTurnos = new CatTurnos();
    @EJB
    private CatTurnosFacadeLocal catTurnosFacade;
    
    private List<CatTurnos> catTurnosList= new ArrayList<CatTurnos>();

    public CatTurnos getCatTurnos() {
        return catTurnos;
    }

    public void setCatTurnos(CatTurnos catTurnos) {
        this.catTurnos = catTurnos;
    }

    public List<CatTurnos> getCatTurnosList() {
        if (catTurnosList.isEmpty()){
          try{
            catTurnosList = catTurnosFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTurnosList;
    }

    public void setCatTurnosList(List<CatTurnos> catTurnosList) {
        this.catTurnosList = catTurnosList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTurnos = (CatTurnos) table.getRowData();
            catTurnosFacade.remove(catTurnos);
            catTurnosList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
