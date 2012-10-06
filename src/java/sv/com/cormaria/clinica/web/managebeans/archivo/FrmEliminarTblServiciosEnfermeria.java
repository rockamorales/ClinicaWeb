/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ServiciosEnfermeriaDataModel;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServiciosEnfermeria;
import sv.com.cormaria.servicios.facades.archivo.TblServiciosEnfermeriaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServiciosEnfermeriaFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmEliminarTblServiciosEnfermeria extends PageBase {

    @EJB
    private CatTipoServiciosEnfermeriaFacadeLocal tipoServiciosFacade;

    @EJB
    private TblServiciosEnfermeriaFacadeLocal tblServiciosFacade;
    
    private List<CatTipoServiciosEnfermeria> tipoServiciosList = new ArrayList<CatTipoServiciosEnfermeria>();
    /**
     * Creates a new instance of FrmConsultaTblServiciosEnfermeria
     */
    public FrmEliminarTblServiciosEnfermeria() {
    }

    public List<CatTipoServiciosEnfermeria> getTipoServiciosList() {
        if (tipoServiciosList.isEmpty()){
            try{
                tipoServiciosList = tipoServiciosFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tipoServiciosList;
    }

    public void setTipoServiciosList(List<CatTipoServiciosEnfermeria> tipoServiciosList) {
        this.tipoServiciosList = tipoServiciosList;
    }
    
    public void buscar(ActionEvent ae){
        ServiciosEnfermeriaDataModel model = (ServiciosEnfermeriaDataModel) this.getBean("#{serviciosEnfermeriaDataModel}", ServiciosEnfermeriaDataModel.class);
        model.clear();
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            TblServiciosEnfermeria tblServicio = (TblServiciosEnfermeria) table.getRowData();
            tblServiciosFacade.remove(tblServicio);
            ServiciosEnfermeriaDataModel model = (ServiciosEnfermeriaDataModel)this.getBean("#{serviciosEnfermeriaDataModel}", ServiciosEnfermeriaDataModel.class);
            model.clear();
            this.addInfo("El servicio de enfermeria ha sido eliminado", "El servicio de enfermeria ha sido eliminado");
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }

}
