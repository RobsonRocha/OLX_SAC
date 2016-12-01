package br.com.olx.challenge.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.olx.challenge.dao.LoginDAO;
import br.com.olx.challenge.model.UserLogin;

@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5711072656668392562L;

	private UserLogin loggedUser;

	private String login;
	private String password;
	
	@ManagedProperty(value="#{redirectBean}")
    private RedirectBean navigation;

	public RedirectBean getNavigation() {
		return navigation;
	}

	public void setNavigation(RedirectBean navigation) {
		this.navigation = navigation;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String doLogin() {

		LoginDAO login = new LoginDAO();
		this.loggedUser = login.doLogin(this.login, this.password);
		if (this.loggedUser != null)
			return navigation.redirectToMain();
		return null;
	}

	public String logout() {
		this.loggedUser = null;
		if(FacesContext.getCurrentInstance() != null)
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return navigation.redirectToLogin();
	}

	public UserLogin getLoggedUser() {
		return loggedUser;
	}

}
