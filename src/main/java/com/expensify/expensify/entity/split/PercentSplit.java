package com.expensify.expensify.entity.split;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.expensify.expensify.entity.User;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue("PERCENT")
public class PercentSplit extends Split {

	private double percent;

	public PercentSplit() {

	}

	public PercentSplit(User user, double percent) {
		super(user);
		this.percent = percent;
	}

	public double getPercent() {
		return percent;
	}

	public void serPercent(double percent) {
		this.percent = percent;
	}
}
