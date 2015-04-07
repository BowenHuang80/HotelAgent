/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author brian
 */
@Named(value = "loginMB")
@RequestScoped
public class LoginMB {

    @ManagedProperty(value = "#{activeUserMB}")
    private ActiveUserMB userMB;
    /**
     * Creates a new instance of LoginMB
     */
    public LoginMB() {
    }
    
    
    public String actionLogin() {
        /// userMB.setActiveUser() =
        return "roomlist";
    }
}
