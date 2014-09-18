package at.schrogl.fsfinance.webapp.gui.handler;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class LoginHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

	// ==============================================================
	// Action Methods
	// ==============================================================

	public void doLogin(ActionEvent event) {
		System.out.println(event.getComponent().getClientId());
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
