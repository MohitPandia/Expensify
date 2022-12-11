package com.expensify.expensify.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DueAmountPK implements Serializable {

	@ManyToOne
	@JoinColumn
	private User userFrom;
	@ManyToOne
	@JoinColumn
	private User userTo;

	@Override
	public String toString() {
		return "DueAmountPK [userFrom=" + userFrom.getUsername() + ", userTo=" + userTo.getUsername() + "]";
	}

}
