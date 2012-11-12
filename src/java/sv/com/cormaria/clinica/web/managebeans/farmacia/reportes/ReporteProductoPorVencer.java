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



/**
 *
 * @author Schumy
 */
@ManagedBean
@RequestScoped
public class ReporteProductoPorVencer {
@EJB

private String docType;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    /**
     * Creates a new instance of RptProductos
     */
    public ReporteProductoPorVencer() {
    }
    
}
