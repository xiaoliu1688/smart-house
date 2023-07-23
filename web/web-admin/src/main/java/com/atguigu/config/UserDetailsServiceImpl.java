package com.atguigu.config;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Admin;
import com.atguigu.service.AdminService;
import com.atguigu.service.PermissionService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.ui.context.ThemeSource;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 刘翰林
 * @create 2023-06-19 12:02
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Reference
    AdminService adminService;

    @Reference
    PermissionService permissionService;

    /**
     * Spring Security支持通过实现UserDetailsService接口的方式来提供用户认证授权信息
     * @date
     * @author
     * @return
     * @throws
     */

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //通过用户名查找用户信息
        Admin admin = adminService.findAdminByName(userName);

        System.out.println(admin);
        if(admin == null){
            throw new UsernameNotFoundException("用户不存在");
        }

        //查询该用户所有的权限
        List<String> codes = permissionService.findPermissionCodeByAdminId(admin.getId());

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        //通过权限code获取创建权限对象
        for (String code : codes) {
            if(!StringUtils.isEmpty(code)){

                //创建grantedAuthority对象
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(code);
                //将对象放入权限集合
                grantedAuthorities.add(simpleGrantedAuthority);
            }
        }

        //给用户授权，个人盲猜是这里查出来的用户密码跟输入的去比对并进行授权
        return new User(userName,admin.getPassword(),grantedAuthorities);
    }
}
