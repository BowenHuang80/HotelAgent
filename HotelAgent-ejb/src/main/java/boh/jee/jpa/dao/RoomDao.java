/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.jpa.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author brian
 */
@Remote
public interface RoomDao extends GenericDao {
    /**
     * 
     * @param lFloor no lower than lFloor, ground floor is 1
     * @param hFloor no higher than hFloor, 0 no limit
     * @return 
     */
    List findRoomsByFloor(Integer lFloor, Integer hFloor);
    List findRoomsBySize(Integer size);
}
