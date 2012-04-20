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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoReferencia;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoReferenciaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatTipoReferencia")
@RequestScoped
public class FrmCatTipoReferencia extends PageBase {
    private CatTipoReferencia catTipoReferencia = new CatTipoReferencia();
    @EJB
    private CatTipoReferenciaFacadeLocal catTipoReferenciaFacade;
    
    private List<CatTipoReferencia> catTipoReferenciaList= new ArrayList<CatTipoReferencia>();

    public CatTipoReferencia getCatTipoReferencia() {
        return catTipoReferencia;
    }

    public void setCatTipoReferencia(CatTipoReferencia catTipoReferencia) {
        this.catTipoReferencia = catTipoReferencia;
    }

    public List<CatTipoReferencia> getCatTipoReferenciaList() {
        if (catTipoReferenciaList.isEmpty()){
          try{
            catTipoReferenciaList = catTipoReferenciaFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catTipoReferenciaList;
    }

    public void setCatTipoReferenciaList(List<CatTipoReferencia> catTipoReferenciaList) {
        this.catTipoReferenciaList = catTipoReferenciaList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catTipoReferencia = (CatTipoReferencia) table.getRowData();
            catTipoReferenciaFacade.remove(catTipoReferencia);
            catTipoReferenciaList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
