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
public interface RoomServiceRemote {

    Object getAll();

    Object getRoomById(Integer roomId);
    
}
