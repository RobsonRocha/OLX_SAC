package br.com.olx.challenge.test;

import static org.easymock.EasyMock.mock;

import java.util.List;

import org.junit.Before;
import org.primefaces.context.RequestContext;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import br.com.olx.challenge.bean.LoginBean;
import br.com.olx.challenge.bean.RedirectBean;
import br.com.olx.challenge.model.Calls;
import br.com.olx.challenge.model.UserLogin;
import br.com.olx.challenge.test.mock.MainMock;
import br.com.olx.challenge.test.util.Utils;

public class CallsTest {
	
	private Calls calls;
	private UserLogin loggedUser;
	
	@Before
	public void createMock(){
		RequestContext mock = mock(RequestContext.class);
	}
		
	@AfterMethod
	public void deleteCalls(){
		MainMock mm = new MainMock();
		
		mm.deleteCalls(this.calls);
	}
	
	@AfterMethod
	public void deleteLogin(){
		MainMock mm = new MainMock();
		mm.deleteUserLogin(this.loggedUser);
	}
	
	@Test
	public void insertCallsFromCommonUserTest(){
		MainMock mm = new MainMock();
		
		String login = "challenge1";
		String password = Utils.randomPassword();
		
		this.loggedUser = mm.createUserLogin(login, password, false);
		
		LoginBean lb = new LoginBean();
		
		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();
		
		AssertJUnit.assertTrue(lb.getLoggedUser() != null);
		
		mm.setLoginBean(lb);
		
		Calls c = new Calls();
		
		c.setDescription("Teste");
		c.setReason('S');
		c.setState("RJ");
		c.setTypeCall('C');		
		this.calls = c;
		
		mm.insertCall(c);
		
		List<Calls> list = mm.getCalls(); 
		
		Calls insertedCall = list.get(0);
		
		AssertJUnit.assertTrue(list.size()==0);
		AssertJUnit.assertTrue(insertedCall.getDescription().equals(c.getDescription()));
		AssertJUnit.assertTrue(insertedCall.getReason() == c.getReason());
		AssertJUnit.assertTrue(insertedCall.getState().equals(c.getState()));
		AssertJUnit.assertTrue(insertedCall.getTypeCall() == c.getTypeCall());
		AssertJUnit.assertTrue(insertedCall.getUser().equals(this.loggedUser));
		
	}
	
}
