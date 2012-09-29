/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author romorales
 */
public class MonthWeek implements Serializable{
    private int weekOfYear;
    private Date startDate;
    private Date endDate;
    private int selectedMonth;
    private List<MonthDay> daysOfWeek = new ArrayList<MonthDay>();

    public MonthWeek(int weekOfYear, Date startDate, Date endDate, int selectedMonth) {
        this.weekOfYear = weekOfYear;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }
    
    public int getMonthForStartDay(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        return cal.get(Calendar.MONTH);
    }

    public int getMonthForEndDay(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        return cal.get(Calendar.MONTH);
    }    
    
    public int getStartDay(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public int getEndDay(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }    
    
    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<MonthDay> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<MonthDay> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
    
    public void addDayOfWeek(MonthDay day){
        daysOfWeek.add(day);
    }
}