/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.dataproviders;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sv.com.cormaria.servicios.criteria.SearchCriteria;
import sv.com.cormaria.servicios.entidades.farmacia.TblAlquilerEquipo;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.farmacia.TblAlquilerEquipoFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class AlquileresDataProvider implements ClinicaDataProvider<TblAlquilerEquipo, Integer> {
    @EJB
    private TblAlquilerEquipoFacadeLocal facade;

    @ManagedProperty(value="#{alquileresSearchCriteria}")
    private SearchCriteria criteria;

    @Override
    public Integer getRowKey(TblAlquilerEquipo o) {
            return o.getNumSolAlquiler();
    }

    @Override
    public List<TblAlquilerEquipo> getData(int firtRows, int numberOfRows) {
        if (criteria!=null){
              return facade.find(criteria, firtRows, numberOfRows);
        }
        return new ArrayList<TblAlquilerEquipo>();
    }

    @Override
    public Integer getPk(TblAlquilerEquipo item) {
         return item.getNumSolAlquiler();
    }

    @Override
    public int getRowCount() {
        if (criteria!=null){
           return facade.count(criteria);
        }
        return 0;
    }

    @Override
    public TblAlquilerEquipo getItemByPk(Integer pk) throws ClinicaModelexception {
      return facade.find(pk);
    }

    @Override
    public boolean exist(Integer pk) throws ClinicaModelexception {
            return facade.find(pk)!=null;
    }

    public SearchCriteria getCriteria() {
            return criteria;
    }

    public void setCriteria(SearchCriteria criteria) {
            this.criteria = criteria;
    }
}