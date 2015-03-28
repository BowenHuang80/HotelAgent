/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import boh.jee.ejb.hotelagent.remotelib.HAException;
import boh.jee.ejb.hotelagent.remotelib.UserServiceRemote;
import boh.jee.ejb.model.Booking;
import boh.jee.ejb.model.BookingDetail;
import boh.jee.ejb.model.Guest;
import boh.jee.ejb.model.Room;
import javax.inject.Named;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author brian
 */
//@Named(value = "activeUserMB")
@ManagedBean
@SessionScoped
public class ActiveUserMB implements Serializable {

    private List<Booking> bookedRoom;
    Guest activeUser;
    private String userName;
    private String userPhone;
    private String userEmail;

    private boolean logged;
    private String userRole;
    private boolean illegalAccess;
    private String errorMsg;
    
    @EJB
    private UserServiceRemote userSrv;
    
    
    
    /**
     * Creates a new instance of ActiveUserMB
     */
    public ActiveUserMB() {
        activeUser = null;
    }

    public String actionSignup() {
        Guest user = new Guest();
        user.setGuestName(userName);
        user.setGuestPhone(userPhone);
        user.setGuestEmail(userEmail);
        
        if( userSrv.userSignUp(user) ) {
            return "index";
        }
        else {
            illegalAccess=true;
            setErrorMsg("User name already exists.");
            return "signup";
        }
    }
    
    public String actionLogin() {
        
//        try {
//            userSrv.testException();
//        } catch (HAException ex) {
//            Logger.getLogger(ActiveUserMB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
        if( userSrv.userLogin(getUserName(), getUserPhone()) ) {
            activeUser = new Guest();
            logged = true;
            return "index";
        }
        else { //login failed
            return "signup";
        }
    }
    
    public String actionLogout() {
        userSrv.userLogout();
        activeUser = null;
        logged = false;
        return "index";
    }
    
    /**
     * @return the userSrv
     */
    public UserServiceRemote getUserSrv() {
        return userSrv;
    }

    /**
     * @param userSrv the userSrv to set
     */
    public void setUserSrv(UserServiceRemote userSrv) {
        this.userSrv = userSrv;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the userPhone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone the userPhone to set
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the isLogged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param isLogged the isLogged to set
     */
    public void setLogged(boolean isLogged) {
        this.logged = isLogged;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
        return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * @return the illegalAccess
     */
    public boolean isIllegalAccess() {
        return illegalAccess;
    }

    /**
     * @param illegalAccess the illegalAccess to set
     */
    public void setIllegalAccess(boolean illegalAccess) {
        this.illegalAccess = illegalAccess;
    }

    /**
     * @return the bookedRoom
     */
    public List<BookingDetail> getBookedRoom() {
        bookedRoom = (List<Booking>)userSrv.bookedRooms();
        List<BookingDetail> lst = new ArrayList<BookingDetail>();
        for( Booking bk : bookedRoom ) {
            lst.addAll( bk.getBookingDetailCollection() );
        }
        
        return lst;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public static class BookingRooms {
        Room room;
        BookingDetail bookingDetail;
    }
}
