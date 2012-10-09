/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.util.Calendar;

/**
 *
 * @author romorales
 */
public class NewClass {
    public static void main(String[] args){
        Calendar calStart = Calendar.getInstance();
        calStart.setFirstDayOfWeek(Calendar.SUNDAY);
        System.out.println("Week: "+calStart.get(Calendar.WEEK_OF_YEAR));        
        System.out.println("Date: "+calStart.getTime());
        calStart.set(Calendar.WEEK_OF_YEAR, 1);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setFirstDayOfWeek(Calendar.SUNDAY);
        calEnd.set(Calendar.WEEK_OF_YEAR, 51);
        System.out.println("CalStart: "+calStart.getTime());
        System.out.println("CalEnd: "+calEnd.getTime());
        System.out.println("Week: "+calEnd.get(Calendar.WEEK_OF_YEAR));
    }
}