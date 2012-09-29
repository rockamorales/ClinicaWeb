/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmTblControlCitas {

    /**
     * Creates a new instance of FrmTblControlCitas
     */
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private String daysOfWeekStr[] = {"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    private String monthStrShort[] = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
    private String monthStr[] = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    
    private List<MonthDay> monthDays = new ArrayList<MonthDay>();
    private List<MonthWeek> monthWeeks = new ArrayList<MonthWeek>();
    
    public FrmTblControlCitas() {
    }

    public List<MonthWeek> getMonthWeeks() {
        System.out.println("Ejecutanto getMonthWeeks");
        monthWeeks.clear();
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.setFirstDayOfWeek(Calendar.SUNDAY);
        System.out.println(calStart.getTime());        
        calStart.set(Calendar.MONTH, this.getMonth());
        calStart.set(Calendar.DAY_OF_MONTH, calStart.getActualMinimum(Calendar.DAY_OF_MONTH));
        System.out.println(calStart.getTime());
        System.out.println(calStart.get(Calendar.DAY_OF_WEEK));
        calStart.add(Calendar.DAY_OF_MONTH, -1*(calStart.get(Calendar.DAY_OF_WEEK)-1));
        System.out.println(calStart.getTime());

        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        System.out.println(calEnd.getTime());        
        calEnd.setFirstDayOfWeek(Calendar.SUNDAY);
        calEnd.set(Calendar.MONTH, this.getMonth());
        calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(calEnd.getTime());
        System.out.println(calEnd.get(Calendar.DAY_OF_WEEK));
        calEnd.add(Calendar.DAY_OF_MONTH, (7-calEnd.get(Calendar.DAY_OF_WEEK)));
        System.out.println(calEnd.getTime());
        
        //cal.set
        
        int week = calStart.get(Calendar.WEEK_OF_YEAR);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.setTime(calStart.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 6);
        MonthWeek weekObj = new MonthWeek(week, calStart.getTime(), cal.getTime(), this.getMonth());
        this.monthWeeks.add(weekObj);
        for (; calStart.getTime().before(calEnd.getTime()) || calStart.getTime().equals(calEnd.getTime());){
            System.out.println(week);
            System.out.println(calStart.get(Calendar.WEEK_OF_YEAR));
            if (week != calStart.get(Calendar.WEEK_OF_YEAR)){
                cal.setTime(calStart.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 6);
                weekObj = new MonthWeek(calStart.get(Calendar.WEEK_OF_YEAR), calStart.getTime(), cal.getTime(), this.getMonth());
                this.monthWeeks.add(weekObj);
                week = calStart.get(Calendar.WEEK_OF_YEAR);
            }
            weekObj.addDayOfWeek(new MonthDay(calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.DAY_OF_WEEK), daysOfWeekStr[calStart.get(Calendar.DAY_OF_WEEK)-1], calStart.get(Calendar.MONTH), calStart.get(Calendar.YEAR), this.monthStrShort[calStart.get(Calendar.MONTH)], this.monthStr[calStart.get(Calendar.MONTH)], this.getMonth()));
            calStart.add(Calendar.DAY_OF_MONTH, 1);
        }
        return monthWeeks;
    }

    public void setMonthWeeks(List<MonthWeek> monthWeeks) {
        this.monthWeeks = monthWeeks;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    
    public List<MonthDay> getMonthDays() {
        return monthDays;
    }

    public void setMonthDays(List<MonthDay> monthDays) {
        this.monthDays = monthDays;
    }
    
    public String getMonthName(){
        return this.monthStr[this.getMonth()];
    }
    
    public void previous(ActionEvent ae){
        if (this.getMonth()==0){
            this.setMonth(11);
            this.setYear(this.getYear()-1);
        }else{
            this.setMonth(this.getMonth()-1);
        }
    }
    public void next(ActionEvent ae){
        if (this.getMonth()==11){
            this.setMonth(0);
            this.setYear(this.getYear()+1);
        }else{
            this.setMonth(this.getMonth()+1);
        }
    }
}