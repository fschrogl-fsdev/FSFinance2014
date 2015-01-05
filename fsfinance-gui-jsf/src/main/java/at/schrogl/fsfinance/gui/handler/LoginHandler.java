/**
 * This file is part of FSFinance.
 *
 * FSFinance is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FSFinance is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with FSFinance. If not, see <http://www.gnu.org/licenses/>.
 */
package at.schrogl.fsfinance.gui.handler;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import at.schrogl.fsfinance.gui.ApplicationConfig;

@ManagedBean
@ViewScoped
public class LoginHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private Boolean rememberMe;
	
	@ManagedProperty("#{applicationConfig}")
	private ApplicationConfig appConfig;

	// ==============================================================
	// Action Methods
	// ==============================================================

	/**
	 * This method is invoked after successful input validation. Since Spring Security
	 * is used for authentication and authorization this method's only purpose is to
	 * forward the user's credentials to a Spring Security Servlet.
	 * 
	 * @param event Event that triggered the method's invocation
	 */
	public void doLogin(ActionEvent event) throws IOException, ServletException {
		/*
		 * We don't want the request parameters (username, password, etc.) to be lost,
		 * hence we dispatch/forward the request ourself and don't send a redirect
		 * back to the client.
		 * 
		 * Info: For this to work Spring Security's Filter Chain must be configured
		 * to also accept FORWARD requests (besides default REQUEST requests for logout).
		 */
		String dispatchUrl = appConfig.getProperty("security.loginProcessUrl");
		FacesContext.getCurrentInstance().getExternalContext().dispatch(dispatchUrl);
		FacesContext.getCurrentInstance().responseComplete();		
	}
	
	/**
	 * Checks the HTTP request for failure parameters from Spring Security, to determine if
	 * a previous login attempt failed.
	 * 
	 * @return <code>true</code> if a failure parameters is found, otherwise <code>false</code>
	 */
	public boolean isLoginFailure() {
		String urlParameter = appConfig.getProperty("security.loginFailureParm");
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.containsKey(urlParameter);
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	@NotNull
	@Length(min = 3, max = 50)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotNull
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getRememberMe() {
		return rememberMe;
	}
	
	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	public void setAppConfig(ApplicationConfig appConfig) {
		this.appConfig = appConfig;
	}

}
