package com.expensify.expensify.entity.expense;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.expensify.expensify.entity.ExpenseStatus;
import com.expensify.expensify.entity.ExpenseType;
import com.expensify.expensify.entity.Group;
import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.split.Split;

import lombok.Data;

@Data
@Entity
@Table(name = "expense")
@Inheritance
@DiscriminatorColumn(name = "expenseTypeClass")
public abstract class Expense implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String expenseName;
	private double amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private User expensePaidBy;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "expense")
	private List<Split> splits;

	@Enumerated(EnumType.STRING)
	private ExpenseType expenseType;

	@Enumerated(EnumType.STRING)
	private ExpenseStatus expenseStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Group expGroup;
	@Embedded
	private ExpenseData expenseData;

	public Expense(double amount, User expensePaidBy, List<Split> splits, ExpenseData expenseData) {
		this.amount = amount;
		this.expensePaidBy = expensePaidBy;
		this.splits = splits;
		this.expenseData = expenseData;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date timestamp;

	@PrePersist
	private void onCreate() {
		timestamp = new Date();
	}

	public Expense() {
	}

	public abstract boolean validate();
}
