package io.maang.bos.index;

import io.maang.bos.domain.take_delivery.WayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WayBillIndexRepository extends ElasticsearchRepository<WayBill,Integer> {
}
