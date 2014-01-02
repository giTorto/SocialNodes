/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Giulian
 */
public class DBmanager {
    private transient Connection con;//transient = non serializzabile

    public DBmanager(String dburl) throws SQLException {

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver", true, getClass().getClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e.toString(), e);
        }

        Connection con = DriverManager.getConnection(dburl);

        this.con = con;

    }

    public static void shutdown() {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {

            Logger.getLogger(DBmanager.class.getName()).info(ex.getMessage());

        }

    }
    
    /**
     * Autentica un utente in base a un nome utente e a una password
     *
     * @param username il nome utente
     * @param password la password
     * @return null se l'utente non è autenticato, un oggetto User se l'utente
     * esiste ed è autenticato
     * @throws java.sql.SQLException
     */
    public UtenteBean authenticate(String username, String password) throws SQLException {

        // usare SEMPRE i PreparedStatement, anche per query banali. 
        // *** MAI E POI MAI COSTRUIRE LE QUERY CONCATENANDO STRINGHE !!!! 
        PreparedStatement stm = con.prepareStatement("SELECT * FROM utente WHERE username = ? AND password = ?");
        try {
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    UtenteBean user = new UtenteBean();
                    user.setUsername(username);
                    user.setEmail("email");
                    user.setLast_access(rs.getTimestamp("data_ultimo_acc"));
                    user.setId(rs.getInt("idutente"));
                    return user;
                } else {
                    return null;

                }

            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                rs.close();
            }

        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }

    }
    
    public UtenteBean getMoreUtente(int id) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM utente WHERE idutente = ?");
        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    UtenteBean user = new UtenteBean();
                    user.setUsername(rs.getString("username"));

                    user.setId(rs.getInt("idutente"));
                    return user;
                } else {
                    return null;

                }

            } finally {
                // ricordarsi SEMPRE di chiudere i ResultSet in un blocco finally 
                rs.close();
            }

        } finally { // ricordarsi SEMPRE di chiudere i PreparedStatement in un blocco finally
            stm.close();
        }
    }
    
    public List<GruppoBean> getGruppiPubblici() throws SQLException {

        List<GruppoBean> gruppi = new ArrayList<GruppoBean>();
  
        PreparedStatement stm
                = con.prepareStatement("SELECT * FROM gruppo where pubblico=? ");

        try {
            stm.setInt(1, 1);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    GruppoBean p = new GruppoBean();
                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("data_creazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setIdOwner(rs.getInt("idowner"));
                    gruppi.add(p);
                }
            } finally {

                rs.close();
            }
        } finally {

            stm.close();
        }

        return gruppi;

    }
    
    
     /**
     * Permette di ottenere facilmente la lista di tutti i post di un gruppo ora
     * perfezionata, in ogni post c'è un oggetto Utente che è il writer
     *
     * @param idgruppo dai in input l'id del gruppo desiderato
     * @return ricevi la lista dei post in ordine di data inversa
     * @throws SQLException
     */
    public List<PostBean> getPostsGruppo(int idgruppo) throws SQLException {

        List<PostBean> posts = new ArrayList<>();
       
        String link;
        PreparedStatement stm
                = con.prepareStatement("SELECT * FROM post "
                        + "WHERE idgruppo = ? ORDER BY data_ora DESC");

        try {
            stm.setInt(1, idgruppo);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    PostBean p = new PostBean();
                    //Utente tu = getMoreUtente(rs.getInt("idwriter"));
                    p.setTesto(rs.getString("testo"));
                    p.setData_ora(rs.getTimestamp("data_ora"));
                    p.setIdwriter(rs.getInt("idwriter"));
                    if (rs.getString("dbname") != null) {
                        link = "<a href='fileDownload?fileId=" + rs.getInt("idpost") + "'>" + rs.getString("realname") + "</a>";
                        p.setLink(link);
                    };
                    posts.add(p);
                }
            } finally {

                rs.close();
            }
        } finally {

            stm.close();
        }

        return posts;

    }
}
