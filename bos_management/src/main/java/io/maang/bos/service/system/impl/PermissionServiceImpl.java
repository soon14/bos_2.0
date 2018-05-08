package io.maang.bos.service.system.impl;

import io.maang.bos.dao.system.PermissionRepository;
import io.maang.bos.domain.system.Permission;
import io.maang.bos.domain.system.User;
import io.maang.bos.service.system.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 描述:
 *
 * @outhor ming
 * @create 2018-04-25 19:56
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<Permission> findByUser(User user) {
        if (user.getUsername().equals("admin")) {
            //返回所有的权限
            return permissionRepository.findAll();
        } else {
            //根据用户查
            return permissionRepository.findByUser(user.getId());
        }

    }
}
