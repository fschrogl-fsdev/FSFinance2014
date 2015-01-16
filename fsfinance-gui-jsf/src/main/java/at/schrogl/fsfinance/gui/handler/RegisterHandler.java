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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.schrogl.fsfinance.business.UserManagement;
import at.schrogl.fsfinance.business.exceptions.UserAlreadyExistsException;
import at.schrogl.fsfinance.gui.ApplicationConfig;
import at.schrogl.fsfinance.gui.PageUrl;
import at.schrogl.fsfinance.persistence.entities.User;

@ManagedBean
@RequestScoped
public class RegisterHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterHandler.class);

	private static final String htmlIdUsername = "input-username";
	private static final String htmlIdPasswordRepeated = "input-passwordRepeat";
	private static final String htmlIdEmail = "input-email";

	private User user = new User();
	private String rawPassword;
	private String rawPasswordRepeated;

	@ManagedProperty("#{applicationConfig}")
	private ApplicationConfig applicationConfig;

	@ManagedProperty("#{userManagement}")
	private UserManagement userManagement;

	// ==============================================================
	// Action Methods
	// ==============================================================

	public String doRegister() {
		LOGGER.info("{} :: Trying to register new user", user);

		// Check if username only contains whitelisted chars
		String whitelistChars = applicationConfig.getProperty("users.allowedChars");
		if (whitelistChars != null) {
			LOGGER.debug("{} :: Checking username against whitelisted chars.", user);
			for (char c : user.getUsername().toCharArray()) {
				if (whitelistChars.indexOf(c) < 0) {
					LOGGER.debug("{} :: Invalid char '{}' in username", user, c);
					LOGGER.info("{} :: Username contains invalid char(s)! Aborting registration.", user);
					String errText = applicationConfig.getBundleMessage("msg_err_usernameBadChar", c);
					FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errText, errText);
					FacesContext.getCurrentInstance().addMessage(htmlIdUsername, errorMsg);
					return null;
				}
			}
		}

		// Verify if passwords match
		if (!rawPassword.equals(rawPasswordRepeated)) {
			LOGGER.debug("{} :: Passwords don't match: pwd={}, pwdRepeated={}", user, rawPassword, rawPasswordRepeated);
			LOGGER.info("{} :: Registration canceled! User not persisted to database.", user);
			String errText = applicationConfig.getBundleMessage("msg_err_passwordsNoMatch");
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errText, errText);
			FacesContext.getCurrentInstance().addMessage(htmlIdPasswordRepeated, errorMsg);
			return null;
		}

		// Register new user
		User registeredUser = null;
		try {
			user.setForename(convertEmptyStringToNull(user.getForename()));
			user.setSurname(convertEmptyStringToNull(user.getSurname()));
			user.setEmail(user.getEmail().trim().toLowerCase());
			registeredUser = userManagement.register(user, rawPassword);
			LOGGER.info("{} :: Registration successful! User persisted to database.", registeredUser);
		} catch (UserAlreadyExistsException uae_ex) {
			LOGGER.info("{} :: User already exists! Aborting registration.", user);
			if (uae_ex.getExistingUser().getUsername().equalsIgnoreCase(user.getUsername())) {
				String componentLabel = applicationConfig.getBundleMessage("label_username");
				String errText = applicationConfig.getBundleMessage("msg_err_userAlreadyExists", componentLabel,
						uae_ex.getOffendingProperty());
				FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errText, errText);
				FacesContext.getCurrentInstance().addMessage(htmlIdUsername, errorMsg);
			}
			if (uae_ex.getExistingUser().getEmail().equalsIgnoreCase(user.getEmail())) {
				String componentLabel = applicationConfig.getBundleMessage("label_usermail");
				String errText = applicationConfig.getBundleMessage("msg_err_userAlreadyExists", componentLabel,
						uae_ex.getOffendingProperty());
				FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errText, errText);
				FacesContext.getCurrentInstance().addMessage(htmlIdEmail, errorMsg);
			}
			return null;
		}

		// Create session and redirect newly created user
		LOGGER.debug("{} :: Logging in and redirecting user.", registeredUser);
		User loggedInUser = userManagement.loginUser(registeredUser);
		return (loggedInUser != null) ? PageUrl.HOME : PageUrl.LOGIN;
	}

	// ==============================================================
	// Helper Methods
	// ==============================================================

	private String convertEmptyStringToNull(String value) {
		value = (value != null) ? value.trim() : null;
		return (value != null && value.length() > 0) ? value : null;
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
	public String getRawPassword() {
		return rawPassword;
	}

	public void setRawPassword(String rawPassword) {
		this.rawPassword = rawPassword;
	}

	public String getRawPasswordRepeated() {
		return rawPasswordRepeated;
	}

	public void setRawPasswordRepeated(String rawPasswordRepeated) {
		this.rawPasswordRepeated = rawPasswordRepeated;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public void setUserManagement(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

}
