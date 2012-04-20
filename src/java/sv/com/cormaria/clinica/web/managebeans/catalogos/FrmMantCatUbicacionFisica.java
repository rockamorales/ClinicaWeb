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
import sv.com.cormaria.servicios.entidades.catalogos.CatUbicacionFisica;
import sv.com.cormaria.servicios.facades.catalogos.CatUbicacionFisicaFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatUbicacionFisicaFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatUbicacionFisica")
@RequestScoped
public class FrmMantCatUbicacionFisica extends PageBase{
    private CatUbicacionFisica catUbicacionFisica = new CatUbicacionFisica();
    
    @EJB
    private CatUbicacionFisicaFacadeLocal facade;
    @ManagedProperty(value ="#{param.codUbicacionFisica}")
    private Integer codUbicacionFisica;

    public Integer getCodUbicacionFisica() {
        return codUbicacionFisica;
    }

    public void setCodUbicacionFisica(Integer codUbicacionFisica) {
        if(codUbicacionFisica != null){
            try{
            catUbicacionFisica = facade.find(codUbicacionFisica);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codUbicacionFisica = codUbicacionFisica;
    }
    
    
    
    public CatUbicacionFisica getCatUbicacionFisica() {
        return catUbicacionFisica;
    }

    public void setCatUbicacionFisica(CatUbicacionFisica catUbicacionFisica) {
        this.catUbicacionFisica = catUbicacionFisica;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catUbicacionFisica.getCodUbiFisica()!= null){
                facade.edit(catUbicacionFisica);
            }else{
                facade.create(catUbicacionFisica);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catUbicacionFisica = new CatUbicacionFisica();
    }
    
    
}
