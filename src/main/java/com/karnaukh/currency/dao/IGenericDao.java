package com.karnaukh.currency.dao;

import javax.persistence.EntityManager;
import java.sql.SQLException;

public interface IGenericDao<T, PK> {
	T getByPK(PK primaryKey) throws SQLException;

	void save(T t) throws SQLException;

	void update(T t) throws SQLException;

	void delete(T t) throws SQLException;

	EntityManager getEntityManager();
}