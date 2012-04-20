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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoConsulta;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoConsultaFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantCatTipoConsulta")
@RequestScoped
public class FrmMantCatTipoConsulta extends PageBase{
    private CatTipoConsulta catTipoConsulta = new CatTipoConsulta();
    
    @EJB
    private CatTipoConsultaFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTipoConsulta}")
    private Integer codTipoConsulta;

    public Integer getCodTipoConsulta() {
        return codTipoConsulta;
    }

    public void setCodTipoConsulta(Integer codTipoConsulta) {
        if(codTipoConsulta != null){
            try{
            catTipoConsulta = facade.find(codTipoConsulta);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTipoConsulta = codTipoConsulta;
    }
    
    
    
    public CatTipoConsulta getCatTipoConsulta() {
        return catTipoConsulta;
    }

    public void setCatTipoConsulta(CatTipoConsulta catTipoConsulta) {
        this.catTipoConsulta = catTipoConsulta;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catTipoConsulta.getCodTipConsulta() != null){
                facade.edit(catTipoConsulta);
            }else{
                facade.create(catTipoConsulta);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTipoConsulta = new CatTipoConsulta();
    }
    
    
}
