/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.jpa.dao;

import boh.jee.ejb.model.Guest;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author brian
 */
@Stateless
public class GuestDaoImpl extends GenericDaoImpl<Guest,Integer> implements GuestDao {

    @Override
    public List findGuestByEmail(String email) {
        Query query = entityManager.createNamedQuery("Guest.findByGuestEmail");
        query.setParameter("guestEmail", email);
        
        return (List) query.getResultList();
    }
    
    @Override
    public List findGuestByName(String name) {
        Query query = entityManager.createNamedQuery("Guest.findByGuestName");
        query.setParameter("guestName", name);
        
        List<Guest> lst = query.getResultList();
        return lst;
    }
    
}
