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
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

import at.schrogl.fsfinance.business.UserManagementBean;

@ManagedBean
@ViewScoped
public class LoginHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	@ManagedProperty(value = "#{userManagementBean}")
	private UserManagementBean userManagement;

	// ==============================================================
	// Action Methods
	// ==============================================================

	public void doLogin(ActionEvent event) {
		System.out.println(event.getComponent().getClientId());
		System.out.println(userManagement);
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

	public UserManagementBean getUserManagement() {
		return userManagement;
	}

	public void setUserManagement(UserManagementBean userManagement) {
		this.userManagement = userManagement;
	}

}
