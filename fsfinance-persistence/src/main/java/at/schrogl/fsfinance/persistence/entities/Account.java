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
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import at.schrogl.fsfinance.persistence.daos.AccountDao;

/**
 * This entity class represents an account.
 * 
 * @author Fritz Schrogl
 * @since 0.1.0
 */
@Entity
@Table(name = "ACCOUNTS")
@NamedQueries({
		@NamedQuery(name = AccountDao.NQ_ListAll, query = "SELECT a FROM Account a"),
		@NamedQuery(name = AccountDao.NQ_ByName, query = "SELECT a FROM Account a WHERE a.name = :name")
})
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;

	// Columns
	private Long id;
	private String name;
	private String description;
	private String color;

	// Associations
	private User user;
	private Set<Transaction> transactions;

	// =================================================================
	// Helper Methods
	// =================================================================

	public void addTransaction(Transaction transaction) {
		transaction.setAccount(this);
		transactions.add(transaction);
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
	@Length(min = 1, max = 255)
	@Column(name = "NAME", length = 255, unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(max = 255)
	@Column(name = "DESCRIPTION", length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "COLOR", length = 7)
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_USER", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy("DATE")
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

}
