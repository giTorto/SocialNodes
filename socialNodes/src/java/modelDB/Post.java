/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelDB;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Giulian
 */
public class Post {
    private String testo;
    private Timestamp data_ora;
    private Utente writer;
    private int idgruppo;
    private String link;
    private DBmanager manager;
    private String realname;
    
    Post(){
        manager = new DBmanager();
  
    }

    /**
     * @return the testo
     */
    public String getTesto() {
        return testo;
    }

    /**
     * @param testo the testo to set
     */
    public void setTesto(String testo) {
        this.testo = testo;
    }

    /**
     * @return the data_ora
     */
    public Date getData_ora() {
        return data_ora;
    }

    /**
     * @param data_ora the data_ora to set
     */
    public void setData_ora(Timestamp data_ora) {
        this.data_ora = data_ora;
    }

    /**
     * @return the idwriter
     */
    public Utente getWriter() {
        return writer;
    }

    /**
     * @param writer the idwriter to set
     */
    public void setWriter(Utente writer) {
        this.writer = writer;
    }

    /**
     * @return the idgruppo
     */
    public int getIdgruppo() {
        return idgruppo;
    }

    /**
     * @param idgruppo the idgruppo to set
     */
    public void setIdgruppo(int idgruppo) {
        this.idgruppo = idgruppo;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the realname
     */
    public String getRealname() {
        return realname;
    }

    /**
     * @param realname the realname to set
     */
    public void setRealname(String realname) {
        this.realname = realname;
    }
    
}
