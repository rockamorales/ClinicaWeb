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
import sv.com.cormaria.servicios.entidades.catalogos.CatSexo;
import sv.com.cormaria.servicios.facades.catalogos.CatSexoFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmCatSexo")
@RequestScoped
public class FrmCatSexo extends PageBase {
    private CatSexo catSexo = new CatSexo();
    @EJB
    private CatSexoFacadeLocal catSexoFacade;
    
    private List<CatSexo> catSexoList= new ArrayList<CatSexo>();

    public CatSexo getCatSexo() {
        return catSexo;
    }

    public void setCatSexo(CatSexo catSexo) {
        this.catSexo = catSexo;
    }

    public List<CatSexo> getCatSexoList() {
        if (catSexoList.isEmpty()){
          try{
            catSexoList = catSexoFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catSexoList;
    }

    public void setCatSexoList(List<CatSexo> catSexoList) {
        this.catSexoList = catSexoList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catSexo = (CatSexo) table.getRowData();
            catSexoFacade.remove(catSexo);
            catSexoList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
