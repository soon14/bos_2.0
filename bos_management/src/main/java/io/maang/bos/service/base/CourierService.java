package io.maang.bos.service.base;

import io.maang.bos.domain.base.Courier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CourierService {
    void save(Courier courier);

   Page<Courier> findPageDate(Specification<Courier> specification, Pageable pageable);

    void delBatch(String[] idArray);

    void restoreBatch(String[] idArray);
}
