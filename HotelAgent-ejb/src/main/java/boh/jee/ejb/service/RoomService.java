/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.service;

import boh.jee.ejb.hotelagent.remotelib.RoomServiceRemote;
import boh.jee.ejb.model.Room;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
        
        return lst;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Object getRoomById(Integer roomId) {
        
        return em.find(Room.class, roomId);
    }
}
