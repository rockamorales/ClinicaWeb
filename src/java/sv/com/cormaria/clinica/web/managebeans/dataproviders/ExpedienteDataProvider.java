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
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class ExpedienteDataProvider implements ClinicaDataProvider<TblExpedientePacientes, Integer> {
    @EJB
    private TblExpedientePacientesFacadeLocal expedienteLocal;

    @ManagedProperty(value="#{expedientesSearchCriteria}")
    private SearchCriteria criteria;

    @Override
    public Integer getRowKey(TblExpedientePacientes o) {
            return o.getNumExpediente();
    }

    @Override
    public List<TblExpedientePacientes> getData(int firtRows, int numberOfRows) {
        if (criteria!=null){
              return expedienteLocal.find(criteria, firtRows, numberOfRows);
        }
        return new ArrayList<TblExpedientePacientes>();
    }

    @Override
    public Integer getPk(TblExpedientePacientes item) {
            return item.getNumExpediente();
    }

    @Override
    public int getRowCount() {
        if (criteria!=null){
           return expedienteLocal.count(criteria);
        }
        return 0;
    }

    @Override
    public TblExpedientePacientes getItemByPk(Integer pk) throws ClinicaModelexception {
      return expedienteLocal.find(pk);
    }

    @Override
    public boolean exist(Integer pk) throws ClinicaModelexception {
            return expedienteLocal.find(pk)!=null;
    }

    public SearchCriteria getCriteria() {
            return criteria;
    }

    public void setCriteria(SearchCriteria criteria) {
            this.criteria = criteria;
    }
}
