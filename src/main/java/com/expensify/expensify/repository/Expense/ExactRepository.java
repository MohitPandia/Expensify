package com.expensify.expensify.repository.Expense;

import com.expensify.expensify.entity.expense.ExactExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExactRepository extends JpaRepository<ExactExpense,Long> {
}
