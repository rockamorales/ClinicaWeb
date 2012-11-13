/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.administracion;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.farmacia.TblAlquilerEquipo;
import sv.com.cormaria.servicios.entidades.farmacia.TblDetalleAlquilerEquipo;
import sv.com.cormaria.servicios.enums.EstadoAlquiler;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblAlquilerEquipoFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblDetalleAlquilerEquipoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean
@ViewScoped
public class FrmMantTblAlquilerEquipo extends PageBase{
    private TblAlquilerEquipo tblAlquilerEquipo = new TblAlquilerEquipo();
    private TblDetalleAlquilerEquipo tblDetalleAlq = new TblDetalleAlquilerEquipo();
    
    @EJB
    private TblAlquilerEquipoFacadeLocal facade;

    @EJB
    private TblDetalleAlquilerEquipoFacadeLocal detalleAlqFacade;

    @EJB
    private TblProductoFacadeLocal productoFacade;

    @EJB
    private TblExpedientePacientesFacadeLocal tblExpedienteFacade;    
    
    private TblExpedientePacientes tblExpediente = new TblExpedientePacientes();
       
    private List<TblDetalleAlquilerEquipo> detalleAlqList = new ArrayList<TblDetalleAlquilerEquipo>();
    
    private List<TblProducto> productosList = new ArrayList<TblProducto>();
    
    private Integer numSolAlquiler;
    
    

    public List<TblProducto> getProductosList() {
        if (productosList.isEmpty()){
            try{
                productosList = productoFacade.findProductoAlquiler();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return productosList;
    }

    public void setProductosList(List<TblProducto> productosList) {
        this.productosList = productosList;
    }

    public TblAlquilerEquipo getTblAlquilerEquipo() {
        return tblAlquilerEquipo;
    }

    public void setTblAlquilerEquipo(TblAlquilerEquipo tblAlquilerEquipo) {
        this.tblAlquilerEquipo = tblAlquilerEquipo;
    }

    public TblDetalleAlquilerEquipo getTblDetalleAlq() {
        return tblDetalleAlq;
    }

    public void setTblDetalleAlq(TblDetalleAlquilerEquipo tblDetalleAlq) {
        this.tblDetalleAlq = tblDetalleAlq;
    }

    public List<TblDetalleAlquilerEquipo> getDetalleAlqList() {
        if (detalleAlqList.isEmpty()){
            if (this.getTblAlquilerEquipo().getNumSolAlquiler()!=null && this.getTblAlquilerEquipo().getNumSolAlquiler()>0){
                try{
                    detalleAlqList = this.detalleAlqFacade.findByNumSolAlquiler(this.getTblAlquilerEquipo().getNumSolAlquiler());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        return detalleAlqList;
    }

    public void setDetalleAlqList(List<TblDetalleAlquilerEquipo> detalleAlqList) {
        this.detalleAlqList = detalleAlqList;
    }

    public Integer getNumSolAlquiler() {
        return numSolAlquiler;
    }

    public void setNumSolAlquiler(Integer numSolAlquiler) {
        this.numSolAlquiler = numSolAlquiler;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(tblAlquilerEquipo.getNumSolAlquiler() != null && tblAlquilerEquipo.getNumSolAlquiler()>0){
                facade.edit(tblAlquilerEquipo);
                this.addInfo("Se Modifico el Alquiler de Equipo con Exito", "Se Modifico el Alquiler de Equipo con Exito");
            }else{
                //tblAlquilerEquipo.setNumExpediente(this.);
                tblAlquilerEquipo.setEstAlquiler(EstadoAlquiler.CREADO);
                facade.create(tblAlquilerEquipo);
                this.addInfo("Se Agrego el Alquiler de Equipo con Exito", "Se Agrego el Alquiler de Equipo con Exito");
            }
        }catch(Exception x){
            x.printStackTrace();
            //this.addError(x.getMessage(), x.getMessage());
            this.addError("Error de Datos, No se creo el Alquiler de Equipo", "Error de Datos, No se creo el Alquiler de Equipo");
        }
    }
    
    public void agregarDetalleReq(ActionEvent ae){
        try{
            if (this.getTblDetalleAlq().getTblDetalleAlquilerEquipoPK().getNumSolAlquiler()!=null && this.getTblDetalleAlq().getTblDetalleAlquilerEquipoPK().getNumSolAlquiler()>0){
                this.tblDetalleAlq = detalleAlqFacade.edit(tblDetalleAlq);
            }else{
                tblDetalleAlq.getTblDetalleAlquilerEquipoPK().setNumSolAlquiler(this.getTblAlquilerEquipo().getNumSolAlquiler());
                this.tblDetalleAlq = detalleAlqFacade.create(tblDetalleAlq);
            }
            this.tblDetalleAlq = new TblDetalleAlquilerEquipo();
            this.detalleAlqList.clear();
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public void nuevo(ActionEvent ae){
        this.tblAlquilerEquipo = new TblAlquilerEquipo();
        this.detalleAlqList.clear();
    }
    
    public void init(){
        if (this.getNumSolAlquiler()!=null && this.getNumSolAlquiler()>0){
            try{
                this.tblAlquilerEquipo = this.facade.find(this.getNumSolAlquiler());
                this.tblExpediente = tblAlquilerEquipo.getExpediente();
            }catch(Exception ex){
                this.addError(ex.getMessage(), ex.getMessage());
            }
        }
    }
    
    public void deleteDetalle(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            TblDetalleAlquilerEquipo detalle = (TblDetalleAlquilerEquipo) table.getRowData();
            detalleAlqFacade.remove(detalle);
            detalleAlqList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }    
    
    public String modificar(){
        return "frmMantTblAlquilerEquipo.jsf?faces-redirect=true&numSolAlquiler="+this.getTblAlquilerEquipo().getNumSolAlquiler();
    }
    
    public void eliminar(ActionEvent ae){
        try{
            this.facade.remove(tblAlquilerEquipo);
            this.addInfo("El registro ha sido eliminado", "El registro ha sido eliminado");
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public TblExpedientePacientes getTblExpediente() {
        return tblExpediente;
    }

    public void setTblExpediente(TblExpedientePacientes tblExpediente) {
        this.tblExpediente = tblExpediente;
    }
        
    public void searchExpedienteByNum(){
      try{
          this.tblExpediente = tblExpedienteFacade.find(this.tblExpediente.getNumExpediente());
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
  
   public void seleccionar(ActionEvent ae){
    try{
        UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
        System.out.println("Buscando el expediente... "+((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        this.tblExpediente = tblExpedienteFacade.find(((TblExpedientePacientes)table.getRowData()).getNumExpediente());
        this.tblAlquilerEquipo.setNumExpediente(this.tblExpediente.getNumExpediente());
        System.out.println("Expediente encontrado... ");
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
  }
     
  public void buscar(ActionEvent ae){
     ExpedienteDataModel model = (ExpedienteDataModel) this.getBean("#{expedienteDataModel}", ExpedienteDataModel.class);
     model.clear();
  }    
}