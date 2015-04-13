/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.service;

import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.model.BookingDetail;
import boh.jee.ejb.model.CustomerMessage;
import boh.jee.ejb.model.Room;
import java.util.Date;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author brian
 */
@Singleton
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class RoomService implements RoomServiceRemote {

    @PersistenceContext(unitName="boh.jee.ejb_HotelAgent-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
            
    @Override
    public Object getAll() {
        Query q = em.createNamedQuery("Room.findAll");
        List<Room> lst = q.getResultList();
        for( Room rm : lst) {
            rm.setBookingDetailCollection(null);
        }
        return lst;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Object getRoomById(Integer roomId) {
        
        return em.find(Room.class, roomId);
    }

    @Override
    public Object findRoomAvailability(Integer roomId, Date startDate, Date endDate) {
        
        Room tgtRoom;
        tgtRoom = em.getReference(Room.class, roomId);
        
        Query q = em.createNamedQuery("BookingDetail.findAvaliableDates");
        q.setParameter("roomId", tgtRoom);
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        
        List<BookingDetail> lst = q.getResultList();

        return lst;
    }

    @Override
    public List<CustomerMessage> getMessageByDate(Date date) {
        
        TypedQuery q = em.createNamedQuery("CustomerMessage.findByMsgDone", CustomerMessage.class);
        q.setParameter("msgDone", Boolean.FALSE);
        //q.setParameter("msgTime", date);
        
        List<CustomerMessage> l = q.getResultList();
        
        for(CustomerMessage msg : l) {
            msg.setMsgDone(Boolean.TRUE);
            em.merge(msg);
        }
        
        return l;
    }

    @Override
    public List<CustomerMessage> getAllMessages() {
        TypedQuery q = em.createNamedQuery("CustomerMessage.findAll", CustomerMessage.class);
        
        return q.getResultList();
    }
    
    
}
