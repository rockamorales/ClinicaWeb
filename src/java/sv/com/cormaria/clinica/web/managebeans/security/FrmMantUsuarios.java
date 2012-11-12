/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.managebeans.security;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.administracion.TblEmpleado;
import sv.com.cormaria.servicios.entidades.administracion.TblMedico;
import sv.com.cormaria.servicios.entidades.security.CatRoles;
import sv.com.cormaria.servicios.entidades.security.CatRolesUsuario;
import sv.com.cormaria.servicios.entidades.security.CatRolesUsuarioPK;
import sv.com.cormaria.servicios.entidades.security.TblUsuarios;
import sv.com.cormaria.servicios.facades.administracion.TblEmpleadoFacadeLocal;
import sv.com.cormaria.servicios.facades.administracion.TblMedicoFacadeLocal;
import sv.com.cormaria.servicios.facades.security.CatRolesSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.CatRolesUsuarioSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.ExceptionHelper;
import sv.com.cormaria.servicios.facades.security.TblUsuariosSessionFacadeLocal;

/**
 *
 * @author Administrador
 */
@ManagedBean
@ViewScoped
public class FrmMantUsuarios extends PageBase {
    @EJB
    TblUsuariosSessionFacadeLocal usuariosFacade;
    
    @EJB
    CatRolesUsuarioSessionFacadeLocal rolesUsuarioLocal;

    @EJB
    CatRolesSessionFacadeLocal rolesLocal;

    @EJB
    TblEmpleadoFacadeLocal empleadoLocal;

    @EJB
    TblMedicoFacadeLocal medicosLocal;
        
    private Long roleid;
    private Long selectedRoleId;
    private String fecIniCreacion;
    private String fecEndCreacion;
    private String docType;
    private String rptFileName = "/Reports/Lista_de_usuarios_con_parametros.jasper";

    private List<CatRolesUsuario> rolesUsuarioList = new ArrayList<CatRolesUsuario>();
    private List<SelectItem> rolesList = new ArrayList<SelectItem>();
    
    private List<TblEmpleado> empleadosList = new ArrayList<TblEmpleado>();
    
    private List<TblMedico> medicosList = new ArrayList<TblMedico>();
    
    private TblUsuarios tblUsuario = new TblUsuarios();
    private Long codUsuario;
    private String contrasenaAnterior;
    private String nuevaContrasena;
    private String confirmacionContrasena;

    public List<TblEmpleado> getEmpleadosList() {
        if (empleadosList.isEmpty()){
            try{
                empleadosList = empleadoLocal.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return empleadosList;
    }

    public String getContrasenaAnterior() {
        return contrasenaAnterior;
    }

    public void setContrasenaAnterior(String contrasenaAnterior) {
        this.contrasenaAnterior = contrasenaAnterior;
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }

    public String getConfirmacionContrasena() {
        return confirmacionContrasena;
    }

    public void setConfirmacionContrasena(String confirmacionContrasena) {
        this.confirmacionContrasena = confirmacionContrasena;
    }

    public void setEmpleadosList(List<TblEmpleado> empleadosList) {
        this.empleadosList = empleadosList;
    }

    public List<TblMedico> getMedicosList() {
        if (medicosList.isEmpty()){
            try{
                medicosList = medicosLocal.findActive();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return medicosList;
    }

    public void setMedicosList(List<TblMedico> medicosList) {
        this.medicosList = medicosList;
    }
    
    public String getFecIniCreacion() {
        return fecIniCreacion;
    }

    public void setFecIniCreacion(String fecIniCreacion) {
        this.fecIniCreacion = fecIniCreacion;
    }

    public String getFecEndCreacion() {
        return fecEndCreacion;
    }

    public void setFecEndCreacion(String fecEndCreacion) {
        this.fecEndCreacion = fecEndCreacion;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getRptFileName() {
        return rptFileName;
    }

    public void setRptFileName(String rptFileName) {
        this.rptFileName = rptFileName;
    }

    public TblUsuarios getTblUsuario() {
        return tblUsuario;
    }

    public void setTblUsuario(TblUsuarios tblUsuario) {
        this.tblUsuario = tblUsuario;
    }
    
    public List<CatRolesUsuario> getRolesUsuarioList() {
        if (rolesUsuarioList.isEmpty()){
            System.out.println("Getting roles usuario: "+getTblUsuario().getNumUsuario());
            if (this.getTblUsuario().getNumUsuario()!=null){
                try{
                   rolesUsuarioList = rolesUsuarioLocal.findAllRolesByUser(getTblUsuario().getNumUsuario());
                }catch(Exception ex){
                   ex.printStackTrace();
                }
            }
        }
        return rolesUsuarioList;
    }
    
    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Long getRoleid() {
            return roleid;
    }

    public void setRoleid(Long roleid) {
            this.roleid = roleid;
    }

    public Long getSelectedRoleId() {
            return selectedRoleId;
    }

    public void setSelectedRoleId(Long selectedRoleId) {
            this.selectedRoleId = selectedRoleId;
    }

    public void setRolesUsuarioList(List<CatRolesUsuario> rolesUsuarioList) {
        this.rolesUsuarioList = rolesUsuarioList;
    }

    public List<SelectItem> getRolesList() {
        if (rolesList.isEmpty()){
            try{
                List<CatRoles> l = rolesLocal.findAllActiveRoles();
                rolesList.add(new SelectItem(-1L,"  --  "));
                for (CatRoles role : l) {
                        rolesList.add(new SelectItem(role.getCodRol(),role.getNomRol()));				
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return rolesList;
    }

    public void setRolesList(List<SelectItem> rolesList) {
            this.rolesList = rolesList;
    }
    
    public void guardar(ActionEvent ae ){
      try{
          if (!validate(getTblUsuario())){
             return;
          }
          
          if (tblUsuario.getNumEmpleado() == -1){
             tblUsuario.setNumEmpleado(null);
          }
          
          if (tblUsuario.getNumMedico() == -1){
              tblUsuario.setNumMedico(null);
          }
          
          if (getTblUsuario().getNumUsuario()!=null && getTblUsuario().getNumUsuario()>0){
             setTblUsuario(usuariosFacade.update(getTblUsuario()));
          }else{
             getTblUsuario().setNumUsuario(null);
             setTblUsuario(usuariosFacade.create(getTblUsuario()));
          }
          this.addInfo("La informacion ha sido almacenada satisfactoriamente", "La informacion ha sido almacenada satisfactoriamente");
        }catch(Exception ex){
            ex.printStackTrace();
            Throwable root = ExceptionHelper.getRootCause(ex);
            this.addError(root.getMessage(), root.toString());
        }
    }

    public boolean validate(TblUsuarios usuario){
        return true;
    }

    public void nuevo(ActionEvent ae){
       System.out.println("Limpiando los campos para agregar un nuevo registro");
       this.getRolesUsuarioList().clear();
       setTblUsuario(new TblUsuarios());
    }
    
    public void addRole(ActionEvent ae){
     try{
        CatRolesUsuario roleusuario = new CatRolesUsuario();
        CatRolesUsuarioPK rolesusuarioPK = new CatRolesUsuarioPK();
        rolesusuarioPK.setNumUsuario(getTblUsuario().getNumUsuario());
        rolesusuarioPK.setCodRol(this.getSelectedRoleId());
        roleusuario.setId(rolesusuarioPK);
        if (validate(roleusuario)){
            rolesUsuarioLocal.create(roleusuario);
        }
        this.getRolesUsuarioList().clear();
     }catch(Exception ex){
        ex.printStackTrace();
        this.addError(ex.getMessage(), ex.toString());
     }
   }

    public void deleteRole(ActionEvent ae){
        try{
            UIDataTable datatable = (UIDataTable)ae.getComponent().getParent().getParent();
            CatRolesUsuario rolesusuarioData = (CatRolesUsuario)datatable.getRowData();
            rolesUsuarioLocal.delete(rolesusuarioData);
            this.rolesUsuarioList.clear();
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.toString());
        }
    }
    
    public boolean validate(CatRolesUsuario r){
      boolean isValid = true;
      if (r.getId().getCodRol()==null || r.getId().getCodRol()==-1L){
         this.addError("Por favor seleccione un role valido", "Por favor seleccione un role valido");
         isValid = false;
      }
      if (r.getId().getNumUsuario()==null || r.getId().getNumUsuario()==0L || r.getId().getNumUsuario()==0L){
          this.addError("Por favor seleccione el usuario al que desea agregar el role", "Por favor seleccione el usuario al que desea agregar el role");
          isValid = false;
      }
      return isValid;
    }
    
    public String mostrarReporte(){
        return "/ReportServlet?faces-redirect=true&rptFileName="+rptFileName+"&docType="+docType+"&fechaIniCreacion="+fecIniCreacion+"&fecEndCreacion="+fecEndCreacion;
    }
    
    public void init(){
        System.out.println("Usuario: "+codUsuario);
        if (!FacesContext.getCurrentInstance().isPostback()){
            System.out.println("Usuario: "+codUsuario);
            if (codUsuario!=null && codUsuario>0){
                    try{
                        System.out.println("Usuario: "+codUsuario);
                        setTblUsuario(usuariosFacade.findUsuarioByPk(codUsuario));
                    }catch(Exception ex){
                        ex.printStackTrace();
                        this.addError(ex.getMessage(), ex.getMessage());
                    }
                }
        }
   }

    public void cambiarContrasena(ActionEvent ae){
        try{
            usuariosFacade.cambiarContrasena(tblUsuario.getAliUsuario(), this.getContrasenaAnterior(), nuevaContrasena, confirmacionContrasena);
            this.addInfo("La contrasena ha sido restablecida", "La contrasena ha sido restablecida");
        }catch(Exception ex){
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
    
    public void restablecer(ActionEvent ae){
        try{
            usuariosFacade.restablecerContrasena(tblUsuario.getAliUsuario(), tblUsuario.getConUsuario(), tblUsuario.getPasswordConfirmation());
            this.addInfo("La contrasena ha sido restablecida", "La contrasena ha sido restablecida");
        }catch(Exception ex){
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
}