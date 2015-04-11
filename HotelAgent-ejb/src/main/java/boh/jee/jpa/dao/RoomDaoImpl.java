/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.jpa.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author brian
 */
@Stateless
public class RoomDaoImpl extends GenericDaoImpl implements RoomDao {

    @Override
    public List findRoomsByFloor(Integer lFloor, Integer hFloor) {
        String q = "SELECT e FROM " + entityClass.getName() + " e WHERE e.roomFloor >= :lowFloor";
        
        if( hFloor.intValue() != 0 ) {
             q += " and e.roomFloor <= :highFloor";
        }
		Query query = entityManager.createQuery( q );
			
		query.setParameter("lowFloor", lFloor);
        
        if( hFloor.intValue() != 0 ) {
            query.setParameter("highFloor", hFloor);
        }
		return (List) query.getResultList();
    }

    @Override
    public List findRoomsBySize(Integer size) {
        Query query = entityManager.createNamedQuery("Room.findByRoomSize");
        query.setParameter("roomSize", size.intValue());
        
        return (List) query.getResultList();
    }
}
