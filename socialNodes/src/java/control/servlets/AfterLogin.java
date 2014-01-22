/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control.servlets;

import java.util.ArrayList;
import javax.servlet.http.Cookie;
import modelDB.Gruppo;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelDB.DBmanager;
import modelDB.Utente;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import util.MyUtil;
import modelDB.Message;

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

                Timestamp last_access = user.getLast_access();
                Message data_accesso = new Message();
                if (last_access != null) {
                    data_accesso.setMessaggio("Benvenuto " + user.getUsername() + "il tuo ultimo accesso è " + last_access.toString());
                } else {
                    data_accesso.setMessaggio("Benvenuto " + user.getUsername() + " è il tuo primo accesso");
                }
                session.setAttribute("data_accesso", data_accesso);

                try {
                    ArrayList<Message> newInviti = new ArrayList<>();
                    newInviti = manager.getNewsInviti(last_access, user.getId());
                    ArrayList<Message> newPosts = new ArrayList<>();
                    newPosts = manager.getNewsPost(last_access, user.getId());
                    request.setAttribute("nuovInviti", newInviti);
                    request.setAttribute("nuoviPosts", newPosts);
                } catch (SQLException e) {
                }

                //recupero dal bb e metto tra i request attribute gli oggetti che servono per creare correttamente la pagina main
                //servono i gruppi dell'utente e le cose del quickdisplay
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
                ArrayList<Gruppo> gruppi_invitati = user.getInviti();
                request.setAttribute("gruppi", gruppi_invitati);
                dispatcher = request.getRequestDispatcher("/afterLogged/showInviti.jsp");
                dispatcher.forward(request, response);
                break;
            case "showgroups":
                ArrayList<Gruppo> g = new ArrayList<Gruppo>();
                ArrayList<Gruppo> gruppi_parte = user.getGruppiParte();
                ArrayList<Gruppo> gruppi_mio = user.getGruppiOwn();
                g = (new Gruppo()).listaGruppiPubblici();
                request.setAttribute("gruppi_pubblici", g);
                request.setAttribute("gruppi_miei", gruppi_mio);
                request.setAttribute("gruppi_parte", gruppi_parte);
                dispatcher = request.getRequestDispatcher("/afterLogged/showGroups.jsp");
                dispatcher.forward(request, response);
                break;
            case "showgruppi":
                //request.setAttribute("utente", user); dipende se si vuole lavorare su request o session
                //per l'utente ha più senso lavorare in sessione
                dispatcher = request.getRequestDispatcher("/afterLogged/showGroups.jsp");
                dispatcher.forward(request, response);
                break;
            case "topersonalsettings":
                Message mess = new Message();
                request.setAttribute("messaggioBean", mess);
                dispatcher = request.getRequestDispatcher("/afterLogged/showPersonalSettings.jsp");
                dispatcher.forward(request, response);
                break;
            case "logout":

                session.removeAttribute("user");
                session.invalidate();

                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                }

                //request.setAttribute("message", "Logout effettuato con successo");
                //dispatcher = request.getRequestDispatcher("/index.jsp");
                //dispatcher.forward(request, response);
                response.sendRedirect(request.getContextPath());
                break;
            case "tomoderatore":
                ArrayList<Gruppo> allgruppi = null;
                try {
                    allgruppi = user.getAllGruppi();
                } catch (Exception e) {
                    //merda!
                }
                request.setAttribute("allgruppi", allgruppi);
                dispatcher = request.getRequestDispatcher("/afterLogged/moderatore.jsp");
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
        String operazione, username = null;
        Message messaggioBean = new Message();
        messaggioBean.setMessaggio("");
        messaggioBean.setValue("");
        messaggioBean.setTipoutente("");
        String new_username = null;
        String new_password = null;
        String op = request.getParameter("op");

        if (op == null) {
            op = "personalsetting";
        }

        boolean ok_session_user = false;
        HttpSession session = request.getSession(false);
        Utente user = (Utente) session.getAttribute("user");
        if (user != null) {
            ok_session_user = true;
        }
        /*
         QUA BISOGNA FARE L'ITERATOR PER PRENDRSI I VARI PARAMETRI
         */
        switch (op) {
            case "personalsetting"://caso in cui sto modificando le impostazioni personali

                boolean imgyes = false;
                String tmp = null;
                try {
                    String path, relPath, fileName;
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

                                    tmp = user.getUsername();
                                    tipo = MyUtil.getExtension(fileName);

                                    tmp = tmp + tipo;
                                    path += "\\" + tmp;
                                    relPath += "\\avatar\\" + tmp;

                                    output = new BufferedOutputStream(new FileOutputStream(path, false));

                                    int data = -1;
                                    while ((data = is.read()) != -1) {
                                        output.write(data);
                                    }

                                    output.close();
                                    imgyes = true;
                                    user.setAvatar_link(tmp);
                                    manager.setNewImage(user.getId(), tmp);

                                } catch (IOException ioe) {
                                    throw new ServletException(ioe.getMessage());
                                    //System.err.println("Errore nello scrivere il file appena caricato all'interno del filesystem: " + ioe.getLocalizedMessage());
                                } catch (SQLException s) {

                                } finally {
                                    is.close();
                                    if (output != null) {
                                        output.close();
                                    }
                                }
                            }
                        } else {
                            switch (item.getFieldName()) {
                                case "new_username":
                                    new_username = Streams.asString(item.openStream());
                                    //recupero nuovousername e salvataggio nel db
                                    if (new_username != null && !new_username.equals("")) {
                                        try {
                                            if (!new_username.contains("£") && !new_username.contains(" ")) {
                                                manager.updateUserName(user.getId(), new_username);
                                            } else {
                                                messaggioBean.setMessaggio("L'username scelto non è valido");

                                            }
                                        } catch (SQLException ex) {
                                            Logger.getLogger(AfterLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            System.err.println("Afterlogin: personalsettings, errore nell'aggiornare username dell'utente " + user.getUsername());
                                        }
                                    }
                                    break;
                                case "new_password":
                                    new_password = Streams.asString(item.openStream());
                                    //recupero nuovapassword e salvataggio nel db
                                    if (new_password != null && !new_password.equals("")) {
                                        try {
                                            if (!new_password.contains(" ")) {
                                                manager.updateUserPassword(user.getId(), new_password);
                                            } else {
                                                messaggioBean.setMessaggio("La password scelta non è valida");

                                            }
                                        } catch (SQLException ex) {
                                            Logger.getLogger(AfterLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            System.err.println("Afterlogin: personalsettings, errore nell'aggiornare password dell'utente " + user.getUsername());
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                } catch (FileUploadException ex) {
                    Logger.getLogger(FirstCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (messaggioBean.getMessaggio() == null  || messaggioBean.getMessaggio().equals("")) {
                    response.sendRedirect(request.getContextPath() + "/afterLogin?op=main");
                } else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("showPersonalSettings.jsp");
                    request.setAttribute("messaggioBean", messaggioBean);
                    dispatcher.forward(request, response);
                }

                break;
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
