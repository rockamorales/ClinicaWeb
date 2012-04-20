package sv.com.cormaria.clinica.web.managebeans.security;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import sv.com.cormaria.clinica.web.managebeans.base.PageBase;
import sv.com.cormaria.servicios.facades.security.CatUrlMenuSessionFacade;

@ManagedBean(name="frmFileSystem")
@RequestScoped
public class FrmFileSystem extends PageBase {
	@EJB
	private CatUrlMenuSessionFacade urlMenuSession;
	
    private static String SRC_PATH = "/";
    
    private FileSystemNode[] srcRoots;
    
    public synchronized FileSystemNode[] getSourceRoots() {
        if (srcRoots == null) {
            srcRoots = new FileSystemNode(SRC_PATH).getNodes();
        }
        return srcRoots;
    }
    
    /*public void processSelection(NodeSelectedEvent event) {
        HtmlTree tree = (HtmlTree) event.getComponent();
        FileSystemNode nodeTitle = (FileSystemNode) tree.getRowData();
        FrmPermisos frmPermisos = (FrmPermisos) this.getBean("#{frmPermisos}", FrmPermisos.class);
        frmPermisos.getPermisos().setUrlinfoadicional(nodeTitle.getPath());
    }*/
    
    public void addUrl(ActionEvent ae){
    	try{
                System.out.println(ae.getComponent().getParent().getParent().getParent().getParent().getClass().getName());
	    	/*Tree tree = (HtmlTree) ae.getComponent().getParent().getParent().getParent().getParent();
	    	FileSystemNode node = (FileSystemNode) tree.getRowData();
	    	Urlsmenu urlMenu = new Urlsmenu();
	    	Menu menu = (Menu) this.getBean("#{menu}", Menu.class);
	    	urlMenu.setCodigomenu(menu.getCodigomenu());
	    	String path = node.getPath();
	    	path = path.replace(".jsp", ".jsf");
	    	urlMenu.setUrl(path);
	    	urlMenuSession.insert(urlMenu);
	    	FrmOpcionesMenu frmMenu = (FrmOpcionesMenu)this.getBean("#{frmOpcionesMenu}", FrmOpcionesMenu.class);
	    	frmMenu.setUrlsList(new ArrayList<Urlsmenu>());*/
    	}catch(Exception ex){
    		ex.printStackTrace();
    		this.addError(ex.getMessage(), ex.getMessage());
    	}
    }
}