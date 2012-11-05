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
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatBancos;
import sv.com.cormaria.servicios.entidades.catalogos.CatCarisma;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonacion;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonante;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoPago;
import sv.com.cormaria.servicios.entidades.farmacia.TblIngresosProducto;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleIngresoProducto;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleIngresoProductoPK;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.enums.EstadoComprobanteDonacion;
import sv.com.cormaria.servicios.facades.catalogos.CatBancosFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatCarismaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonanteFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoPagoFacadeLocal;
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
    private transient CatTipoDonacionFacadeLocal catTipoDonacionFacade;
    @EJB
    private transient CatTipoDonanteFacadeLocal catTipoDonanteFacade;
    @EJB
    private transient CatTipoPagoFacadeLocal catTipoPagoFacade;
    @EJB
    private transient CatBancosFacadeLocal catBancosFacade;
    @EJB
    private transient CatCarismaFacadeLocal catCarismaFacade;
    @EJB
    private TblDetalleIngresoProductoFacadeLocal cblDetalleIngresoProductoFacade;  
    @EJB
    private TblProductoFacadeLocal productoFacade;
    private Integer numComDonacion;
    private List<SelectItem> catTipoDonacionList = new ArrayList<SelectItem>();
    private List<SelectItem> catTipoDonanteList = new ArrayList<SelectItem>();
    private List<SelectItem> catTipoPagoList = new ArrayList<SelectItem>();
    private List<SelectItem> catBancosList = new ArrayList<SelectItem>();
    private List<SelectItem> catCarismaList = new ArrayList<SelectItem>();
    private List<TblDetalleIngresoProducto> cblDetalleIngresoProductoList= new ArrayList<TblDetalleIngresoProducto>();
    private List<SelectItem> tblProductoList = new ArrayList<SelectItem>();

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

    public List<SelectItem> getTblProductoList() {
          if (tblProductoList.isEmpty()){
            try{
                List<TblProducto> l = productoFacade.findActive();
                
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
    
    public List<SelectItem> getCatCarismaList() {
          if (catCarismaList.isEmpty()){
            try{
                List<CatCarisma> l = catCarismaFacade.findActive();
                for (CatCarisma catCarisma : l) {
                    catCarismaList.add(new SelectItem(catCarisma.getCodCarisma(), catCarisma.getNomCarisma()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }        
        return catCarismaList;
    }

    public void setCatCarismaList(List<SelectItem> catCarismaList) {
        this.catCarismaList = catCarismaList;
    }
    
    public List<SelectItem> getCatBancosList() {
          if (catBancosList.isEmpty()){
            try{
                List<CatBancos> l = catBancosFacade.findActive();
                for (CatBancos catBancos : l) {
                    catBancosList.add(new SelectItem(catBancos.getCodBanco(), catBancos.getNomBanco()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }         
        return catBancosList;
    }

    public void setCatBancosList(List<SelectItem> catBancosList) {
        this.catBancosList = catBancosList;
    }
    

    public List<SelectItem> getCatTipoPagoList() {
          if (catTipoPagoList.isEmpty()){
            try{
                List<CatTipoPago> l = catTipoPagoFacade.findActive();
                for (CatTipoPago catTipoPago : l) {
                    catTipoPagoList.add(new SelectItem(catTipoPago.getCodTipPago(), catTipoPago.getNomTipPago()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        } 
        return catTipoPagoList;
    }

    public void setCatTipoPagoList(List<SelectItem> catTipoPagoList) {
        this.catTipoPagoList = catTipoPagoList;
    }
    
    public List<SelectItem> getCatTipoDonanteList() {
        if (catTipoDonanteList.isEmpty()){
            try{
                List<CatTipoDonante> l = catTipoDonanteFacade.findActive();
                for (CatTipoDonante catTipoDonante : l) {
                    catTipoDonanteList.add(new SelectItem(catTipoDonante.getCodTipDonante(), catTipoDonante.getNomTipDonante()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }        
        return catTipoDonanteList;
    }

    public void setCatTipoDonanteList(List<SelectItem> catTipoDonanteList) {
        this.catTipoDonanteList = catTipoDonanteList;
    }

    
    public List<SelectItem> getCatTipoDonacionList() {
        if (catTipoDonacionList.isEmpty()){
            try{
                List<CatTipoDonacion> l = catTipoDonacionFacade.findActive();
                for (CatTipoDonacion catTipoDonacion : l) {
                    catTipoDonacionList.add(new SelectItem(catTipoDonacion.getCodTipDonacion(), catTipoDonacion.getNomTipDonacion()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
            
        return catTipoDonacionList;
    }

    public void setCatTipoDonacionList(List<SelectItem> catTipoDonacionList) {
        this.catTipoDonacionList = catTipoDonacionList;
    }
    
    
    public Integer get() {
        return numComDonacion;
    }

    public void setNumIngreso(Integer numComDonacion) {
        this.numComDonacion = numComDonacion;
    }
        
    public void guardar(ActionEvent ae){
        try{
            if(tblIngresosProducto.getNumIngreso() != null){
                facade.edit(tblIngresosProducto);
            }else{
                tblIngresosProducto.setEstComDonacion(EstadoComprobanteDonacion.EMITIDO);
                facade.create(tblIngresosProducto);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblIngresosProducto = new TblIngresosProducto();
    }
    
        
    public void recibirPago(ActionEvent rp){
        try{
            tblIngresosProducto.setEstIngresoProducto(EstadoComprobanteDonacion.PAGADO);
            facade.recibirPago(tblIngresosProducto);

        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }  
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
            tblDetalleIngresoProducto.setTotIteComDonacion(tblDetalleIngresoProducto.getPreUniComDonacion()*tblDetalleIngresoProducto.getCanProComDonacion());
            cblDetalleIngresoProductoFacade.create(tblDetalleIngresoProducto);
            tblDetalleIngresoProducto = new TblDetalleIngresoProducto();
            this.getCblDetalleIngresoProductoList().clear();
            tblIngresosProducto = facade.find(tblIngresosProducto.getNumIngreso());
            this.addInfo("El producto fue agregado exitosamente", "El producto fue agregado exitosamente");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void seleccionarProducto(TblProducto producto){
        if (producto!=null){
            tblDetalleIngresoProducto.setTblDetalleIngresoProductoPK(new TblDetalleIngresoProductoPK(this.tblIngresosProducto.getNumIngreso(), producto.getNumProducto()));
            tblDetalleIngresoProducto.setPreUniComDonacion(producto.getPreFinProducto());
            tblDetalleIngresoProducto.setPresentacion(producto.getCatPresentacionProducto().getNomPreProducto());
        }
    }
    public void seleccionarProducto(ValueChangeEvent v){
        try{
        TblProducto producto = productoFacade.find((Integer)v.getNewValue());
        if (producto!=null){
            tblDetalleIngresoProducto.setTblDetalleIngresoProductoPK(new TblDetalleIngresoProductoPK(this.tblIngresosProducto.getNumIngreso(), producto.getNumProducto()));
            tblDetalleIngresoProducto.setPreUniComDonacion(producto.getPreFinProducto());
            tblDetalleIngresoProducto.setTotIteComDonacion(producto.getPreFinProducto()*tblDetalleIngresoProducto.getCanProComDonacion());
            tblDetalleIngresoProducto.setPresentacion(producto.getCatPresentacionProducto().getNomPreProducto());
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
        if (detalle.getCanProComDonacion() <= 0){
            this.addError("Ingrese Cantidad mayor a cero", "Ingrese Cantidad mayor a cero");
            validationOk = false;   
        }
        return validationOk;
    }
    
    public void init(){
        if (this.getNumIngreso()!=null && this.getNumIngreso() > 0 && (tblIngresosProducto.getNumIngreso()==null || tblIngresosProducto.getNumIngreso()<=0)){
            try{
                tblIngresosProducto = facade.find(this.getNumIngreso());
                this.getCblDetalleIngresoProductoList().clear();
            }catch(Exception ex){
                this.addError(ex.getMessage(), ex.getMessage());
            }
        }
    }
    
    public void changeTipoPago(){
        System.out.println("Changing tipo pago.....");
    }
}