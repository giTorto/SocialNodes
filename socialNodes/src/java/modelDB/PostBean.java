/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelDB;

import java.util.Date;

/**
 *
 * @author Giulian
 */
public class PostBean {
    private String testo;
    private Date data_ora;
    private int idwriter;
    private int idgruppo;
    private String link;
   

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
    public void setData_ora(Date data_ora) {
        this.data_ora = data_ora;
    }

    /**
     * @return the idwriter
     */
    public int getIdwriter() {
        return idwriter;
    }

    /**
     * @param idwriter the idwriter to set
     */
    public void setIdwriter(int idwriter) {
        this.idwriter = idwriter;
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
    
}
