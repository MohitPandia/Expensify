package com.expensify.expensify.repository.Split;

import com.expensify.expensify.entity.split.Split;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitRepository extends JpaRepository<Split,Long> {
}
