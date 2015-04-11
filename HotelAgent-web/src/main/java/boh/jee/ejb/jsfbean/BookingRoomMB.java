/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import boh.jee.ejb.hotelagent.remotelib.HAException;
import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.hotelagent.remotelib.UserServiceRemote;
import boh.jee.ejb.model.BookingDetail;
import boh.jee.ejb.model.Guest;
import boh.jee.ejb.model.Room;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    @EJB
    private RoomServiceRemote rm;
    
    @ManagedProperty(value="#{roomListMB}")
    RoomListMB roomsMB;

    @ManagedProperty(value="#{activeUserMB}")
    private ActiveUserMB userMB;
    
    
    @ManagedProperty(value = "#{param.selRoomId}")
    String selectedRoomId;
  
    Room selRoom;  
    
    //@Future(message = "You can't book for the past")
    private Date startDate;
    //@Future(message = "You can't book for the past")
    private Date endDate;
    
    private String guests;
    
    
    private String roomAvailability;
    private String updateBookingId;
    /**
     * Creates a new instance of SelectedRoomMB
     */
    public BookingRoomMB() {
        selRoom = null;
        
        Map<String,String> params = 
                      FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        String upd = params.get("update");
        if(upd != null && !upd.isEmpty() )  {
            updateBookingId = upd;
        }
        else {
            updateBookingId = null;
        }
        
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
        if( selRoom == null ) {
            int roomId = Integer.parseInt(selectedRoomId);

            for( Room rm : roomsMB.getRoomList() ) {
                if( rm.getRoomId() == roomId ) {
                    selRoom = rm;
                    break;
                }
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
    public Date getStartDate() {
        
        if( startDate == null ) {
            
            if( getUpdateBookingId() != null ) {
                if( selRoom != null ) {
                    Map<String,String> params = 
                                  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                    String sD = (String)params.get("startDate");
                    String eD = (String)params.get("endDate");
                    String gst = (String)params.get("guests");
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        startDate = sdf.parse(sD);
                    } catch (ParseException ex) {
                        Logger.getLogger(BookingRoomMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        if( endDate == null ) {
            
            if( getUpdateBookingId() != null ) {
                if( selRoom != null ) {
                    Map<String,String> params = 
                                  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                    String eD = (String)params.get("endDate");
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        endDate = sdf.parse(eD);
                    } catch (ParseException ex) {
                        Logger.getLogger(BookingRoomMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the guests
     */
    public String getGuests() {

        if( guests == null ) {
            
            if( getUpdateBookingId() != null ) {
                if( selRoom != null ) {
                    Map<String,String> params = 
                                  FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
                    guests = (String)params.get("guests");
                }
            }
        }
        
        return guests;
    }

    /**
     * @param guests the guests to set
     */
    public void setGuests(String guests) {
        this.guests = guests;
    }

    public String actionUpdateBooking() {
        UserServiceRemote rms = userMB.getUserSrv();
        
        try {
            if(getUpdateBookingId() != null ) {
                rms.updateBooking(Integer.parseInt(getUpdateBookingId()), startDate,endDate, Integer.parseInt(guests));
                this.userMB.setBookedRoom(null);
            }
        } catch (HAException ex) {
            Logger.getLogger(BookingRoomMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "mybookedrooms";        
    }
    
    public String actionBookRoom() {
        Map<String,String> params = 
                      FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        selectedRoomId = params.get("selRoomId");
        
        UserServiceRemote rms = userMB.getUserSrv();
        
        try {
                rms.bookRoom(Integer.parseInt(selectedRoomId), startDate, endDate, Integer.parseInt(guests) );
        } catch (HAException ex) {
            Logger.getLogger(BookingRoomMB.class.getName()).log(Level.SEVERE, null, ex);
            return "login?faces-redirect=true";
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

    /**
     * @return the roomAvailability
     */
    public String getRoomAvailability() {
        //["9-3-2015", "14-3-2015", "15-3-2015"]
        Calendar cal = Calendar.getInstance();
        Date tdy = new Date();
        cal.setTime(tdy);
        cal.add(Calendar.DATE, 60);
        Date end = cal.getTime();
        List<BookingDetail> dts = (List<BookingDetail>) rm.findRoomAvailability(getSelRoom().getRoomId(), tdy, end);
        StringBuilder sb = new StringBuilder();
        //2015-03-09T12:00:00Z
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        for( BookingDetail bkd : dts ) {
            Date sD = bkd.getStartDate();
            Date eD = bkd.getEndDate();
            if(sb.length() > 0 ) {
                sb.append(",");
            }
            sb.append("{")
            .append("startDate : \"").append(sdf.format(sD)).append("T12:00:00Z\"")
            .append(", endDate : \"").append(sdf.format(eD)).append("T12:00:00Z\"}");
        }
        roomAvailability = sb.toString();
        
        return roomAvailability;
    }

    /**
     * @param roomAvailability the roomAvailability to set
     */
    public void setRoomAvailability(String roomAvailability) {
        this.roomAvailability = roomAvailability;
    }

    /**
     * @return the updateBookingId
     */
    public String getUpdateBookingId() {
        return updateBookingId;
    }
    
}
