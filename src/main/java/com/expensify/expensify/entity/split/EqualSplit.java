package com.expensify.expensify.entity.split;

import com.expensify.expensify.entity.User;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue("EQUAL")
public class EqualSplit extends Split {

    public EqualSplit() {
    }

    public EqualSplit(User user){
        super(user);
    }
}
