/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.entidades.administracion.TblProducto;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblProductoFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatEspecialidad")
@RequestScoped
public class FrmMantCatEspecialidad extends PageBase{
    private CatEspecialidad catEspecialidad = new CatEspecialidad();
    
    @EJB
    private CatEspecialidadFacadeLocal facade;

    @EJB
    private TblProductoFacadeLocal productoFacade;
    
    @ManagedProperty(value ="#{param.codEspecialidad}")
    private Integer codEspecialidad;

    private List<SelectItem> serviciosList = new ArrayList<SelectItem>();

    public List<SelectItem> getServiciosList() {
        if (this.serviciosList.isEmpty()){
            try{
                List<TblProducto> productosList = productoFacade.findActiveServices();
                for (TblProducto tblProducto : productosList) {
                    serviciosList.add(new SelectItem(tblProducto.getNumProducto(), tblProducto.getNomProducto()));
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return serviciosList;
    }

    public void setServiciosList(List<SelectItem> serviciosList) {
        this.serviciosList = serviciosList;
    }
    
    public Integer getCodEspecialidad() {
        return codEspecialidad;
    }

    public void setCodEspecialidad(Integer codEspecialidad) {
        if(codEspecialidad != null){
            try{
                catEspecialidad = facade.find(codEspecialidad);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codEspecialidad = codEspecialidad;
    }
        
    public CatEspecialidad getCatEspecialidad() {
        return catEspecialidad;
    }

    public void setCatEspecialidad(CatEspecialidad catEspecialidad) {
        this.catEspecialidad = catEspecialidad;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catEspecialidad.getCodEspecialidad() != null){
                facade.edit(catEspecialidad);
            }else{
                facade.create(catEspecialidad);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catEspecialidad = new CatEspecialidad();
    }
    
    
}
