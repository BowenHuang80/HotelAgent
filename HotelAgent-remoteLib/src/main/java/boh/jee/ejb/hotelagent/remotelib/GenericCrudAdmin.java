/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.ejb.hotelagent.remotelib;

import java.io.Serializable;
import java.util.Collection;
import javax.ejb.Remote;

/**
 *
 * @author brian
 */
@Remote
public interface GenericCrudAdmin {
    public  Serializable create(Serializable t)  throws HAException ;
    public  Serializable find(Serializable id,Class type);
    public void delete(Serializable t);
    public  Serializable update(Serializable t);
    public Collection<Serializable> findByNamedQuery(String queryName);
    public Collection<Serializable> findByNamedQuery(String queryName,int resultLimit);
}
