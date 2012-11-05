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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataGrid;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ComprobanteDonacionEmitidosDataModel;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ServiciosEnfermeriaPagadosDataModel;
import sv.com.cormaria.servicios.entidades.catalogos.CatBancos;
import sv.com.cormaria.servicios.entidades.catalogos.CatCarisma;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonacion;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoDonante;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoPago;
import sv.com.cormaria.servicios.entidades.colecturia.TblComprobanteDonacion;
import sv.com.cormaria.servicios.entidades.colecturia.TblDetalleComprobanteDonacion;
import sv.com.cormaria.servicios.entidades.colecturia.TblDetalleComprobanteDonacionPK;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.enums.EstadoComprobanteDonacion;
import sv.com.cormaria.servicios.enums.TipoComprobanteDonacion;
import sv.com.cormaria.servicios.facades.catalogos.CatBancosFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatCarismaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoDonanteFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoPagoFacadeLocal;
import sv.com.cormaria.servicios.facades.colecturia.TblComprobanteDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.colecturia.TblDetalleComprobanteDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;
import sv.com.cormaria.servicios.helpers.NumToText;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class FrmMantTblDonacionesVoluntarias extends PageBase{
    private TblComprobanteDonacion tblComprobanteDonacion = new TblComprobanteDonacion();
    private TblDetalleComprobanteDonacion tblDetalleComprobanteDonacion = new TblDetalleComprobanteDonacion();    
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
    private Integer numComDonacion;
    private List<SelectItem> catTipoDonacionList = new ArrayList<SelectItem>();
    private List<SelectItem> catTipoDonanteList = new ArrayList<SelectItem>();
    private List<SelectItem> catTipoPagoList = new ArrayList<SelectItem>();
    private List<SelectItem> catBancosList = new ArrayList<SelectItem>();
    private List<SelectItem> catCarismaList = new ArrayList<SelectItem>();
    private List<TblDetalleComprobanteDonacion> cblDetalleComprobanteDonacionList= new ArrayList<TblDetalleComprobanteDonacion>();
    private List<SelectItem> tblProductoList = new ArrayList<SelectItem>();

    public TblComprobanteDonacion getTblComprobanteDonacion() {
        return tblComprobanteDonacion;
    }

    public void setTblComprobanteDonacion(TblComprobanteDonacion tblComprobanteDonacion) {
        this.tblComprobanteDonacion = tblComprobanteDonacion;
    }

    public TblDetalleComprobanteDonacion getTblDetalleComprobanteDonacion() {
        return tblDetalleComprobanteDonacion;
    }

    public void setTblDetalleComprobanteDonacion(TblDetalleComprobanteDonacion tblDetalleComprobanteDonacion) {
        this.tblDetalleComprobanteDonacion = tblDetalleComprobanteDonacion;
    }
    
    public List<TblDetalleComprobanteDonacion> getCblDetalleComprobanteDonacionList() {
        if (cblDetalleComprobanteDonacionList.isEmpty()){
          try{
              if(tblComprobanteDonacion.getNumComDonacion() != null){
                cblDetalleComprobanteDonacionList = cblDetalleComprobanteDonacionFacade.findByComprobanteDonacion(tblComprobanteDonacion.getNumComDonacion());
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
        this.numComDonacion = numComDonacion;
    }
        
    public void guardar(ActionEvent ae){
        try{
            if (!validateHeader(tblComprobanteDonacion)){
                return;
            }
            if(tblComprobanteDonacion.getNumComDonacion() != null){
                facade.edit(tblComprobanteDonacion);
            }else{
                tblComprobanteDonacion.setEstComDonacion(EstadoComprobanteDonacion.EMITIDO);
                facade.create(tblComprobanteDonacion);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.tblComprobanteDonacion = new TblComprobanteDonacion();
    }
    
    public boolean validatePayment(TblComprobanteDonacion tblComprobanteDonacion){
        boolean validationOk = true;
        if (tblComprobanteDonacion.getMontoRecibido()<=0 || tblComprobanteDonacion.getMontoRecibido()<tblComprobanteDonacion.getTotDonacion()){
            validationOk = false;
            this.addError("El monto recibido no puede ser menor al total de la donacion", "El monto recibido no puede ser menor al total de la donacion");
        }
        return validationOk;
    }
        
    public void recibirPago(ActionEvent rp){
        try{
            if (!validateHeader(tblComprobanteDonacion) || !validatePayment(tblComprobanteDonacion)){
                return;
            }
            tblComprobanteDonacion.setEstComDonacion(EstadoComprobanteDonacion.PAGADO);
            facade.recibirPago(tblComprobanteDonacion);
            ComprobanteDonacionEmitidosDataModel dataModel = (ComprobanteDonacionEmitidosDataModel) this.getBean("#{comprobanteDonacionEmitidosDataModel}", ComprobanteDonacionEmitidosDataModel.class);
            dataModel.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }  
    }
    
    public void marcarDonacionPagada(ActionEvent rp){
        try{
            if (!validateHeader(tblComprobanteDonacion)){
                return;
            }
            tblComprobanteDonacion.setEstComDonacion(EstadoComprobanteDonacion.PAGADO);
            String letras = NumToText.convertirLetras(tblComprobanteDonacion.getTotDonacion());
            tblComprobanteDonacion.setCanLetras(letras);
            facade.recibirPago(tblComprobanteDonacion);
            ComprobanteDonacionEmitidosDataModel dataModel = (ComprobanteDonacionEmitidosDataModel) this.getBean("#{comprobanteDonacionEmitidosDataModel}", ComprobanteDonacionEmitidosDataModel.class);
            dataModel.clear();
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
            if (!validate(tblDetalleComprobanteDonacion)){
                return;
            }
            tblDetalleComprobanteDonacion.getTblDetalleComprobanteDonacionPK().setNumComDonacion(tblComprobanteDonacion.getNumComDonacion());
            tblDetalleComprobanteDonacion.setTotIteComDonacion(tblDetalleComprobanteDonacion.getPreUniComDonacion()*tblDetalleComprobanteDonacion.getCanProComDonacion());
            cblDetalleComprobanteDonacionFacade.create(tblDetalleComprobanteDonacion);
            tblDetalleComprobanteDonacion = new TblDetalleComprobanteDonacion();
            this.getCblDetalleComprobanteDonacionList().clear();
            tblComprobanteDonacion = facade.find(tblComprobanteDonacion.getNumComDonacion());
            this.addInfo("El producto fue agregado exitosamente", "El producto fue agregado exitosamente");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    
    public void seleccionarProducto(TblProducto producto){
        if (producto!=null){
            tblDetalleComprobanteDonacion.setTblDetalleComprobanteDonacionPK(new TblDetalleComprobanteDonacionPK(this.tblComprobanteDonacion.getNumComDonacion(), producto.getNumProducto()));
            tblDetalleComprobanteDonacion.setPreUniComDonacion(producto.getPreFinProducto());
            tblDetalleComprobanteDonacion.setPresentacion(producto.getCatPresentacionProducto().getNomPreProducto());
        }
    }
    public void seleccionarProducto(ValueChangeEvent v){
        try{
        TblProducto producto = productoFacade.find((Integer)v.getNewValue());
        if (producto!=null){
            tblDetalleComprobanteDonacion.setTblDetalleComprobanteDonacionPK(new TblDetalleComprobanteDonacionPK(this.tblComprobanteDonacion.getNumComDonacion(), producto.getNumProducto()));
            tblDetalleComprobanteDonacion.setPreUniComDonacion(producto.getPreFinProducto());
            tblDetalleComprobanteDonacion.setTotIteComDonacion(producto.getPreFinProducto()*tblDetalleComprobanteDonacion.getCanProComDonacion());
            tblDetalleComprobanteDonacion.setPresentacion(producto.getCatPresentacionProducto().getNomPreProducto());
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
            tblComprobanteDonacion = facade.find(tblComprobanteDonacion.getNumComDonacion());
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    } 
    
    public boolean validateHeader(TblComprobanteDonacion header){
        boolean validationOk = true;
        if (header.getTipComprobante()!=null && (header.getTipComprobante() == TipoComprobanteDonacion.COBRO || header.getTipComprobante() == TipoComprobanteDonacion.DEVOLUCION)){
            if (header.getNumFacDonacion()==null || header.getNumFacDonacion().trim().equals("")){
                validationOk = false;
                this.addError("Por favor ingrese el numero de documento pre-impreso", "Por favor ingrese el numero de documento pre-impreso");
            }
        }
        return validationOk;
    }
    
    public boolean validate (TblDetalleComprobanteDonacion detalle){
        boolean validationOk = true;
        if (detalle.getCanProComDonacion() <= 0){
            this.addError("Ingrese Cantidad mayor a cero", "Ingrese Cantidad mayor a cero");
            validationOk = false;   
        }
        return validationOk;
    }
    
    public void init(){
        //this.getNumComDonacion()!=null && this.getNumComDonacion() > 0 && (tblComprobanteDonacion.getNumComDonacion()==null || tblComprobanteDonacion.getNumComDonacion()<=0)
        if (!FacesContext.getCurrentInstance().isPostback()){
            try{
                if (this.getNumComDonacion()!=null && this.getNumComDonacion()>0){
                    tblComprobanteDonacion = facade.find(this.getNumComDonacion());
                }
                this.getCblDetalleComprobanteDonacionList().clear();
            }catch(Exception ex){
                this.addError(ex.getMessage(), ex.getMessage());
            }
            if (tblComprobanteDonacion.getNumComDonacion()==null || tblComprobanteDonacion.getNumComDonacion()<=0){
                tblComprobanteDonacion.setTipComprobante(TipoComprobanteDonacion.DONACION);
            }
        }
    }
    
    public void changeTipoPago(){
        System.out.println("Changing tipo pago.....");
        tblComprobanteDonacion.setMontoRecibido(tblComprobanteDonacion.getTotDonacion());
        tblComprobanteDonacion.setCambio(0.00F);
    }
  
  public void eliminar(ActionEvent ae){
    try{
        UIDataGrid table = (UIDataGrid) ae.getComponent().getParent();
        this.cblDetalleComprobanteDonacionList.clear();
        this.facade.remove((TblComprobanteDonacion)table.getRowData());
        ComprobanteDonacionEmitidosDataModel dataModel = (ComprobanteDonacionEmitidosDataModel) this.getBean("#{comprobanteDonacionEmitidosDataModel}", ComprobanteDonacionEmitidosDataModel.class);
        dataModel.clear();
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
  }
    
  public void seleccionarFromDataGrid(ActionEvent ae){
    try{
        UIDataGrid table = (UIDataGrid) ae.getComponent().getParent();
        this.tblComprobanteDonacion = this.facade.find(((TblComprobanteDonacion)table.getRowData()).getNumComDonacion());
        this.cblDetalleComprobanteDonacionList.clear();
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
   }
       
   public void clear(ActionEvent ae){
        ComprobanteDonacionEmitidosDataModel dataModel = (ComprobanteDonacionEmitidosDataModel) this.getBean("#{comprobanteDonacionEmitidosDataModel}", ComprobanteDonacionEmitidosDataModel.class);
        dataModel.clear();
   }
   
   public void calcularMontoLetras(){
       tblComprobanteDonacion.setCanLetras(NumToText.convertirLetras(tblComprobanteDonacion.getTotDonacion()));
   }
}