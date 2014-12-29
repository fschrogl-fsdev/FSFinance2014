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
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.schrogl.fsfinance.business.UserManagement;
import at.schrogl.fsfinance.business.exceptions.UserAlreadyExistsException;
import at.schrogl.fsfinance.gui.PageUrl;
import at.schrogl.fsfinance.persistence.entities.User;

@ManagedBean
@ViewScoped
public class RegisterHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterHandler.class);

	private User user = new User();
	private String rawPassword;
	private HtmlInputSecret rawPasswordRepeated;

	@ManagedProperty("#{userManagement}")
	private UserManagement userManagement;

	// ==============================================================
	// Action Methods
	// ==============================================================

	public String doRegister() {
		// Verify if passwords match
		if (!rawPassword.equals(rawPasswordRepeated.getValue())) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Passwords don't match: User={}, pwd={}, pwdRepeated={}", user.getUsername(), rawPassword,
						rawPasswordRepeated.getValue());
			}
			String errText = getBundleMessage("msg_err_passwordsNoMatch");
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errText, errText);
			FacesContext.getCurrentInstance().addMessage(rawPasswordRepeated.getId(), errorMsg);
			return null;
		}

		// Register new user
		User registeredUser = null;
		try {
			user.setForename(convertEmptyStringToNull(user.getForename()));
			user.setSurname(convertEmptyStringToNull(user.getSurname()));
			registeredUser = userManagement.register(user, rawPassword);
			LOGGER.debug("Created user {}", registeredUser);
		} catch (UserAlreadyExistsException uae_ex) {
			String errText = getBundleMessage("msg_err_userAlreadyExists", uae_ex.getOffendingProperty());
			FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, errText, errText);
			FacesContext.getCurrentInstance().addMessage(null, errorMsg);
			return null;
		}

		// Create session and redirect newly created user
		User loggedInUser = userManagement.loginUser(registeredUser);
		return (loggedInUser != null) ? PageUrl.HOME : PageUrl.LOGIN;
	}

	// ==============================================================
	// Helper Methods
	// ==============================================================

	private String getBundleMessage(String msgCode, Object...arguments) {
		FacesContext facesCtx = FacesContext.getCurrentInstance();
		ResourceBundle rb = facesCtx.getApplication().getResourceBundle(facesCtx, PageUrl.msgBundle);
		return MessageFormat.format(rb.getString(msgCode), arguments);
	}
	
	private String convertEmptyStringToNull(String value) {
		return (value != null && value.trim().length() == 0) ? null: value;
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

	public HtmlInputSecret getRawPasswordRepeated() {
		return rawPasswordRepeated;
	}

	public void setRawPasswordRepeated(HtmlInputSecret rawPasswordRepeated) {
		this.rawPasswordRepeated = rawPasswordRepeated;
	}

	public void setUserManagement(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

}
