/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.hotelagent.remotelib;

import javax.ejb.Remote;

/**
 *
 * @author brian
 */
@Remote
public interface UserServiceRemote {

    void bookRoom(Integer roomId, String startDate, String endDate, Integer guests) throws HAException;

    Object userLogin(String name, String phone);

    void userLogout();

    Boolean userSignUp(Object newUser);

    Object bookedRooms();

    void testException() throws HAException;

    void addRoom(Object newRoom) throws HAException;

    void deleteRoom(Integer Integer) throws HAException;
    
}
