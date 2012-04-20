package sv.com.cormaria.clinica.web.managebeans.security;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.event.ActionEvent;

import org.richfaces.component.UIDataTable;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.entidades.security.TblUsuarios;
import sv.com.cormaria.servicios.facades.security.TblUsuariosSessionFacadeLocal;

@ManagedBean(name="frmUsuarios")
@RequestScoped
public class FrmUsuarios extends PageBase{
    @EJB
    TblUsuariosSessionFacadeLocal usuarioLocal;

    private List<TblUsuarios> usuariosList = new ArrayList<TblUsuarios>();

    private HtmlInputText usuariosid = new HtmlInputText();
    private Long selectedUserId;

    public FrmUsuarios(){
    }
    
    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public HtmlInputText getUsuariosid() {
        return usuariosid;
    }

    public void setUsuariosid(HtmlInputText usuariosid) {
        this.usuariosid = usuariosid;
    }

    public List<TblUsuarios> getUsuariosList() {
        if (usuariosList.isEmpty()){
            try{
                usuariosList = usuarioLocal.findAllUsuarios();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return usuariosList;
    }

    public void setUsuariosList(List<TblUsuarios> usuariosList) {
        this.usuariosList = usuariosList;
    }	

    public void delete(ActionEvent ae){
        try{
            System.out.println("Eliminando un usuario");
            UIDataTable datatable = (UIDataTable)ae.getComponent().getParent().getParent().getParent();
            TblUsuarios usuarioData = (TblUsuarios)datatable.getRowData();
            System.out.println("usuarioData: "+usuarioData.getNumUsuario());
            usuarioLocal.delete(usuarioData);
            this.getUsuariosList().clear();
            this.addInfo("El registro ha sido eliminado", "El registro ha sido eliminado");
        }catch(Exception ex){
            ex.printStackTrace();
            this.addError(ex.getMessage(), ex.getMessage());
        }
    }
}