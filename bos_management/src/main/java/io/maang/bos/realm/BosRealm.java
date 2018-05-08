package io.maang.bos.realm;

import io.maang.bos.domain.system.Permission;
import io.maang.bos.domain.system.Role;
import io.maang.bos.domain.system.User;
import io.maang.bos.service.system.PermissionService;
import io.maang.bos.service.system.RoleService;
import io.maang.bos.service.system.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述:
 * 自定义realm  实现安全数据的连接
 *
 * @outhor ming
 * @create 2018-04-24 17:14
 */
@Component
public class BosRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    //授权管理
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("shiro 授权管理");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //根据当前登陆用户查询对应角色和权限
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //调用业务层查询角色
        List<Role> roles = roleService.findByUser(user);
        for (Role role : roles) {
            simpleAuthorizationInfo.addRole(role.getKeyword());
        }

        //调用业务层查询权限
        List<Permission> permissions = permissionService.findByUser(user);
        for (Permission permission : permissions) {
            simpleAuthorizationInfo.addStringPermission(permission.getKeyword());
        }

        return simpleAuthorizationInfo;
    }

    @Override
    //认证管理
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("shiro 认证管理");
        //转换token
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByUsername(usernamePasswordToken.getUsername());
        if (user == null) {
            //用户名不存在 当认证方法放回null抛出 org.apache.shiro.authc.UnknownAccountException

            return null;
        } else {
            //用户名存在
            //当返回用户密码时,securityManager安全管理器,自动比较返回密码和输入密码是否一致
            //如果一致登陆成功,不一致,抛异常 org.apache.shiro.authc.IncorrectCredentialsException
            //参数一:期望登陆后,subject中保存的信息
            //参数二:密码
            //参数三:realm名称
            return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        }

    }
}
