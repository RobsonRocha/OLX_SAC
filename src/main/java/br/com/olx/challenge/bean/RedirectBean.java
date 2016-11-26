package br.com.olx.challenge.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RedirectBean implements Serializable {
 
    private static final long serialVersionUID = 1520318172495977648L;
 
    public String redirectToLogin() {
        return "/login.xhtml?faces-redirect=true";
    }
     
    public String redirectToMain() {
    	return "/restricted/main.xhtml?faces-redirect=true";
    }      
     
}
