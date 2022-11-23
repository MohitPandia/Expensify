package com.expensify.expensify.repository.Expense;

import com.expensify.expensify.entity.expense.EqualExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EqualRepository extends JpaRepository<EqualExpense,Long> {
}
