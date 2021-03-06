/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.jsfbean;

import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.model.Room;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author brian
 */
@ManagedBean
//@SessionScoped
@RequestScoped
public class RoomListMB implements Serializable {

    @EJB
    private RoomServiceRemote rm;
    
    List<Room> roomList;
    
    /**
     * Creates a new instance of RoomListMB
     */
    public RoomListMB() {
        roomList = null;
    }
    
    public List<Room> getRoomList() {
        if( roomList == null ) {
            roomList = (List<Room>)rm.getAll();
        }
        
        return roomList;
    }
    
    public String actionSelectRoom() {
        return "bookroom";
        //if !logined 
    }
}
