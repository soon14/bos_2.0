package io.maang.bos.service.system;

import io.maang.bos.domain.system.Permission;
import io.maang.bos.domain.system.User;

import java.util.List;

public interface PermissionService {

    List<Permission> findByUser(User user);
}
