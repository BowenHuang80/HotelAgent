/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.jpa.dao;

/**
 *
 * @author brian
 */
public interface GenericDao<E, K> {
      void persist(E entity);
      void remove(E entity);
      void update(E entity);
      E findById(K id);
}
