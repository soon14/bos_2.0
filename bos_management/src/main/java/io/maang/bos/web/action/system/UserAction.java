package io.maang.bos.web.action.system;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 描述:
 * 系统用户权限
 *
 * @outhor ming
 * @create 2018-04-24 16:39
 */
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class UserAction extends io.maang.bos.web.action.common.BaseAction<io.maang.bos.domain.system.User> {

    @Action(value = "user_login", results = {
            @Result(name = "login", type = "redirect", location = "/login.html"),
            @Result(name = "success", type = "redirect", location = "/index.html")})
    public String login() {
        //用户名和密码都保存再model中
        //基于shiro实现登陆
        org.apache.shiro.subject.Subject subject = org.apache.shiro.SecurityUtils.getSubject();

        //用户名和密码信息
        org.apache.shiro.authc.AuthenticationToken token = new org.apache.shiro.authc.UsernamePasswordToken(model.getUsername(), model.getPassword());

        try {
            subject.login(token);
            //用户登陆成功将用户信息保存到session中
            return SUCCESS;
        } catch (org.apache.shiro.authc.AuthenticationException e) {
            //"登陆失败"
            e.printStackTrace();
            return LOGIN;
        }
    }

    @Action(value = "user_logout", results = {
            @Result(name = "success", type = "redirect", location = "/login.html")})
    public String logout() {

        //基于shiro完成退出
        org.apache.shiro.subject.Subject subject = org.apache.shiro.SecurityUtils.getSubject();
        subject.logout();
        return SUCCESS;
    }


}
