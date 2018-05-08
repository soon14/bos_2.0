package io.maang.bos.dao.system;

import io.maang.bos.domain.system.Role;
import io.maang.bos.domain.system.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query("from Role r inner join fetch r.users u where u.id = ?")
    List<Role> findByUser(Integer id);
}
