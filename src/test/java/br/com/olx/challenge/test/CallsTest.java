package br.com.olx.challenge.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.olx.challenge.bean.LoginBean;
import br.com.olx.challenge.bean.RedirectBean;
import br.com.olx.challenge.model.Calls;
import br.com.olx.challenge.model.UserLogin;
import br.com.olx.challenge.test.mock.MainMock;
import br.com.olx.challenge.test.util.Utils;

public class CallsTest {
	
	private String baseUrl;
	private WebDriver driver;
	
	private List<Calls> calls = new ArrayList<Calls>();
	private List<UserLogin> loggedUsers = new ArrayList<UserLogin>();
	
	@BeforeClass
	public void openBrowser() {
		baseUrl = System.getProperty("webdriver.base.url");
		if (baseUrl == null || baseUrl.trim().isEmpty())
			baseUrl = "http://localhost:8080/sacweb";
		String pathChromeDriver = System.getProperty("webdriver.chrome.driver");
		if(pathChromeDriver == null || pathChromeDriver.trim().isEmpty())
			System.setProperty("webdriver.chrome.driver",
				"/Projetos/primefaces/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseUrl);
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.close();
	}

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
	
	@Test
	public void insertWithAllFieldsNullTest() throws InterruptedException{
		MainMock mm = new MainMock();

		String strLogin = "challenge1";
		String strPassword = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(strLogin, strPassword, false);
		this.loggedUsers.add(loggedUser);
		
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys(strLogin);
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys(strPassword);		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement btnMenu = driver.findElement(By
				.className("ui-button-text"));
		Assert.assertTrue(btnMenu.getText().equals(strLogin));
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnInsert = driver.findElement(By.id("main:mnuInsert"));
		btnInsert.click();
		WebElement btnInsertCall = driver.findElement(By.id("formInsert:btnInsert"));
		btnInsertCall.click();
		Thread.sleep(1500);
		List<WebElement> errors = driver.findElements(By
				.className("ui-growl-title"));
		Assert.assertTrue(errors.get(0).getText().equals("Escolha o tipo do chamado."));
		Assert.assertTrue(errors.get(1).getText().equals("Escolha o motivo."));
		Assert.assertTrue(errors.get(2).getText().equals("Escolha o Estado."));
		Assert.assertTrue(errors.get(3).getText().equals("Preencha o campo Descri��o."));
		
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnLogout = driver.findElement(By.id("main:mnuExit"));
		btnLogout.click();
		Thread.sleep(3000);
		WebElement login2 = driver.findElement(By.id("frmLogin:login"));
		Assert.assertTrue(login2 != null);
	}
	
	@Test
	public void insertWithoutTypeTest() throws InterruptedException{
		MainMock mm = new MainMock();

		String strLogin = "challenge1";
		String strPassword = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(strLogin, strPassword, false);
		this.loggedUsers.add(loggedUser);
		
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys(strLogin);
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys(strPassword);		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement btnMenu = driver.findElement(By
				.className("ui-button-text"));
		Assert.assertTrue(btnMenu.getText().equals(strLogin));
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnInsert = driver.findElement(By.id("main:mnuInsert"));
		btnInsert.click();
		
		List<WebElement> reasons = driver.findElements(By.id("formInsert:reason"));
		reasons.get(0).click();
		
		WebElement state = driver.findElement(By.id("formInsert:state"));
		state.sendKeys("RJ");
		
		WebElement description = driver.findElement(By.id("formInsert:description"));
		description.sendKeys("Teste Unit�rio.");
		
		WebElement btnInsertCall = driver.findElement(By.id("formInsert:btnInsert"));
		btnInsertCall.click();
		Thread.sleep(3000);
		List<WebElement> errors = driver.findElements(By
				.className("ui-growl-title"));
		Assert.assertTrue(errors.get(0).getText().equals("Escolha o tipo do chamado."));
		
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnLogout = driver.findElement(By.id("main:mnuExit"));
		btnLogout.click();
		Thread.sleep(3000);
		WebElement login2 = driver.findElement(By.id("frmLogin:login"));
		Assert.assertTrue(login2 != null);
	}
	
	@Test
	public void insertWithoutReasonTest() throws InterruptedException{
		MainMock mm = new MainMock();

		String strLogin = "challenge1";
		String strPassword = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(strLogin, strPassword, false);
		this.loggedUsers.add(loggedUser);
		
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys(strLogin);
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys(strPassword);		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement btnMenu = driver.findElement(By
				.className("ui-button-text"));
		Assert.assertTrue(btnMenu.getText().equals(strLogin));
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnInsert = driver.findElement(By.id("main:mnuInsert"));
		btnInsert.click();
		
		List<WebElement> types = driver.findElements(By.id("formInsert:typeCall"));
		types.get(0).click();
		
		WebElement state = driver.findElement(By.id("formInsert:state"));
		state.sendKeys("RJ");
		
		WebElement description = driver.findElement(By.id("formInsert:description"));
		description.sendKeys("Teste Unit�rio.");
		
		WebElement btnInsertCall = driver.findElement(By.id("formInsert:btnInsert"));
		btnInsertCall.click();
		Thread.sleep(3000);
		List<WebElement> errors = driver.findElements(By
				.className("ui-growl-title"));
		Assert.assertTrue(errors.get(0).getText().equals("Escolha o motivo."));
		
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnLogout = driver.findElement(By.id("main:mnuExit"));
		btnLogout.click();
		Thread.sleep(3000);
		WebElement login2 = driver.findElement(By.id("frmLogin:login"));
		Assert.assertTrue(login2 != null);
	}
	
	@Test
	public void insertWithoutStateTest() throws InterruptedException{
		MainMock mm = new MainMock();

		String strLogin = "challenge1";
		String strPassword = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(strLogin, strPassword, false);
		this.loggedUsers.add(loggedUser);
		
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys(strLogin);
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys(strPassword);		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement btnMenu = driver.findElement(By
				.className("ui-button-text"));
		Assert.assertTrue(btnMenu.getText().equals(strLogin));
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnInsert = driver.findElement(By.id("main:mnuInsert"));
		btnInsert.click();
		
		List<WebElement> reasons = driver.findElements(By.id("formInsert:reason"));
		reasons.get(0).click();
		
		List<WebElement> types = driver.findElements(By.id("formInsert:typeCall"));
		types.get(0).click();
		
		WebElement description = driver.findElement(By.id("formInsert:description"));
		description.sendKeys("Teste Unit�rio.");
		
		WebElement btnInsertCall = driver.findElement(By.id("formInsert:btnInsert"));
		btnInsertCall.click();
		Thread.sleep(3000);
		List<WebElement> errors = driver.findElements(By
				.className("ui-growl-title"));
		Assert.assertTrue(errors.get(0).getText().equals("Escolha o Estado."));
		
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnLogout = driver.findElement(By.id("main:mnuExit"));
		btnLogout.click();
		Thread.sleep(3000);
		WebElement login2 = driver.findElement(By.id("frmLogin:login"));
		Assert.assertTrue(login2 != null);
	}
	
	@Test
	public void insertWithoutDescriptionTest() throws InterruptedException{
		MainMock mm = new MainMock();

		String strLogin = "challenge1";
		String strPassword = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(strLogin, strPassword, false);
		this.loggedUsers.add(loggedUser);
		
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys(strLogin);
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys(strPassword);		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement btnMenu = driver.findElement(By
				.className("ui-button-text"));
		Assert.assertTrue(btnMenu.getText().equals(strLogin));
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnInsert = driver.findElement(By.id("main:mnuInsert"));
		btnInsert.click();
		
		List<WebElement> reasons = driver.findElements(By.id("formInsert:reason"));
		reasons.get(0).click();
		
		List<WebElement> types = driver.findElements(By.id("formInsert:typeCall"));
		types.get(0).click();
		
		WebElement state = driver.findElement(By.id("formInsert:state"));
		state.sendKeys("RJ");
		
		WebElement btnInsertCall = driver.findElement(By.id("formInsert:btnInsert"));
		btnInsertCall.click();
		Thread.sleep(3000);
		List<WebElement> errors = driver.findElements(By
				.className("ui-growl-title"));
		Assert.assertTrue(errors.get(0).getText().equals("Preencha o campo Descri��o."));
		
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnLogout = driver.findElement(By.id("main:mnuExit"));
		btnLogout.click();
		Thread.sleep(3000);
		WebElement login2 = driver.findElement(By.id("frmLogin:login"));
		Assert.assertTrue(login2 != null);
	}
	
	@Test
	public void correctInsertTest() throws InterruptedException{
		MainMock mm = new MainMock();

		String strLogin = "challenge1";
		String strPassword = Utils.randomPassword();

		UserLogin loggedUser = mm.createUserLogin(strLogin, strPassword, false);
		this.loggedUsers.add(loggedUser);
		
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys(strLogin);
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys(strPassword);		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement btnMenu = driver.findElement(By
				.className("ui-button-text"));
		Assert.assertTrue(btnMenu.getText().equals(strLogin));
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnInsert = driver.findElement(By.id("main:mnuInsert"));
		btnInsert.click();
		
		List<WebElement> reasons = driver.findElements(By.id("formInsert:reason"));
		reasons.get(0).click();
		
		List<WebElement> types = driver.findElements(By.id("formInsert:typeCall"));
		types.get(0).click();
		
		WebElement state = driver.findElement(By.id("formInsert:state"));
		state.sendKeys("RJ");
		
		WebElement description = driver.findElement(By.id("formInsert:description"));
		description.sendKeys("Teste Unit�rio.");
		
		WebElement btnInsertCall = driver.findElement(By.id("formInsert:btnInsert"));
		btnInsertCall.click();
		Thread.sleep(3000);
				
		btnMenu = driver.findElement(By.name("main:splMenu_menuButton"));
		btnMenu.click();
		WebElement btnLogout = driver.findElement(By.id("main:mnuExit"));
		btnLogout.click();
		Thread.sleep(3000);
		WebElement login2 = driver.findElement(By.id("frmLogin:login"));
		Assert.assertTrue(login2 != null);
		
		LoginBean lb = new LoginBean();

		lb.setLogin(loggedUser.getLogin());
		lb.setPassword(loggedUser.getPassword());
		lb.setNavigation(new RedirectBean());
		lb.doLogin();

		Assert.assertTrue(lb.getLoggedUser() != null);

		mm.setLoginBean(lb);

		List<Calls> list = mm.getCalls();

		Calls insertedCall = list.get(0);
		
		Assert.assertTrue(insertedCall.getDescription().equals("Teste Unit�rio."));
		Assert.assertTrue(insertedCall.getUser().getLogin().equals(loggedUser.getLogin()));
		
		calls.add(insertedCall);		
		
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
