/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatOrigenIngresoProducto;
import sv.com.cormaria.servicios.facades.catalogos.CatOrigenIngresoProductoFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatOrigenIngresoProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatOrigenIngresoProducto")
@RequestScoped
public class FrmMantCatOrigenIngresoProducto extends PageBase{
    private CatOrigenIngresoProducto catOrigenIngresoProducto = new CatOrigenIngresoProducto();
    
    @EJB
    private CatOrigenIngresoProductoFacadeLocal facade;
    @ManagedProperty(value ="#{param.codOriIngreso}")
    private Integer codOriIngreso;

    public Integer getCodOriIngreso() {
        return codOriIngreso;
    }

    public void setCodOriIngreso(Integer codOriIngreso) {
        if(codOriIngreso != null){
            try{
            catOrigenIngresoProducto = facade.find(codOriIngreso);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codOriIngreso = codOriIngreso;
    }
    
    
    
    public CatOrigenIngresoProducto getCatOrigenIngresoProducto() {
        return catOrigenIngresoProducto;
    }

    public void setCatOrigenIngresoProducto(CatOrigenIngresoProducto catOrigenIngresoProducto) {
        this.catOrigenIngresoProducto = catOrigenIngresoProducto;
    }
    
       public boolean validar(){
       boolean isValid = true;
       
       if (this.getCatOrigenIngresoProducto().getNomOriIngreso() == ""){
          isValid = false;
          this.addError("Porfavor ingrese el nombre del Origen del Producto", "Porfavor ingrese el nombre del Origen del Producto");
       } 

       
       return isValid;
     
   }    
    
    public void guardar(ActionEvent ae){
        try{
           if (!validar()){
               return;
           }             
            if(catOrigenIngresoProducto.getCodOriIngreso() != null){
                facade.edit(catOrigenIngresoProducto);
            }else{
                facade.create(catOrigenIngresoProducto);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catOrigenIngresoProducto = new CatOrigenIngresoProducto();
    }
    
    
}
