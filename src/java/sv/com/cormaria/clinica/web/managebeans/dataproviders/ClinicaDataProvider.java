/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.dataproviders;

/**
 *
 * @author romorales
 */
import java.util.List;
import sv.com.cormaria.servicios.exceptions.ClinicaModelexception;

public interface ClinicaDataProvider<T, RKT> {
	public RKT getRowKey(T o);
	public List<T> getData(int firtRows, int numberOfRows);
	public RKT getPk(T item);
	public int getRowCount();
	public T getItemByPk(RKT pk) throws ClinicaModelexception;
	public boolean exist(RKT pk) throws ClinicaModelexception;
}