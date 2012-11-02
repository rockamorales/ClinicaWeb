/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.cormaria.clinica.web.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author romorales
 */
@WebServlet(name = "ReportServlet", urlPatterns = {"/ReportServlet"})
public class ReportServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType(CONTENT_TYPE);
        ServletOutputStream ouputStream = response.getOutputStream();
        String reportFileName = (String)request.getParameter("rptFileName");
        String docType = (String)request.getParameter("docType");
        String all = (String)request.getParameter("todos");
        Enumeration pageParams = request.getParameterNames();
        String paramsName;
        System.out.println("Parametro: "+(String)request.getParameter("frmUsuariosMant:rptFileName"));
        while (pageParams.hasMoreElements()){
            paramsName = (String)pageParams.nextElement();
            System.out.println("Parametro"+paramsName);
            if (paramsName.contains("rptFileName")){
                reportFileName = request.getParameter(paramsName);
            }
            if (paramsName.contains("docType")){
                docType = request.getParameter(paramsName);
            }
            if (paramsName.contains("all")){
                all = request.getParameter(paramsName);
            }
        }
        

        //PrintWriter out = response.getWriter();
        //String fileName = request.getSession().getServletContext().getRealPath("/reportes/");
        System.out.println(reportFileName);
        String[] filePathWithoutExtension = reportFileName.split("\\.");
        String[] fileName = filePathWithoutExtension[0].split("/");
        System.out.println(fileName.length);
        byte[] bytes = null;
        String html = null;
        Connection conexion = null;
        try {
            Context init = new InitialContext();
            //Context context = (Context) init.lookup("java:");
            DataSource dataSource = (DataSource) init.lookup("jdbc/clinicaDS");
            conexion = dataSource.getConnection();
            File reportFile = new File(request.getSession().getServletContext().
                                       getRealPath(reportFileName));
            //request.
            //Sin Parametros
            HashMap parameters = null;
            if (all != null){
                parameters = new HashMap();
            }else{
                parameters = proccessParameters(request);
            }
            //new HashMap();
            //parameters.put("Parametro1", "valor");
            //JasperRunManager.runReportToHtmlFile(
            if (docType.equals("pdf")){
                bytes = createPdf(reportFile.getPath(), parameters,
                                      conexion);
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                //response.setHeader("Content-Disposition", "attachment; filename=\""+fileName[fileName.length-1]+"\";");

                System.out.println("response: " + response==null);
                System.out.println("response: " + response==null);
                ouputStream.write(bytes, 0, bytes.length);

            }else if(docType.equals("xls")){
                bytes = createXls(reportFile.getPath(), parameters,
                                      conexion, request, fileName[fileName.length-1]);
                response.setContentType("application/vnd.ms-excel");
                response.setContentLength(bytes.length);
                response.setHeader("Content-Disposition", "attachment; filename=\""+fileName[fileName.length-1]+"\";");
                System.out.println("response: " + response==null);
                //ServletOutputStream ouputStream = response.getOutputStream();
                System.out.println("response: " + response==null);
                ouputStream.write(bytes, 0, bytes.length);

            }else if(docType.equals("doc")){

            }else{
                bytes = createHtml(reportFile.getPath(), parameters,
                                      conexion, request, fileName[fileName.length-1]);
                    response.setContentType("text/html");
                    response.setContentLength(bytes.length);
                    response.setHeader("Content-Disposition", "attachment; filename=\""+fileName[0]+"\";");

                    System.out.println("response: " + response==null);
                    //ServletOutputStream ouputStream = response.getOutputStream();
                    System.out.println("response: " + response==null);
                    ouputStream.write(bytes);
            }
            if (conexion != null) {
              if (!conexion.isClosed()) {
                conexion.close();
              }
            }
        }catch(JRException jr){
            jr.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NamingException ex) {
            ex.printStackTrace();
        }finally{
          try {
            if (conexion != null) {
              if (!conexion.isClosed()) {
                conexion.close();
              }
            }
          }
          catch (SQLException ex1) {
            ex1.printStackTrace();
          }
        }
        ouputStream.flush();
        ouputStream.close();
    }
    
    public HashMap proccessParameters(HttpServletRequest request){
        HashMap parameters = new HashMap();
        Enumeration pageParams = request.getParameterNames();
        String paramsName;
        while (pageParams.hasMoreElements()){
            paramsName = (String)pageParams.nextElement();
            if (!paramsName.contains("DataType") && !paramsName.equalsIgnoreCase("rptFileName") && !paramsName.equalsIgnoreCase("docType") && !paramsName.equalsIgnoreCase("todos") && !paramsName.equalsIgnoreCase("faces-redirect")){
                String dataType = request.getParameter(paramsName+"DataType");
                if (dataType!=null && dataType.equals("numerico")){
                    parameters.put(paramsName,new Integer(request.getParameter(paramsName)));
                }else if (dataType!=null && dataType.equals("fecha")){
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try{
                        formatter.parse(request.getParameter(paramsName));
                    }catch(Exception ex){
                    }
                }else{
                    parameters.put(paramsName,request.getParameter(paramsName));
                }
            }
        }
        //parameters.put(,)
        return parameters;
    }
    //Process the HTTP Get request
    private byte[] createPdf(String jasperPath, HashMap parameters, Connection conexion) throws JRException {
        return JasperRunManager.runReportToPdf(jasperPath, parameters, conexion);
    }
    private byte[] createHtml(String jasperPath, HashMap parameters, Connection conexion, HttpServletRequest request, String filePath) throws JRException {
        byte[] bytes = null;
        try {
            String htmlFileName = request.getSession().getServletContext().
                                  getRealPath("/reportes/") + filePath +".html";
            JasperRunManager.runReportToHtmlFile(jasperPath, htmlFileName,
                                                 parameters, conexion);
            File file = new File(htmlFileName);
            FileInputStream fStream = new FileInputStream(file);
            bytes = new byte[(int) fStream.getChannel().size()];
            fStream.read(bytes);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
    private byte[] createXls(String jasperPath,HashMap parameters, Connection conexion, HttpServletRequest request, String filePath){
        //Nombre archivo resultado.
        try {
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasperPath, parameters, conexion);
            String xlsFileName = request.getSession().getServletContext().
                       getRealPath("/Reportes/")+filePath+".xls";
            //Creacion del XLS
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.
                                  JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.
                                  OUTPUT_FILE_NAME, xlsFileName);
            //exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
            //                      Boolean.TRUE);
            //exporter.setParameter(JRXlsExporterParameter.,
            //                      Boolean.TRUE);
            //exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            //exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporter.exportReport();
            File file = new File(xlsFileName);
            FileInputStream fStream = new FileInputStream(file);
            byte[] bytes = new byte[(int)fStream.getChannel().size()];
            fStream.read(bytes);
            return bytes;
        } catch (JRException ex) {
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
