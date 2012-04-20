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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonante;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonanteFacadeLocal;

/**
 *
 * @author schumy
 */
@ManagedBean (name = "frmCatTipoDonante")
@RequestScoped
public class FrmCatTipoDonante extends PageBase {
    private CatTipoDonante catTipoDonante = new CatTipoDonante();
    @EJB
    private CatTipoDonanteFacadeLocal catTipoDonanteFacade;
    
    private List<CatTipoDonante> catTipoDonanteList= new ArrayList<CatTipoDonante>();

    public CatTipoDonante getCatTipoDonante() {
        return catTipoDonante;
    }

    public void setCatTipoDonante(CatTipoDonante catTipoDonante) {
        this.catTipoDonante = catTipoDonante;
    }

    public List<CatTipoDonante> getCatTipoDonanteList() {
        if (catTipoDonanteList.isEmpty()){
          try{
            catTipoDonanteList = catTipoDonanteFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoDonanteList;
    }

    public void setCatTipoDonanteList(List<CatTipoDonante> catTipoDonanteList) {
        this.catTipoDonanteList = catTipoDonanteList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoDonante = (CatTipoDonante) table.getRowData();
            catTipoDonanteFacade.remove(catTipoDonante);
            catTipoDonanteList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
