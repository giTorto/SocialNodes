
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
import java.util.Calendar;
import java.util.HashMap;
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
                    user.setIsModeratore(rs.getInt("moderatore"));
                    user.setEmail(rs.getString("email"));
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
                    user.setIsModeratore(rs.getInt("moderatore"));
                    user.setEmail(rs.getString("email"));
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
                    p.setNomeOwner( (this.getMoreUtente(rs.getInt("idowner"))).getUsername() );
                    p.setIsPublic(rs.getInt("pubblico"));
                    p.setIsAttivo(rs.getInt("attivo"));
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


    public ArrayList<Gruppo> getGruppiParte(int id) throws SQLException {
        ArrayList<Gruppo> gruppi = new ArrayList<Gruppo>();

        PreparedStatement stm = con.prepareStatement("SELECT * FROM gruppi_partecipanti g natural join gruppo gr  where g.idutente =? and invito_acc>0");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo p = new Gruppo();
                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("data_creazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setIdOwner(rs.getInt("idowner"));
                    p.setNomeOwner( (this.getMoreUtente(rs.getInt("idowner"))).getUsername() );
                    p.setIsPublic(rs.getInt("pubblico"));
                    p.setIsAttivo(rs.getInt("attivo"));
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

        PreparedStatement stm = con.prepareStatement("SELECT gr.nome, gr.data_creazione, gr.idgruppo, gr.idowner "
                + "FROM gruppi_partecipanti g inner join gruppo gr on g.idgruppo=gr.idgruppo where g.idutente =? and invito_acc=0");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo p = new Gruppo();
                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("data_creazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setNomeOwner(getMoreUtente(rs.getInt("idowner")).getUsername());
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

        PreparedStatement stm = con.prepareStatement("SELECT * "
                + "FROM gruppo g, utente u where u.idutente = g.idowner and u.idutente =? ");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Gruppo p = new Gruppo();
                    p.setNome(rs.getString("nome"));
                    p.setData_creazione(rs.getTimestamp("data_creazione"));
                    p.setIdgruppo(rs.getInt("idgruppo"));
                    p.setIdOwner(rs.getInt("idowner"));
                    p.setIsPublic(rs.getInt("pubblico"));
                    p.setIsAttivo(rs.getInt("attivo"));
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
            stm.setTimestamp(1, data_acc);
            stm.setInt(2, idutente);
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    ArrayList<Message> getNewsInviti(Timestamp data_last_access, int id) throws SQLException {
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

        } finally {

            rs.close();
        }
        return news;
    }

    ArrayList<Message> getNewsPost(Timestamp data_last_access, int id) throws SQLException {
        ArrayList<Message> news = new ArrayList<>();
        PreparedStatement stm;
           stm = con.prepareStatement("SELECT DISTINCT g.nome,g.idgruppo FROM (gruppo g inner join post p on g.idgruppo=p.idgruppo)"
                    + " where p.data_ora>? and ( g.idgruppo in "
                    + "( SELECT g.idgruppo from gruppo g inner join utente u on u.idutente=g.idowner where u.idutente=?) "
                    + "OR g.idgruppo in (SELECT g.idgruppo from gruppo g inner join gruppi_partecipanti gr on "
                    + "g.idgruppo=gr.idgruppo where gr.idutente = ? ) )");
            stm.setTimestamp(1, data_last_access);
            stm.setInt(2, id);
            stm.setInt(3, id);
           
        ResultSet rs = stm.executeQuery();

        try {
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

    public void creaGruppo(Utente u, String nome, boolean isPublic) throws SQLException {
        int idutente = u.getId();
        Date data = new Date(Calendar.getInstance().getTimeInMillis());
        PreparedStatement stm
                = con.prepareStatement("INSERT INTO gruppo (nome,data_creazione,idowner,pubblico,attivo) values(?,?,?,?,?) ");
        try {
            stm.setString(1, nome);
            stm.setDate(2, data);
            stm.setInt(3, idutente);
            stm.setInt(5, 1);
            if (isPublic) {
                stm.setInt(4, 1);
            } else {
                stm.setInt(4, 0);
            }
            int rowsaffected = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("DbManager: creagruppo, qlcs è andato storto\n");
        } finally {
            stm.close();
        }
    }

    public void updatePartecipanti(int idutente, int id_gruppo_accettato) throws SQLException {
        PreparedStatement stm = con.prepareStatement("UPDATE GRUPPI_PARTECIPANTI   SET INVITO_ACC = 1  WHERE IDUTENTE=? AND idgruppo=?");
        try {
            stm.setInt(1, idutente);
            stm.setInt(2, id_gruppo_accettato);
            int executeUpdate = stm.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("DbManager: updatepartecipanti, Errore nell'aggiornare i partecipanti");
        } finally {
            stm.close();
        }
    }

    public void insertInvito(int groupid, int idutente) throws SQLException {
        PreparedStatement stm = con.prepareStatement("INSERT INTO gruppi_partecipanti (idgruppo,idutente,invito_acc,data_invio) values(?,?,?,?)");
        int zero = 0;
        Date data = new Date(Calendar.getInstance().getTimeInMillis());
        try {
            stm.setInt(1, groupid);
            stm.setInt(2, idutente);
            stm.setInt(3, zero);
            stm.setDate(4, data);

            int executeUpdate = stm.executeUpdate();
        } catch (Exception e) {
            System.err.println("errore nel inserire l'invito");
        } finally {
            stm.close();
        }
    }

    public Utente getMoreByUserName(String ut) throws SQLException {

        PreparedStatement stm = con.prepareStatement("SELECT * FROM utente WHERE username = ?");
        try {
            stm.setString(1, ut);
            ResultSet rs = stm.executeQuery();

            try {
                if (rs.next()) {
                    Utente user = new Utente();
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

    public boolean controllaInvitogià_esistente(int groupid, int idutente) throws SQLException {//ritorna true se c'è già un invito, false altrimenti

        boolean retval = false;
        try {
            PreparedStatement stm = con.prepareStatement("select * from gruppi_partecipanti where idgruppo= ? and idutente= ?");

            stm.setInt(1, groupid);
            stm.setInt(2, idutente);

            ResultSet rs = stm.executeQuery();

            if (!rs.next()) {
                System.out.println("No data");
                retval = false;
            } else {
                retval = true;
            }
            stm.close();
        } catch (SQLException e) {
            System.err.println("errore nel verificare se l'invito esisteva già");
            return false;
        }
        return retval;
    }

    /**
     * Dato un idgruppo restituisce un oggetto contenente tutte le sue info
     *
     * @param Idgruppo
     * @return oggetto
     * @throws SQLException
     */
    public Gruppo getGruppo(int Idgruppo) throws SQLException {
        Gruppo group = new Gruppo();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM gruppo g where g.idgruppo=?");
        try {
            stm.setInt(1, Idgruppo);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {

                    group.setNome(rs.getString("nome"));
                    Date date = rs.getDate("data_creazione");
                    long timestamp = date.getTime();
                    Timestamp tsdate = new Timestamp(timestamp);
                    group.setData_creazione(tsdate);
                    group.setIdgruppo(rs.getInt("idgruppo"));
                    group.setNomeOwner(getMoreUtente(rs.getInt("idowner")).getUsername());
                    group.setIsPublic(rs.getInt("pubblico"));
                    group.setIsAttivo(rs.getInt("attivo"));

                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return group;
    }

    public ArrayList<Gruppo> getAllGruppi() throws SQLException {
        PreparedStatement stm = con.prepareStatement("select * from UTENTE.GRUPPO");
        ArrayList<Gruppo> allgruppi = new ArrayList<>();
        try {
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Gruppo group = new Gruppo();
                    group.setNome(rs.getString("nome"));
                    Date date = rs.getDate("data_creazione");
                    long timestamp = date.getTime();
                    Timestamp tsdate = new Timestamp(timestamp);
                    group.setData_creazione(tsdate);
                    group.setIdgruppo(rs.getInt("idgruppo"));
                    group.setNomeOwner(getMoreUtente(rs.getInt("idowner")).getUsername());
                    group.setIsPublic(rs.getInt("pubblico"));
                    group.setIsAttivo(rs.getInt("attivo"));
                    allgruppi.add(group);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }

        return allgruppi;
    }

    public Gruppo getGruppo(String nome) throws SQLException {
        ArrayList<Gruppo> gruppi = new ArrayList<Gruppo>();
        PreparedStatement stm = con.prepareStatement("SELECT * FROM gruppo g where g.nome=?");
        try {
            stm.setString(1, nome);
            ResultSet rs = stm.executeQuery();
            try {
                while (rs.next()) {
                    Gruppo group = new Gruppo();
                    group.setNome(rs.getString("nome"));
                    Date date = rs.getDate("data_creazione");
                    long timestamp = date.getTime();
                    Timestamp tsdate = new Timestamp(timestamp);
                    group.setData_creazione(tsdate);
                    group.setIdgruppo(rs.getInt("idgruppo"));
                    group.setNomeOwner(getMoreUtente(rs.getInt("idowner")).getUsername());
                    group.setIsAttivo(rs.getInt("attivo"));
                    gruppi.add(group);
                }
            } finally {
                rs.close();
            }
        } finally {
            stm.close();
        }
        return gruppi.get(0);
    }

    public int getNumPostPerGruppo(int idgruppo) throws SQLException {

        PreparedStatement stm
                = con.prepareStatement("SELECT COUNT (p.idgruppo) AS count "
                        + "FROM post p  "
                        + "WHERE p.idgruppo = ?");

        try {
            stm.setInt(1, idgruppo);
            ResultSet resultSet = stm.executeQuery();

            try {
                if (resultSet.next()) {
                    return resultSet.getInt("count");
                } else {
                    return 0;
                }

            } finally {
                resultSet.close();
            }
        } finally {
            stm.close();
        }

    }

    public Date getDataUltimoPost(int idgruppo) throws SQLException {
        Date data = null;

        PreparedStatement stm = con.prepareStatement("SELECT max(data_ora) as maxdata from post where idgruppo = ? ");

        try {
            stm.setInt(1, idgruppo);
            ResultSet resultSet = stm.executeQuery();

            try {
                if (resultSet.next()) {
                    return resultSet.getDate("maxdata");
                } else {
                    return null;
                }

            } finally {
                resultSet.close();
            }
        } finally {
            stm.close();
        }

    }

    public List<Integer> getUtenti(int idgruppo) throws SQLException {

        List<Integer> allUsers = new ArrayList<Integer>();
        PreparedStatement stm
                = con.prepareStatement("SELECT DISTINCT  idutente FROM gruppi_partecipanti "
                        + "                                    WHERE idgruppo = ?  and invito_acc=1");

        try {
            stm.setInt(1, idgruppo);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {

                    Integer ut = new Integer(rs.getInt("idutente"));

                    allUsers.add(ut);
                }
            } finally {

                rs.close();
            }
        } finally {

            stm.close();
        }

        return allUsers;
    }

    public void updateGroupName(int idgroup, String nuovo_nome) throws SQLException {

        PreparedStatement stm = con.prepareStatement("UPDATE GRUPPO   SET NOME = ?  WHERE IDGRUPPO = ?");
        try {
            stm.setString(1, nuovo_nome);
            stm.setInt(2, idgroup);

            int executeUpdate = stm.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Errore nell'aggiornare il gruppo con id:" + idgroup);
        } finally {
            stm.close();
        }
    }

    public void updateGroupFlag(int idgroup, int isPublic) throws SQLException {

        PreparedStatement stm = con.prepareStatement("UPDATE GRUPPO   SET PUBBLICO = ?  WHERE IDGRUPPO = ?");
        try {
            stm.setInt(1, isPublic);
            stm.setInt(2, idgroup);

            int executeUpdate = stm.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Errore nell'aggiornare il flag del gruppo con id:" + idgroup);
        } finally {
            stm.close();
        }
    }

    public void updateUserName(int idutente, String new_username) throws SQLException {
        PreparedStatement stm = con.prepareStatement("UPDATE UTENTE   SET username = ?  WHERE IDUTENTE = ?");
        try {
            stm.setString(1, new_username);
            stm.setInt(2, idutente);
            int executeUpdate = stm.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Errore nell'aggiornare username per l'utente con id:" + idutente);
        } finally {
            stm.close();
        }
    }

    public boolean updateUserPassword(int idutente, String new_password) throws SQLException {
        boolean retval = true;
        PreparedStatement stm = con.prepareStatement("UPDATE UTENTE   SET password = ?  WHERE IDUTENTE = ?");
        try {
            stm.setString(1, new_password);
            stm.setInt(2, idutente);
            int executeUpdate = stm.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Errore nell'aggiornare password per l'utente con id:" + idutente);
            retval = false;
        } finally {
            stm.close();
        }
        return retval;
    }
    
    
    /**
     * Ricerca l'ID del file basandosi sul nome. Se il file viene trovato, viene
     * impostato un campo per indicarlo come recente
     * @param idgruppo id del gruppo in cui si è e lo si sta richiedendo
     * @param fileName Nome del file
     * @param user Nome dell'utente da linkare
     * @return Url del file associato al nome Utente o stringa vuota
     */
    public int getLinkByName(String fileName, String user, int idgruppo) {
        int retVal = 0;
        try {

            ResultSet rs;

            PreparedStatement stm
                    = con.prepareStatement("SELECT * FROM POST p inner join utente u on p.idwriter = u.idutente WHERE u.username = ? AND realname=? "
                            + "AND idgruppo = ?");
            stm.setString(1, user);
            stm.setString(2, fileName);
            stm.setInt(3,idgruppo);
            rs = stm.executeQuery();
            rs.next();
            retVal = rs.getInt("idpost");

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retVal;
    }

    /**
     * Ricerca l'ID del file basandosi sul nome. Il file ricercato è quello
     * aggiunto per ultimo
     *
     * @param fileName Nome del FIlE da cercare
     * @return ID del file o stringa vuota
     */
    public int getLRULink(String fileName,int idgruppo) {

        int retVal = 0;
        try {

            ResultSet rs;

            PreparedStatement stm
                    = con.prepareStatement("SELECT * FROM POST WHERE realname=? "
                            + "AND idgruppo = ? ORDER BY data_ora DESC FETCH FIRST 1 ROWS ONLY");
            stm.setString(1, fileName);
            stm.setInt(2,idgruppo);
            rs = stm.executeQuery();
            rs.next();
            retVal = rs.getInt("idpost");
            

            stm.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retVal;
    }

        

    

    /**
     * Trova nel DB i dati di un file basandosi sul suo id
     *
     * @param idpost id del file
     * @return i dati trovati o un set vuoto
     */
    public HashMap<String, String> getRealAndDBName(int idpost) throws SQLException {
        ResultSet rs;
        HashMap<String, String> retVal = new HashMap<String, String>();
        //String query="SELECT GROUPID,REALNAME,DBNAME FROM USERS.FILES WHERE FILEID="+id+" AND GROUPID ="+groupId;
        //String query="SELECT GROUPID,REALNAME,DBNAME FROM USERS.FILES WHERE FILEID="+id+" AND GROUPID IN ("+GET_GROUPS_QUERY+userId+")";
        PreparedStatement pr = con.prepareStatement("SELECT * FROM POST where idpost=?");

        try {
            pr.setInt(1, idpost);

            rs = pr.executeQuery();
            if (rs.next()) {
                retVal.put("path", "media\\" + rs.getString("idgruppo") + "\\" + rs.getString("dbname"));
                retVal.put("name", rs.getString("realname"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBmanager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retVal;
    }

    
        /**
     * Aggiunge un nuovo file al DB
     *
     * @param user l'utente che ha aggiunto il post
     * @param idgruppo ID del gruppo in cui è stato posto
     * @param realname nome del file secondo l'utente
     * @param dbname nome univoco del file generato automaticamente
     * @param testo il testo del post
     */
    public void addPostFile(Utente user, int idgruppo, String realname, String dbname, String testo) throws SQLException {
        int idutente = user.getId();

       Calendar calendar = Calendar.getInstance();
                        java.util.Date now = calendar.getTime();
                        Timestamp data_acc = new Timestamp(now.getTime());

        PreparedStatement stm
                = con.prepareStatement("INSERT INTO POST (data_ora,testo,idwriter,idgruppo,realname,dbname) values(?,?,?,?,?,?) ");

        try {
            stm.setTimestamp(1, data_acc);
            stm.setString(2, testo);
            stm.setInt(3, idutente);
            stm.setInt(4, idgruppo);
            stm.setString(5, realname);
            stm.setString(6, dbname);
            int executeUpdate = stm.executeUpdate();
        } catch (SQLException ex) {

        } finally {
            stm.close();
        }

    }
    
     /**
     * Permette di ottenere facilmente la lista di tutti i post di un gruppo ora
     * perfezionata, in ogni post c'è un oggetto Utente che è il writer
     *
     * @param g dai in input il gruppo di cui vuoi vedere i post
     * @return ricevi la lista dei post in ordine di data inversa
     * @throws SQLException
     */
    public ArrayList<Post> getPostsGruppo(Gruppo g) throws SQLException {

        ArrayList<Post> posts = new ArrayList<Post>();
        int id = g.getIdgruppo();
        String link = "";
        PreparedStatement stm
                = con.prepareStatement("SELECT * FROM post "
                        + "WHERE idgruppo = ? ORDER BY data_ora DESC");

        try {
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();

            try {

                while (rs.next()) {
                    Post p = new Post();
                    //Utente tu = getMoreUtente(rs.getInt("idwriter"));
                    p.setTesto(rs.getString("testo"));
                    p.setData_ora(rs.getTimestamp("data_ora"));
                    p.setWriter(getMoreUtente(rs.getInt("idwriter")));
                    p.setRealname(rs.getString("realname"));
                    if (rs.getString("dbname") != null) {
                        link = "/afterLogged/groupCtrl/fileDownload?fileId=" + rs.getInt("idpost");
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
    
        /*
     Da mettere la getpostgruppo ma va modificata molto...e soprattuto aspetto di vedere
     come gestire gli avatar perchè se ogni post ha bisogno dell'avatar per essere printoutato
     allora finchè non sappiamo come gestire gli avatar non ci serve recuperare i post
     */
//    public boolean checkUtenteOwnGruppo(Utente u, int idgruppo) throws SQLException {
//        boolean retval=false;
//        PreparedStatement stm
//                = con.prepareStatement("select utente.username from UTENTE.GRUPPO inner join utente on gruppo.idowner=utente.idutente where idgruppo=?");
//
//        try {
//            stm.setInt(1, idgruppo);
//            ResultSet rs=stm.executeQuery();
//            String retrieved_username=rs.getString("username");
//            if (retrieved_username.equals(u.getUsername()))
//                retval=true;
//            
//        } catch (SQLException ex) {
//            System.err.println("dbmanager: checkutenteowngruppo errore sql");
//        }
//        
//        
//        return retval;
//    }
    
}
