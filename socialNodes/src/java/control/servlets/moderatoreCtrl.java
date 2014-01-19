/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelDB.DBmanager;
import modelDB.Gruppo;
import modelDB.Message;
import modelDB.Utente;
import util.MyUtil;

/**
 *
 * @author Giulian
 */
public class moderatoreCtrl extends HttpServlet {

    private DBmanager manager;

    @Override
    public void init() {
        // inizializza il DBManager dagli attributi di Application
        this.manager = (DBmanager) super.getServletContext().getAttribute("dbmanager");
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

        HttpSession session = request.getSession(false);
        Utente user = (Utente) session.getAttribute("user");

        String operazione = null;
        operazione = request.getParameter("op");
        Message messaggioBean = new Message();
        messaggioBean.setMessaggio("");
        messaggioBean.setTipoutente("");
        messaggioBean.setValue("");
        RequestDispatcher dispatcher;
        request.setAttribute("messaggioBean", messaggioBean);

        if (operazione != null) {
            switch (operazione) {
                case "actionmoderatore":
                    System.out.println("recuperiamo param dal form");
                    String isAttivo = request.getParameter("isAttivo");
                    String groupid = request.getParameter("groupid");
                    System.out.println("groupid aggiornato:  " + groupid);
                    System.out.println("il flag attivo/bloccato deve valere:  " + isAttivo);
                    //bloccare/sbloccare un gruppo
                    if (isAttivo == null) {
                        isAttivo = "0";
                    }
                    int idgruppo = Integer.parseInt(groupid);
                    int attivo = Integer.parseInt(isAttivo);
                    if (idgruppo > 0) {
                        try {
                            Gruppo g = manager.getGruppo(idgruppo);
                            if (g.getIsAttivo() != attivo) {
                                //aggiorna il db
                                if (attivo == 0) {
                                    //sto bloccando il gruppo
                                    MyUtil.bloccaGruppo(user, idgruppo, manager);
                                } else {
                                    MyUtil.attivaGruppo(idgruppo, manager); //sto sbloccando il gruppo
                                }
                            }
                        } catch (SQLException e) {
                            //merda!
                        }
                    }

                    dispatcher = request.getRequestDispatcher("/afterLogged/moderatore.jsp");
                    dispatcher.forward(request, response);
                    // response.sendRedirect(request.getContextPath());
                    break;
            }
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
