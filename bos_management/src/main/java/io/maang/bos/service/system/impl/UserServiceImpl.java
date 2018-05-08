package io.maang.bos.service.system.impl;

import io.maang.bos.dao.system.UserRepository;
import io.maang.bos.domain.system.User;
import io.maang.bos.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-24 17:27
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
