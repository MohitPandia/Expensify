package com.expensify.expensify.repository.Split;

import com.expensify.expensify.entity.split.PercentSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PercentageSplitRepository extends JpaRepository<PercentSplit,Long> {
}
