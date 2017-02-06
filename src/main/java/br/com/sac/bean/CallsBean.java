package br.com.sac.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.context.RequestContext;

import br.com.sac.dao.CallsDAO;
import br.com.sac.model.Calls;

@ManagedBean(name="callsBean")
@ViewScoped
public class CallsBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7742892874166712124L;
	
	@ManagedProperty(value="#{loginBean}")
	private LoginBean loginBean;	

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	private List<Calls> calls;
	private List<Calls> callsFakes;
	private Map<String,List<Calls>> callsMap;
	private Calls selectedCall;
	
	private char typeCall;	
	private String state;
	private char reason;
	private String description;	
	
	public char getTypeCall() {
		return typeCall;
	}

	public void setTypeCall(char typeCall) {
		this.typeCall = typeCall;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public char getReason() {
		return reason;
	}

	public void setReason(char reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void getAllCalls() {
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		
		try {
			emf = Persistence
					.createEntityManagerFactory("SAC");
			em = emf.createEntityManager();
			
			List<Object[]> listGroup = null;
			
			if (loginBean.getLoggedUser().isAdmin()){
				this.setCalls((List<Calls>) em
						.createNamedQuery("Calls.allCalls").getResultList());
				listGroup = em.createNamedQuery("Calls.allGroups").getResultList();
			}
			else{
				this.setCalls((List<Calls>) em
						.createNamedQuery("Calls.callsByLogin")
						.setParameter("login", loginBean.getLogin())
						.getResultList());
				listGroup = em.createNamedQuery("Calls.allGroupsByLogin").setParameter("login",  loginBean.getLogin()).getResultList();
			}
			
			callsMap = new HashMap<String, List<Calls>>();
			callsFakes = new ArrayList<Calls>();
			
			List<String> keys = new ArrayList<String>(); 
			
			if(listGroup.isEmpty())
				return;
			
			for(Object o[] : listGroup){
				
				String key = String.valueOf(o[1])+String.valueOf(o[0]);
				
				if(!keys.contains(key)){
					keys.add(key);
					Calls c = new Calls();
					c.setInsertionDate((Timestamp) o[2]);
					c.setState(String.valueOf(o[1]));
					callsFakes.add(c);
				}
			}			
			
			for(Calls c : this.calls){
				String key = c.getState()+c.getInsertionDateString();
				List<Calls> list = callsMap.get(key);
				
				if(list == null){
					list = new ArrayList<Calls>();
					list.add(c);
					callsMap.put(key, list);
				}
				else{
					list.add(c);
					callsMap.put(key, list);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		finally{
			em.close();
			emf.close();
		}

	}
	
	public void insertCall() throws SQLException {
		CallsDAO callsDAO = new CallsDAO();
		Calls calls = new Calls();
		
		calls.setDescription(description);
		calls.setInsertionDate(new Date());
		calls.setReason(reason);
		calls.setState(state);
		calls.setTypeCall(typeCall);
		calls.setUser(loginBean.getLoggedUser());
		
		if(callsDAO.insert(calls)){
			if(RequestContext.getCurrentInstance() != null)
				RequestContext.getCurrentInstance().execute("PF('dlgInsert').hide()");
			getAllCalls();
			description = null;			
			reason = 'x';
			state = null;
			typeCall = 'x';			
			
		}
	}
	
	public List<Calls> getCallsGroup(String state, String date){
		
		if(callsMap != null)
			return callsMap.get(state+date);
		return null;		
	}
	
	public List<Calls> getCalls() {
		return calls;
	}

	public void setCalls(List<Calls> calls) {
		this.calls = calls;
	}
	
	public List<Calls> getCallsFakes() {
		return callsFakes;
	}

	public Calls getSelectedCall() {
		return selectedCall;
	}

	public void setSelectedCall(Calls selectedCall) {
		this.selectedCall = selectedCall;
	}	

}
