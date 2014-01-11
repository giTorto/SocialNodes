/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giulian
 */
public class DBmanager {

    private static transient Connection con;//transient = non serializzabile
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final String DBURL = "jdbc:derby://localhost:1527/socialdb;user=utente;password=utente";

    DBmanager() {
        try {
            Class.forName(DRIVER, true, getClass().getClassLoader());
            con = DriverManager.getConnection(DBURL);
        } catch (SQLException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
    public Utente authenticate(String email, String password) throws SQLException {

        // usare SEMPRE i PreparedStatement, anche per query banali. 
        // *** MAI E POI MAI COSTRUIRE LE QUERY CONCATENANDO STRINGHE !!!! 
        PreparedStatement stm = con.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?");
        try {
            stm.setString(1, email);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    Utente user = new Utente();
                    user.setUsername(rs.getString("username"));
                    user.setEmail(email);
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

    public Utente getMoreUtente(int id) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM utente WHERE idutente = ?");
        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    Utente user = new Utente();
                    user.setUsername(rs.getString("username"));
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

    public Utente getMoreUtente(String email_utente) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM utente WHERE email = ?");
        try {
            stm.setString(1, email_utente);
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    Utente user = new Utente();
                    user.setUsername(rs.getString("username"));
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

    public String getPasswordUtente(String email_utente) throws SQLException {
        PreparedStatement stm = con.prepareStatement("SELECT * FROM utente WHERE email = ?");
        try {
            stm.setString(1, email_utente);
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    String retval = rs.getString("password");
                    return retval;
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

    public boolean nameAlreadyExist(String username) throws SQLException {
        PreparedStatement stm = con.prepareStatement("Select * from utente where username=?");
        stm.setString(1, username);
        ResultSet rs = stm.executeQuery();

        if (rs.next() == false) {
            return false;
        } else {
            return true;
        }
    }

    public boolean mailAlreadyExist(String email) throws SQLException {
        PreparedStatement stm = con.prepareStatement("Select * from utente where username=?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();

        if (rs.next() == false) {
            return false;
        } else {
            return true;
        }
    }

    public void addUtente(String username, String email, String password) throws SQLException {

        PreparedStatement stm;
        stm = con.prepareStatement("INSERT INTO utente (username,email,password,moderatore) "
                + "values (?,?,?,?)");
        try {
            stm.setString(1, username);
            stm.setString(2, email);
            stm.setString(3, password);
            stm.setInt(4, 0);
            stm.executeUpdate();
        } finally {
            stm.close();
        }

    }

    public ArrayList<Gruppo> getGruppiPubblici() throws SQLException {

        ArrayList<Gruppo> gruppi = new ArrayList<Gruppo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM gruppo where pubblico=? ");

        try {
            stm.setInt(1, 1);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo p = new Gruppo();
                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("data_creazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setIdOwner(rs.getInt("idowner"));
                    p.setIsPublic(rs.getInt("pubblico"));
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
    static public ArrayList<Post> getPostsGruppo(int idgruppo) throws SQLException {

        ArrayList<Post> posts = new ArrayList<>();

        String link;
        PreparedStatement stm = con.prepareStatement("SELECT * FROM post "
                + "WHERE idgruppo = ? ORDER BY data_ora DESC");

        try {
            stm.setInt(1, idgruppo);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Post p = new Post();
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

    public ArrayList<Gruppo> getGruppiParte(int id) throws SQLException {
        ArrayList<Gruppo> gruppi = new ArrayList<Gruppo>();

        PreparedStatement stm = con.prepareStatement("SELECT NOME, DATA_CREAZIONE, IDGRUPPO FROM gruppi_partecipanti g natural join gruppo gr  where g.idutente =? and invito_acc>0");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo p = new Gruppo();

                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("data_creazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setNomeOwner((this.getMoreUtente(id)).getUsername());
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

    public ArrayList<Gruppo> getInviti(int id) throws SQLException {
        ArrayList<Gruppo> gruppi = new ArrayList<Gruppo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM gruppi_partecipanti g inner"
                + " join gruppo gr on g.idgruppo=gr.idgruppo where g.idutente =? and invito_acc=0");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo p = new Gruppo();

                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("datacreazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setNomeOwner((this.getMoreUtente(id)).getUsername());
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

    public ArrayList<Gruppo> getGruppiOwn(int id) throws SQLException {

        ArrayList<Gruppo> gruppi = new ArrayList<>();

        PreparedStatement stm = con.prepareStatement("SELECT NOME,DATA_CREAZIONE,IDGRUPPO FROM gruppo g, utente u where u.idutente = g.idowner and u.idutente =? ");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo p = new Gruppo();
                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("data_creazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setNomeOwner((this.getMoreUtente(id)).getUsername());
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

    public void setNewdate(Timestamp data_acc, int idutente) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement("Update utente set data_ultimo_acc=? where idutente=? ");
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    ArrayList<Message> getNews(Timestamp data_last_access, int id) throws SQLException {
        ArrayList<Message> news = new ArrayList<>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM gruppo g inner join gruppi_partecipanti gr on "
                + " g.idgruppo = gr.idgruppo where gr.data_invio>? and gr.idutente=?");

        stm.setTimestamp(1, data_last_access);
        stm.setInt(2, id);

        ResultSet rs = stm.executeQuery();

        try {

            while (rs.next()) {
                Message p = new Message();
                p.setMessaggio("Sei stato invitato al gruppo " + rs.getString("nome"));
                p.setLink("<a href=afterLogged/afterLogin?op=showinviti\">Guarda gli inviti</a> ");
                p.setValue((rs.getTimestamp("data_invio")).toString());
                news.add(p);
            }
            stm.close();

            stm = con.prepareStatement("SELECT DISTINCT g.nome,g.idgruppo FROM (gruppo g inner join post p on g.idgruppo=p.idgruppo)"
                    + " where p.data_ora>? and ( g.idgruppo in "
                    + "( SELECT g.idgruppo from gruppo g inner join utente u on u.idutente=g.idowner where u.idutente=?) "
                    + "OR g.idgruppo in (SELECT g.idgruppo from gruppo g inner join gruppi_partecipanti gr on "
                    + "g.idgruppo=gr.idgruppo where gr.idutente = ? ) )");

            stm.setTimestamp(1, data_last_access);
            stm.setInt(2, id);
            stm.setInt(3, id);

            rs = stm.executeQuery();

            while (rs.next()) {
                Message p = new Message();
                p.setMessaggio("Sei stato invitato al gruppo " + rs.getString("nome"));
                p.setLink("<a href=afterLogged/afterLogin?op=showGroups?id=" + (rs.getInt("idgruppo")) + ">" + "Guarda gli inviti</a> ");
                p.setValue((rs.getTimestamp("data_ora")).toString());
                news.add(p);
            }
        } finally {

            rs.close();
        }

        return news;
    }

    public List<Gruppo> getInvitiGruppi(Utente u) throws SQLException {
        List<Gruppo> gruppi = new ArrayList<Gruppo>();
        int id = u.getId();
        PreparedStatement stm
                = con.prepareStatement("SELECT g.idgruppo,g.nome,g.idowner,g.data_creazione,grp.idutente,u.username,grp.invito_acc, g.pubblico \n"
                        + "                        FROM (gruppo g INNER JOIN gruppi_partecipanti grp ON grp.idgruppo = g.idgruppo) INNER JOIN utente u on grp.idutente=u.idutente\n"
                        + "                        WHERE grp.idutente=? AND grp.invito_acc=0 and g.pubblico=0");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo gruppo = new Gruppo();
                    gruppo.setNomeOwner(getMoreUtente(rs.getInt("idowner")).getUsername());
                    gruppo.setNome(rs.getString("nome"));
                    Date data = rs.getDate("data_creazione");
                    Timestamp ts = new Timestamp(data.getTime());
                    gruppo.setData_creazione(ts);
                    gruppo.setIdgruppo(rs.getInt("idgruppo"));
                    gruppi.add(gruppo);

                }
            } finally {

                rs.close();
            }
        } finally {

            stm.close();
        }

        return gruppi;

    }

}
