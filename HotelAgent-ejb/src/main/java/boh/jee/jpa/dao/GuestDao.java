/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.jpa.dao;

import boh.jee.ejb.model.Guest;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 *
 * @author brian
 */
@Remote
public interface GuestDao extends GenericDao<Guest,Integer> {
    List findGuestByEmail(String email);
    List findGuestByName(String name);
}
