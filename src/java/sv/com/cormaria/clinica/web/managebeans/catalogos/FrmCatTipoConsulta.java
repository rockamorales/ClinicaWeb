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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoConsulta;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoConsultaFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmCatTipoConsulta")
@RequestScoped
public class FrmCatTipoConsulta extends PageBase {
    private CatTipoConsulta catTipoConsulta = new CatTipoConsulta();
    @EJB
    private CatTipoConsultaFacadeLocal catTipoConsultaFacade;
    
    private List<CatTipoConsulta> catTipoConsultaList= new ArrayList<CatTipoConsulta>();

    public CatTipoConsulta getCatTipoConsulta() {
        return catTipoConsulta;
    }

    public void setCatTipoConsulta(CatTipoConsulta catTipoConsulta) {
        this.catTipoConsulta = catTipoConsulta;
    }

    public List<CatTipoConsulta> getCatTipoConsultaList() {
        if (catTipoConsultaList.isEmpty()){
          try{
            catTipoConsultaList = catTipoConsultaFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoConsultaList;
    }

    public void setCatTipoConsultaList(List<CatTipoConsulta> catTipoConsultaList) {
        this.catTipoConsultaList = catTipoConsultaList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoConsulta = (CatTipoConsulta) table.getRowData();
            catTipoConsultaFacade.remove(catTipoConsulta);
            catTipoConsultaList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
