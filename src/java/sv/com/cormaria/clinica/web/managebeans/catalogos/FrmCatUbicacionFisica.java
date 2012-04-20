/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatUbicacionFisica;
import sv.com.cormaria.servicios.entidades.catalogos.CatUbicacionFisica;
import sv.com.cormaria.servicios.facades.catalogos.CatUbicacionFisicaFacadeLocal;

/**
 *
 * @author Schumy
 */
@ManagedBean (name = "frmCatUbicacionFisica")
@RequestScoped
public class FrmCatUbicacionFisica extends PageBase {
    private CatUbicacionFisica catUbicacionFisica = new CatUbicacionFisica();
    @EJB
    private CatUbicacionFisicaFacadeLocal catUbicacionFisicaFacade;
    
    private List<CatUbicacionFisica> catUbicacionFisicaList= new ArrayList<CatUbicacionFisica>();

    public CatUbicacionFisica getCatUbicacionFisica() {
        return catUbicacionFisica;
    }

    public void setCatUbicacionFisica(CatUbicacionFisica catUbicacionFisica) {
        this.catUbicacionFisica = catUbicacionFisica;
    }

    public List<CatUbicacionFisica> getCatUbicacionFisicaList() {
        if (catUbicacionFisicaList.isEmpty()){
          try{
            catUbicacionFisicaList = catUbicacionFisicaFacade.findAll();
          }catch(Exception x) {
              x.printStackTrace();
          }
        }
        return catUbicacionFisicaList;
    }

    public void setCatUbicacionFisicaList(List<CatUbicacionFisica> catUbicacionFisicaList) {
        this.catUbicacionFisicaList = catUbicacionFisicaList;
    }

    public void delete(ActionEvent ae){
        try{
            UIDataTable table = (UIDataTable) ae.getComponent().getParent().getParent();
            catUbicacionFisica = (CatUbicacionFisica) table.getRowData();
            catUbicacionFisicaFacade.remove(catUbicacionFisica);
            catUbicacionFisicaList.clear();
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
}
