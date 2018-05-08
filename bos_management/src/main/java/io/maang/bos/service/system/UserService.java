package io.maang.bos.service.system;

import io.maang.bos.domain.system.User;

public interface UserService {
    User findByUsername(String username);
}
