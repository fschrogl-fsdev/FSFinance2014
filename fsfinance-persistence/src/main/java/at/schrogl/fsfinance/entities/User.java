/* This file is part of FSFinance.
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
 * along with FSFinance. If not, see <http://www.gnu.org/licenses/>. */
package at.schrogl.fsfinance.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This entity represents an user.
 * 
 * @author Fritz Schrogl
 */
@Entity
@Table(name = "Users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;
	private String salt;
	private String forename;
	private String surname;
	private String email;

	// =================================================================
	// Getter + Setter
	// =================================================================

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length = 50, unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(length = 255, nullable = false)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 255)
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(length = 255)
	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	@Column(length = 255)
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(length = 255)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
