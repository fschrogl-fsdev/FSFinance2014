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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.schrogl.fsfinance.business.UserManagement;
import at.schrogl.fsfinance.persistence.entities.User;
import at.schrogl.fsfinance.persistence.enums.Authorities;

@ManagedBean
@SessionScoped
public class HomeHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeHandler.class);

	@ManagedProperty("#{userManagement}")
	private UserManagement userManagement;

	private User user;

	@PostConstruct
	public void initialize() {
		this.user = userManagement.getCurrentUser();
		LOGGER.debug("Creating session bean for {}", user);
	}

	@PreDestroy
	public void destruct() {
		LOGGER.debug("Destroying session bean for {}", user);
	}

	public String getUserDisplayName() {
		if (user.getForename() != null && user.getSurname() != null)
			return String.format("%s %s", user.getForename(), user.getSurname());
		else
			return user.getUsername();
	}

	public Boolean hasRole(String roleString) {
		Authorities authority = Authorities.valueOf(roleString);
		return user.getAuthorities().contains(authority);
	}

	// ==============================================================
	// Getter and Setter
	// ==============================================================

	public void setUserManagement(UserManagement userManagement) {
		this.userManagement = userManagement;
	}

}
