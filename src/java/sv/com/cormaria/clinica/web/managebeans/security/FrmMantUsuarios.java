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
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.security.CatRoles;
import sv.com.cormaria.servicios.entidades.security.CatRolesUsuario;
import sv.com.cormaria.servicios.entidades.security.CatRolesUsuarioPK;
import sv.com.cormaria.servicios.entidades.security.TblUsuarios;
import sv.com.cormaria.servicios.facades.security.CatRolesSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.CatRolesUsuarioSessionFacadeLocal;
import sv.com.cormaria.servicios.facades.security.ExceptionHelper;
import sv.com.cormaria.servicios.facades.security.TblUsuariosSessionFacadeLocal;

/**
 *
 * @author Administrador
 */
@ManagedBean
@RequestScoped
public class FrmMantUsuarios extends PageBase {
    @EJB
    TblUsuariosSessionFacadeLocal usuariosFacade;
    
    @EJB
    CatRolesUsuarioSessionFacadeLocal rolesUsuarioLocal;

    @EJB
    CatRolesSessionFacadeLocal rolesLocal;
    
    private Long roleid;
    private Long selectedRoleId;

    private List<CatRolesUsuario> rolesUsuarioList = new ArrayList<CatRolesUsuario>();
    private List<SelectItem> rolesList = new ArrayList<SelectItem>();
    
    @ManagedProperty(value="#{usuario}")
    private UsuarioForm usuario;

    @ManagedProperty(value="#{param.codUsuario}")
    private Long codUsuario;
        
    public List<CatRolesUsuario> getRolesUsuarioList() {
        if (rolesUsuarioList.isEmpty()){
            System.out.println("Getting roles usuario: "+this.usuario.getTblUsuario().getNumUsuario());
            if (this.usuario.getTblUsuario().getNumUsuario()!=null){
                try{
                   rolesUsuarioList = rolesUsuarioLocal.findAllRolesByUser(usuario.getTblUsuario().getNumUsuario());
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
        if (codUsuario!=null){
            //if (this.usuario==null){
            //    System.out.println("Cod usuario 2: "+codUsuario);
            //    this.usuario = (UsuarioForm) this.getBean("#{usuario}", UsuarioForm.class);
            //}
            if (this.usuario.getTblUsuario().getNumUsuario()==null){
                try{
                    this.usuario.setTblUsuario(usuariosFacade.findUsuarioByPk(codUsuario));
                }catch(Exception ex){
                    ex.printStackTrace();
                    this.addError(ex.getMessage(), ex.getMessage());
                }
            }
        }
        this.codUsuario = codUsuario;
    }

    public UsuarioForm getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioForm usuario) {
        this.usuario = usuario;
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
          if (!validate(usuario.getTblUsuario())){
             usuario.getTblUsuario().setNumUsuario(null);
             return;
          }
          if (usuario.getTblUsuario().getNumUsuario()!=null && usuario.getTblUsuario().getNumUsuario()!=0){
             usuario.setTblUsuario(usuariosFacade.update(usuario.getTblUsuario()));
          }else{
             usuario.getTblUsuario().setNumUsuario(null);
             usuario.setTblUsuario(usuariosFacade.create(usuario.getTblUsuario()));
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
       this.usuario.setTblUsuario(new TblUsuarios());
    }
    
    public void addRole(ActionEvent ae){
     try{
        CatRolesUsuario roleusuario = new CatRolesUsuario();
        CatRolesUsuarioPK rolesusuarioPK = new CatRolesUsuarioPK();
        rolesusuarioPK.setNumUsuario(usuario.getTblUsuario().getNumUsuario());
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
}