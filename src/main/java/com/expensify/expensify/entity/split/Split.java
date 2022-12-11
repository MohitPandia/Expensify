package com.expensify.expensify.entity.split;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.expensify.expensify.entity.User;
import com.expensify.expensify.entity.expense.Expense;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Inheritance
@DiscriminatorColumn(name = "SplitType")
public abstract class Split implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private User user;
	private double amount;

	private boolean isSettleUP;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	@JsonIgnore
	private Expense expense;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date timestamp;

	@PrePersist
	private void onCreate() {
		timestamp = new Date();
	}

	public Split() {

	}

	public Split(User user) {
		this.user = user;
	}
}
