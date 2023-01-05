package com.expensify.expensify.repository.Expense;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.expensify.expensify.entity.expense.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	@Query("select DISTINCT  e from Expense e join e.splits s where e.id = s.expense.id and e.expenseStatus <> 'DELETED' and  e.expensePaidBy.id = ?1 or s.user.id = ?1 ")
	List<Expense> findExpenseByUserIdAndSort(Long userid, Sort sort);
}
