package com.expensify.expensify.repository.Split;

import com.expensify.expensify.entity.split.EqualSplit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EqualSplitRepository extends JpaRepository<EqualSplit,Long> {
}
