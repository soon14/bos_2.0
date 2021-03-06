package io.maang.crm.dao;

import io.maang.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);

    @Query("update Customer set fixedAreaId = ? where id = ?")
    @Modifying
    void updateFixedId(String fixedAreaId, Integer id);

    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    void clearFixedAreaId(String fixedAreaId);

    Customer findByTelephone(String telephone);

    @Query("update Customer set type=1 where telephone=?")
    @Modifying
    void updateType(String telephone);

    Customer findByTelephoneAndPassword(String telephone, String password);

    @Query("select fixedAreaId from Customer where address=?")
    String findFixedAreaIdByAddress(String address);
}
