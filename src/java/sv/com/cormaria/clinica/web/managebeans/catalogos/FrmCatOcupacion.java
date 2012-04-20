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
import sv.com.cormaria.servicios.entidades.catalogos.CatOcupacion;
import sv.com.cormaria.servicios.facades.catalogos.CatOcupacionFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatOcupacion")
@RequestScoped
public class FrmCatOcupacion extends PageBase {
    private CatOcupacion catOcupacion = new CatOcupacion();
    @EJB
    private CatOcupacionFacadeLocal catOcupacionFacade;
    
    private List<CatOcupacion> catOcupacionList= new ArrayList<CatOcupacion>();

    public CatOcupacion getCatOcupacion() {
        return catOcupacion;
    }

    public void setCatOcupacion(CatOcupacion catOcupacion) {
        this.catOcupacion = catOcupacion;
    }

    public List<CatOcupacion> getCatOcupacionList() {
        if (catOcupacionList.isEmpty()){
          try{
            catOcupacionList = catOcupacionFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catOcupacionList;
    }

    public void setCatOcupacionList(List<CatOcupacion> catOcupacionList) {
        this.catOcupacionList = catOcupacionList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catOcupacion = (CatOcupacion) table.getRowData();
            catOcupacionFacade.remove(catOcupacion);
            catOcupacionList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
