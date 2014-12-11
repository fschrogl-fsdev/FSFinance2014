package at.schrogl.fsfinance.business.exceptions;

public class UserAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String username;

	public UserAlreadyExistsException(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String toString() {
		return String.format("A user with username '%s' already exists!", username);
	}
}
