/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelDB;

/**
 *
 * @author Giulian
 */
public class Message {
    private String messaggio;
    private String tipoutente;
    private String value;
    
            
    public Message(){
        messaggio=null;
        tipoutente=null;
        value=null;
    }

    /**
     * @return the messaggio
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * @param messaggio the messaggio to set
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

    /**
     * @return the tipoutente
     */
    public String getTipoutente() {
        return tipoutente;
    }

    /**
     * @param tipoutente the tipoutente to set
     */
    public void setTipoutente(String tipoutente) {
        this.tipoutente = tipoutente;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
    
}
