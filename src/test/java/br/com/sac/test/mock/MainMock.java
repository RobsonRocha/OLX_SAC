package br.com.sac.test.mock;

import java.sql.SQLException;
import java.util.List;

import br.com.sac.bean.CallsBean;
import br.com.sac.bean.LoginBean;
import br.com.sac.dao.CallsDAO;
import br.com.sac.dao.LoginDAO;
import br.com.sac.model.Calls;
import br.com.sac.model.UserLogin;
import br.com.sac.test.util.Utils;

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
