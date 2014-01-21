
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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelDB.DBmanager;
import modelDB.Gruppo;
import modelDB.Message;
import modelDB.Post;
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

        if (operazione != null) {
            switch (operazione) {

                case "postnonloggato":
                    response.sendRedirect(request.getContextPath());
                    return;
                // break;
                case "gotocrea":
                    dispatcher = request.getRequestDispatcher("/createAccount.jsp");

                    break;
                case "gotorecoverpassword":
                    dispatcher = request.getRequestDispatcher("/recoverpassword.jsp");

                    break;

                case "displaygroup":
                    Gruppo gruppo_disp = null;
                    ArrayList<Post> posts = new ArrayList<>();
                    try {
                        String groupid = request.getParameter("groupid");
                        gruppo_disp = manager.getGruppo(Integer.parseInt(groupid));
                        posts = manager.getPostsGruppo(gruppo_disp);
                    } catch (SQLException e) {
                        System.err.println("groupCtrl: case displaygroup, errore nel recuperare il gruppo dal db");
                    }

                    request.setAttribute("gruppo", gruppo_disp);
                    request.setAttribute("lista_post", posts);

                    dispatcher = request.getRequestDispatcher("/groupcontrolled/displaygroup.jsp");
                    dispatcher.forward(request, response);

                    break;

                default:
                    //qua visto che il bean è nella request e poi però bisogna fare per forza il redirect per riscrivere l'url, il bean vien perso
                    //bisogna inventarsi qlcs per scrivere il messaggio, tipo bean con scope application
                    messaggioBean.setMessaggio("Attenzione: Utilizza gli elementi disposti in questa pagina per compiere le operazioni che desideri");
                    response.sendRedirect(request.getContextPath());
                    return;

            }
        } else {
            response.sendRedirect(request.getContextPath());
            return;
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
                case "recoverpassword":
                    boolean ok = true;
                    Utente utente = null;
                    String user_password = "";
                    String nomeutente = "";
                    String to,
                     subject = "Recupero password",
                     text = "Socialnodes:\nScusa! non siamo riusciti a generare correttamente il testo da includere in questa mail.";
                    to = request.getParameter("email_to_recover");

                    if (to != null && !to.equals("")) {
                        try {

                            utente = manager.getMoreUtente(to);
                            user_password = manager.getPasswordUtente(to);
                            nomeutente = utente.getUsername();

                        } catch (Exception e) {
                            //dispatcher = request.getRequestDispatcher("/errorpage.jsp");
                            ok = false;
                            System.out.println("FirstCtrl: gestione mail, Errore nel recupero dati con cui mandare la mail");
                        }
                        try {
                            text = "Socialnodes:\n\nCaro " + nomeutente + ", la tua password è \"" + user_password + "\"\n";
                            if (ok && utente != null) {
                                MyUtil.sendMailGoogle(to, subject, text);
                            } else {
                                dispatcher = request.getRequestDispatcher("/index.jsp");
                                messaggioBean.setMessaggio("Attenzione: L'e-mail inserita per la procedura di recupero password\nnon e' corretta. Se vuoi puoi sempre registrarti al sistema!");
                                request.setAttribute("messaggioBean", messaggioBean);
                                dispatcher.forward(request, response);
                            }

                        } catch (AddressException e) {
                            Logger.getLogger(FirstCtrl.class.getName()).log(Level.SEVERE, null, e);
                            //dispatcher = request.getRequestDispatcher("/errorpage.jsp");
                        } catch (MessagingException ex) {
                            Logger.getLogger(FirstCtrl.class.getName()).log(Level.SEVERE, null, ex);
                            //dispatcher = request.getRequestDispatcher("/errorpage.jsp");
                        }
                        dispatcher = request.getRequestDispatcher("/terminecontrollapassword.jsp");
                        dispatcher.forward(request, response);
                    }

                    break;
                case "login":

                    username = request.getParameter("email");
                    password = request.getParameter("password");
                    //inoltre qui va aggiunto l'aggiornamento della data dell'ultimo accesso nel db
                    try {
                        user = manager.authenticate(username, password);
                    } catch (Exception e) {
                        response.sendRedirect(request.getContextPath());
                        return;
                    }

                    if (user == null) {
                        //l'utente non esiste response.sendRedirect(request.getContextPath() + "/logIn.jsp");

                        dispatcher = request.getRequestDispatcher("/index.jsp");
                        messaggioBean.setMessaggio("Attenzione: L'e-mail o la password inserita non e' corretta");
                        request.setAttribute("messaggioBean", messaggioBean);
                        dispatcher.forward(request, response);
                        //response.sendRedirect(request.getContextPath());
                        return;
                    } else {

                        HttpSession sessione = request.getSession(true);

                        Timestamp last_access = user.getLast_access();
                        Calendar calendar = Calendar.getInstance();
                        java.util.Date now = calendar.getTime();
                        Timestamp data_acc = new Timestamp(now.getTime());

                        Message data_accesso = new Message();
                        if (last_access != null) {
                            data_accesso.setMessaggio("Benvenuto " + user.getUsername() + " il tuo ultimo accesso è stato il " + last_access.toString());
                        } else {
                            data_accesso.setMessaggio("Benvenuto " + user.getUsername() + " è il tuo primo accesso");
                        }
//                       
                        session.setAttribute("messaggio_main", data_accesso);
                        ServletContext ctx = getServletContext();
                        //la parte qui sotto setta il link all'inizio ;)
                        if (user.getAvatar_link().equals("££standard_avatar$$.png")) {
                            user.setAvatar_link(request.getContextPath() + "/standard_image/££standard_avatar$$.png");
                        } else {
                            user.setAvatar_link(request.getContextPath() + "/media/avatar/" + user.getAvatar_link());
                        }
                        manager.setNewdate(data_acc, user.getId());
                        // request.setAttribute("user",user);
                        sessione.setAttribute("user", user);

                        // HttpSession sessione = request.getSession(true);
                        // sessione.setAttribute("user", user);
                        response.sendRedirect(request.getContextPath() + "/afterLogged/afterLogin?op=main");
                    }
                    break;
                case ""://caso in cui sto creando un account
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

                                        tmp = username;
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

                                        /*  if (!MyUtil.isImage(new File(path))) { non funziona
                                         //qui devo passare attraverso un bean o something like that per segnalare
                                         //che il file non è un immagine
                                         //response.sendRedirect(request.getContextPath() + "/createAccount.jsp");
                                           
                                         dispatcher = request.getRequestDispatcher("/createAccount.jsp");
                                         messaggioBean.setMessaggio("Attenzione Per l'avatar è necessario scegliere un immagine");
                                         request.setAttribute("messaggioBean", messaggioBean);
                                         dispatcher.forward(request, response);
                                         } */
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
                                            messaggioBean.setMessaggio("Attenzione Username già in uso");
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
                                            messaggioBean.setMessaggio("Attenzione email già in uso");
                                            request.setAttribute("messaggioBean", messaggioBean);
                                            dispatcher.forward(request, response);
                                        }
                                        break;
                                    case "password":
                                        password = Streams.asString(item.openStream());
                                        if (password == null || password.equals("")) {
                                            dispatcher = request.getRequestDispatcher("/createAccount.jsp");
                                            messaggioBean.setMessaggio("Attenzione Scegliere una password");
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
                    //dbmanager inseriscie riga nella tabella utente
                    if (!imgyes) {
                        manager.addUtente(username, email, password); //qui volendo si potrebbe passare per utente ma non darebbe nessun vantaggio
                    } else {
                        manager.addUtente(username, email, password, tmp);
                    }

                    //response.sendRedirect(request.getContextPath() + "/logIn.jsp");
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                    messaggioBean.setValue(email);
                    messaggioBean.setMessaggio("Registrazione effettuata con successo!\n");
                    request.setAttribute("messaggioBean", messaggioBean);
                    dispatcher.forward(request, response);
                    break;

                default:
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                    dispatcher.forward(request, response);
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
