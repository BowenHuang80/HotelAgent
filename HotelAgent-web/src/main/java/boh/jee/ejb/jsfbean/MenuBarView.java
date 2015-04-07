/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author brian
 */
@ManagedBean
@SessionScoped
public class MenuBarView implements Serializable {
     
    @ManagedProperty(value="#{activeUserMB}")
    private ActiveUserMB userMB;
    
    private MenuModel model;
 
    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
         
        //First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Welcome");
         
        DefaultMenuItem item = new DefaultMenuItem("Rooms");
        item.setUrl("roomlist");
        firstSubmenu.addElement(item);
         
        item = new DefaultMenuItem("Sign In");
        item.setUrl("login");
        firstSubmenu.addElement(item);
        
        model.addElement(firstSubmenu);
    }
 
    public MenuModel getModel() {
        return model;
    }   
     
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
