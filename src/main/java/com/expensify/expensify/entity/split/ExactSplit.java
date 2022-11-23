package com.expensify.expensify.entity.split;

import com.expensify.expensify.entity.User;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("EXACT")
public class ExactSplit extends Split {

    public ExactSplit() {
    }

    public ExactSplit(User user, double amount){
        super(user);
        this.setAmount(amount);
    }
}
