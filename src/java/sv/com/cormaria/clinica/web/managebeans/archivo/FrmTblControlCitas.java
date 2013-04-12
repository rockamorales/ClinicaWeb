/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.archivo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import org.richfaces.component.UIRepeat;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.archivo.TblExpedientePacientes;
import sv.com.cormaria.servicios.entidades.archivo.TblProgramacionCitas;
import sv.com.cormaria.servicios.enums.EstadoProgramacionCitas;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblExpedientePacientesFacadeLocal;
import sv.com.cormaria.servicios.facades.archivo.TblProgramacionCitasFacadeLocal;
import sv.com.cormaria.servicios.helpers.MonthDay;
import sv.com.cormaria.servicios.helpers.MonthWeek;

/**
 *
 * @author romorales
 */
@ManagedBean
@ViewScoped
public class FrmTblControlCitas extends PageBase {

    /**
     * Creates a new instance of FrmTblControlCitas
     */
    private TblProgramacionCitas cita = new TblProgramacionCitas();
    private List<TblProgramacionCitas> citasList = new ArrayList<TblProgramacionCitas>();
    @EJB
    private TblProgramacionCitasFacadeLocal programacionCitasFacade;

    @EJB
    private TblExpedientePacientesFacadeLocal expedienteFacade;
    
    @EJB
    private TblMedicoFacadeLocal medicosFacade;

    private List<TblMedico> tblMedicosList = new ArrayList<TblMedico>();

    private TblExpedientePacientes expediente = new TblExpedientePacientes();
    private int month = Calendar.getInstance().get(Calendar.MONTH);
    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private String daysOfWeekStr[] = {"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
    private String monthStrShort[] = {"Ene","Feb","Mar","Abr","May","Jun","Jul","Ago","Sep","Oct","Nov","Dic"};
    private String monthStr[] = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    
    private List<MonthDay> monthDays = new ArrayList<MonthDay>();
    private List<MonthWeek> monthWeeks = new ArrayList<MonthWeek>();
//    private MonthWeek selectedWeek;
    private boolean yearAndMonthApplied = false;
    private String addedFecCita;
    private Integer numMedico;
    private String addedNumCita;
    
    public FrmTblControlCitas() {
        //Calendar cal = Calendar.getInstance();
        //cal.setFirstDayOfWeek(Calendar.SUNDAY);
        //this.setWeek(cal.get(Calendar.WEEK_OF_YEAR));
    }

    public Integer getNumMedico() {
        return numMedico;
    }

    public void setNumMedico(Integer numMedico) {
        this.numMedico = numMedico;
    }
    
    /*public MonthWeek getSelectedWeek() {
        try{
            //System.out.println("Week: "+this.getWeek());
            Calendar calStart = Calendar.getInstance();
            calStart.setFirstDayOfWeek(Calendar.SUNDAY);
            calStart.set(Calendar.YEAR, this.getYear());
            calStart.set(Calendar.HOUR_OF_DAY, 0);
            calStart.set(Calendar.MINUTE, 0);
            calStart.set(Calendar.SECOND, 0);
            calStart.set(Calendar.MILLISECOND, 0);
            calStart.set(Calendar.DAY_OF_WEEK, calStart.getActualMinimum(Calendar.DAY_OF_WEEK));
            Calendar calEnd = Calendar.getInstance();
            calEnd.setFirstDayOfWeek(Calendar.SUNDAY);
            calEnd.set(Calendar.YEAR, this.getYear());
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.SECOND, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            calEnd.set(Calendar.DAY_OF_WEEK, calEnd.getActualMaximum(Calendar.DAY_OF_WEEK));
            System.out.println("Week: "+this.getWeek());
            System.out.println("Cal start: "+calStart.getTime());
            System.out.println("Cal end: "+calEnd.getTime());
            Map<Date, MonthDay> dayScheduleList = programacionCitasFacade.findScheduleByRange(calStart.getTime(), calEnd.getTime());
            this.setMonth(calStart.get(Calendar.MONTH));
            //Calendar cal = Calendar.getInstance();
            //cal.setFirstDayOfWeek(Calendar.SUNDAY);
            //cal.setTime(calStart.getTime());
            //cal.add(Calendar.DAY_OF_MONTH, 6);
            MonthDay current = dayScheduleList.get(calStart.getTime());
            selectedWeek = new MonthWeek(week, calStart.getTime(), calEnd.getTime(), this.getMonth());
            
            for (; calStart.getTime().before(calEnd.getTime()) || calStart.getTime().equals(calEnd.getTime());){
                --if (week != calStart.get(Calendar.WEEK_OF_YEAR)){
                    cal.setTime(calStart.getTime());
                    cal.add(Calendar.DAY_OF_MONTH, 6);
                    weekObj = new MonthWeek(calStart.get(Calendar.WEEK_OF_YEAR), calStart.getTime(), cal.getTime(), this.getMonth());
                    this.monthWeeks.add(weekObj);
                    week = calStart.get(Calendar.WEEK_OF_YEAR);
                }--
                if (current!=null){
                    selectedWeek.addDayOfWeek(new MonthDay(calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.DAY_OF_WEEK), daysOfWeekStr[calStart.get(Calendar.DAY_OF_WEEK)-1], calStart.get(Calendar.MONTH), calStart.get(Calendar.YEAR), this.monthStrShort[calStart.get(Calendar.MONTH)], this.monthStr[calStart.get(Calendar.MONTH)], this.getMonth(), calStart.getTime(), current.getCitas(), current.getScheduleCount()));
                }else{
                    selectedWeek.addDayOfWeek(new MonthDay(calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.DAY_OF_WEEK), daysOfWeekStr[calStart.get(Calendar.DAY_OF_WEEK)-1], calStart.get(Calendar.MONTH), calStart.get(Calendar.YEAR), this.monthStrShort[calStart.get(Calendar.MONTH)], this.monthStr[calStart.get(Calendar.MONTH)], this.getMonth(), calStart.getTime(), new ArrayList<TblProgramacionCitas>(), 0));
                }
                calStart.add(Calendar.DAY_OF_MONTH, 1);
                current = dayScheduleList.get(calStart.getTime());
            }
            return selectedWeek;
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
        return selectedWeek;
    }*/

    public List<TblMedico> getTblMedicosList() {
        if (this.tblMedicosList.isEmpty()){
            try{
                tblMedicosList = medicosFacade.findAll();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return tblMedicosList;
    }

    public void setTblMedicosList(List<TblMedico> tblMedicosList) {
        this.tblMedicosList = tblMedicosList;
    }
    
    public String getAddedFecCita() {
        return addedFecCita;
    }

    public void setAddedFecCita(String addedFecCita) {
        this.addedFecCita = addedFecCita;
    }

    public String getAddedNumCita() {
        return addedNumCita;
    }

    public void setAddedNumCita(String addNumCita) {
        this.addedNumCita = addNumCita;
    }
    
    public List<TblProgramacionCitas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<TblProgramacionCitas> citasList) {
        this.citasList = citasList;
    }

    public TblProgramacionCitas getCita() {
        return cita;
    }

    public void setCita(TblProgramacionCitas cita) {
        this.cita = cita;
    }
    
    public List<MonthWeek> getMonthWeeks() {
        monthWeeks.clear();
        try{
            Calendar calStart = Calendar.getInstance();
            calStart.set(Calendar.YEAR, this.getYear());
            calStart.set(Calendar.HOUR_OF_DAY, 0);
            calStart.set(Calendar.MINUTE, 0);
            calStart.set(Calendar.SECOND, 0);
            calStart.set(Calendar.MILLISECOND, 0);
            calStart.setFirstDayOfWeek(Calendar.SUNDAY);
            calStart.set(Calendar.MONTH, this.getMonth());
            calStart.set(Calendar.DAY_OF_MONTH, calStart.getActualMinimum(Calendar.DAY_OF_MONTH));
            calStart.add(Calendar.DAY_OF_MONTH, -1*(calStart.get(Calendar.DAY_OF_WEEK)-1));

            Calendar calEnd = Calendar.getInstance();
            calEnd.set(Calendar.YEAR, this.getYear());
            calEnd.set(Calendar.HOUR_OF_DAY, 0);
            calEnd.set(Calendar.MINUTE, 0);
            calEnd.set(Calendar.SECOND, 0);
            calEnd.set(Calendar.MILLISECOND, 0);
            calEnd.setFirstDayOfWeek(Calendar.SUNDAY);
            calEnd.set(Calendar.MONTH, this.getMonth());
            calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
            calEnd.add(Calendar.DAY_OF_MONTH, (7-calEnd.get(Calendar.DAY_OF_WEEK)));
            Map<Date, MonthDay> dayScheduleList; 
            if (this.getNumMedico()!=null && this.getNumMedico()>0){
                dayScheduleList = programacionCitasFacade.findScheduleByRange(calStart.getTime(), calEnd.getTime(), this.getNumMedico());
            }else{
                dayScheduleList = programacionCitasFacade.findScheduleByRange(calStart.getTime(), calEnd.getTime());            
            }
            int week = calStart.get(Calendar.WEEK_OF_YEAR);
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.SUNDAY);
            cal.setTime(calStart.getTime());
            cal.add(Calendar.DAY_OF_MONTH, 6);
            MonthDay current = dayScheduleList.get(calStart.getTime());
            MonthWeek weekObj = new MonthWeek(week, calStart.getTime(), cal.getTime(), this.getMonth());
            this.monthWeeks.add(weekObj);
            for (; calStart.getTime().before(calEnd.getTime()) || calStart.getTime().equals(calEnd.getTime());){
                if (week != calStart.get(Calendar.WEEK_OF_YEAR)){
                    cal.setTime(calStart.getTime());
                    cal.add(Calendar.DAY_OF_MONTH, 6);
                    weekObj = new MonthWeek(calStart.get(Calendar.WEEK_OF_YEAR), calStart.getTime(), cal.getTime(), this.getMonth());
                    this.monthWeeks.add(weekObj);
                    week = calStart.get(Calendar.WEEK_OF_YEAR);
                }
                if (current!=null){
                    weekObj.addDayOfWeek(new MonthDay(calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.DAY_OF_WEEK), daysOfWeekStr[calStart.get(Calendar.DAY_OF_WEEK)-1], calStart.get(Calendar.MONTH), calStart.get(Calendar.YEAR), this.monthStrShort[calStart.get(Calendar.MONTH)], this.monthStr[calStart.get(Calendar.MONTH)], this.getMonth(), calStart.getTime(), current.getCitas(), current.getScheduleCount()));
                }else{
                    weekObj.addDayOfWeek(new MonthDay(calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.DAY_OF_WEEK), daysOfWeekStr[calStart.get(Calendar.DAY_OF_WEEK)-1], calStart.get(Calendar.MONTH), calStart.get(Calendar.YEAR), this.monthStrShort[calStart.get(Calendar.MONTH)], this.monthStr[calStart.get(Calendar.MONTH)], this.getMonth(), calStart.getTime(), new ArrayList<TblProgramacionCitas>(), 0));                
                }
                calStart.add(Calendar.DAY_OF_MONTH, 1);
                current = dayScheduleList.get(calStart.getTime());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
        return monthWeeks;
    }

    public void setMonthWeeks(List<MonthWeek> monthWeeks) {
        this.monthWeeks = monthWeeks;
    }

    public TblExpedientePacientes getExpediente() {
        return expediente;
    }

    public void setExpediente(TblExpedientePacientes expediente) {
        System.out.println("Setting expediente: "+expediente.getNumExpediente());
        this.expediente = expediente;
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
    
    public void nextYear(ActionEvent ae){
        this.setYear(this.getYear()+1);
    }

    public void previousYear(ActionEvent ae){
            this.setYear(this.getYear()-1);
    }
    
    public void agregarCita(ActionEvent ae){
        cita.setNumCita(null);
        cita.setFecOtoCita(null);
        cita.setEstCita(EstadoProgramacionCitas.PROGRAMADA);
    }
    
    public void guardar(ActionEvent ae){
        try{
            if (cita.getNumCita()!=null && cita.getNumCita()>0){
                this.programacionCitasFacade.create(cita);
            }else{
                this.programacionCitasFacade.edit(cita);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }    
    }

    
    public List<TblExpedientePacientes> autocomplete(String text){
        try{
            return expedienteFacade.findByPrefix(text);
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
        return new ArrayList<TblExpedientePacientes>();
    }
    
    public void find(){
        System.out.println("Expediente seleccionado: "+expediente.getNumExpediente());
    }
    
    public void change(ValueChangeEvent vce){
        System.out.println("vce: "+expediente.getNumExpediente());
    }
    
    public void init(){
          try{
            if (addedFecCita!=null && !addedFecCita.trim().equals("")){
                if (!yearAndMonthApplied){
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha = format.parse(addedFecCita);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fecha);
                    this.setYear(cal.get(Calendar.YEAR));
                    this.setMonth(cal.get(Calendar.MONTH));
                    yearAndMonthApplied = true;
                }
                //cita.setFecCita(format.parse(addedFecCita));
            }
          }catch(Exception ex){
              //Me vale madre si hay un error
          }
    }    
    
    public void mostrarTodas(ActionEvent ae){
        try{
            System.out.println("Source: "+ae.getComponent().getParent().getClass());
            System.out.println("Source: "+ae.getComponent().getParent().getParent().getClass());
            UIRepeat repeat = (UIRepeat)ae.getComponent().getParent().getParent();
            MonthDay day = (MonthDay) repeat.getRowData();
            System.out.println("Day: "+day.getDayDate());
            if (this.getNumMedico()!=null && this.getNumMedico()>0){
                citasList = programacionCitasFacade.findByDay(day.getDayDate(), this.getNumMedico());
            }else{
                citasList = programacionCitasFacade.findByDay(day.getDayDate());
            }
            System.out.println("Cantidad de citas: "+citasList.size());
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public void eliminar(ActionEvent ae){
        try{
            UIRepeat repeat = (UIRepeat)ae.getComponent().getParent();
            TblProgramacionCitas cita = (TblProgramacionCitas) repeat.getRowData();
            programacionCitasFacade.remove(cita);
            citasList = programacionCitasFacade.findByDay(cita.getFecCita());
            //TODO: Una forma mas eficiente de hacer esto podria ser extrayendo la lista de citas para el dia en donde se ha eliminado
            // y actualizar la lista de citas de dicho dia. En este momento en cada llamada al metodo getMonthWeeks se construye la estructura 
            // del calendario
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
}