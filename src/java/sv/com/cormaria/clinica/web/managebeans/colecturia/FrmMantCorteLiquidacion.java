/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.colecturia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.colecturia.CorteDiario;
import sv.com.cormaria.servicios.entidades.colecturia.TblLiquidacion;
import sv.com.cormaria.servicios.facades.colecturia.TblComprobanteDonacionFacadeLocal;
import sv.com.cormaria.servicios.facades.colecturia.TblLiquidacionFacadeLocal;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmMantCorteLiquidacion extends PageBase{

    private List<CorteDiario> corteDiario = new ArrayList<CorteDiario>();
    private Date fechaCorte = new java.util.Date();
    private TblLiquidacion liquidacion = new TblLiquidacion();
    
    @EJB
    private TblComprobanteDonacionFacadeLocal tblComprobanteDonacionLocal;
    @EJB
    private TblLiquidacionFacadeLocal tblLiquidacionLocal;
    
    /**
     * Creates a new instance of FrmMantCorteLiquidacion
     */
    public FrmMantCorteLiquidacion() {
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public List<CorteDiario> getCorteDiario() {
        return corteDiario;
    }

    public TblLiquidacion getLiquidacion() {
        return liquidacion;
    }

    public void setLiquidacion(TblLiquidacion liquidacion) {
        this.liquidacion = liquidacion;
    }

    public void setCorteDiario(List<CorteDiario> corteDiario) {
        this.corteDiario = corteDiario;
    }
    
    public Double getTotalEfectivo(){
        double total = 0.00D;
        for (CorteDiario corteDiario1 : corteDiario) {
            if (corteDiario1.getCodTipPago()!=null && corteDiario1.getCodTipPago() == 1){
                total+=corteDiario1.getTotal();
            }
        }
        return total;        
    }

    public Double getTotalCheque(){
        double total = 0.00D;
        for (CorteDiario corteDiario1 : corteDiario) {
            if (corteDiario1.getCodTipPago()!=null && corteDiario1.getCodTipPago() == 2){
                total+=corteDiario1.getTotal();
            }
        }
        return total;        
    }
    
    public List<CorteDiario> getCheques(){
        List<CorteDiario> corteDiarioChequesList = new ArrayList<CorteDiario>();
        for (CorteDiario corteDiario1 : corteDiario) {
            if (corteDiario1.getCodTipPago()!=null && corteDiario1.getCodTipPago() == 2){
                corteDiarioChequesList.add(corteDiario1);
            }
        }
        return corteDiarioChequesList;
    }

    public List<CorteDiario> getEfectivo(){
        List<CorteDiario> corteDiarioEfectivoList = new ArrayList<CorteDiario>();
        for (CorteDiario corteDiario1 : corteDiario) {
            if (corteDiario1.getCodTipPago()!=null && corteDiario1.getCodTipPago() == 1){
                corteDiarioEfectivoList.add(corteDiario1);
            }
        }
        return corteDiarioEfectivoList;
    }
    
    public Double getTotal(){
        double total = 0.00D;
        for (CorteDiario corteDiario1 : corteDiario) {
            total+=corteDiario1.getTotal();
        }
        return total;
    }
    
    public void init(){
        try{
            if (corteDiario.isEmpty()){
                corteDiario = tblComprobanteDonacionLocal.calcularCorteDiario1(fechaCorte);
                liquidacion = tblLiquidacionLocal.findByDate(fechaCorte);
                if (liquidacion==null){
                    liquidacion = new TblLiquidacion();
                    liquidacion.setCanBil1(0);
                    liquidacion.setCanBil5(0);
                    liquidacion.setCanBil10(0);
                    liquidacion.setCanBil20(0);
                    liquidacion.setCanBil50(0);
                    liquidacion.setCanBil100(0);                    
                }
                liquidacion.setTotCorte(getTotal());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError("Ocurrio un error calculando el total de las transacciones efectuadas", ex.getMessage());
        }
    }
    
    public void calcular(){
        liquidacion.setTotEfectivo((liquidacion.getCanBil1()!=null?liquidacion.getCanBil1():0.00D)+(liquidacion.getCanBil10()!=null?liquidacion.getCanBil10()*10:0.00D)+(liquidacion.getCanBil100()!=null?liquidacion.getCanBil100()*100:0.00D)+(liquidacion.getCanBil20()!=null?liquidacion.getCanBil20()*20:0.00D)+(liquidacion.getCanBil5()!=null?liquidacion.getCanBil5()*5:0.00D)+(liquidacion.getCanBil50()!=null?liquidacion.getCanBil50()*50:0.00D)+(liquidacion.getMonMoneda()!=null?liquidacion.getMonMoneda():0.00D));
        Double totalLiquidacion = (liquidacion.getTotCheques()!=null?liquidacion.getTotCheques():0.00D)+(liquidacion.getTotEfectivo()!=null?liquidacion.getTotEfectivo():0.00D);
        Double totalCheques = liquidacion.getTotCheques()!=null?liquidacion.getTotCheques():0.00D;
        Double totalCorte = liquidacion.getTotCorte()!=null?liquidacion.getTotCorte():0.00D;
        if (totalLiquidacion <= totalCorte){
            liquidacion.setCanFaltante(totalCorte - (totalCheques+liquidacion.getTotEfectivo()));
            liquidacion.setCanSobrante(0D);
        }else{
            liquidacion.setCanSobrante((totalCheques+liquidacion.getTotEfectivo()) - totalCorte);
            liquidacion.setCanFaltante(0D);  
        }
    }
    
    public void guardar(ActionEvent ae){
        try{
            if (this.getSessionBean().getUsuario()==null || this.getSessionBean().getUsuario().getNumEmpleado()==null || this.getSessionBean().getUsuario().getNumEmpleado() <=0){
                this.addError("El usuario no tiene un codigo de empleado asociado", "El usuario no tiene un codigo de empleado asociado");
                return;
            }
            if (liquidacion.getNumLiquidacion()!=null && liquidacion.getNumLiquidacion() >0){
                liquidacion.setTotCorCheque(this.getTotalCheque());
                liquidacion.setTotCorEfectivo(this.getTotalEfectivo());
                liquidacion.setTotCorte(this.getTotal());
                liquidacion.setFecLiquidacion(fechaCorte);
                liquidacion.setNumEmpleado(this.getSessionBean().getUsuario().getNumEmpleado());
                liquidacion = tblLiquidacionLocal.edit(liquidacion);
            }else{
                liquidacion.setFecLiquidacion(fechaCorte);
                liquidacion.setTotCorCheque(this.getTotalCheque());
                liquidacion.setTotCorEfectivo(this.getTotalEfectivo());
                liquidacion.setTotCorte(this.getTotal());
                liquidacion.setNumEmpleado(this.getSessionBean().getUsuario().getNumEmpleado());
                liquidacion = tblLiquidacionLocal.create(liquidacion);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public void selectDate(){
        this.liquidacion = new TblLiquidacion();
        this.corteDiario.clear();
    }
}