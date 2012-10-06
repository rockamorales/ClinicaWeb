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
import sv.com.cormaria.servicios.entidades.archivo.TblServiciosEnfermeria;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.archivo.TblServiciosEnfermeriaFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class ServiciosEnfermeriaPagadosDataProvider implements ClinicaDataProvider<TblServiciosEnfermeria, Integer> {
    @EJB
    private TblServiciosEnfermeriaFacadeLocal facade;

    @ManagedProperty(value="#{serviciosEnfermeriaPagadosSearchCriteria}")
    private SearchCriteria criteria;

    @Override
    public Integer getRowKey(TblServiciosEnfermeria o) {
            return o.getNumExpediente();
    }

    @Override
    public List<TblServiciosEnfermeria> getData(int firtRows, int numberOfRows) {
        if (criteria!=null){
              return facade.find(criteria, firtRows, numberOfRows);
        }
        return new ArrayList<TblServiciosEnfermeria>();
    }

    @Override
    public Integer getPk(TblServiciosEnfermeria item) {
            return item.getNumExpediente();
    }

    @Override
    public int getRowCount() {
        if (criteria!=null){
           return facade.count(criteria);
        }
        return 0;
    }

    @Override
    public TblServiciosEnfermeria getItemByPk(Integer pk) throws ClinicaModelexception {
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