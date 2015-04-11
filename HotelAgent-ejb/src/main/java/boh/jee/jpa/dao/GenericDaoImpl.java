/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boh.jee.jpa.dao;

import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author brian
 * @param <E>
 */
public abstract class GenericDaoImpl<E,K> implements GenericDao<E,K> {
	protected Class entityClass;

	@PersistenceContext
	protected EntityManager entityManager;

	public GenericDaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class) genericSuperclass.getActualTypeArguments()[1];
	}
    @Override
    public void update(E entity) { entityManager.merge(entity); }
    @Override
	public void persist(E entity) { entityManager.persist(entity); }
    @Override
	public void remove(E entity) { entityManager.remove(entity); }
    @Override
	public E findById(K id) { return (E) entityManager.find(entityClass, id); }
}
