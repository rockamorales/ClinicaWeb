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
import org.richfaces.component.UIDataGrid;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.farmacia.TblDespachos;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleDespacho;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleDespachoPK;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.entidades.administracion.TblInstitucion;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoSalida;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.enums.EstadoDetalleDespacho;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.administracion.TblInstitucionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoSalidaFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblDespachosFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblDetalleDespachoFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantTblDespachos")
@ViewScoped
public class FrmMantTblDespachos extends PageBase{
    private TblDespachos tblDespachos = new TblDespachos();
    private TblDetalleDespacho tblDetalleDespacho = new TblDetalleDespacho();
    private List<TblDespachos> pendingDeliveringDespachos = new ArrayList<TblDespachos>();
    
    @EJB
    private transient TblDespachosFacadeLocal facade;
    @EJB
    private transient CatTipoSalidaFacadeLocal catTipoSalidaFacade;
    @EJB
    private transient TblInstitucionFacadeLocal tblInstitucionFacade;
    @EJB
    private TblDetalleDespachoFacadeLocal tblDetalleDespachoFacade;  
    
    @EJB
    private TblProductoFacadeLocal productoFacade;
    private Integer numeroDespacho;
    private List<SelectItem> catTipoSalidaList = new ArrayList<SelectItem>();
    private List<SelectItem> tblInstitucionList = new ArrayList<SelectItem>();
    private List<TblDetalleDespacho> cblDetalleDespachoList= new ArrayList<TblDetalleDespacho>();
    private List<SelectItem> tblProductoList = new ArrayList<SelectItem>();
    private TblProducto selectedProduct = new TblProducto();
    private List<TblDespachos> despachosList = new ArrayList<TblDespachos>();
    
    public TblDespachos getTblDespachos() {
        return tblDespachos;
    }

    public void setTblDespachos(TblDespachos tblDespachos) {
        this.tblDespachos = tblDespachos;
    }

    public List<TblDespachos> getDespachosList() {
        if (despachosList.isEmpty()){
            try{
                despachosList = facade.findDespachosCreados();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return despachosList;
    }

    public void setDespachosList(List<TblDespachos> despachosList) {
        this.despachosList = despachosList;
    }

    public TblDetalleDespacho getTblDetalleDespacho() {
        return tblDetalleDespacho;
    }

    public void setTblDetalleDespacho(TblDetalleDespacho tblDetalleDespacho) {
        this.tblDetalleDespacho = tblDetalleDespacho;
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
    
    public List<TblDetalleDespacho> getCblDetalleDespachoList() {
        if (cblDetalleDespachoList.isEmpty()){
          try{
              if(tblDespachos.getNumDespacho() != null){
                cblDetalleDespachoList = tblDetalleDespachoFacade.findByDespachoProducto(tblDespachos.getNumDespacho());
              }
          }catch(Exception x) {
              x.printStackTrace();
          }
        }         
        return cblDetalleDespachoList;
    }

    public void setCblDetalleIngresoProductoList(List<TblDetalleDespacho> cblDetalleDespachoList) {
        this.cblDetalleDespachoList = cblDetalleDespachoList;
    }
    
    public List<SelectItem> getCatTipoSalidaList() {
        if (catTipoSalidaList.isEmpty()){
            try{
                List<CatTipoSalida> l = catTipoSalidaFacade.findActive();
                for (CatTipoSalida catTipoSalida : l) {
                    catTipoSalidaList.add(new SelectItem(catTipoSalida.getCodTipSalida(), catTipoSalida.getNomTipSalida()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }        
        return catTipoSalidaList;
    }

    public void setCatTipoSalidaList(List<SelectItem> catTipoSalidaList) {
        this.catTipoSalidaList = catTipoSalidaList;
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

    public Integer getNumeroDespacho() {
        return numeroDespacho;
    }

    public void setNumeroIngreso(Integer numeroDespacho) {
        this.numeroDespacho = numeroDespacho;
    }

    public Integer getNumDespacho(Integer numDespacho) {
        return numDespacho;
    }

    public void setNumDespacho(Integer numDespacho) {
        this.numeroDespacho = numDespacho;
    }
        
    public void guardar(ActionEvent ae){
        try{
            if(tblDespachos.getNumDespacho() != null){
                facade.edit(tblDespachos);
            }else{
                tblDespachos.setEstDespacho(EstadoDetalleDespacho.CREADO); 
                if(this.getSessionBean().getUsuario().getNumEmpleado()==null){
                    this.addError("Su usuario no tiene un empleado asociado", "Su usuario no tiene un empleado asociado");
                }
                tblDespachos.setNumEmpleado(this.getSessionBean().getUsuario().getNumEmpleado());
                facade.create(tblDespachos);
                this.addInfo("El Ingreso ha sido guardado con exito", "El Ingreso ha sido guardado con exito");
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblDespachos = new TblDespachos();
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
            if (!validate(tblDetalleDespacho)){
                return;
            }
            tblDetalleDespacho.getTblDetalleDespachoPK().setNumDespacho(tblDespachos.getNumDespacho());
            tblDetalleDespacho.setCorDetDespacho(0);
            tblDetalleDespacho.setEstDespacho(EstadoDetalleDespacho.ENTREGADO);
            tblDetalleDespachoFacade.create(tblDetalleDespacho);
            tblDetalleDespacho = new TblDetalleDespacho();
            this.getCblDetalleDespachoList().clear();
            tblDespachos = facade.find(tblDespachos.getNumDespacho());
            this.addInfo("El producto fue agregado exitosamente", "El producto fue agregado exitosamente");
            this.selectedProduct= new TblProducto();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }

    public TblProducto getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(TblProducto selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
    
    public void seleccionarProductoByCodigo(ValueChangeEvent ve){
        try{
            selectedProduct = productoFacade.find((Integer)ve.getNewValue());
            if (selectedProduct!=null){
                tblDetalleDespacho.setTblDetalleDespachoPK(new TblDetalleDespachoPK(this.tblDespachos.getNumDespacho(), selectedProduct.getNumProducto()));
                tblDetalleDespacho.setPreUniDetDespacho(selectedProduct.getPreFinProducto());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }        
    }
    
    public void seleccionarProducto(TblProducto producto){
        if (producto!=null){
            tblDetalleDespacho.setTblDetalleDespachoPK(new TblDetalleDespachoPK(this.tblDespachos.getNumDespacho(), producto.getNumProducto()));
            tblDetalleDespacho.setPreUniDetDespacho(producto.getPreFinProducto());
        }
        
    }
    public void seleccionarProducto(ValueChangeEvent v){
        try{
            selectedProduct = productoFacade.find((Integer)v.getNewValue());
            if (selectedProduct!=null){
                tblDetalleDespacho.setTblDetalleDespachoPK(new TblDetalleDespachoPK(this.tblDespachos.getNumDespacho(), selectedProduct.getNumProducto()));
                tblDetalleDespacho.setPreUniDetDespacho(selectedProduct.getPreFinProducto());
                System.out.println("Precio enviado:"+selectedProduct.getPreFinProducto());
            }   
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    public void deleteProducto(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            TblDetalleDespacho tblDetalleDespacho1 = (TblDetalleDespacho) table.getRowData();
            tblDetalleDespachoFacade.remove(tblDetalleDespacho1);
            cblDetalleDespachoList.clear();
            tblDespachos = facade.find(tblDespachos.getNumDespacho());
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    } 
    
    public void entregarDespacho(ActionEvent ae){
        try{
            this.tblDespachos.setEstDespacho(EstadoDetalleDespacho.ENTREGADO);
            this.facade.edit(tblDespachos);
            this.cblDetalleDespachoList.clear();
            this.despachosList.clear();
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public boolean validate (TblDetalleDespacho detalle){
        boolean validationOk = true;
        if (detalle.getCanDetDespacho() <= 0){
            this.addError("Ingrese Cantidad mayor a cero", "Ingrese Cantidad mayor a cero");
            validationOk = false;   
        }
        if (detalle.getTblDetalleDespachoPK().getNumProducto() == -1  ){
            this.addError("Seleccione el Producto", "Seleccione el Producto");
            validationOk = false;   
        }
        return validationOk;
    }
    
    public void init(){
        if (!FacesContext.getCurrentInstance().isPostback()){
            if (this.getNumeroDespacho() != null && this.getNumeroDespacho()>0){
                try{
                    tblDespachos = facade.find(this.getNumeroDespacho());
                    this.getCblDetalleDespachoList().clear();
                }catch(Exception ex){
                    this.addError(ex.getMessage(), ex.getMessage());
                }
            }
        }
    }
    
    public void seleccionarFromDataGrid(ActionEvent ae){
        try{
            UIDataGrid table = (UIDataGrid) ae.getComponent().getParent();
            this.tblDespachos = facade.find(((TblDespachos)table.getRowData()).getNumDespacho());
            this.cblDetalleDespachoList.clear();
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
}