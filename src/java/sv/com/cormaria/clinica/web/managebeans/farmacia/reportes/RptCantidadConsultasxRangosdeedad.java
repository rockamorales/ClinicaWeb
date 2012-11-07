/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.farmacia.reportes;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sv.com.cormaria.servicios.entidades.catalogos.CatCategoriaProducto;
import sv.com.cormaria.servicios.entidades.catalogos.CatEspecialidad;
import sv.com.cormaria.servicios.facades.catalogos.CatCategoriaProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatEspecialidadFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean
@RequestScoped
public class RptCantidadConsultasxRangosdeedad {
@EJB
private CatEspecialidadFacadeLocal catEspecialidadFacade;
private Integer catProducto;
private String docType;

    public Integer getCatProducto() {
        return catProducto;
    }

    public void setCatProducto(Integer catProducto) {
        this.catProducto = catProducto;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
    
    private List<CatEspecialidad> especialidadesList = new ArrayList<CatEspecialidad>();

    public List<CatEspecialidad> getEspecialidadesList() {
        if (especialidadesList.isEmpty()){
            try{
                especialidadesList = catEspecialidadFacade.findActive();
            }catch(Exception ex){
                
            }
        }
        return especialidadesList;
    }

    public void setEspecialidadesList(List<CatEspecialidad> especialidadesList) {
        this.especialidadesList = especialidadesList;
    }
}