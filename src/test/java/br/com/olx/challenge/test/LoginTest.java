package br.com.olx.challenge.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import br.com.olx.challenge.model.UserLogin;
import br.com.olx.challenge.test.mock.MainMock;
import br.com.olx.challenge.test.util.Utils;

public class LoginTest {

	private String baseUrl;
	private WebDriver driver;
	
	private List<UserLogin> loggedUsers = new ArrayList<UserLogin>();
	

	@AfterMethod
	public void delete() {
		MainMock mm = new MainMock();
		
		for(UserLogin ul : this.loggedUsers)
			mm.deleteUserLogin(ul);
	}


	@BeforeClass
	public void openBrowser() {
		baseUrl = System.getProperty("webdriver.base.url");
		if (baseUrl == null || baseUrl.trim().isEmpty())
			baseUrl = "http://localhost:8080/sacweb";
		
		String usingFireFox = System.getProperty("webdriver.using.firefox");
		if(usingFireFox != null && usingFireFox.trim().toLowerCase().equals("true"))
			driver = new FirefoxDriver();
		else {
			String pathChromeDriver = System.getProperty("webdriver.chrome.driver");
			if(pathChromeDriver == null || pathChromeDriver.trim().isEmpty())
				System.setProperty("webdriver.chrome.driver",
						"/Projetos/primefaces/chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.get(baseUrl);
	}
	
	@AfterClass
	public void closeBrowser() {
		driver.close();
	}
	
	@Test
	public void loginAndPasswordNullTest() throws InterruptedException {
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.clear();
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.clear();
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(1000);
		List<WebElement> errors = driver.findElements(By
				.className("ui-messages-error-summary"));
		Assert.assertTrue(errors.get(0).getText().equals("Preencha o campo Login."));
		Assert.assertTrue(errors.get(1).getText().equals("Preencha o campo Senha."));
	}
	
	@Test
	public void passwordNullTest() throws InterruptedException {
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys("Test");
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.clear();
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(1000);
		WebElement error = driver.findElement(By
				.className("ui-messages-error-summary"));
		Assert.assertTrue(error.getText().equals("Preencha o campo Senha."));
	}
	
	@Test
	public void loginNullTest() throws InterruptedException {
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.clear();
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys("Test");		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement error = driver.findElement(By
				.className("ui-messages-error-summary"));
		Assert.assertTrue(error.getText().equals("Preencha o campo Login."));		
	}
	
	@Test
	public void wrongLoginAndPasswordTest() throws InterruptedException {
		WebElement login = driver.findElement(By.id("frmLogin:login"));
		login.sendKeys("Test");
		WebElement password = driver.findElement(By.id("frmLogin:password"));
		password.sendKeys("fsadfasfsa");		
		WebElement button = driver.findElement(By.id("frmLogin:doLogin"));
		button.click();
		Thread.sleep(3000);
		WebElement error = driver.findElement(By
				.className("ui-messages-error-summary"));
		Assert.assertTrue(error.getText().equals("Login e/ou senha inválidos."));		
	}
	
	@Test
	public void correctLoginTest() throws InterruptedException {
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
		WebElement btnLogout = driver.findElement(By.id("main:mnuExit"));
		btnLogout.click();
		Thread.sleep(3000);
		WebElement login2 = driver.findElement(By.id("frmLogin:login"));
		Assert.assertTrue(login2 != null);
	}
}
