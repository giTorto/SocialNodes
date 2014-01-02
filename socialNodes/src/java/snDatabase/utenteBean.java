/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package snDatabase;

import java.util.Date;

/**
 *
 * @author Giulian
 */
public class utenteBean {
    private String email;
    private String username;
    private Date last_access;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLast_access() {
        return last_access;
    }

    public void setLast_access(Date last_access) {
        this.last_access = last_access;
    }
    
    
    
}
