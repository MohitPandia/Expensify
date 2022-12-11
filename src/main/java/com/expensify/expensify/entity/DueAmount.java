package com.expensify.expensify.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DueAmount {

	@EmbeddedId
	private DueAmountPK dueA1mountPK;
	private double amount;

}
