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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoProducto;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoProductoFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoProductoFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantCatTipoProducto")
@RequestScoped
public class FrmMantCatTipoProducto extends PageBase{
    private CatTipoProducto catTipoProducto = new CatTipoProducto();
    
    @EJB
    private CatTipoProductoFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipoProducto}")
    private Integer codTipoProducto;

    public Integer getCodTipoProducto() {
        return codTipoProducto;
    }

    public void setCodTipoProducto(Integer codTipoProducto) {
        if(codTipoProducto != null){
            try{
            catTipoProducto = facade.find(codTipoProducto);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipoProducto = codTipoProducto;
    }
    
    
    
    public CatTipoProducto getCatTipoProducto() {
        return catTipoProducto;
    }

    public void setCatTipoProducto(CatTipoProducto catTipoProducto) {
        this.catTipoProducto = catTipoProducto;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catTipoProducto.getCodTipProducto() != null){
                facade.edit(catTipoProducto);
            }else{
                facade.create(catTipoProducto);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoProducto = new CatTipoProducto();
    }
    
    
}
