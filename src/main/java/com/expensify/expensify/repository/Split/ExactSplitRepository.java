package com.expensify.expensify.repository.Split;

import com.expensify.expensify.entity.split.ExactSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExactSplitRepository extends JpaRepository<ExactSplit,Long> {
}
