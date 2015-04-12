/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import boh.jee.ejb.hotelagent.remotelib.HAException;
import boh.jee.ejb.hotelagent.remotelib.UserServiceRemote;
import boh.jee.ejb.model.Admin;
import boh.jee.ejb.model.Booking;
import boh.jee.ejb.model.BookingDetail;
import boh.jee.ejb.model.Guest;
import boh.jee.ejb.model.Room;
import boh.jee.ejb.model.User;
import javax.inject.Named;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.primefaces.context.RequestContext;

/**
 *
 * @author brian
 */
//@Named(value = "activeUserMB")
@ManagedBean
@SessionScoped
public class ActiveUserMB implements Serializable {

    private List<BookingDetail> bookedRoom;
    Guest activeUser;
    private String userName;
    private String userPhone;
    private String userEmail;

    private boolean logged;
    private String userRole;
    private boolean illegalAccess;
    private String errorMsg;
    
    //Room number for Service 
    private String roomNumber;
    private String roomMsg;
    
    @EJB
    private UserServiceRemote userSrv;
    
    @Resource(mappedName = "jms/QueueFactory")
    private QueueConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/myQueue")
    private Queue queue;

    private Connection connection;    
    
    /**
     * Creates a new instance of ActiveUserMB
     */
    public ActiveUserMB() {
        activeUser = null;
        logged = false;
    }

    /**
     * For normal user to create a new account
     * @return 
     */
    public String actionSignup() {
        Guest user = null;
        
        user = new User();
        
        user.setGuestName(userName);
        user.setGuestPhone(userPhone);
        user.setGuestEmail(userEmail);
        
        if( userSrv.userSignUp(user) ) {
            return "roomlist";
        }
        else {
            illegalAccess=true;
            //setErrorMsg("User name already exists.");
            return "signup";
        }
    }
    
    public String actionLogin() {
        
//        try {
//            userSrv.testException();
//        } catch (HAException ex) {
//            Logger.getLogger(ActiveUserMB.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
        activeUser = (Guest) userSrv.userLogin(getUserName(), getUserPhone()) ;
        } catch (Exception e) {
            
        }
        if( null != activeUser ) {
            logged = true;
            if( activeUser instanceof Admin ) {
                setUserRole( "Admin" );
                return "adminpanel?faces-redirect=true";
            }
            else {
                setUserRole("User");
                return "roomlist?faces-redirect=true";
            }
            
            
        }
        else { //login failed

            FacesContext.getCurrentInstance().addMessage("loginForm", new FacesMessage("Failed","Username or password is incorrect"));
            return "";
        }
    }
    
    public String actionLogout() {
        userSrv.userLogout();
        activeUser = null;
        logged = false;
        return "roomlist?faces-redirect=true";
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
    public boolean getLogged() {
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
        
        return this.activeUser instanceof Admin ? "admin":"guest";
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
        if( bookedRoom == null) {
            bookedRoom = (List<BookingDetail>)userSrv.bookedRooms();
            if( bookedRoom == null ) {
                bookedRoom = new ArrayList<BookingDetail>();
            }
        }
//        List<BookingDetail> lst = new ArrayList<BookingDetail>();
//        for( Booking bk : bookedRoom ) {
//            lst.addAll( bk.getBookingDetailCollection() );
//        }
        return bookedRoom;
    }
    
    public void setBookedRoom(List<BookingDetail> lst) {
        this.bookedRoom = lst;
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
    
    public String actionUpdateBooking(BookingDetail bkd) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        
        String url = "bookroom" + "?faces-redirect=true&selRoomId="+bkd.getRoomId().getRoomId().intValue() 
                + "&startDate="+sdf.format( bkd.getStartDate() )
                + "&endDate=" + sdf.format(bkd.getEndDate())
                + "&guests=" + bkd.getBookingId().getGuests()
                + "&update=" + bkd.getBookingDetailId().intValue();
        
        return url;
    }
    
    public boolean getBookingChangable(BookingDetail bkd) {
        Date tdy = new Date();
        if( tdy.after( bkd.getStartDate() ) ) {
            return false;
        }
        return true;
    } 
    
    public String actionSendMessage() {

        try {
            QueueConnection queueConnection =
                    connectionFactory.createQueueConnection();
            
            QueueSession queueSession =   queueConnection.createQueueSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            
            QueueSender queueSender = queueSession.createSender(queue);
            
            TextMessage message = queueSession.createTextMessage();
            
            StringBuilder sb = new StringBuilder();
            sb.append("ROOMNUMBER:").append(this.roomNumber).append("\n")
                    .append("GUESTNAME:").append(activeUser.getGuestName()).append("\n")
                    .append("TEXT:" + getRoomMsg());
            
            message.setText(sb.toString());
            queueSender.send(message);
            
//            queueSender.close();
//            queueSession.close();
//            queueConnection.close();
            
        } catch (JMSException ex) {
            Logger.getLogger(ActiveUserMB.class.getName()).log(Level.SEVERE, null, ex);
        }
            return "roomlist?faces-redirect=true";        
    }

    /**
     * @return the roomNumber
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * @param roomNumber the roomNumber to set
     */
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * @return the roomMsg
     */
    public String getRoomMsg() {
        return roomMsg;
    }

    /**
     * @param roomMsg the roomMsg to set
     */
    public void setRoomMsg(String roomMsg) {
        this.roomMsg = roomMsg;
    }
    
    public static class BookingRooms {
        Room room;
        BookingDetail bookingDetail;
    }
}
