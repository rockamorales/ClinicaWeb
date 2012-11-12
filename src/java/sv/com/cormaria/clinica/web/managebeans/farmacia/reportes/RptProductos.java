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
import sv.com.cormaria.servicios.entidades.catalogos.CatTipoProducto;
import sv.com.cormaria.servicios.facades.catalogos.CatCategoriaProductoFacadeLocal;
import sv.com.cormaria.servicios.facades.catalogos.CatTipoProductoFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean
@RequestScoped
public class RptProductos {
@EJB
private CatCategoriaProductoFacadeLocal catCategoriaFacade;
private CatTipoProductoFacadeLocal catTipoFacade;
private Integer catProducto;
private Integer tipProducto;
private String docType;

    public Integer getCatProducto() {
        return catProducto;
    }

    public void setCatProducto(Integer catProducto) {
        this.catProducto = catProducto;
    }

    public Integer getTipProducto() {
        return tipProducto;
    }

    public void setTipProducto(Integer tipProducto) {
        this.tipProducto = tipProducto;
    }
    

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }
    
private List<CatCategoriaProducto> categoriaList = new ArrayList<CatCategoriaProducto>();
private List<CatTipoProducto> tipoList = new ArrayList<CatTipoProducto>();
    /**
     * Creates a new instance of RptProductos
     */
    public RptProductos() {
    }

    public List<CatCategoriaProducto> getCategoriaList() {
        if (categoriaList.isEmpty()){
            try{
                 categoriaList = catCategoriaFacade.findAll(); 
            }catch(Exception ex ){
             }
        }
            
        return categoriaList;
    }

    public void setCategoriaList(List<CatCategoriaProducto> categoriaList) {
        this.categoriaList = categoriaList;
    }
    
    public List<CatTipoProducto> getTipoList() {
        if (tipoList.isEmpty()){
            try{
                 tipoList = catTipoFacade.findAll(); 
            }catch(Exception ex ){
             }
        }
            
        return tipoList;
    }

    public void setTipoList(List<CatTipoProducto> tipoList) {
        this.tipoList = tipoList;
    }
    
    
}
