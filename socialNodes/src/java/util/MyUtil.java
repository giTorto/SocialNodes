/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.activation.MimetypesFileTypeMap;
import java.security.Security;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import modelDB.Gruppo;
import modelDB.Utente;
import modelDB.DBmanager;

/**
 *
 * @author Giulian
 */
public class MyUtil {

    public static ArrayList<String> parseFromString(String phrase_inviti) {
        ArrayList<String> retval = new ArrayList<String>();
        retval.clear();
        String delims = "[,]";
        String[] tokens = phrase_inviti.split(delims);
        retval.addAll(Arrays.asList(tokens));
        return retval;
    }

    public static ArrayList<String> sendinviti(ArrayList<String> usernames, int idgruppo, DBmanager manager) {
        ArrayList<String> utentiNonEsistenti = new ArrayList<String>();
        boolean noerr = true;
        for (String username : usernames) {
            try {
                Utente u = manager.getMoreByUserName(username);
                if (u != null && !manager.controllaInvitogià_esistente(idgruppo, u.getId())) {
                    Gruppo invitante = manager.getGruppo(idgruppo);
                    String nomeownerinvitante = invitante.getNomeOwner();
                    Utente owner = manager.getMoreByUserName(nomeownerinvitante);
                    if (u.getId() != owner.getId()) {
                        try {
                            manager.insertInvito(idgruppo, u.getId());
                        } catch (SQLException ex) {
                            Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    //System.err.println("Util.sendinviti: Impossibile invitare " + username);
                    utentiNonEsistenti.add(username);
                    noerr = false;
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyUtil.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Util.sendinviti: Impossibile invitare " + username);
            }
        }
        if (noerr) {
            return null;
        } else {
            return utentiNonEsistenti;
        }

    }

    public static boolean isImage(File f) {

        String mimetype = new MimetypesFileTypeMap().getContentType(f);

        String type = mimetype.split("/")[0];
        if (type.equals("image")) {
            return true;
        } else {
            f.delete();
            return false;
        }

    }

    /**
     * Su alcuni browser (IE) il file viene caricato con il path assoluto,
     * quindi bisogna recuperare il solo nome del file
     *
     * @param fileName
     * @return vero nome del file
     */
    public static String formatName(String fileName) {
        int index = fileName.lastIndexOf("\\");
        if (index > 0) {
            return fileName.substring(index + 1);
        }
        return fileName;
    }

    /**
     * Dato un file, trova la sua estensione
     *
     * @param fileName nome del file da controllare
     * @return estensione del file in input
     */
    public static String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        return (index >= 0) ? fileName.substring(index) : "";

    }

    public static void sendMailGoogle(String from_mail, String to, String from_password, String subject, String text) throws AddressException, MessagingException {
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
// Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");

        final String username = "socialnodes@gmail.com";
        final String password = "socialnodes123";

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // — Create a new message –
        Message msg = new MimeMessage(session);
// — Set the FROM and TO fields –
        msg.setFrom(
                new InternetAddress(username + ""));
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to, false));
        msg.setSubject(subject);
        msg.setText(text
                + "\n\n\n" + new Date());
        msg.setSentDate(
                new Date());
        Transport.send(msg);

    }

}
