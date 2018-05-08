package io.maang.bos.dao.take_delivery;

import io.maang.bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Order findByOrderNum(String orderNum);
}
