package io.maang.bos.service.system.impl;

import io.maang.bos.dao.system.RoleRepository;
import io.maang.bos.domain.system.Role;
import io.maang.bos.domain.system.User;
import io.maang.bos.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-25 19:38
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findByUser(User user) {

        //基于用户查询角色
        //admin具有的所有的角色
        if (user.getUsername().equals("admin")) {
            return roleRepository.findAll();
        } else {
            return roleRepository.findByUser(user.getId());

        }
    }
}
