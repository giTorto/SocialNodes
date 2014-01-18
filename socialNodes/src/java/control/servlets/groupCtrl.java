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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
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
                Gruppo gruppo_disp = null;
                ArrayList<Post> posts = new ArrayList<>();
                try {
                    String groupid = request.getParameter("groupid");                
                    gruppo_disp = manager.getGruppo(Integer.parseInt(groupid));
                    posts= manager.getPostsGruppo(gruppo_disp);
                } catch (SQLException e) {
                    System.err.println("groupCtrl: case displaygroup, errore nel recuperare il gruppo dal db");
                }

                request.setAttribute("gruppo", gruppo_disp);
                request.setAttribute("lista_post", posts);

                dispatcher = request.getRequestDispatcher("/groupcontrolled/displaygroup.jsp");
                dispatcher.forward(request, response);

                break;

            case "settings":
                Gruppo gruppo_sett = null;
                try {
                    String groupid = request.getParameter("groupid");

                    gruppo_sett = manager.getGruppo(Integer.parseInt(groupid));
                } catch (SQLException e) {
                    System.err.println("groupCtrl: case displaygroup, errore nel recuperare il gruppo dal db");
                }

                request.setAttribute("gruppo", gruppo_sett);

                dispatcher = request.getRequestDispatcher("/groupcontrolled/settings_group.jsp");
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

        String operazione;

        operazione = request.getParameter("op");
        if (operazione == null) {
            operazione = "";
        }

        RequestDispatcher dispatcher;

        HttpSession session = request.getSession(false);
        Utente user = (Utente) session.getAttribute("user");

        switch (operazione) {
            case "creagruppo": //codice per gestire la creazione di un gruppo
            {
                System.out.println("Analisi del form di creazione nuovo gruppo");
                String inviti2parse = "";
                String creazione_gruppoNome = "";
                ArrayList<String> utentiSbagliati = new ArrayList<String>();
                boolean ok_crea_gruppo = true;
                boolean ok_inviti = true;
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

                try {
                    inviti2parse = request.getParameter("areainviti");
                    if (inviti2parse == null) {
                        ok_inviti = false;
                    }
                } catch (Exception e) {
                    ok_inviti = false;
                }

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

                if (ok_crea_gruppo && !"".equals(inviti2parse) && !"".equals(creazione_gruppoNome) && creazione_gruppoNome != null) {
                    try {
                        Utente ownernewgroup = (Utente) ((HttpServletRequest) request).getSession().getAttribute("user");
                        try {
                            manager.creaGruppo(user, creazione_gruppoNome, isPublic);

                            Gruppo gruppo_appena_creato = manager.getGruppo(creazione_gruppoNome);
                            ArrayList<String> username_invitati = MyUtil.parseFromString(inviti2parse);
                            utentiSbagliati = MyUtil.sendinviti(username_invitati, gruppo_appena_creato.getIdgruppo(), manager);
                            if (!utentiSbagliati.isEmpty()) {
                                //prepara un messaggio bean degli invitati a cui non si è potuto mandare l'invito
                            }
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
                //se tutto è andato bene
                //response.sendRedirect(request.getContextPath()+"/afterLogged/main.jsp");
                dispatcher = request.getRequestDispatcher("/afterLogged/main.jsp");
                dispatcher.forward(request, response);
            }
            break;

            case "accettainviti": //codice per gestire gli inviti accettati dall'utente
            {
                ArrayList<Integer> groupids = new ArrayList<Integer>();
                Enumeration paramNames = request.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    Integer param_groupid;
                    String param = (String) paramNames.nextElement();
                    if (!param.equals("op")) {
                        groupids.add(Integer.parseInt(param));
                    }
                }

                try {
                    Utente utente = (Utente) request.getSession().getAttribute("user");
                    try {
                        for (Integer id_gruppo_accettato : groupids) {
                            manager.updatePartecipanti(utente.getId(), (int) id_gruppo_accettato);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(groupCtrl.class.getName()).log(Level.SEVERE, null, ex);

                    }
                } catch (Exception e) {
                    System.err.println("errore nel gestire gli inviti");
                }
                //se tutto è andato bene
                dispatcher = request.getRequestDispatcher("/afterLogged/main.jsp");
                dispatcher.forward(request, response);
            }
            break;

            case "modificagruppo": {
                System.out.println("modificagruppo!!!");
                //dichiarazione parametri passati dal form modificagruppo
                int idgruppo = -1;
                String nuovonome = null;
                String radio = null;
                String inviti2parse = null;
                Gruppo gruppo = null;

                //recupero idgruppo
                try {
                    idgruppo = Integer.parseInt(request.getParameter("groupid"));
                } catch (Exception e) {
                    System.err.println("groupCtrl: modificagruppo, errore nel recupero dell'id del gruppo da modificare");
                }
                //recupero info sul gruppo da modificare
                try {
                    gruppo = manager.getGruppo(idgruppo);
                } catch (SQLException e) {
                    System.err.println("groupCtrl: modificagruppo, errore nel recupero del gruppo da modificare");
                }

                //recupero parametro nuovonome e salvataggio nel db
                boolean ok_nuovonome = true;
                try {
                    nuovonome = request.getParameter("modifica_nomegruppo");
                } catch (Exception e) {
                    ok_nuovonome = false;
                }
                if (ok_nuovonome && nuovonome != null) {
                    try {
                        manager.updateGroupName(gruppo.getIdgruppo(), nuovonome);
                    } catch (SQLException ex) {
                        Logger.getLogger(groupCtrl.class.getName()).log(Level.SEVERE, null, ex);
                        System.err.println("groupCtrl: modificagruppo: errore nel cambio nome al gruppo");
                    }
                }

                //recupero parametro radio pubblico o privato e conseguenti operazioni su db
                boolean ok_radio = true;
                int isPublic = gruppo.getIsPublic();
                try {
                    radio = request.getParameter("radios");
                    if (radio.equals("privato")) {
                        isPublic = 0;
                    } else if (radio.equals("pubblico")) {
                        isPublic = 1;
                    } else {
                        ok_radio = false;
                    }
                } catch (Exception e) {
                    ok_radio = false;
                }

                if (ok_radio && (gruppo.getIsPublic() != isPublic)) {
                    //se è stato cambiato il flag allora agisici di conseguenza
                    System.out.println("grouctrl: modificagruppo sto cambiando le impostazioni del gruppo perchè è stato impostato un nuovo flag");
                    try {
                        manager.updateGroupFlag(gruppo.getIdgruppo(), isPublic);
                    } catch (SQLException e) {
                        System.err.println("groupCtrl: modificagruppo: errore nel cambio flag al gruppo");
                    }
                    //codice per gestire le FAMOSE CONSIDERAZIONI circa il passaggio dei partecipanti da pubblico a privato ecc
                }

                //gestione degli eventuali inviti, da fare solo se il radios=privato
                if (isPublic == 0) {
                    //codice per mandare gli inviti, recupero parametro invitati
                    boolean ok_inviti = true;
                    try {
                        inviti2parse = request.getParameter("areainviti");
                        if (inviti2parse == null || inviti2parse.equals("")) {
                            ok_inviti = false;
                        }
                    } catch (Exception e) {
                        ok_inviti = false;
                        System.err.println("groupCtrl: modificagruppo, errore nel recupero degli invitati");
                    }
                    if (ok_inviti) {
                        try {
                            ArrayList<String> username_invitati = MyUtil.parseFromString(inviti2parse);
                            ArrayList<String> utentiSbagliati = new ArrayList<>();
                            utentiSbagliati = MyUtil.sendinviti(username_invitati, gruppo.getIdgruppo(), manager);
                            if (!utentiSbagliati.isEmpty()) {
                                //prepara un messaggio bean degli invitati a cui non si è potuto mandare l'invito
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(groupCtrl.class.getName()).log(Level.SEVERE, null, ex);
                            System.err.println("Groupctrl: modificagruppo, errore nel mandare nuovi inviti " + ex.getMessage());
                        }
                    }

                }

                //fine modificagruppo
//                dispatcher = request.getRequestDispatcher("/groupcontrolled/displaygroup.jsp");
//                dispatcher.forward(request, response);
                response.sendRedirect(request.getContextPath());
            }

            break;

            case "": { // caso in cui sto facendo aggiunta post

                //  
                
                Integer idgruppo = 0;
                String messaggio = "";

                String fileName, relPath;
                String path;
                String tmp = null;
                fileName = null;
                InputStream inStream;

                try {
                    ServletFileUpload fileUpload = new ServletFileUpload();
                    FileItemIterator items = fileUpload.getItemIterator(request);

                    while (items.hasNext()) {
                        FileItemStream item = items.next();
                        if (!item.isFormField()) {
                            InputStream is = new BufferedInputStream(item.openStream());
                            if (is.available() > 0) {
                                BufferedOutputStream output = null;
                                try {
                                    ServletContext scx = getServletContext();
                                    path = scx.getRealPath("") + "\\media";
                                    relPath = "media";
                                    makeDir(path);
                                    path += "\\" + idgruppo;
                                    makeDir(path);
                                    fileName = MyUtil.formatName(item.getName());
                                    //seed è il seme per l'algoritmo di hashing, per renderlo unico è composto dal nome del file, l'id utente e il tempo preciso al millisecondo (che garantisce)

                                    String seed = fileName + user.getId() + new Timestamp(new java.util.Date().getTime()).toString();
                                    tmp = md5(seed);
                                    tmp = tmp + MyUtil.getExtension(fileName);
                                    path += "\\" + tmp;
                                    relPath += "\\" + (idgruppo + "\\" + tmp);

                                    output = new BufferedOutputStream(new FileOutputStream(path, false));
                                    int data = -1;
                                    while ((data = is.read()) != -1) {
                                        output.write(data);
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
                                case "idgruppo":
                                    idgruppo = Integer.parseInt(Streams.asString(item.openStream()));
                                    break;
                                case "messaggio":
                                    messaggio = Streams.asString(item.openStream());
                                    if (messaggio == null || "".equals(messaggio)) {
                                        Message error = new Message();
                                        error.setMessaggio("E' necessario inserire del testo");
                                        request.setAttribute("messaggioBean", error);
                                        dispatcher = request.getRequestDispatcher("/socialNodes/afterLogged/groupCtrl?op=displaygroup&groupid=" + idgruppo);
                                        dispatcher.forward(request, response);
                                    }
                                    break;
                            }
                        }
                    }
                    String resultament = checkText(messaggio, fileName, tmp, idgruppo);
                    manager.addPostFile(user, idgruppo, fileName, tmp, resultament);
                    response.sendRedirect("/socialNodes/afterLogged/groupCtrl?op=displaygroup&groupid="+idgruppo);
                    
                    
                } catch (FileUploadException ex) {
                    Logger.getLogger(groupCtrl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(groupCtrl.class.getName()).log(Level.SEVERE, null, ex);
                }

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

    MessageDigest messageDigest = null;
    final String dollars = "$$";

    public void makeDir(String path) throws ServletException {
        File theDir = new File(path);
        if (!theDir.exists()) {
            boolean result = theDir.mkdir();
            if (!result) {
                throw new ServletException("Cannot create a DIR");
            }
        }

    }

    public String md5(String gen) {
        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(groupCtrl.class.getName()).log(Level.SEVERE, "Non va il cypher", ex);
            }
        }
        byte[] mdbytes = messageDigest.digest(gen.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    /**
     * Controlla il testo sistemando eventuali link
     *
     * @param text testo dell'utente
     * @param fileName eventuale file caricato dall'utente
     * @param fileId eventuale id del file caricato dall'utente
     * @return il testo con i link esatti
     */
    public String checkText(String text, String fileName, String fileId, int idgruppo) {
        Boolean found = false;
        ArrayList<String> parts = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        int end, finder;
        int index = 0;
        String retVal = "", toAdd, nameFound, textFound;
        if (text != null && !text.equals("")) {
            while (!text.equals("")) {
                end = text.indexOf(dollars);

                if (end < 0) {
                    parts.add(index, text);
                    names.add(index, null);
                    text = "";
                } else {
                    toAdd = text.substring(0, end);
                    if (end > 0) {
                        if (found) {
                            finder = toAdd.indexOf(":");
                            if (finder > 0) {
                                nameFound = text.substring(0, finder);
                                textFound = text.substring(finder + 1, end);
                                parts.add(index, textFound);
                                names.add(index, nameFound);
                            } else {
                                parts.add(index, toAdd);
                                names.add(index, "");
                            }
                        } else {
                            parts.add(index, toAdd);
                            names.add(index, null);
                        }
                        index++;

                    }
                    found = !found;
                    text = text.substring(end + 2);
                }

            }
            for (int i = 0; i < parts.size(); i++) {
                nameFound = names.get(i);
                textFound = parts.get(i);
                if (null != nameFound) {

                    String tmp = createLink(textFound, nameFound, fileId, idgruppo);
                    retVal += tmp;

                } else {
                    retVal += textFound;
                }
            }

        }

        return retVal;
    }

    /**
     * Trasforma un link dell'utente in un link HTML
     *
     * @param text testo del link
     * @param name nome dell'utente uploader del file
     * @param id dell'eventuale file caricato dall'utente
     * @return anchor tag aggiustata oppure il testo in input
     */
    public String createLink(String text, String name, String id, int idgruppo) {
        int idt = 0;

        if (!(id == null)) {
            idt = Integer.parseInt(id);
        }

        int tmp;
        if ("-1".equals(id) || id == null) {
            if (!(name.equals("") || name.equals(" "))) {
                if (name.contains("http")) {
                    tmp = 0;
                } else {
                    tmp = manager.getLinkByName(text, name, idgruppo);
                }

            } else {
                tmp = manager.getLRULink(text, idgruppo);
            }
        } else {
            tmp = idt;
        }

        if ("".equals(tmp) || tmp == 0) {
            text = text.toLowerCase();
            if ((name.contains("http") && text.contains("//")) || (name.contains("//") && name.contains("https"))) {
                if (text.contains("www")) {
                    return "<a href='" + name + "://" + text + "'>" + text.substring(2) + "</a>";
                } else {
                    return "<a href='" + name + "://" + text + "'>" + text.substring(2) + "</a>";
                }

            } else if (text.contains("www")) {
                return "<a href='http://" + text + "'>" + text + "</a>";

            } else {
                return text;
            }
        } else {

            return "<a href='fileDownload?fileId=" + tmp + "'>" + text + "</a>";
        }
    }

}
