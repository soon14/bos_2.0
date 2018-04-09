package io.maang.bos.dao.base;

import io.maang.bos.domain.base.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StandardRepository extends JpaRepository<Standard,Integer> {

    @Query(value = "from Standard")
    List<Standard> findall();
}
