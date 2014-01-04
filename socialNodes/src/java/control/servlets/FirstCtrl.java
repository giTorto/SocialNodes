/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class FirstCtrl extends HttpServlet {

    private DBmanager manager;

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
        

    }

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
       String operazione;
        operazione = request.getParameter("op");
        Utente user = null;

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            user = (Utente) session.getAttribute("user");
        }
        try {
           switch (operazione) {
               case "log":
                   {
                       if (user != null) {
                           //sei già loggato con l'utente user.getUsername
                       }      String username = request.getParameter("email");
                String password = request.getParameter("password");
                       //inoltre qui va aggiunto l'aggiornamento della data dell'ultimo accesso nel db
                       user = manager.authenticate(username, password);
                       if (user == null) {
                           //l'utente non esiste
                           response.sendRedirect(request.getContextPath() + "/logIn.jsp");
                       } else {
                           HttpSession sessione = request.getSession(true);
                           sessione.setAttribute("user", user);
                           response.sendRedirect(request.getContextPath() + "/main.jsp");
                       }      break;
                   }
               case "creacc":
                   {
                       Boolean riuscito;
                       String username = request.getParameter("username");
                       String password = request.getParameter("password");
                       String email = request.getParameter("email");
                       //dbmanager inseriscie riga nella tabella utente
                       riuscito = manager.addUtente(username, email, password); //qui volendo si potrebbe passare per utente ma non darebbe nessun vantaggio
                       if(riuscito){
                           //a creazione effettuata rimanda a pagina di login
                           response.sendRedirect(request.getContextPath() + "/login.jsp");
                       }else{
                           //qui devo passare attraverso un bean o something like that per segnalare che email o username sono già
                           //nel sistema
                           response.sendRedirect(request.getContextPath()+"/createAccount.jsp");
                       }      break;
                   }
           }

        } catch (SQLException ex) {
            Logger.getLogger(FirstCtrl.class.getName()).log(Level.SEVERE, null, ex);
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
