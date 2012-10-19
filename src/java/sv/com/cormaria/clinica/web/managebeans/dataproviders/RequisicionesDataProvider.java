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
import sv.com.cormaria.servicios.entidades.administracion.TblRequisiciones;
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.administracion.TblRequisicionesFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class RequisicionesDataProvider implements ClinicaDataProvider<TblRequisiciones, Integer> {
    @EJB
    private TblRequisicionesFacadeLocal facade;

    @ManagedProperty(value="#{requisicionesSearchCriteria}")
    private SearchCriteria criteria;

    @Override
    public Integer getRowKey(TblRequisiciones o) {
            return o.getNumRequisicion();
    }

    @Override
    public List<TblRequisiciones> getData(int firtRows, int numberOfRows) {
        if (criteria!=null){
              return facade.find(criteria, firtRows, numberOfRows);
        }
        return new ArrayList<TblRequisiciones>();
    }

    @Override
    public Integer getPk(TblRequisiciones item) {
         return item.getNumRequisicion();
    }

    @Override
    public int getRowCount() {
        if (criteria!=null){
           return facade.count(criteria);
        }
        return 0;
    }

    @Override
    public TblRequisiciones getItemByPk(Integer pk) throws ClinicaModelexception {
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