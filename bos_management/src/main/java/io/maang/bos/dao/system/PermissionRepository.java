package io.maang.bos.dao.system;

import io.maang.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Integer> {

    @Query("from Permission p inner join fetch p.roles r inner join fetch r.users u where u.id=?")
    List<Permission> findByUser(Integer id);
}
