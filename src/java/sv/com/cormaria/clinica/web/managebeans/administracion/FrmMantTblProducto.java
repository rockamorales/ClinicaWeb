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
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoProducto;
import sv.com.cormaria.servicios.entidades.catalogos.CatCategoriaProducto;
import sv.com.cormaria.servicios.entidades.catalogos.CatPresentacionProducto;
import sv.com.cormaria.servicios.enums.Estado;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacade;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoProductoFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatCategoriaProductoFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatCategoriaProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatPresentacionProductoFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatPresentacionProductoFacadeLocal;
import sv.com.cormaria.servicios.helpers.ValidationUtils;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmMantTblProducto")
@RequestScoped
public class FrmMantTblProducto extends PageBase{
    private TblProducto tblProducto = new TblProducto();

    @EJB
    private TblProductoFacadeLocal facade;
    @EJB
    private CatTipoProductoFacadeLocal catTipoProductoFacade;
    @EJB
    private CatCategoriaProductoFacadeLocal catCategoriaProductoFacade;
    @EJB
    private CatPresentacionProductoFacadeLocal catPresentacionProductoFacade;    

    
    @ManagedProperty(value ="#{param.numProducto}")
    private Integer numProducto;
    private List<SelectItem> catTipoProductoList = new ArrayList<SelectItem>();
    private List<SelectItem> catCategoriaProductoList = new ArrayList<SelectItem>();
    private List<SelectItem> catPresentacionProductoList = new ArrayList<SelectItem>();
    
    public List<SelectItem> getcatTipoProductoList() {
        if (catTipoProductoList.isEmpty()){
            try{
                List<CatTipoProducto> l = catTipoProductoFacade.findActive();
                for (CatTipoProducto catTipoProducto: l) {
                    catTipoProductoList.add(new SelectItem(catTipoProducto.getCodTipProducto(), catTipoProducto.getNomTipProducto()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return catTipoProductoList;
    }

    public void setCatTipoProductoList(List<SelectItem> catTipoProductoList) {
        this.catTipoProductoList = catTipoProductoList;
    }

    public List<SelectItem> getCatCategoriaProductoList() {
        if (catCategoriaProductoList.isEmpty()){
            try{
                List<CatCategoriaProducto> l = catCategoriaProductoFacade.findActive();
                for (CatCategoriaProducto catCategoriaProducto: l) {
                    catCategoriaProductoList.add(new SelectItem(catCategoriaProducto.getCodCatProducto(), catCategoriaProducto.getNomCatProducto()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return catCategoriaProductoList;
    }

    public void setCatCategoriaProductoList(List<SelectItem> catCategoriaProductoList) {
        this.catCategoriaProductoList = catCategoriaProductoList;
    }

    public List<SelectItem> getCatPresentacionProductoList() {
        if (catPresentacionProductoList.isEmpty()){
            try{
                List<CatPresentacionProducto> l = catPresentacionProductoFacade.findActive();
                for (CatPresentacionProducto catPresentacionProducto: l) {
                    catPresentacionProductoList.add(new SelectItem(catPresentacionProducto.getCodPreProducto(), catPresentacionProducto.getNomPreProducto()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return catPresentacionProductoList;
    }

    public void setCatPresentacionProductoList(List<SelectItem> catPresentacionProductoList) {
        this.catPresentacionProductoList = catPresentacionProductoList;
    }

    
    public Integer getNumProducto() {
        return numProducto;
    }

    public void setNumProducto(Integer numProducto) {
        if(numProducto != null){
            try{
            tblProducto = facade.find(numProducto);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numProducto = numProducto;
    }
    
    public TblProducto getTblProducto() {
        return tblProducto;
    }

    public void setTblProducto(TblProducto tblProducto) {
        this.tblProducto = tblProducto;
    }
    
    public boolean validar(){
       boolean isValid = true;
       
       if (this.getTblProducto().getCodTipProducto() == null) {
          isValid = false;
          this.addError("Por favor seleccione el Tipo de Producto", "Por favor seleccione el Tipo de Producto");
       }
       if (this.getTblProducto().getCodTipProducto() == null) {
          isValid = false;
          this.addError("Por favor seleccione Categoria de Producto", "Por favor seleccione el Categoria de Producto");
       }
       if (this.getTblProducto().getCodPreProducto() == null) {
          isValid = false;
          this.addError("Por favor seleccione el Presentacion de Producto", "Por favor seleccione el Presentacion de Producto");
       }
       if (tblProducto.getNomProducto()==null || tblProducto.getNomProducto().trim().equals("")){
            this.addError("Por favor ingrese los nombres del Producto", "Por favor ingrese los nombres del Producto");
            isValid = false;
        }
       return isValid;
  }    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }  
       
            if(tblProducto.getNumProducto() != null){
                facade.edit(tblProducto);
            }else{
                facade.create(tblProducto);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblProducto = new TblProducto();
    }
    
    
}
