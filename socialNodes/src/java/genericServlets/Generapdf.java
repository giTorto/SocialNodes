/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genericServlets;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelDB.Gruppo;
import modelDB.Utente;
import modelDB.DBmanager;

/**
 *
 * @author Lorenzo
 */
public class Generapdf extends HttpServlet {

    private DBmanager manager;
    Gruppo gruppo;
    int numpost = -1;
    Date datalastpost;
    ArrayList<Utente> utenti_gruppo = new ArrayList<Utente>();

    @Override
    public void init() {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBmanager) super.getServletContext().getAttribute("dbmanager");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            gruppo = manager.getGruppo(Integer.parseInt(request.getParameter("groupid")));
            numpost = manager.getNumPostPerGruppo(Integer.parseInt(request.getParameter("groupid")));
            datalastpost = manager.getDataUltimoPost(Integer.parseInt(request.getParameter("groupid")));
        } catch (SQLException | NullPointerException ex) {
            Logger.getLogger(Generapdf.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect(request.getContextPath() + "/afterLogged/afterLogin?op=main");
        }

        try {
            List<Integer> users_ids = manager.getUtenti(gruppo.getIdgruppo());
            for (Integer user_id : users_ids) {
                Utente u = null;
                u = manager.getMoreUtente(user_id);
                utenti_gruppo.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Generapdf.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("application/pdf");

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            if (gruppo != null) {
                document.add(new Paragraph("Nome del gruppo: " + gruppo.getNome()));
                document.add(new Paragraph("Proprietario gruppo: " + gruppo.getNomeOwner()));
            } else {
                document.add(new Paragraph("Gruppo sconosciuto"));
            }

            Paragraph p = new Paragraph("Di seguito vengono riportati gli username degli utenti del gruppo \"" + gruppo.getNome() + "\"\n");
            p.setSpacingAfter(25);
            document.add(p);

            PdfPTable table = new PdfPTable(1); // Code 1

            // Code 2
            table.addCell("Nomi degli utenti partecipanti");

            for (Utente utente : utenti_gruppo) {
                table.addCell(utente.getUsername());
            }
            if (utenti_gruppo.isEmpty()) {
                table.addCell("Il proprietario del gruppo è attualmente l'unico partecipante");
            }

            document.add(table);
            if (datalastpost == null || numpost == -1) {
                document.add(new Paragraph("Pare che nessuno abbia mai pubblicato un post!"));
            } else {
                document.add(new Paragraph("Data ultimo post: " + datalastpost.toString()));
                document.add(new Paragraph("Numero di post inseriti in questo gruppo: " + numpost));
            }

        } catch (DocumentException de) {
            de.printStackTrace();
            System.err.println("document: " + de.getMessage());
        }

        document.close();
        utenti_gruppo.clear();

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
