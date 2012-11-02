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
import sv.com.cormaria.servicios.facades.catalogos.CatCategoriaProductoFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean
@RequestScoped
public class RptProductos {
@EJB
private CatCategoriaProductoFacadeLocal catCategoriaFacade;
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
    
private List<CatCategoriaProducto> categoriaList = new ArrayList<CatCategoriaProducto>();
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
}
