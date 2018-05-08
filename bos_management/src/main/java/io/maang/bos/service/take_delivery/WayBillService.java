package io.maang.bos.service.take_delivery;

import io.maang.bos.domain.take_delivery.WayBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WayBillService {
    void save(WayBill wayBill);

    Page<WayBill> findPageData(Pageable pageable);

    WayBill findByWayBillNum(String wayBillNum);

    Page<WayBill> findPageData(WayBill model, Pageable pageable);
}
