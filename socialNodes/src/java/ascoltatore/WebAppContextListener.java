/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ascoltatore;

import java.security.Security;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import modelDB.DBmanager;
import modelDB.Gruppo;
import modelDB.Message;
import util.MyUtil;

/**
 * Web application lifecycle listener.
 *
 * @author Giulian
 */
public class WebAppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dburl = sce.getServletContext().getInitParameter("dburl");

        try {
            DBmanager manager = new DBmanager(dburl);
            sce.getServletContext().setAttribute("dbmanager", manager);//pubblico l'attributo per il context
            ArrayList<Gruppo> g = new ArrayList<>();
            g = (new Gruppo()).listaGruppiPubblici();
            Message mess = new Message();
            mess.setMessaggio(" ");
            sce.getServletContext().setAttribute("messaggioBean", mess);
            sce.getServletContext().setAttribute("public_groups", g);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);

        }

        //inizializzazione della defaultsession
        System.out.println("Inizializzazione della defaultsession");
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

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        sce.getServletContext().setAttribute("MailSession", session);
        MyUtil.sc = sce.getServletContext();

        System.out.println("Inizializzazione della defaultsession terminata");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        DBmanager.shutdown();
    }

}
