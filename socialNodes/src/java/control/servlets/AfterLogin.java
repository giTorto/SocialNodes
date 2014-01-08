/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
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

/**
 *
 * @author Giulian
 */
public class AfterLogin extends HttpServlet {
    DBmanager manager;
    
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
        String operazione = request.getParameter("op");
        RequestDispatcher dispatcher;

        HttpSession session = request.getSession(false);
        Utente user = (Utente) session.getAttribute("user");

        switch (operazione) {
            case "main":
                Timestamp data_acc = Timestamp.valueOf(request.getParameter("data_acc"));
                Timestamp last_access = user.getLast_access();
                Message data_accesso = new Message();
                if (last_access!=null){
                    data_accesso.setMessaggio("Benvenuto "+ user.getUsername() + "il tuo ultimo accesso è "+ last_access.toString());
                }else{
                    data_accesso.setMessaggio("Benvenuto " + user.getUsername() +" è il tuo primo accesso");
                }
                
                session.setAttribute("data_accesso", data_accesso);
                manager.setNewdate(data_acc,user.getId());
                dispatcher = request.getRequestDispatcher("/afterLogged/main.jsp");
                dispatcher.forward(request, response);
                break;
            case "tocreation":
                dispatcher = request.getRequestDispatcher("/afterLogged/createGruppo.jsp");
                dispatcher.forward(request, response);
              
                break;
            case "showinviti":
                //request.setAttribute("utente", user); dipende se si vuole lavorare su request o session
                //per l'utente ha più senso lavorare in sessione
                dispatcher = request.getRequestDispatcher("/afterLogged/showInviti.jsp");
                dispatcher.forward(request, response);
                break;
            case "showgruppi":
                //request.setAttribute("utente", user); dipende se si vuole lavorare su request o session
                 //per l'utente ha più senso lavorare in sessione
                dispatcher = request.getRequestDispatcher("/afterLogged/showGroups.jsp");
                dispatcher.forward(request, response);
                break;
            case "logout":

                session.removeAttribute("user");
                session.invalidate();

                request.setAttribute("message", "Logout effettuato con successo");
                dispatcher = request.getRequestDispatcher("/afterLogged/logout.jsp");
                dispatcher.forward(request, response);
                break;
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
