/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.catalogos;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.catalogos.CatExamenesMedicos;
import sv.com.cormaria.servicios.facades.catalogos.CatExamenesMedicosFacade;
import sv.com.cormaria.servicios.facades.catalogos.CatExamenesMedicosFacadeLocal;

/**
 *
 * @author Mackk
 */
@ManagedBean (name = "frmMantCatExamenesMedicos")
@RequestScoped
public class FrmMantCatExamenesMedicos extends PageBase{
    private CatExamenesMedicos catExamenesMedicos = new CatExamenesMedicos();
    
    @EJB
    private CatExamenesMedicosFacadeLocal facade;
    @ManagedProperty(value ="#{param.codExaMedico}")
    private Integer codExaMedico;

    public Integer getCodExaMedico() {
        return codExaMedico;
    }

    public void setCodExaMedico(Integer codExaMedico) {
        if(codExaMedico != null){
            try{
            catExamenesMedicos = facade.find(codExaMedico);
            }catch(Exception x){
                x.printStackTrace();
            }
        }
        this.codExaMedico = codExaMedico;
    }
    
    
    
    public CatExamenesMedicos getCatExamenesMedicos() {
        return catExamenesMedicos;
    }

    public void setCatExamenesMedicos(CatExamenesMedicos catExamenesMedicos) {
        this.catExamenesMedicos = catExamenesMedicos;
    }
    
    public void guardar(ActionEvent ae){
        try{
            if(catExamenesMedicos.getCodExaMedico() != null){
                facade.edit(catExamenesMedicos);
            }else{
                facade.create(catExamenesMedicos);
            }
        }catch(Exception x){
            x.printStackTrace();
            this.addError(x.getMessage(), x.getMessage());
        }
    }
    public void nuevo(ActionEvent ae){
        this.catExamenesMedicos = new CatExamenesMedicos();
    }
    
    
}
