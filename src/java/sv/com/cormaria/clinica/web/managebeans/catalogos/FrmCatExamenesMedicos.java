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
import sv.com.cormaria.servicios.entidades.catalogos.CatExamenesMedicos;
import sv.com.cormaria.servicios.facades.catalogos.CatExamenesMedicosFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatExamenesMedicos")
@RequestScoped
public class FrmCatExamenesMedicos extends PageBase {
    private CatExamenesMedicos catExamenesMedicos = new CatExamenesMedicos();
    @EJB
    private CatExamenesMedicosFacadeLocal catExamenesMedicosFacade;
    
    private List<CatExamenesMedicos> catExamenesMedicosList= new ArrayList<CatExamenesMedicos>();

    public CatExamenesMedicos getCatExamenesMedicos() {
        return catExamenesMedicos;
    }

    public void setCatExamenesMedicos(CatExamenesMedicos catExamenesMedicos) {
        this.catExamenesMedicos = catExamenesMedicos;
    }

    public List<CatExamenesMedicos> getCatExamenesMedicosList() {
        if (catExamenesMedicosList.isEmpty()){
          try{
            catExamenesMedicosList = catExamenesMedicosFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catExamenesMedicosList;
    }

    public void setCatExamenesMedicosList(List<CatExamenesMedicos> catExamenesMedicosList) {
        this.catExamenesMedicosList = catExamenesMedicosList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catExamenesMedicos = (CatExamenesMedicos) table.getRowData();
            catExamenesMedicosFacade.remove(catExamenesMedicos);
            catExamenesMedicosList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
