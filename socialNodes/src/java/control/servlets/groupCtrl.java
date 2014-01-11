/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelDB.DBmanager;
import modelDB.Utente;

/**
 *
 * @author Giulian
 */
public class groupCtrl extends HttpServlet {

    private DBmanager manager;

    @Override
    public void init() {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBmanager) super.getServletContext().getAttribute("dbmanager");
    }

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
        String operazione = request.getParameter("op");
        RequestDispatcher dispatcher;

        HttpSession session = request.getSession(false);
        Utente user = (Utente) session.getAttribute("user");

        switch (operazione) {
            case "displaygroup":
                dispatcher = request.getRequestDispatcher("/groupcontrolled/displaygroup.jsp");
                dispatcher.forward(request, response);

                break;
            default:
                //da decidere cosa fare
                response.sendRedirect(request.getContextPath());
                return;
        }

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

        String operazione = request.getParameter("op");
        RequestDispatcher dispatcher;

        HttpSession session = request.getSession(false);
        Utente user = (Utente) session.getAttribute("user");

        switch (operazione) {
            case "creagruppo":   //codice per gestire la creazione di un gruppo
                System.out.println("Analisi del form di creazione nuovo gruppo");
                String inviti2parse = "";
                String creazione_gruppoNome = "";
                // ArrayList<String> utentiSbagliati = new ArrayList<String>();
                boolean ok_crea_gruppo = true;
                // boolean ok_inviti = true;
                boolean isPublic = false;
                boolean ok_radio = true;

                //recupero parametri per la creazione
                try {
                    creazione_gruppoNome = request.getParameter("creazione_gruppo_nome");
                    if (creazione_gruppoNome == null) {
                        ok_crea_gruppo = false;
                    }
                } catch (Exception e) {
                    ok_crea_gruppo = false;
                }

//                try {
//                    inviti2parse = request.getParameter("areainviti");
//                    if (inviti2parse == null) {
//                        ok_inviti = false;
//                    }
//                } catch (Exception e) {
//                    ok_inviti = false;
//                }
                try {
                    String radio = request.getParameter("radios");
                    if (radio.equals("privato")) {
                        isPublic = false;
                    } else if (radio.equals("pubblico")) {
                        isPublic = true;
                    } else {
                        ok_radio = false;
                    }
                } catch (Exception e) {
                    ok_radio = false;
                }

                if (ok_crea_gruppo && /*!"".equals(inviti2parse) && */ !"".equals(creazione_gruppoNome) && creazione_gruppoNome != null) {
                    try {
                        Utente ownernewgroup = (Utente) ((HttpServletRequest) request).getSession().getAttribute("user");
                        try {
                            manager.creaGruppo(user, creazione_gruppoNome, isPublic);
                        } catch (SQLException ex) {
                            Logger.getLogger(groupCtrl.class.getName()).log(Level.SEVERE, null, ex);
                            System.err.println("Groupctrl: errore nel creare un gruppo " + ex.getMessage());
                        }

                    } catch (Exception e) {
                        System.err.println("Groupctrl: errore! " + e.getMessage());
                    }
                } else {
                    dispatcher = request.getRequestDispatcher("/errorpage.jsp");
                     dispatcher.forward(request, response);
                }
                //response.sendRedirect(request.getContextPath()+"/afterLogged/main.jsp");
                dispatcher = request.getRequestDispatcher("/afterLogged/main.jsp");
                dispatcher.forward(request, response);
                break;

            default:
                dispatcher = request.getRequestDispatcher("/errorpage.jsp");
                 dispatcher.forward(request, response);
        }
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
