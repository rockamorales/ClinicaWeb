/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.administracion;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblInstitucion;
import sv.com.cormaria.servicios.entidades.catalogos.CatRubro;
import sv.com.cormaria.servicios.enums.Estado;
import sv.com.cormaria.servicios.facades.administracion.TblInstitucionFacade;
import sv.com.cormaria.servicios.facades.administracion.TblInstitucionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatRubroFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatRubroFacade;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantTblInstitucion")
@RequestScoped
public class FrmMantTblInstitucion extends PageBase{
    private TblInstitucion tblInstitucion = new TblInstitucion();

    @EJB
    private TblInstitucionFacadeLocal facade;
    @EJB
    private CatRubroFacadeLocal CatRubroFacade;

    
    @ManagedProperty(value ="#{param.numInstitucion}")
    private Integer numInstitucion;
    private List<SelectItem> catRubroList = new ArrayList<SelectItem>();

    public List<SelectItem> getCatRubroList() {
        if (catRubroList.isEmpty()){
            try{
                List<CatRubro> l = CatRubroFacade.findActive();
                for (CatRubro catRubro: l) {
                    catRubroList.add(new SelectItem(catRubro.getCodRubro(), catRubro.getNomRubro()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return catRubroList;
    }

    public void setCatRubroList(List<SelectItem> catRubroList) {
        this.catRubroList = catRubroList;
    }

    public Integer getNumInstitucion() {
        return numInstitucion;
    }

    public void setNumInstitucion(Integer numInstitucion) {
        if(numInstitucion != null){
            try{
            tblInstitucion = facade.find(numInstitucion);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numInstitucion = numInstitucion;
    }
    
    public TblInstitucion getTblInstitucion() {
        return tblInstitucion;
    }

    public void setTblInstitucion(TblInstitucion tblInstitucion) {
        this.tblInstitucion = tblInstitucion;
    }
    
    public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblInstitucion().getCodRubro() == null) {
          isValid = false;
          this.addError("Por favor seleccione el Rubro", "Por favor seleccione el Rubro");
       } 

       return isValid;
  }    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }  
       
            if(tblInstitucion.getNumInstitucion() != null){
                facade.edit(tblInstitucion);
            }else{
                facade.create(tblInstitucion);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblInstitucion = new TblInstitucion();
    }
    
    
}
