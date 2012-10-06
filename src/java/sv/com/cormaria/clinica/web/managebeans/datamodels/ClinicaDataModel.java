/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.datamodels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import sv.com.cormaria.clinica.web.managebeans.dataproviders.ClinicaDataProvider;

/**
 *
 * @author romorales
 */
public abstract class ClinicaDataModel<T, RKT> extends ExtendedDataModel<T> {

    RKT rowKey = null;
    private Map<RKT, T> wrappedData = new HashMap<RKT, T>();
    private List<RKT> wrappedKeys = new ArrayList<RKT>();
    private SequenceRange currentRange = null;
    private boolean detached = false;
    private RKT currentPk;
    
    @Override
    public void setRowKey(Object o) {
    	rowKey = (RKT)o;
        this.currentPk = rowKey;
    }
 
    @Override
    public Object getRowKey() {
        return rowKey;
    }
    
	@Override
	public void walk(FacesContext context, DataVisitor visitor, Range r, Object o) {
        int firstRow = ((SequenceRange)r).getFirstRow();
        int numberOfRows = ((SequenceRange)r).getRows();
        int currentFirstRow = -1;
        int currentNumberOfRows = -1;
        if (this.currentRange !=null){
          currentFirstRow = this.currentRange.getFirstRow();
          currentNumberOfRows = this.currentRange.getRows();
        }
        if ((detached) && ((firstRow == currentFirstRow && numberOfRows == currentNumberOfRows) || currentRange == null)){ 
      	// Is this serialized model
          // Here we just ignore current Rage and use whatever data was saved in serialized model.
          // Such approach uses much more getByPk() operations, instead of just one request by range.
          // Concrete case may be different from that, so you can just load data from data provider by range.
          // We are using wrappedKeys list only to preserve actual order of items.
          // if (wrappedKeys.size()>0 && (firstRow != currentFirstRow || numberOfRows != currentNumberOfRows || currentPk == null)){
          // setRowKey(dataProvider.getPk(wrappedKeys.get(0)));
          //}
          for (Object key:wrappedKeys) {
              setRowKey(key);
              visitor.process(context, key, o);
          }
        } else { // if not serialized, then we request data from data provider
                wrappedKeys = new ArrayList<RKT>();
                //try{
                  List<T> c = getDataProvider().getData(new Integer(firstRow),
                      numberOfRows);
                  for (T item : c) {
                    RKT pk = getDataProvider().getPk(item);
                    wrappedKeys.add(pk);
                    wrappedData.put(pk, item);
                    visitor.process(context, pk,o);
                  }
                //}catch(Exception ex){
                  //ex.printStackTrace();
                 //throw new IOException("Ocurrio un error extrayendo la informacion desde la base de datos. Detalle del Error: "+ex.getMessage());
                //}finally{
                  this.currentRange = (SequenceRange) r;
                //}
        }
	}

    private Integer rowCount; // better to buffer row count locally	
	@Override
	public int getRowCount() {
        //log.info("getRowCount...");
        if (rowCount==null){
            try {
              rowCount = getDataProvider().getRowCount();
              //log.info("Row count: "+rowCount);
              return rowCount.intValue();
            }
            catch (Exception ex) {
              ex.printStackTrace();
            }
            return 0;
        } else {
        //	log.info("Serialiazed Row count: "+rowCount);
            return rowCount.intValue();
        }
	}

	@Override
	public T getRowData() {
        //log.info("getRowData...");
        if (currentPk==null) {
            return null;
        } else {
            T ret = wrappedData.get(currentPk);
            //log.info("getWrappedData: "+ret);
            if (ret==null) {
                try {
                  ret = getDataProvider().getItemByPk(currentPk);
                }
                catch (Exception ex) {
                  ex.printStackTrace();
                }
                wrappedData.put(currentPk, ret);
                //getLogger().info("Getting from provider...");
                return ret;
            } else {
                return ret;
            }
        }
	}

	@Override
	public int getRowIndex() {
        throw new UnsupportedOperationException();
    }

    /**
     * Unused rudiment from old JSF staff.
     */
    @Override
    public Object getWrappedData() {
         throw new UnsupportedOperationException();
    }

	@Override
	public boolean isRowAvailable() {
        if (currentPk==null) {
            return false;
	    } else {
	       boolean result = wrappedData.containsKey(currentPk);
	       if (result){
	         return result;
	       }else{
	            try {
	              return getDataProvider().exist(currentPk);
	            }
	            catch (Exception ex) {
	              ex.printStackTrace();
	            }
	       }
	    }
	    return false;
	}

 
    @Override
    public void setRowIndex(int rowIndex) {
        throw new UnsupportedOperationException();
    }
  
    @Override
    public void setWrappedData(Object data) {
        throw new UnsupportedOperationException();
    }
    
    public void clear(){
    	this.wrappedData.clear();
    	this.wrappedKeys.clear();
    	this.currentRange = null;
    	this.detached = false;
    	this.rowCount = null;
    }
    
    public abstract ClinicaDataProvider<T, RKT> getDataProvider();

    public abstract void setDataProvider(ClinicaDataProvider<T, RKT> dataProvider);
    
}