/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.consultasmedicas;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataGrid;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ConsultasPagadasDataModel;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ConsultasSignosVitalesDataModel;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ExpedienteDataModel;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblConsultas;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblDetalleReceta;
import sv.com.cormaria.servicios.entidades.consultasmedicas.TblRecetaMedica;
import sv.com.cormaria.servicios.entidades.farmacia.TblProducto;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblConsultasFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblDetalleRecetaFacadeLocal;
import sv.com.cormaria.servicios.facades.consultasmedicas.TblRecetaMedicaFacadeLocal;
import sv.com.cormaria.servicios.facades.farmacia.TblProductoFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmMantTblConsultas extends PageBase {

    
    private TblConsultas consulta = new TblConsultas();
    
    @EJB
    private TblConsultasFacadeLocal tblConsultasFacade;

    @EJB
    private TblRecetaMedicaFacadeLocal tblRecetaMedicaFacade;

    @EJB
    private TblDetalleRecetaFacadeLocal tblDetalleRecetaFacade;    

    @EJB
    private TblProductoFacadeLocal tblProductoFacade;
    
    private TblRecetaMedica recetaMedica = new TblRecetaMedica();
    
    private List<TblDetalleReceta> detalleReceta = new ArrayList<TblDetalleReceta>();

    private TblDetalleReceta receta = new TblDetalleReceta();
    
    private List<TblProducto> productosList = new ArrayList<TblProducto>();
    
    private Integer numConsulta;
    
    
    /**
     * Creates a new instance of FrmMantTblConsultas
     */
    public FrmMantTblConsultas() {
    }

    public TblConsultas getConsulta() {
        return consulta;
    }

    public void setConsulta(TblConsultas consulta) {
        this.consulta = consulta;
    }

    public List<TblProducto> getProductosList() {
        if (productosList.isEmpty()){
            try{
                productosList = tblProductoFacade.findMedicamentos();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return productosList;
    }

    public void setProductosList(List<TblProducto> productosList) {
        this.productosList = productosList;
    }

    public List<TblDetalleReceta> getDetalleReceta() {
        if( detalleReceta.isEmpty()){
            if (this.getRecetaMedica().getNumReceta()!=null && this.getRecetaMedica().getNumReceta()>0){
                try{
                    detalleReceta = tblDetalleRecetaFacade.findByNumReceta(this.getRecetaMedica().getNumReceta());
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        return detalleReceta;
    }

    public void setDetalleReceta(List<TblDetalleReceta> detalleReceta) {
        this.detalleReceta = detalleReceta;
    }

    public TblDetalleReceta getReceta() {
        return receta;
    }

    public void setReceta(TblDetalleReceta receta) {
        this.receta = receta;
    }
    
    public TblRecetaMedica getRecetaMedica() {
        return recetaMedica;
    }

    public void setRecetaMedica(TblRecetaMedica recetaMedica) {
        this.recetaMedica = recetaMedica;
    }

    public Integer getNumConsulta() {
        return numConsulta;
    }

    public void setNumConsulta(Integer numConsulta) {
        this.numConsulta = numConsulta;
    }
    
    public void guardar(ActionEvent ae){
        try{
            consulta = tblConsultasFacade.edit(consulta);
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public void init(){
        if (numConsulta != null && numConsulta >0){
            try{
                consulta = tblConsultasFacade.find(numConsulta);
                recetaMedica = tblRecetaMedicaFacade.findByNumExpediente(consulta.getNumConsulta());
            }catch(Exception ex){
                ex.printStackTrace();
                this.addError(ex.getMessage(), ex.getMessage());
            }
        }
    }

    
   public void seleccionar(ActionEvent ae){
    try{
        UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
        this.consulta = tblConsultasFacade.find(((TblConsultas)table.getRowData()).getNumConsulta());
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
   }

   public void seleccionarFromDataGrid(ActionEvent ae){
    try{
        UIDataGrid table = (UIDataGrid) ae.getComponent().getParent();
        this.consulta = tblConsultasFacade.find(((TblConsultas)table.getRowData()).getNumConsulta());
        
    }catch(Exception x){
        x.printStackTrace();
        this.addError(x.getMessage(), x.getMessage());
    }
   }
   
   
   public void buscar(ActionEvent ae){
      ExpedienteDataModel model = (ExpedienteDataModel) this.getBean("#{expedienteDataModel}", ExpedienteDataModel.class);
      model.clear();
   }    
    
    public void searchExpedienteByNum(){
      try{
          this.consulta = tblConsultasFacade.find(this.consulta.getNumConsulta());
          if (this.consulta == null){
              this.addInfo("No se encontro ninguna consulta con el numero especificado", "No se encontro ninguna consulta con el numero especificado");
          }
      }catch(Exception ex){
          this.addError(ex.getMessage(), ex.getMessage());
      }
   }
    
    
    public void clear(ActionEvent ae){
        ConsultasSignosVitalesDataModel dataModel = (ConsultasSignosVitalesDataModel) this.getBean("#{consultasSignosVitalesDataModel}", ConsultasSignosVitalesDataModel.class);
        dataModel.clear();
    }    
}