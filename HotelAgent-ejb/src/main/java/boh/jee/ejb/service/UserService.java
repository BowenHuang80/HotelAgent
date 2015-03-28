/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.service;

import boh.jee.ejb.hotelagent.remotelib.HAException;
import boh.jee.ejb.hotelagent.remotelib.UserServiceRemote;
import boh.jee.ejb.model.Booking;
import boh.jee.ejb.model.BookingDetail;
import boh.jee.ejb.model.Guest;
import boh.jee.ejb.model.Room;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author brian
 */
@Stateful
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class UserService implements UserServiceRemote {

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
        
        Date sD, eD;
        
        SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy"); 

        try {
            sD = ft.parse(startDate);
            eD = ft.parse(endDate);
            
        } catch (ParseException ex) {
            throw new HAException("Wrong Date Format");
        }
        
        
        em.persist(booking);
    }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Boolean userLogin(String name, String phone) {
        Boolean result = false;
        
        TypedQuery q = em.createNamedQuery("Guest.findByGuestName", Guest.class);
        q.setParameter("guestName", name);
        
        List<Guest> users = q.getResultList();
                
        for( Guest tgtUser : users ) {
            if( tgtUser.getGuestPhone().equals(phone) ) {
                activeUser = tgtUser;
                result = true;
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
        List<Booking> allBookings = new ArrayList<Booking>();
        
        if( activeUser != null ) {
            Query q = em.createNamedQuery("Booking.findByGuests");
        
            allBookings.addAll(q.getResultList());
        }
       
        return allBookings;
    }

    @Override
    public void testException() throws HAException {
        
        throw new HAException("test exception");
    }
    
    
}
