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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServicio;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServicioFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoServicio")
@RequestScoped
public class FrmCatTipoServicio extends PageBase {
    private CatTipoServicio catTipoServicio = new CatTipoServicio();
    @EJB
    private CatTipoServicioFacadeLocal catTipoServicioFacade;
    
    private List<CatTipoServicio> catTipoServicioList= new ArrayList<CatTipoServicio>();

    public CatTipoServicio getCatTipoServicio() {
        return catTipoServicio;
    }

    public void setCatTipoServicio(CatTipoServicio catTipoServicio) {
        this.catTipoServicio = catTipoServicio;
    }

    public List<CatTipoServicio> getCatTipoServicioList() {
        if (catTipoServicioList.isEmpty()){
          try{
            catTipoServicioList = catTipoServicioFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoServicioList;
    }

    public void setCatTipoServicioList(List<CatTipoServicio> catTipoServicioList) {
        this.catTipoServicioList = catTipoServicioList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoServicio = (CatTipoServicio) table.getRowData();
            catTipoServicioFacade.remove(catTipoServicio);
            catTipoServicioList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
