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
import sv.com.cormaria.servicios.entidades.catalogos.CatSexo;
import sv.com.cormaria.servicios.facades.catalogos.CatSexoFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantCatSexo")
@RequestScoped
public class FrmMantCatSexo extends PageBase{
    private CatSexo catSexo = new CatSexo();
    
    @EJB
    private CatSexoFacadeLocal facade;
    @ManagedProperty(value ="#{param.codSexo}")
    private Integer codSexo;

    public Integer getCodSexo() {
        return codSexo;
    }

    public void setCodSexo(Integer codSexo) {
        if(codSexo != null){
            try{
            catSexo = facade.find(codSexo);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codSexo = codSexo;
    }
    
    
    
    public CatSexo getCatSexo() {
        return catSexo;
    }

    public void setCatSexo(CatSexo catSexo) {
        this.catSexo = catSexo;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catSexo.getCodSexPaciente()!= null){
                facade.edit(catSexo);
            }else{
                facade.create(catSexo);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catSexo = new CatSexo();
    }
    
    
}
