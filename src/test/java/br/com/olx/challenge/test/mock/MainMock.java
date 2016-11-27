package br.com.olx.challenge.test.mock;

import java.sql.SQLException;
import java.util.List;

import br.com.olx.challenge.bean.CallsBean;
import br.com.olx.challenge.bean.LoginBean;
import br.com.olx.challenge.dao.CallsDAO;
import br.com.olx.challenge.dao.LoginDAO;
import br.com.olx.challenge.model.Calls;
import br.com.olx.challenge.model.UserLogin;
import br.com.olx.challenge.test.util.Utils;

public class MainMock {
	
	private LoginBean loginBean;
	
	public UserLogin createUserLogin(String login, String password, boolean isAdmin){
		
		UserLogin ul = new UserLogin();
		ul.setAdmin(isAdmin);
		ul.setLogin(login);
		ul.setPassword(Utils.convertStringToMd5(password));
		
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.insert(ul);
		
		ul.setPassword(password);
		
		return ul;
	}
	
	public void deleteUserLogin(UserLogin ul){
		LoginDAO loginDAO = new LoginDAO();
		loginDAO.delete(ul);
	}	
	
	public void insertCall(Calls call) throws SQLException {
		CallsBean cb = new CallsBean();
		
		cb.setTypeCall(call.getTypeCall());
		cb.setDescription(call.getDescription());
		cb.setState(call.getState());
		cb.setReason(call.getReason());
		cb.setLoginBean(this.loginBean);
		cb.insertCall();
			
	}
	
	public List<Calls> getCalls(){
		CallsBean cb = new CallsBean();
		cb.setLoginBean(this.loginBean);
		cb.getAllCalls();
		
		return cb.getCalls();
	}
	
	public void deleteCalls(Calls call){
		CallsDAO cd = new CallsDAO();
		
		cd.delete(call);
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
}
