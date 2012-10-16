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
import sv.com.cormaria.servicios.entidades.administracion.TblBeneficiarios;
import sv.com.cormaria.servicios.entidades.catalogos.CatRubro;
import sv.com.cormaria.servicios.enums.Estado;
import sv.com.cormaria.servicios.facades.administracion.TblBeneficiariosFacade;
import sv.com.cormaria.servicios.facades.administracion.TblBeneficiariosFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatRubroFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatRubroFacade;
import sv.com.cormaria.servicios.helpers.ValidationUtils;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantTblBeneficiarios")
@RequestScoped
public class FrmMantTblBeneficiarios extends PageBase{
    private TblBeneficiarios tblBeneficiarios = new TblBeneficiarios();

    @EJB
    private TblBeneficiariosFacadeLocal facade;
    @EJB
    private CatRubroFacadeLocal CatRubroFacade;

    
    @ManagedProperty(value ="#{param.numBeneficiarios}")
    private Integer numBeneficiarios;
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

    public Integer getNumBeneficiarios() {
        return numBeneficiarios;
    }

    public void setNumBeneficiarios(Integer numBeneficiarios) {
        if(numBeneficiarios != null){
            try{
            tblBeneficiarios = facade.find(numBeneficiarios);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numBeneficiarios = numBeneficiarios;
    }
    
    public TblBeneficiarios getTblBeneficiarios() {
        return tblBeneficiarios;
    }

    public void setTblBeneficiarios(TblBeneficiarios tblBeneficiarios) {
        this.tblBeneficiarios = tblBeneficiarios;
    }
    
    public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblBeneficiarios().getCodRubro() == null) {
          isValid = false;
          this.addError("Por favor seleccione el Rubro", "Por favor seleccione el Rubro");
       } 
       if (tblBeneficiarios.getNomBeneficiario()==null || tblBeneficiarios.getNomBeneficiario().trim().equals("")){
            this.addError("Por favor ingrese los nombres del Beneficiario", "Por favor ingrese los nombres del Beneficiario");
            isValid = false;
        }
       
        
      if (tblBeneficiarios.getNitBeneficiario()==null || tblBeneficiarios.getNitBeneficiario().trim().equals("")){
            this.addError("Por favor ingrese el número de NIT del Beneficiario", "Por favor ingrese el número de DUI del Beneficiario");
            isValid = false;
      }
            
            if (ValidationUtils.validarNIT(tblBeneficiarios.getNitBeneficiario())== false) {
            this.addError("El número de NIT del Beneficiario NO ES VALIDO", "El número de NIT del Beneficiario NO ES VALIDO");
            this.addInfo("Ingrese nuevamente el NIT del Beneficiario" , "Ingrese nuevamente el NIT del Beneficiario");
            isValid = false;
            }
        

       return isValid;
  }    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }  
       
            if(tblBeneficiarios.getNumBeneficiario() != null){
                facade.edit(tblBeneficiarios);
            }else{
                facade.create(tblBeneficiarios);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblBeneficiarios = new TblBeneficiarios();
    }
    
    
}
