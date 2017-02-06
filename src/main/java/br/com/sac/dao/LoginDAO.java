package br.com.sac.dao;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.sac.model.UserLogin;
import br.com.sac.util.CryptoPassword;


public class LoginDAO {
	
	@SuppressWarnings({"unchecked" })
	public UserLogin doLogin(String login, String password){
		EntityManagerFactory emf = null;
	    EntityManager em = null;
	    
		try {
			
			emf = Persistence.createEntityManagerFactory("SAC");
		    em = emf.createEntityManager();
			
			List<UserLogin> user = (List<UserLogin>) em.createNamedQuery("UserLogin.findByUser")
					.setParameter("login", login)
					.setParameter("senha", CryptoPassword.convertStringToMd5(password)).getResultList();
			

			if (user != null && user.size() == 1) {// .size() == 1) {
				UserLogin userFound = (UserLogin) user.get(0);
				return userFound;
			}
			if(FacesContext.getCurrentInstance() != null)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login e/ou senha inválidos.", ""));
			return null;
		} catch (Exception e) {
			e.printStackTrace();

		}
		finally{
			em.close();
			emf.close();
		}
		return null;
	}
	
	public void insert(UserLogin userLogin){
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("SAC");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(userLogin);
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
	
	public void delete(UserLogin userLogin){
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory("SAC");
			em = emf.createEntityManager();
			em.getTransaction().begin();
			UserLogin deleteUserLogin = em.find(UserLogin.class, userLogin.getLogin());
			em.remove(deleteUserLogin);
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
