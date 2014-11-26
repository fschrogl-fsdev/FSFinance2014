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
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import at.schrogl.fsfinance.business.UserManagementBean;
import at.schrogl.fsfinance.persistence.entities.User;

@ManagedBean
@ViewScoped
public class RegisterHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private User user = new User();
	private String passwordPlain;
	private String passwordPlainRepeated;

	@ManagedProperty("#{userManagement}")
	private UserManagementBean userManagement;

	// ==============================================================
	// Action Methods
	// ==============================================================

	public void doRegister(ActionEvent event) {
		System.out.println(event.getComponent().getClientId());
		System.out.println(user);
		System.out.println(passwordPlain + "//" + passwordPlainRepeated);
		System.out.println("userMgmtBean: " + userManagement);
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
	@Length(min = 8, max = 24)
	public String getPasswordPlain() {
		return passwordPlain;
	}
	
	public void setPasswordPlain(String passwordPlain) {
		this.passwordPlain = passwordPlain;
	}

	public String getPasswordPlainRepeated() {
		return passwordPlainRepeated;
	}
	
	public void setPasswordPlainRepeated(String passwordPlainRepeated) {
		this.passwordPlainRepeated = passwordPlainRepeated;
	}

	public UserManagementBean getUserManagement() {
		return userManagement;
	}

	public void setUserManagement(UserManagementBean userManagement) {
		this.userManagement = userManagement;
	}

}
