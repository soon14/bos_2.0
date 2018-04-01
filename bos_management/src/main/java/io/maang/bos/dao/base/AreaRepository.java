package io.maang.bos.dao.base;

import io.maang.bos.domain.base.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AreaRepository extends JpaRepository<Area,String>,JpaSpecificationExecutor<Area> {
}
