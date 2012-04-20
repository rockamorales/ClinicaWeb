/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import java.util.Calendar;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatTurnos;
import sv.com.cormaria.servicios.facades.catalogos.CatTurnosFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatTurnosFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatTurnos")
@RequestScoped
public class FrmMantCatTurnos extends PageBase{
    private CatTurnos catTurnos = new CatTurnos();
    
    @EJB
    private CatTurnosFacadeLocal facade;
    @ManagedProperty(value ="#{param.codTurSemanal}")
    private Integer codTurSemanal;

    public Integer getCodTurSemanal() {
        return codTurSemanal;
    }

    public void setCodTurSemanal(Integer codTurSemanal) {
        if(codTurSemanal != null){
            try{
            catTurnos = facade.find(codTurSemanal);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codTurSemanal = codTurSemanal;
    }
    
    
    
    public CatTurnos getCatTurnos() {
        return catTurnos;
    }

    public void setCatTurnos(CatTurnos catTurnos) {
        this.catTurnos = catTurnos;
    }
    
    public void guardar(ActionEvent ae){
        try{
            Calendar cal1 = Calendar.getInstance();
            cal1.set(Calendar.HOUR_OF_DAY, catTurnos.getHoras());
            cal1.set(Calendar.MINUTE, catTurnos.getMinutos());
            catTurnos.setHorTurno(cal1.getTime());
            if(catTurnos.getCodTurSemanal() != null){
                facade.edit(catTurnos);
            }else{
                facade.create(catTurnos);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catTurnos = new CatTurnos();
    }
    
    
}
