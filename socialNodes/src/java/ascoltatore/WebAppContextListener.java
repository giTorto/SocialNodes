/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ascoltatore;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import modelDB.DBmanager;
import modelDB.Gruppo;
import modelDB.Message;

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
            mess.setMessaggio("");
            sce.getServletContext().setAttribute("messaggioBean", dburl);
            sce.getServletContext().setAttribute("public_groups", g);
        } catch (SQLException ex) {
            Logger.getLogger(getClass().getName()).severe(ex.toString());
            throw new RuntimeException(ex);

        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        DBmanager.shutdown();
    }
}
