package io.maang.bos.dao.take_delivery;

import io.maang.bos.domain.take_delivery.WayBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WayBillRepository extends JpaRepository<WayBill,Integer> {

    WayBill findByWayBillNum(String wayBillNum);
}
