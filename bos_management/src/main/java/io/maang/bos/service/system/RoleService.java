package io.maang.bos.service.system;

import io.maang.bos.domain.system.Role;
import io.maang.bos.domain.system.User;

import java.util.List;

public interface RoleService {

    List<Role> findByUser(User user);
}
