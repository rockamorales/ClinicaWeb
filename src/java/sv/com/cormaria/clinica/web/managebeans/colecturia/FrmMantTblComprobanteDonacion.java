/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.colecturia;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
import sv.com.cormaria.servicios.entidades.colecturia.TblComprobanteDonacion;
import sv.com.cormaria.servicios.entidades.colecturia.TblDetalleComprobanteDonacion;
import sv.com.cormaria.servicios.entidades.colecturia.TblDetalleComprobanteDonacionPK;
import sv.com.cormaria.servicios.entidades.farmacia.TblProducto;
import sv.com.cormaria.servicios.enums.EstadoComprobanteDonacion;
import sv.com.cormaria.servicios.facades.catalogos.CatBancosFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatCarismaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonanteFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoPagoFacadeLocal;
import sv.com.cormaria.servicios.facades.colecturia.TblComprobanteDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.colecturia.TblDetalleComprobanteDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantTblComprobanteDonacion")
@RequestScoped
public class FrmMantTblComprobanteDonacion extends PageBase{
    @ManagedProperty(value="#{tblComprobanteDonacionForm}")
    private TblComprobanteDonacionForm tblComprobanteDonacion;
    @ManagedProperty(value="#{tblDetalleComprobanteDonacionForm}")
    private TblDetalleComprobanteDonacionForm tblDetalleComprobanteDonacion;    
    @EJB
    private transient TblComprobanteDonacionFacadeLocal facade;
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
    private TblDetalleComprobanteDonacionFacadeLocal cblDetalleComprobanteDonacionFacade;  
    @EJB
    private TblProductoFacadeLocal productoFacade;
    @ManagedProperty(value ="#{param.numComDonacion}")
    private Integer numComDonacion;
    private List<SelectItem> catTipoDonacionList = new ArrayList<SelectItem>();
    private List<SelectItem> catTipoDonanteList = new ArrayList<SelectItem>();
    private List<SelectItem> catTipoPagoList = new ArrayList<SelectItem>();
    private List<SelectItem> catBancosList = new ArrayList<SelectItem>();
    private List<SelectItem> catCarismaList = new ArrayList<SelectItem>();
    private List<TblDetalleComprobanteDonacion> cblDetalleComprobanteDonacionList= new ArrayList<TblDetalleComprobanteDonacion>();
    private List<SelectItem> tblProductoList = new ArrayList<SelectItem>();

    public List<TblDetalleComprobanteDonacion> getCblDetalleComprobanteDonacionList() {
        if (cblDetalleComprobanteDonacionList.isEmpty()){
          try{
              System.out.println("Numero Donacion : " + tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion());
              if(tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion() != null){
                cblDetalleComprobanteDonacionList = cblDetalleComprobanteDonacionFacade.findByComprobanteDonacion(tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion());
              }
          }catch(Exception x) {
              x.printStackTrace();
          }
        }         
        return cblDetalleComprobanteDonacionList;
    }

    public void setCblDetalleComprobanteDonacionList(List<TblDetalleComprobanteDonacion> cblDetalleComprobanteDonacionList) {
        this.cblDetalleComprobanteDonacionList = cblDetalleComprobanteDonacionList;
    }

    public TblDetalleComprobanteDonacionForm getTblDetalleComprobanteDonacion() {
        return tblDetalleComprobanteDonacion;
    }

    public void setTblDetalleComprobanteDonacion(TblDetalleComprobanteDonacionForm tblDetalleComprobanteDonacion) {
        this.tblDetalleComprobanteDonacion = tblDetalleComprobanteDonacion;
    }
    

    
    public TblComprobanteDonacionForm getTblComprobanteDonacion() {
        return tblComprobanteDonacion;
    }

    public void setTblComprobanteDonacion(TblComprobanteDonacionForm tblComprobanteDonacion) {
        this.tblComprobanteDonacion = tblComprobanteDonacion;
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
    
    
    public Integer getNumComDonacion() {
        return numComDonacion;
    }

    public void setNumComDonacion(Integer numComDonacion) {
        if(numComDonacion != null){
            try{
            tblComprobanteDonacion.setTblComprobanteDonacion(facade.find(numComDonacion));
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.numComDonacion = numComDonacion;
    }
        
    public void guardar(ActionEvent ae){
        try{
            if(tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion() != null){
                facade.edit(tblComprobanteDonacion.getTblComprobanteDonacion());
            }else{
                tblComprobanteDonacion.getTblComprobanteDonacion().setEstComDonacion(EstadoComprobanteDonacion.EMITIDO);
                facade.create(tblComprobanteDonacion.getTblComprobanteDonacion());
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblComprobanteDonacion.setTblComprobanteDonacion(new TblComprobanteDonacion());
    }
    
        
    public void recibirPago(ActionEvent rp){
        try{
            tblComprobanteDonacion.getTblComprobanteDonacion().setEstComDonacion(EstadoComprobanteDonacion.PAGADO);
            facade.recibirPago(tblComprobanteDonacion.getTblComprobanteDonacion());

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
            if (!validate(tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion())){
                return;
            }
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setTotIteComDonacion(tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().getPreUniComDonacion()*tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().getCanProComDonacion());
            cblDetalleComprobanteDonacionFacade.create(tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion());
            tblDetalleComprobanteDonacion.setTblDetalleComprobanteDonacion(new TblDetalleComprobanteDonacion());
            this.getCblDetalleComprobanteDonacionList().clear();
            tblComprobanteDonacion.setTblComprobanteDonacion(facade.find(tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion()));
            this.addInfo("El producto fue agregado exitosamente", "El producto fue agregado exitosamente");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void seleccionarProducto(TblProducto producto){
        System.out.println("Producto: "+producto);
        if (producto!=null){
            System.out.println("Producto: "+producto.getNomProducto());
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setTblDetalleComprobanteDonacionPK(new TblDetalleComprobanteDonacionPK(this.tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion(), producto.getNumProducto()));
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setPreUniComDonacion(producto.getPreFinProducto());
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setPresentacion(producto.getPreProducto());
        }
    }
    public void seleccionarProducto(ValueChangeEvent v){
        try{
        TblProducto producto = productoFacade.find((Integer)v.getNewValue());
        if (producto!=null){
            System.out.println("Producto: "+producto.getNomProducto());
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setTblDetalleComprobanteDonacionPK(new TblDetalleComprobanteDonacionPK(this.tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion(), producto.getNumProducto()));
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setPreUniComDonacion(producto.getPreFinProducto());
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setTotIteComDonacion(producto.getPreFinProducto()*tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().getCanProComDonacion());
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacion().setPresentacion(producto.getPreProducto());
        }   
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    public void deleteProducto(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            TblDetalleComprobanteDonacion tblDetalleComprobanteDonacion1 = (TblDetalleComprobanteDonacion) table.getRowData();
            cblDetalleComprobanteDonacionFacade.remove(tblDetalleComprobanteDonacion1);
            cblDetalleComprobanteDonacionList.clear();
            tblComprobanteDonacion.setTblComprobanteDonacion(facade.find(tblComprobanteDonacion.getTblComprobanteDonacion().getNumComDonacion()));            
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    } 
    
    public boolean validate (TblDetalleComprobanteDonacion detalle){
        boolean validationOk = true;
        if (detalle.getCanProComDonacion() <= 0){
            this.addError("Ingrese Cantidad mayor a cero", "Ingrese Cantidad mayor a cero");
            validationOk = false;   
        }
        return validationOk;
    }
}
