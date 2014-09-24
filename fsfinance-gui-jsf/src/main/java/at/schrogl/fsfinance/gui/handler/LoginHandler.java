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

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.NotNull;

import at.schrogl.fsfinance.business.UserManagementBean;
import at.schrogl.fsfinance.persistence.entities.User;

@ManagedBean
@SessionScoped
public class LoginHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user = new User();
	private String passwordPlain;

	@ManagedProperty(value = "#{userManagementBean}")
	private UserManagementBean userManagement;

	// ==============================================================
	// Action Methods
	// ==============================================================

	public String doLogin() {
		System.out.println(userManagement);
		return "";
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@NotNull
	public String getPasswordPlain() {
		return passwordPlain;
	}
	
	public void setPasswordPlain(String passwordPlain) {
		this.passwordPlain = passwordPlain;
	}

	public UserManagementBean getUserManagement() {
		return userManagement;
	}

	public void setUserManagement(UserManagementBean userManagement) {
		this.userManagement = userManagement;
	}

}
