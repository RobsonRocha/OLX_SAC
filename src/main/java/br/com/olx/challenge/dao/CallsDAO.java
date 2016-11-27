package br.com.olx.challenge.dao;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.olx.challenge.model.Calls;

public class CallsDAO {

	public boolean insert(Calls calls) throws SQLException {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		boolean result = false;
		try {
			emf = Persistence.createEntityManagerFactory("OLX_SAC");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(calls);
			em.getTransaction().commit();
			result = true;
		}
		catch(Exception e){
			em.getTransaction().rollback();
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
		finally {
			em.close();
			emf.close();
		}
		
		return result;
	}
	
	public void delete(Calls calls){
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("OLX_SAC");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Calls deleteCall = em.find(Calls.class, calls.getNumber());
			em.remove(deleteCall);
			em.getTransaction().commit();
		}
		catch(Exception e){
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			em.close();
			emf.close();
		}
	}

}
