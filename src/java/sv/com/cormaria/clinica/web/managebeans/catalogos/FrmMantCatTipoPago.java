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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoPago;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoPagoFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoPagoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTipoPago")
@RequestScoped
public class FrmMantCatTipoPago extends PageBase{
    private CatTipoPago catTipoPago = new CatTipoPago();
    
    @EJB
    private CatTipoPagoFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipoPago}")
    private Integer codTipoPago;

    public Integer getCodTipoPago() {
        return codTipoPago;
    }

    public void setCodTipoPago(Integer codTipoPago) {
        if(codTipoPago != null){
            try{
            catTipoPago = facade.find(codTipoPago);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipoPago = codTipoPago;
    }
    
    
    
    public CatTipoPago getCatTipoPago() {
        return catTipoPago;
    }

    public void setCatTipoPago(CatTipoPago catTipoPago) {
        this.catTipoPago = catTipoPago;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catTipoPago.getCodTipPago() != null){
                facade.edit(catTipoPago);
            }else{
                facade.create(catTipoPago);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoPago = new CatTipoPago();
    }
    
    
}
