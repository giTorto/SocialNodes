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
public class GruppoBean {
    private int idOwner;
    private String nome;
    private Date data_creazione;
    private Boolean isPublic;

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
    public void setData_creazione(Date data_creazione) {
        this.data_creazione = data_creazione;
    }

    /**
     * @return the isPublic
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * @param isPublic the isPublic to set
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
    
    
}
