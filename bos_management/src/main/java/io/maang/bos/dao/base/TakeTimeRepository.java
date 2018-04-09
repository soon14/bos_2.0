package io.maang.bos.dao.base;

import io.maang.bos.domain.base.TakeTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TakeTimeRepository extends JpaRepository<TakeTime,Integer> {
}
