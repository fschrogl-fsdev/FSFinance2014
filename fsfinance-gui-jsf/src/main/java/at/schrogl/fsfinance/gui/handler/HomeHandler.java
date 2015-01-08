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
