package br.com.olx.challenge.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import br.com.olx.challenge.bean.LoginBean;
import br.com.olx.challenge.bean.RedirectBean;
import br.com.olx.challenge.model.Calls;
import br.com.olx.challenge.model.UserLogin;
import br.com.olx.challenge.test.mock.MainMock;
import br.com.olx.challenge.test.util.Utils;

public class CallsTest {

	private List<Calls> calls = new ArrayList<Calls>();
	private List<UserLogin> loggedUsers = new ArrayList<UserLogin>();
	

	@AfterMethod
	public void delete() {
		MainMock mm = new MainMock();
		
		for(Calls c : this.calls)
			mm.deleteCalls(c);
		
		for(UserLogin ul : this.loggedUsers)
			mm.deleteUserLogin(ul);
	}

	@Test
	public void callsFromCommonUserTest() throws SQLException {
		MainMock mm = new MainMock();

		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		c.setDescription("Teste");
		c.setReason('S');
		c.setState("RJ");
		c.setTypeCall('C');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(loggedUser));

	}
	
	@Test
	public void callsFromAdminTest() throws SQLException {
		MainMock mm = new MainMock();
		
		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		c.setDescription("Teste");
		c.setReason('S');
		c.setState("RJ");
		c.setTypeCall('C');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);
		
		lb.logout();
		
		Assert.assertTrue(lb.getLoggedUser() == null);
		
		login = "challenge2";
		password = Utils.randomPassword();

		loggedUser = mm.createUserLogin(login, password, true);
		this.loggedUsers.add(loggedUser);

		lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c2 = new Calls();

		c2.setDescription("Teste");
		c2.setReason('E');
		c2.setState("ES");
		c2.setTypeCall('T');
		
		mm.insertCall(c2);

		list = mm.getCalls();
		Calls insertedCall2 = null;
		
		for(Calls call : list){
			if(call.getUser().getLogin().equals(loggedUser.getLogin())){
				this.calls.add(call);
				insertedCall2 = call;
				break;
			}
		}
		
		Assert.assertTrue(list.size() >= 2);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(this.loggedUsers.get(0)));
		
		Assert.assertTrue(insertedCall2.getDescription().equals(
				c2.getDescription()));
		Assert.assertTrue(insertedCall2.getReason() == c2.getReason());
		Assert.assertTrue(insertedCall2.getState().equals(c2.getState()));
		Assert.assertTrue(insertedCall2.getTypeCall() == c2.getTypeCall());
		Assert.assertTrue(insertedCall2.getUser().equals(this.loggedUsers.get(1)));

	}
	
	@Test(expectedExceptions=SQLException.class)
	public void insertCallWithWrongTypeTest() throws SQLException {
		MainMock mm = new MainMock();

		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		c.setDescription("Teste");
		c.setReason('S');
		c.setState("RJ");
		c.setTypeCall('X');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(loggedUser));

	}
	
	@Test(expectedExceptions=SQLException.class)
	public void insertCallWithNullTypeTest() throws SQLException {
		MainMock mm = new MainMock();

		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		c.setDescription("Teste");
		c.setReason('S');
		c.setState("RJ");
		//c.setTypeCall('X');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(loggedUser));

	}
	
	@Test(expectedExceptions=SQLException.class)
	public void insertCallWithWrongReasonTest() throws SQLException {
		MainMock mm = new MainMock();

		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		c.setDescription("Teste");
		c.setReason('X');
		c.setState("RJ");
		c.setTypeCall('C');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(loggedUser));

	}
	
	@Test(expectedExceptions=SQLException.class)
	public void insertCallWithNullReasonTest() throws SQLException {
		MainMock mm = new MainMock();

		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		c.setDescription("Teste");
		//c.setReason('S');
		c.setState("RJ");
		c.setTypeCall('C');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(loggedUser));

	}
	
	@Test(expectedExceptions=SQLException.class)
	public void insertCallWithNullStateTest() throws SQLException {
		MainMock mm = new MainMock();

		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		c.setDescription("Teste");
		c.setReason('S');
		//c.setState("RJ");
		c.setTypeCall('C');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(loggedUser));

	}
	
	@Test(expectedExceptions=SQLException.class)
	public void insertCallWithNullDescriptionTest() throws SQLException {
		MainMock mm = new MainMock();

		String login = "challenge1";
		String password = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(login, password, false);
		this.loggedUsers.add(loggedUser);

		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		Calls c = new Calls();

		//c.setDescription("Teste");
		c.setReason('S');
		c.setState("RJ");
		c.setTypeCall('C');
		
		mm.insertCall(c);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		calls.add(insertedCall);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(insertedCall.getDescription().equals(
				c.getDescription()));
		Assert.assertTrue(insertedCall.getReason() == c.getReason());
		Assert.assertTrue(insertedCall.getState().equals(c.getState()));
		Assert.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		Assert.assertTrue(insertedCall.getUser().equals(loggedUser));

	}
}
