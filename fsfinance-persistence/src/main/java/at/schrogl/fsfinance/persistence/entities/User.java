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
package at.schrogl.fsfinance.persistence.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import at.schrogl.fsfinance.persistence.daos.UserDao;

/**
 * This entity class represents an user.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
		@NamedQuery(name = UserDao.NQ_ListAll, query = "SELECT u FROM User u"),
		@NamedQuery(name = UserDao.NQ_ByUsername, query = "SELECT u FROM User u WHERE u.username = :username"),
		@NamedQuery(name = UserDao.NQ_ByEmail, query = "SELECT u FROM User u WHERE u.email = :email")
})
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	// Columns
	private Long id;
	private String username;
	private String password;
	private String salt;
	private String forename;
	private String surname;
	private String email;

	// Associations
	private Set<Account> accounts = new HashSet<Account>();
	private Set<Label> labels = new HashSet<Label>();

	// =================================================================
	// Helper methods
	// =================================================================

	@Override
	public String toString() {
		return id + "/" + username;
	}

	public void addAccount(Account account) {
		account.setUser(this);
		accounts.add(account);
	}

	public void addLabel(Label label) {
		label.setUser(this);
		labels.add(label);
	}

	// =================================================================
	// Getter + Setter
	// =================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Length(min = 3, max = 50)
	@Column(name = "USERNAME", length = 50, unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@NotNull
	@Column(name = "PASSWORD", length = 255, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotNull
	@Column(name = "SALT", length = 255, nullable = false, updatable = false)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Length(max = 255)
	@Column(name = "FORENAME", length = 255)
	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	@Length(max = 255)
	@Column(name = "SURNAME", length = 255)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotNull
	@Length(max = 255)
	@Email
	@Column(name = "EMAIL", length = 255, nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
	public Set<Label> getLabels() {
		return labels;
	}

	public void setLabels(Set<Label> labels) {
		this.labels = labels;
	}

}
