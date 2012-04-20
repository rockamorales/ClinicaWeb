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
import sv.com.cormaria.servicios.entidades.catalogos.CatRubro;
import sv.com.cormaria.servicios.facades.catalogos.CatRubroFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatRubroFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantCatRubro")
@RequestScoped
public class FrmMantCatRubro extends PageBase{
    private CatRubro catRubro = new CatRubro();
    
    @EJB
    private CatRubroFacadeLocal facade;
    @ManagedProperty(value ="#{param.codRubro}")
    private Integer codRubro;

    public Integer getCodRubro() {
        return codRubro;
    }

    public void setCodRubro(Integer codRubro) {
        if(codRubro != null){
            try{
            catRubro = facade.find(codRubro);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codRubro = codRubro;
    }
    
    
    
    public CatRubro getCatRubro() {
        return catRubro;
    }

    public void setCatRubro(CatRubro catRubro) {
        this.catRubro = catRubro;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catRubro.getCodRubro() != null){
                facade.edit(catRubro);
            }else{
                facade.create(catRubro);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catRubro = new CatRubro();
    }
    
    
}

