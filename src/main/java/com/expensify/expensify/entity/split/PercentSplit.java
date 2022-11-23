package com.expensify.expensify.entity.split;

import com.expensify.expensify.entity.User;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("PERCENT")
public class PercentSplit extends Split {

    public PercentSplit() {
    }

    private double percent;

    public PercentSplit(User user, double percent){
        super(user);
        this.percent=percent;
    }

    public double getPercent(){
        return percent;
    }

    public void serPercent(double percent){
        this.percent = percent;
    }
}
