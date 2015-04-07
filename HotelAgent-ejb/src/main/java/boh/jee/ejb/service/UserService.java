/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.service;

import boh.jee.ejb.hotelagent.remotelib.GenericCrudAdmin;
import boh.jee.ejb.hotelagent.remotelib.HAException;
import boh.jee.ejb.hotelagent.remotelib.UserServiceRemote;
import boh.jee.ejb.model.Admin;
import boh.jee.ejb.model.Booking;
import boh.jee.ejb.model.BookingDetail;
import boh.jee.ejb.model.Guest;
import boh.jee.ejb.model.Room;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
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
@Stateful
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class UserService implements UserServiceRemote, GenericCrudAdmin {

    @PersistenceContext(unitName="boh.jee.ejb_HotelAgent-ejb_ejb_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    Guest activeUser = null;
    
    
    @Override
    public void bookRoom(Integer roomId, String startDate, String endDate, Integer guests) throws HAException
    {
        
        if( activeUser == null ) {
            throw new HAException("Illegal access");
        }
        
        boolean result = false;
        
        Room tgtRoom = em.getReference(Room.class, roomId);
        
        Booking booking = new Booking();
        booking.setGuestId(activeUser);
        booking.setGuests(guests.shortValue());

        ArrayList<BookingDetail> lst = new ArrayList<BookingDetail>(1);
        
        BookingDetail bkD = new BookingDetail();
       
        bkD.setBookingId(booking);
        bkD.setRoomId(tgtRoom);
        bkD.setGuestId(activeUser);
        
        Date sD, eD;
        
        SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy"); 

        try {
            sD = ft.parse(startDate);
            eD = ft.parse(endDate);
            
        } catch (ParseException ex) {
            throw new HAException("Wrong Date Format");
        }
        
        bkD.setStartDate(sD);
        bkD.setEndDate(eD);
        
        lst.add(bkD);
        booking.setBookingDetailCollection(lst);
        em.persist(booking);
    }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Object userLogin(String name, String phone) {
        Guest result = null;
        
        TypedQuery q = em.createNamedQuery("Guest.findByGuestName", Guest.class);
        q.setParameter("guestName", name);
        
        List<Guest> users = q.getResultList();
                
        for( Guest tgtUser : users ) {
            if( tgtUser.getGuestPhone().equals(phone) ) {
                activeUser = tgtUser;
                result = tgtUser;
                break;
            }
        }
        
        return result;
    }

    @Override
    public void userLogout() {
        activeUser = null;
    }

    @Override
    public Boolean userSignUp(Object newUser) {
        boolean result = false;
        Guest gst = (Guest)newUser;
        
        TypedQuery q = em.createNamedQuery("Guest.findByGuestName", Guest.class);
        
        List<Guest> users = q.getResultList();
                
        for( Guest tgtUser : users ) {
            if( tgtUser.getGuestName().equals(gst.getGuestName()) ) {                
                result = false;
                break;
            }
        }
        if( result ) {
            em.persist(gst);
        }
        
        return result;
    }

    @Override
    public Object bookedRooms() {
        List<BookingDetail> allBookings = new ArrayList<BookingDetail>();
        
        if( activeUser != null ) {
            Query q = em.createNamedQuery("BookingDetail.findByGuestId");
            q.setParameter("guestId", this.activeUser);
            List<BookingDetail> lst = q.getResultList();
//            for(Booking bking : lst) {
//                Collection<BookingDetail> bkds = bking.getBookingDetailCollection();
//                for(BookingDetail bkd : bkds) {
//                    bkd.getRoomId();
//                    //bkd.
//                }
//            }
            allBookings.addAll(q.getResultList());
        }
       
        return allBookings;
    }

    @Override
    public void testException() throws HAException {
        
        throw new HAException("test exception");
    }
    /**
     * Operations for Admin
     */

    
    /**
     * @SPEC 1.1.a
     * @param newRoom
     * @throws HAException 
     */
    @Override
    public void addRoom(Object newRoom) throws HAException {
        if( !(activeUser instanceof Admin )) {
            throw new HAException("Illegal Access");
        }
        
        try{
            em.persist(newRoom) ;
        } catch( Exception e) {
            throw new HAException(e.getMessage());
        }
    }

    /**
     * @SPEC 1.1.a
     * @param Integer
     * @throws HAException 
     */
    @Override
    public void deleteRoom(Integer Integer) throws HAException {
        throw new HAException("TODO Method");
    }


    protected boolean isAdmin() {
        if( activeUser == null || !(activeUser instanceof Admin) ) {
            return false;
        }
        return true;
    }
    
    /**
     * Generic Interface for Admin 
     * @param t
     * @return 
     */
    @Override
    public Serializable create(Serializable t) throws HAException {
        if( !isAdmin() ) {
            throw new HAException("Illegal Access");
        }
        em.persist(t);
        return t;        
    }

    @Override
    public Serializable find(Serializable id, Class type) {
        if( !isAdmin() ) {
            //throw new HAException("Illegal Access");
        }
        em.find(type, id);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Serializable t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable update(Serializable t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Serializable> findByNamedQuery(String queryName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Serializable> findByNamedQuery(String queryName, int resultLimit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
