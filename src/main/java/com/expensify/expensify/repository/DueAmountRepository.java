package com.expensify.expensify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensify.expensify.entity.DueAmount;
import com.expensify.expensify.entity.DueAmountPK;
import com.expensify.expensify.entity.User;

@Repository
public interface DueAmountRepository extends JpaRepository<DueAmount, DueAmountPK> {

	List<DueAmount> findBydueA1mountPK_UserFrom(User user);

}
