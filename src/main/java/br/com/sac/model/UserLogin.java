package br.com.sac.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries(value = { @NamedQuery(name = "UserLogin.findByUser",
  query = "SELECT c FROM UserLogin c "
  + "WHERE c.password = :senha AND c.login = :login")})
@Table(name = "usuario_login", schema="sac")
public class UserLogin {
	
	@Id
	@Column(name="login")
	private String login;
	@Column(name="senha")
	private String password;
	@Column(name="admin")
	private boolean isAdmin;
	
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
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
	
	@Override
    public int hashCode() {
              final int prime = 31;
              int result = 1;
              result = prime * result + ((login == null) ? 0 :
              login.hashCode());
              return result;
    }

    @Override
    public boolean equals(Object obj) {
              if (this == obj)
                       return true;
              if (obj == null)
                       return false;
              if (getClass() != obj.getClass())
                       return false;
              
              return (obj instanceof UserLogin) ? 
                (this.getLogin() == null ? this == obj :
                this.getLogin().equals((
                 (UserLogin)obj).getLogin())):false;
    }        
	
}
