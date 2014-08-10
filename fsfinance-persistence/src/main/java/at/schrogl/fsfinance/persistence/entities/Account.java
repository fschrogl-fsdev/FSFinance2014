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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This entity class represents an account.
 * 
 * @author Fritz Schrogl
 * @since 0.0.1
 */
@Entity
@Table(name = "ACCOUNTS")
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

	@Column(name = "NAME", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	@OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

}
