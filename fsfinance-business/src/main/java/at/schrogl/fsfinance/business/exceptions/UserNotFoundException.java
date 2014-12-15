package at.schrogl.fsfinance.business.exceptions;

import at.schrogl.fsfinance.persistence.entities.User;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private final User unknownUser;

	public UserNotFoundException(final User unknownUser) {
		this.unknownUser = unknownUser;
	}

	public User getUnknownUser() {
		return unknownUser;
	}

	@Override
	public String getMessage() {
		return String.format("User %s not found!", unknownUser);
	}
}
