/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.farmacia;

import sv.com.cormaria.clinica.web.managebeans.farmacia.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.farmacia.TblIngresosProducto;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleIngresoProducto;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleIngresoProductoPK;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.entidades.administracion.TblInstitucion;
import sv.com.cormaria.servicios.entidades.catalogos.CatOrigenIngresoProducto;
import sv.com.cormaria.servicios.facades.administracion.TblInstitucionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatOrigenIngresoProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblIngresosProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblDetalleIngresoProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantTblIngresosProducto")
@ViewScoped
public class FrmMantTblIngresosProducto extends PageBase{
    private TblIngresosProducto tblIngresosProducto = new TblIngresosProducto();
    private TblDetalleIngresoProducto tblDetalleIngresoProducto = new TblDetalleIngresoProducto();    
    @EJB
    private transient TblIngresosProductoFacadeLocal facade;
    @EJB
    private transient CatOrigenIngresoProductoFacadeLocal catOrigenIngresoProductoFacade;
    @EJB
    private transient TblInstitucionFacadeLocal tblInstitucionFacade;
    @EJB
    private TblDetalleIngresoProductoFacadeLocal cblDetalleIngresoProductoFacade;  
    @EJB
    private TblProductoFacadeLocal productoFacade;
    private Integer numeroIngreso;
    private List<SelectItem> catOrigenIngresoProductoList = new ArrayList<SelectItem>();
    private List<SelectItem> tblInstitucionList = new ArrayList<SelectItem>();
    private List<TblDetalleIngresoProducto> cblDetalleIngresoProductoList= new ArrayList<TblDetalleIngresoProducto>();
    private List<SelectItem> tblProductoList = new ArrayList<SelectItem>();
    private TblProducto selectedProduct = new TblProducto();

    public TblProducto getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(TblProducto selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public TblIngresosProducto getTblIngresosProducto() {
        return tblIngresosProducto;
    }

    public void setTblIngresosProducto(TblIngresosProducto tblIngresosProducto) {
        this.tblIngresosProducto = tblIngresosProducto;
    }

    public TblDetalleIngresoProducto getTblDetalleIngresoProducto() {
        return tblDetalleIngresoProducto;
    }

    public void setTblDetalleIngresoProducto(TblDetalleIngresoProducto tblDetalleIngresoProducto) {
        this.tblDetalleIngresoProducto = tblDetalleIngresoProducto;
    }
    
    public List<SelectItem> getTblProductoList() {
          if (tblProductoList.isEmpty()){
            try{
                List<TblProducto> l = productoFacade.findMedicamentos();
                
                tblProductoList.add(new SelectItem(-1, "Seleccione un producto"));
                for (TblProducto tblProducto : l) {
                    tblProductoList.add(new SelectItem(tblProducto.getNumProducto(), tblProducto.getNomProducto()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }        
        return tblProductoList; 
    }

    public void setTblProductoList(List<SelectItem> tblProductoList) {
        this.tblProductoList = tblProductoList;
    }
    
    public List<TblDetalleIngresoProducto> getCblDetalleIngresoProductoList() {
        if (cblDetalleIngresoProductoList.isEmpty()){
          try{
              if(tblIngresosProducto.getNumIngreso() != null){
                cblDetalleIngresoProductoList = cblDetalleIngresoProductoFacade.findByIngresoProducto(tblIngresosProducto.getNumIngreso());
              }
          }catch(Exception x) {
              x.printStackTrace();
          }
        }         
        return cblDetalleIngresoProductoList;
    }

    public void setCblDetalleIngresoProductoList(List<TblDetalleIngresoProducto> cblDetalleIngresoProductoList) {
        this.cblDetalleIngresoProductoList = cblDetalleIngresoProductoList;
    }
    
    public List<SelectItem> getCatOrigenIngresoProductoList() {
        if (catOrigenIngresoProductoList.isEmpty()){
            try{
                List<CatOrigenIngresoProducto> l = catOrigenIngresoProductoFacade.findActive();
                for (CatOrigenIngresoProducto catOrigenIngresoProducto : l) {
                    catOrigenIngresoProductoList.add(new SelectItem(catOrigenIngresoProducto.getCodOriIngreso(), catOrigenIngresoProducto.getNomOriIngreso()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }        
        return catOrigenIngresoProductoList;
    }

    public void setCatOrigenIngresoProductoList(List<SelectItem> catOrigenIngresoProductoList) {
        this.catOrigenIngresoProductoList = catOrigenIngresoProductoList;
    }

    public List<SelectItem> getTblInstitucionList() {
    if (tblInstitucionList.isEmpty()){
            try{
                List<TblInstitucion> l = tblInstitucionFacade.findActive();
                for (TblInstitucion tblInstitucion : l) {
                    tblInstitucionList.add(new SelectItem(tblInstitucion.getNumInstitucion() ,tblInstitucion.getNomComInstitucion()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tblInstitucionList;
    }

    public void setTblInstitucionList(List<SelectItem> tblInstitucionList) {
        this.tblInstitucionList = tblInstitucionList;
    }

    public Integer getNumeroIngreso() {
        return numeroIngreso;
    }

    public void setNumeroIngreso(Integer numeroIngreso) {
        this.numeroIngreso = numeroIngreso;
    }

    public Integer getNumIngreso(Integer numIngreso) {
        return numIngreso;
    }

    public void setNumIngreso(Integer numIngreso) {
        this.numeroIngreso = numIngreso;
    }
        
    public void guardar(ActionEvent ae){
        try{
            if (!validar(tblIngresosProducto)){
                return;
            }
            if(tblIngresosProducto.getNumIngreso() != null){
                facade.edit(tblIngresosProducto);
            }else{
                tblIngresosProducto.setEstIngresoProducto(1); /*revisar*/
                if(this.getSessionBean().getUsuario()==null || this.getSessionBean().getUsuario().getNumEmpleado()==null){
                    this.addError("Su usuario no tiene un empleado asociado", "Su usuario no tiene un empleado asociado");
                }
                tblIngresosProducto.setNumEmpleado(this.getSessionBean().getUsuario().getNumEmpleado());
                facade.create(tblIngresosProducto);
                this.addInfo("El Ingreso ha sido guardado con exito", "El Ingreso ha sido guardado con exito");
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblIngresosProducto = new TblIngresosProducto();
    }
    
    public List<TblProducto> autoComplete(String prefijo){
        try{
            return productoFacade.findByNombreProducto("%"+prefijo+"%", 0, 10);
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
        return new ArrayList<TblProducto>();
    }
    
    public void agregar(ActionEvent ae){
        try{
            if (!validate(tblDetalleIngresoProducto)){
                return;
            }
            tblDetalleIngresoProducto.getTblDetalleIngresoProductoPK().setNumIngreso(tblIngresosProducto.getNumIngreso());
            tblDetalleIngresoProducto.setCorDetIngreso(0);
            cblDetalleIngresoProductoFacade.create(tblDetalleIngresoProducto);
            tblDetalleIngresoProducto = new TblDetalleIngresoProducto();
            this.getCblDetalleIngresoProductoList().clear();
            tblIngresosProducto = facade.find(tblIngresosProducto.getNumIngreso());
            this.addInfo("El producto fue agregado exitosamente", "El producto fue agregado exitosamente");
            this.selectedProduct= new TblProducto();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void seleccionarProducto(TblProducto producto){
        if (producto!=null){
            tblDetalleIngresoProducto.setTblDetalleIngresoProductoPK(new TblDetalleIngresoProductoPK(this.tblIngresosProducto.getNumIngreso(), producto.getNumProducto()));
            /*tblDetalleIngresoProducto.setCosUniDetIngreso(producto.getPreFinProducto());*/
        }
    }
    public void seleccionarProducto(ValueChangeEvent v){
        try{
        selectedProduct = productoFacade.find((Integer)v.getNewValue());
        if (selectedProduct!=null){
            tblDetalleIngresoProducto.setTblDetalleIngresoProductoPK(new TblDetalleIngresoProductoPK(this.tblIngresosProducto.getNumIngreso(), selectedProduct.getNumProducto()));
            tblDetalleIngresoProducto.setCosUniDetIngreso(selectedProduct.getPreFinProducto());
        }   
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public void seleccionarProductoByCodigo(ValueChangeEvent ve){
        try{
            selectedProduct = productoFacade.find((Integer)ve.getNewValue());
            if (selectedProduct!=null){
                tblDetalleIngresoProducto.setTblDetalleIngresoProductoPK(new TblDetalleIngresoProductoPK(this.tblIngresosProducto.getNumIngreso(), selectedProduct.getNumProducto()));
                tblDetalleIngresoProducto.setCosUniDetIngreso(selectedProduct.getPreFinProducto());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }        
    }
    
    public void deleteProducto(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            TblDetalleIngresoProducto tblDetalleIngresoProducto1 = (TblDetalleIngresoProducto) table.getRowData();
            System.out.println("Num Ingreso: "+tblDetalleIngresoProducto1.getTblDetalleIngresoProductoPK().getNumIngreso());
            System.out.println("Num producto: "+tblDetalleIngresoProducto1.getTblDetalleIngresoProductoPK().getNumProducto());
            cblDetalleIngresoProductoFacade.remove(tblDetalleIngresoProducto1);
            cblDetalleIngresoProductoList.clear();
            tblIngresosProducto = facade.find(tblIngresosProducto.getNumIngreso());
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    } 
    
    public boolean validate (TblDetalleIngresoProducto detalle){
        boolean validationOk = true;
        if (detalle.getCanDetIngreso() <= 0){
            this.addError("Ingrese Cantidad mayor a cero", "Ingrese Cantidad mayor a cero");
            validationOk = false;   
        }        
        if (detalle.getFecExpDetIngreso()==null){
            this.addError("Por favor ingrese la fecha de vencimiento del Producto", "Por favor ingrese la fecha de vencimiento del Producto");
            validationOk = false;
        }else if (detalle.getFecExpDetIngreso().compareTo(new java.util.Date())<=0){
                    this.addError("Fecha de Vencimiento caducada - No se permite el ingreso de producto vencido ", "Fecha de Vencimiento caducada - No se permite el ingreso de producto vencido ");
                    this.addInfo("Revise la fecha de Vencimiento e ingrese los datos nuevamente", "Revise la fecha de Vencimiento e ingrese los datos nuevamente");
                    validationOk = false;
        }
        if (detalle.getCosUniDetIngreso() < 0){
            this.addError("Error de datos: Ingrese Costo Unitario mayor o igual a cero", "Error de datos: Ingrese Costo Unitario mayor o igual a cero");
            validationOk = false;   
        }
        
        
        
        return validationOk;
    }
    
        public boolean validar (TblIngresosProducto ingreso){
        boolean validationOk = true;
        if (ingreso.getCodOriIngreso() <= 0){
            this.addError("Seleccione Origen del Ingreso", "Seleccione Origen del Ingreso");
            validationOk = false;   
        }       
        if (ingreso.getNumDocIngreso() == null || ingreso.getNumDocIngreso().trim().equals("")){
            this.addError("Seleccione Origen del Ingreso", "Seleccione Origen del Ingreso");
            validationOk = false;   
        }
        if (ingreso.getFecIngreso()==null){
            this.addError("Por favor, especifique la fecha de ingreso del Producto", "Por favor, especifique la fecha de ingreso del Producto");
            validationOk = false;
        }else if (ingreso.getFecIngreso().compareTo(new java.util.Date())>0){
                    this.addError("La fecha de ingreso no debe ser mayor a la fecha actual", "La fecha de ingreso no debe ser mayor a la fecha actual");
                    this.addInfo("Especifique la fecha de Ingreso nuevamente", "Especifique la fecha de Ingreso nuevamente");
                    validationOk = false;
        }
        if (ingreso.getNumInstitucion() < 0){
            this.addError("Error de datos: Seleccione Institucion de Procedencia", "Error de datos: Seleccione Institucion de Procedencia");
            validationOk = false;   
        }
        
        
        
        return validationOk;
    }
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            if (this.getNumeroIngreso() != null && this.getNumeroIngreso()>0){
                try{
                    tblIngresosProducto = facade.find(this.getNumeroIngreso());
                    this.getCblDetalleIngresoProductoList().clear();
                }catch(Exception ex){
                    this.addError(ex.getMessage(), ex.getMessage());
                }
            }
        }
    }



    
   
}