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
import sv.com.cormaria.servicios.entidades.catalogos.CatConsultorio;
import sv.com.cormaria.servicios.facades.catalogos.CatConsultorioFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatConsultorio")
@RequestScoped
public class FrmCatConsultorio extends PageBase {
    private CatConsultorio catConsultorio = new CatConsultorio();
    @EJB
    private CatConsultorioFacadeLocal catConsultorioFacade;
    
    private List<CatConsultorio> catConsultorioList= new ArrayList<CatConsultorio>();

    public CatConsultorio getCatConsultorio() {
        return catConsultorio;
    }

    public void setCatConsultorio(CatConsultorio catConsultorio) {
        this.catConsultorio = catConsultorio;
    }

    public List<CatConsultorio> getCatConsultorioList() {
        if (catConsultorioList.isEmpty()){
          try{
            catConsultorioList = catConsultorioFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catConsultorioList;
    }

    public void setCatConsultorioList(List<CatConsultorio> catConsultorioList) {
        this.catConsultorioList = catConsultorioList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catConsultorio = (CatConsultorio) table.getRowData();
            catConsultorioFacade.remove(catConsultorio);
            catConsultorioList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
