/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author romorales
 */
public class MonthDay implements Serializable{
    private int day;
    private int dayOfWeek;
    private String dayOfWeekStr;
    private int month;
    private int year;
    private String monthStr;
    private String monthStrShort;
    private int selectedMonth;

    public MonthDay(int day, int dayOfWeek, String dayOfWeekStr, int month, int year, String monthStrShort, String monthStr, int selectedMonth) {
        this.day = day;
        this.monthStr = monthStr;
        this.monthStrShort = monthStrShort;
        this.dayOfWeek = dayOfWeek;
        this.dayOfWeekStr = dayOfWeekStr;
        this.month = month;
        this.year = year;
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }
    
    public String getMonthStr() {
        return monthStr;
    }
    
    public boolean isFirstDayOfMonth(){
        return day == 1;
    }

    public boolean isLastDayOfMonth(){
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return day == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public String getMonthStrShort() {
        return monthStrShort;
    }

    public void setMonthStrShort(String monthStrShort) {
        this.monthStrShort = monthStrShort;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDayOfWeekStr() {
        return dayOfWeekStr;
    }

    public void setDayOfWeekStr(String dayOfWeekStr) {
        this.dayOfWeekStr = dayOfWeekStr;
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
}
