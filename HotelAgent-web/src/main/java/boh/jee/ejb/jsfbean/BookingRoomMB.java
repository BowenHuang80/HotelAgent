/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import boh.jee.ejb.hotelagent.remotelib.HAException;
import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.hotelagent.remotelib.UserServiceRemote;
import boh.jee.ejb.model.Guest;
import boh.jee.ejb.model.Room;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author brian
 */
@ManagedBean
@RequestScoped
public class BookingRoomMB implements java.io.Serializable {

    
    @ManagedProperty(value="#{roomListMB}")
    RoomListMB roomsMB;

    @ManagedProperty(value="#{activeUserMB}")
    private ActiveUserMB userMB;
    
    
    @ManagedProperty(value = "#{param.selRoomId}")
    String selectedRoomId;
  
    Room selRoom;  
    
    private String startDate;
    private String endDate;
    private String guests;
    
    
    /**
     * Creates a new instance of SelectedRoomMB
     */
    public BookingRoomMB() {
        selRoom = null;
    }
    
    public void bookRoom(){
        //roomsMB.bookRoom(selectedRoom, Date sDate, Date eDate) ;
    }

    /**
     * @return the selectedRoom
     */
    public String getSelectedRoomId() {
        return selectedRoomId;
    }

    /**
     * @param selectedRoom the selectedRoom to set
     */
    public void setSelectedRoomId(String selectedRoom) {
        
        this.selectedRoomId = selectedRoom;
    }

    /**
     * @return the roomsMB
     */
    public RoomListMB getRoomsMB() {
        return roomsMB;
    }

    /**g
     * @param roomsMB the roomsMB to set
     */
    public void setRoomsMB(RoomListMB roomsMB) {
        this.roomsMB = roomsMB;
    }

    /**
     * @return the selRoom
     */
    public Room getSelRoom() {
        int roomId = Integer.parseInt(selectedRoomId);
        for( Room rm : roomsMB.getRoomList() ) {
            if( rm.getRoomId() == roomId ) {
                selRoom = rm;
                break;
            }
        }

        return selRoom;
    }

    /**
     * @param selRoom the selRoom to set
     */
    public void setSelRoom(Room selRoom) {
        
        this.selRoom = selRoom;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the guests
     */
    public String getGuests() {
        return guests;
    }

    /**
     * @param guests the guests to set
     */
    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String actionBookRoom() {
        Map<String,String> params = 
                      FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        selectedRoomId = params.get("selRoomId");
        
        try {
            UserServiceRemote rms = userMB.getUserSrv();
            rms.bookRoom(Integer.parseInt(selectedRoomId), startDate, endDate, Integer.parseInt(guests) );
        } catch (HAException ex) {
            Logger.getLogger(BookingRoomMB.class.getName()).log(Level.SEVERE, null, ex);
             return "login?user=0";
        }
        
        return "mybookedrooms";
    }

    /**
     * @return the userMB
     */
    public ActiveUserMB getUserMB() {
        return userMB;
    }

    /**
     * @param userMB the userMB to set
     */
    public void setUserMB(ActiveUserMB userMB) {
        this.userMB = userMB;
    }
    
}
