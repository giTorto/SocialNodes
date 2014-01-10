/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the t
 emplate in the editor.
 */
package modelDB;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giulian
 */
public class Utente {

    private String email;
    private String username;
    private Timestamp last_access;
    private String avatar_link;
    private int id;
    private DBmanager manager;

    Utente() {
        manager = new DBmanager();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getLast_access() {
        return last_access;
    }

    public void setLast_access(Timestamp last_access) {
        this.last_access = last_access;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Gruppo> getInviti() {
        ArrayList<Gruppo> all = new ArrayList<>();
        try {
            all = manager.getInviti(this.id);
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    public ArrayList<Gruppo> getGruppiParte() {
        ArrayList<Gruppo> all = new ArrayList<>();
        try {
            all = manager.getGruppiParte(this.id);
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;

    }

    public ArrayList<Gruppo> getGruppiOwn() {
        ArrayList<Gruppo> all = new ArrayList<>();
        try {
            all = manager.getGruppiOwn(this.id);
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    public ArrayList<Message> getNews() {
        ArrayList<Message> all = new ArrayList<>();
        
        try {
            all = manager.getNews(this.getLast_access(), this.getId());
            
        } catch (SQLException ex) {
            Logger.getLogger(Utente.class.getName()).log(Level.SEVERE, null, ex);
        }

        return all;
    }

    /**
     * @return the avatar_link
     */
    public String getAvatar_link() {
        return avatar_link;
    }

    /**
     * @param avatar_link the avatar_link to set
     */
    public void setAvatar_link(String avatar_link) {
        this.avatar_link = avatar_link;
    }
}
