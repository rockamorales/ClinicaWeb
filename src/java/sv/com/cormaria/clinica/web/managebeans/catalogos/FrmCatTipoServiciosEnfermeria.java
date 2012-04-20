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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServiciosEnfermeria;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServiciosEnfermeriaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoServiciosEnfermeria")
@RequestScoped
public class FrmCatTipoServiciosEnfermeria extends PageBase {
    private CatTipoServiciosEnfermeria catTipoServiciosEnfermeria = new CatTipoServiciosEnfermeria();
    @EJB
    private CatTipoServiciosEnfermeriaFacadeLocal catTipoServiciosEnfermeriaFacade;
    
    private List<CatTipoServiciosEnfermeria> catTipoServiciosEnfermeriaList= new ArrayList<CatTipoServiciosEnfermeria>();

    public CatTipoServiciosEnfermeria getCatTipoServiciosEnfermeria() {
        return catTipoServiciosEnfermeria;
    }

    public void setCatTipoServiciosEnfermeria(CatTipoServiciosEnfermeria catTipoServiciosEnfermeria) {
        this.catTipoServiciosEnfermeria = catTipoServiciosEnfermeria;
    }

    public List<CatTipoServiciosEnfermeria> getCatTipoServiciosEnfermeriaList() {
        if (catTipoServiciosEnfermeriaList.isEmpty()){
          try{
            catTipoServiciosEnfermeriaList = catTipoServiciosEnfermeriaFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoServiciosEnfermeriaList;
    }

    public void setCatTipoServiciosEnfermeriaList(List<CatTipoServiciosEnfermeria> catTipoServiciosEnfermeriaList) {
        this.catTipoServiciosEnfermeriaList = catTipoServiciosEnfermeriaList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoServiciosEnfermeria = (CatTipoServiciosEnfermeria) table.getRowData();
            catTipoServiciosEnfermeriaFacade.remove(catTipoServiciosEnfermeria);
            catTipoServiciosEnfermeriaList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
