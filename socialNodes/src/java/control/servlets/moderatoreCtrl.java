/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelDB.DBmanager;
import modelDB.Message;

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
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    System.out.println("ricevuto  " + name);
                    System.out.println("ricevuto  " + email);
//                    dispatcher=request.getRequestDispatcher("somepage.jsp");
//                    dispatcher.forward(request, response);
                    response.sendRedirect(request.getContextPath());
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
