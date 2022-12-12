package com.expensify.expensify.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.expensify.expensify.entity.expense.Expense;

import lombok.Data;

@Entity
@Data
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private User user;
	private String message;
	private double amount;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Expense expense;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date timestamp;

	@PrePersist
	private void onCreate() {
		timestamp = new Date();
	}
}
