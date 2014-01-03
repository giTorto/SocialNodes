/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Giulian
 */
public class Gruppo {

    private int idOwner;
    private String nome;
    private Timestamp data_creazione;
    private int isPublic;
    private int idgruppo;
    static private DBmanager manager;
    
    public Gruppo(){
        manager = new DBmanager();
    }
    
    /**
     * @return the idOwner
     */
    public int getIdOwner() {
        return idOwner;
    }

    /**
     * @param idOwner the idOwner to set
     */
    public void setIdOwner(int idOwner) {
        this.idOwner = idOwner;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the data_creazione
     */
    public Date getData_creazione() {
        return data_creazione;
    }

    /**
     * @param data_creazione the data_creazione to set
     */
    public void setData_creazione(Timestamp data_creazione) {
        this.data_creazione = data_creazione;
    }

    /**
     * @return the isPublic
     */
    public int getIsPublic() {
        return isPublic;
    }

    /**
     * @param isPublic the isPublic to set
     */
    public void setIsPublic(int isPublic) {
        this.isPublic = isPublic;
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

    static public ArrayList<Gruppo> listaGruppiPubblici(){
        ArrayList<Gruppo> all = new ArrayList<>();
        try {
           all = manager.getGruppiPubblici();
        } catch (SQLException ex) {
            Logger.getLogger(Gruppo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return all;
    }

}
