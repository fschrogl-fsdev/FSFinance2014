package at.schrogl.fsfinance.persistence.daos;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import at.schrogl.fsfinance.persistence.entities.User;

public interface IUserDao extends JpaRepository<User, Long> {

	public User findByUsername(String username);

	public User findByEmail(String email);

	public List<User> findByForenameOrSurname(String forename, String surname);
	
	public List<User> findByForenameOrSurnameAllIgnoreCase(String forename, String surname);
	
	public Page<User> findByForenameOrSurname(String forename, String surname, Pageable pageable);
	
	public List<User> findByForenameAndSurname(String forename, String surname);
	
	public List<User> findByForenameAndSurnameAllIgnoreCase(String forename, String surname);
	
}
