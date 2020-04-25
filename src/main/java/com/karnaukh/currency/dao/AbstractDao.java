package com.karnaukh.currency.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public abstract class AbstractDao<T, PK extends Serializable> implements IGenericDao<T, PK> {

	@PersistenceContext
	EntityManager entityManager;

	private Class<T> tClass;

	public AbstractDao() {
	}

	public AbstractDao(Class<T> tClass) {
		this.tClass = tClass;
	}

	@Override
	public T getByPK(PK primaryKey) {
		return entityManager.find(tClass, primaryKey);
	}

	@Override
	public void save(T t) {
		entityManager.persist(t);
	}

	@Override
	public void update(T t) {
		entityManager.merge(t);
	}

	@Override
	public void delete(T t) {
		t = entityManager.merge(t);
		entityManager.remove(t);
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}
}
