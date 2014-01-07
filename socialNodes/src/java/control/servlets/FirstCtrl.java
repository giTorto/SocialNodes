/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelDB.DBmanager;
import modelDB.Message;
import modelDB.Utente;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import util.MyUtil;

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
        String operazione = null;
        operazione = request.getParameter("op");
        Message messaggioBean = new Message();
        messaggioBean.setMessaggio("");
        messaggioBean.setTipoutente("");
        messaggioBean.setValue("");
        RequestDispatcher dispatcher;
        request.setAttribute("messaggioBean", messaggioBean);

        switch (operazione) {
            case "gotologin":
                dispatcher = request.getRequestDispatcher("/logIn.jsp");

                break;
            case "gotologin2":
                dispatcher = request.getRequestDispatcher("/login2.jsp");

                break;
            case "gotocrea":
                dispatcher = request.getRequestDispatcher("/createAccount.jsp");

                break;
            case "recoverpassword":
                dispatcher = request.getRequestDispatcher("/recoverpassword.jsp");

                break;

            default:
                dispatcher = request.getRequestDispatcher("/index.jsp");
                break;
        }

        dispatcher.forward(request, response);
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
        String operazione, username = null, password = null, email = null;
        Message messaggioBean = new Message();
        messaggioBean.setMessaggio("");
        messaggioBean.setValue("");
        messaggioBean.setTipoutente("");
        RequestDispatcher dispatcher;
        operazione = request.getParameter("op");
        if (operazione == null) {
            operazione = "";
        }
        Utente user = null;

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            user = (Utente) session.getAttribute("user");
        }
        try {
            switch (operazione) {
                case "login":
                    if (user != null) {
                        //sei già loggato con l'utente user.getUsername
                    }
                    username = request.getParameter("email");
                    password = request.getParameter("password");
                    //inoltre qui va aggiunto l'aggiornamento della data dell'ultimo accesso nel db
                    user = manager.authenticate(username, password);
                    if (user == null) {
                        //l'utente non esiste response.sendRedirect(request.getContextPath() + "/logIn.jsp");
                        //commento la riga sotto solo per provare il login che ho preparato io!!! poi basta scegliere quale mantenere
                        //dispatcher = request.getRequestDispatcher("/logIn.jsp");
                        dispatcher = request.getRequestDispatcher("/login2.jsp");
                        messaggioBean.setMessaggio("L'e-mail o la password inserita non e' corretta");
                        request.setAttribute("messaggioBean", messaggioBean);
                        dispatcher.forward(request, response);
                    } else {
                        dispatcher = request.getRequestDispatcher("/afterLogged/main.jsp");
                        HttpSession sessione = request.getSession(true);
                        sessione.setAttribute("user", user);

                        // request.setAttribute("user",user);
                        dispatcher.forward(request, response);

                       // HttpSession sessione = request.getSession(true);
                        // sessione.setAttribute("user", user);
                        // response.sendRedirect(request.getContextPath() + "/afterLogged/main.jsp");
                    }
                    break;
                case ""://caso in cui sto creando un account
                    try {
                        String path, relPath, fileName, tmp;
                        ServletFileUpload fileUpload = new ServletFileUpload();
                        FileItemIterator items;

                        items = fileUpload.getItemIterator(request);

                        while (items.hasNext()) {
                            FileItemStream item = items.next();
                            if (!item.isFormField()) {
                                //String mimetype= new MimetypesFileTypeMap().getContentType(item);
                                //String type = mimetype.split("/")[0];
                                InputStream is = new BufferedInputStream(item.openStream());
                                if (is.available() > 0) {
                                    BufferedOutputStream output = null;
                                    try {
                                        String tipo;
                                        ServletContext scx = getServletContext();
                                        path = scx.getRealPath("") + "\\media";
                                        relPath = "media";
                                        makeDir(path);
                                        path += "\\" + "avatar";
                                        makeDir(path);
                                        fileName = MyUtil.formatName(item.getName());;
                                        //seed è il seme per l'algoritmo di hashing, per renderlo unico è composto dal nome del file, l'id utente e il tempo preciso al millisecondo (che garantisce)

                                        tmp = username;
                                        tipo = MyUtil.getExtension(fileName);

                                        tmp = tmp + tipo;
                                        path += "\\" + tmp;
                                        relPath += "\\avatar\\" + tmp;

                                        output = new BufferedOutputStream(new FileOutputStream(path, false));
                                        if (!MyUtil.isImage(new File(path))) {
                                            //qui devo passare attraverso un bean o something like that per segnalare
                                            //che il file non è un immagine
                                            //response.sendRedirect(request.getContextPath() + "/createAccount.jsp");
                                            output.close();
                                            dispatcher = request.getRequestDispatcher("/createAccount.jsp");
                                            messaggioBean.setMessaggio("Per l'avatar è necessario scegliere un immagine");
                                            request.setAttribute("messaggioBean", messaggioBean);
                                            dispatcher.forward(request, response);
                                        } else {
                                            int data = -1;
                                            while ((data = is.read()) != -1) {
                                                output.write(data);
                                            }
                                        }

                                    } catch (IOException ioe) {
                                        throw new ServletException(ioe.getMessage());
                                    } finally {
                                        is.close();
                                        if (output != null) {
                                            output.close();
                                        }
                                    }
                                }
                            } else {
                                switch (item.getFieldName()) {
                                    case "username":
                                        username = Streams.asString(item.openStream());
                                        if (manager.nameAlreadyExist(username) || username == null || username.equals("")) {
                                            //qui devo passare attraverso un bean o something like that per segnalare
                                            //che username è già nel sistema
                                            //response.sendRedirect(request.getContextPath() + "/createAccount.jsp");
                                            dispatcher = request.getRequestDispatcher("/createAccount.jsp");
                                            messaggioBean.setMessaggio("Username già in uso");
                                            request.setAttribute("messaggioBean", messaggioBean);
                                            dispatcher.forward(request, response);
                                        }
                                        break;
                                    case "email":
                                        email = Streams.asString(item.openStream());
                                        if (manager.mailAlreadyExist(email) || email == null || email.equals("")) {
                                            //qui devo passare attraverso un bean o something like that per segnalare
                                            //che email è già nel sistema
                                            //response.sendRedirect(request.getContextPath() + "/createAccount.jsp");
                                            dispatcher = request.getRequestDispatcher("/createAccount.jsp");
                                            messaggioBean.setMessaggio("email già in uso");
                                            request.setAttribute("messaggioBean", messaggioBean);
                                            dispatcher.forward(request, response);
                                        }
                                        break;
                                    case "password":
                                        password = Streams.asString(item.openStream());
                                        if (password == null || password.equals("")) {
                                            dispatcher = request.getRequestDispatcher("/createAccount.jsp");
                                            messaggioBean.setMessaggio("Scegliere una password");
                                            request.setAttribute("messaggioBean", messaggioBean);
                                            dispatcher.forward(request, response);
                                        }

                                        break;
                                }
                            }
                        }
                    } catch (FileUploadException ex) {
                        Logger.getLogger(FirstCtrl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //dbmanager inserisce riga nella tabella utente
                    manager.addUtente(username, email, password); //qui volendo si potrebbe passare per utente ma non darebbe nessun vantaggio
                    //response.sendRedirect(request.getContextPath() + "/logIn.jsp");
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                    messaggioBean.setValue(email);
                    request.setAttribute("messaggioBean", messaggioBean);
                    dispatcher.forward(request, response);
                    break;
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

    public void makeDir(String path) throws ServletException {
        File theDir = new File(path);
        if (!theDir.exists()) {
            boolean result = theDir.mkdir();
            if (!result) {
                throw new ServletException("Cannot create a DIR");
            }
        }

    }
}
