package io.maang.bos.dao.take_delivery;

import io.maang.bos.domain.take_delivery.WorkBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkBillRepository extends JpaRepository<WorkBill,Integer> {
}
