package com.expensify.expensify.entity.split;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.expensify.expensify.entity.User;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("SettleUPSplit")
public class SettleUPSplit extends Split {

	public SettleUPSplit() {
		// TODO Auto-generated constructor stub
	}

	public SettleUPSplit(User user, double amount) {
		super(user);
		this.setAmount(amount);
	}

}
