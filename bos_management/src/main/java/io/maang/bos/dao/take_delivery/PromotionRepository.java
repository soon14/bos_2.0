package io.maang.bos.dao.take_delivery;

import io.maang.bos.domain.take_delivery.Promotion;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface PromotionRepository extends JpaRepository<Promotion,Integer> {

    @Query("update Promotion set status='2' where endDate<? and status ='1'")
    @Modifying
    void updeStatus(Date date);
}
