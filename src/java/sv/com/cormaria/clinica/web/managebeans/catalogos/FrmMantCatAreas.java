/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatAreas;
import sv.com.cormaria.servicios.facades.catalogos.CatAreasFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatAreasFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatAreas")
@RequestScoped
public class FrmMantCatAreas extends PageBase{
    private CatAreas catAreas = new CatAreas();
    
    @EJB
    private CatAreasFacadeLocal facade;
    @ManagedProperty(value ="#{param.codArea}")
    private Integer codArea;

    public Integer getCodArea() {
        return codArea;
    }

    public void setCodArea(Integer codArea) {
        if(codArea != null){
            try{
            catAreas = facade.find(codArea);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codArea = codArea;
    }
    
    
    
    public CatAreas getCatAreas() {
        return catAreas;
    }

    public void setCatAreas(CatAreas catAreas) {
        this.catAreas = catAreas;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catAreas.getCodArea() != null){
                facade.edit(catAreas);
            }else{
                facade.create(catAreas);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catAreas = new CatAreas();
    }
    
    
}
