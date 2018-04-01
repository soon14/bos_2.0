package io.maang.bos.service.base;

import io.maang.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {
    void saveBatch(List<Area> areas);

    Page<Area> findPageData(Specification<Area> specification, Pageable pageable);
}
