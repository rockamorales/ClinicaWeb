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
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ServiciosEnfermeriaDataModel;
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoServiciosEnfermeria;
import sv.com.cormaria.servicios.facades.archivo.TblServiciosEnfermeriaFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoServiciosEnfermeriaFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmConsultaTblServiciosEnfermeria extends PageBase {

    @EJB
    private CatTipoServiciosEnfermeriaFacadeLocal tipoServiciosFacade;
    
    private List<CatTipoServiciosEnfermeria> tipoServiciosList = new ArrayList<CatTipoServiciosEnfermeria>();
    /**
     * Creates a new instance of FrmConsultaTblServiciosEnfermeria
     */
    public FrmConsultaTblServiciosEnfermeria() {
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
}