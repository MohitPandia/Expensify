package com.expensify.expensify.repository.Expense;

import com.expensify.expensify.entity.expense.PercentageExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PercentageRepository extends JpaRepository<PercentageExpense,Long> {
}
