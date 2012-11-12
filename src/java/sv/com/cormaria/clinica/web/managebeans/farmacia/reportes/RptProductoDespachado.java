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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoSalida;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoSalidaFacadeLocal;


/**
 *
 * @author Schumy
 */
@ManagedBean
@RequestScoped
public class RptProductoDespachado {
@EJB
private CatTipoSalidaFacadeLocal catTipoSalidaFacade;

private Integer tipSalida;
private String docType;

    public Integer getTipSalidaProducto() {
        return tipSalida;
    }

    public void setTipSalidaProducto(Integer tipSalida) {
        this.tipSalida = tipSalida;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
    
private List<CatTipoSalida> tipoSalidaList = new ArrayList<CatTipoSalida>();
    /**
     * Creates a new instance of RptProductos
     */
    public RptProductoDespachado() {
    }
    
    public List<CatTipoSalida> getTipoSalidaList() {
        if (tipoSalidaList.isEmpty()){
            try{
                 tipoSalidaList = catTipoSalidaFacade.findAll(); 
            }catch(Exception ex ){
             }
        }
            
        return tipoSalidaList;
    }

    public void setTipoSalidaList(List<CatTipoSalida> tipoSalidaList) {
        this.tipoSalidaList = tipoSalidaList;
    }
    
    
}
