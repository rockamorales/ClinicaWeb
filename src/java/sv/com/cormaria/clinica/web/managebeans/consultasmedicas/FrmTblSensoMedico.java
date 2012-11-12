package sv.com.cormaria.clinica.web.managebeans.consultasmedicas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.clinica.web.managebeans.datamodels.ConsultasSensoMedicoDataModel;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmTblSensoMedico extends PageBase{

    @EJB
    private TblMedicoFacadeLocal tblMedicoFacade;
    
    private List<TblMedico> medicosList = new ArrayList<TblMedico>();

    @EJB
    private CatEspecialidadFacadeLocal catEspecialidadFacade;
    
    private List<CatEspecialidad> catEspecialidadList = new ArrayList<CatEspecialidad>();
    
    
    /**
     * Creates a new instance of FrmTblSensoMedico
     */
    public FrmTblSensoMedico() {
    }

    public List<TblMedico> getMedicosList() {
        if (medicosList.isEmpty()){
            try{
                medicosList = tblMedicoFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }   
        return medicosList;
    }

    public void setMedicosList(List<TblMedico> medicosList) {
        this.medicosList = medicosList;
    }

    public List<CatEspecialidad> getCatEspecialidadList() {
        if (catEspecialidadList.isEmpty()){
            try{
                catEspecialidadList = catEspecialidadFacade.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return catEspecialidadList;
    }

    public void setCatEspecialidadList(List<CatEspecialidad> catEspecialidadList) {
        this.catEspecialidadList = catEspecialidadList;
    }
    
    public void buscar(ActionEvent ae){
 
        System.out.println("Buscar ejecutado");
        ConsultasSensoMedicoDataModel dataModel = (ConsultasSensoMedicoDataModel) this.getBean("#{consultasSensoMedicoDataModel}", ConsultasSensoMedicoDataModel.class);
        dataModel.clear();
        System.out.println("Buscar ejecutado");
    }
  
}
