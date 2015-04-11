/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.hotelagent.remotelib;

import java.util.Date;
import javax.ejb.Remote;
//import javax.validation.constraints.Future;

/**
 *
 * @author brian
 */
@Remote
public interface UserServiceRemote {

    void bookRoom(Integer roomId, Date startDate, Date endDate, Integer guests) throws HAException;

    Object userLogin(String name, String phone);

    void userLogout();

    Boolean userSignUp(Object newUser);

    Object bookedRooms();

    void testException() throws HAException;

    void addRoom(Object newRoom) throws HAException;

    void deleteRoom(Integer Integer) throws HAException;

    void updateBooking(Integer bookingDetailId, Date startDate, Date endDate, Integer guests) throws HAException;
    
}
