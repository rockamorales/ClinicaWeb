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
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmCatEspecialidad")
@RequestScoped
public class FrmCatEspecialidad extends PageBase {
    private CatEspecialidad catEspecialidad = new CatEspecialidad();
    @EJB
    private CatEspecialidadFacadeLocal catEspecialidadFacade;
    
    private List<CatEspecialidad> catEspecialidadList= new ArrayList<CatEspecialidad>();

    public CatEspecialidad getCatEspecialidad() {
        return catEspecialidad;
    }

    public void setCatEspecialidad(CatEspecialidad catEspecialidad) {
        this.catEspecialidad = catEspecialidad;
    }

    public List<CatEspecialidad> getCatEspecialidadList() {
        if (catEspecialidadList.isEmpty()){
          try{
            catEspecialidadList = catEspecialidadFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catEspecialidadList;
    }

    public void setCatEspecialidadList(List<CatEspecialidad> catEspecialidadList) {
        this.catEspecialidadList = catEspecialidadList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catEspecialidad = (CatEspecialidad) table.getRowData();
            catEspecialidadFacade.remove(catEspecialidad);
            catEspecialidadList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
