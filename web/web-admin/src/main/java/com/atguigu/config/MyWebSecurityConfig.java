package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author 刘翰林
 * @create 2023-06-18 15:01
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启权限控制标签,启用全局方法安全性
@Configuration  //声明当前是一个配置类
@EnableWebSecurity  //开启SpringSecurity的自动配置,会给我们生成一个登录页面
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    //再内存中设置一个认证的用户名和密码
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//
//        //设置用户名和密码，其中BCryptPasswordEncoder().encode("123456")是设置加密器和加密方式
//        auth.inMemoryAuthentication().withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("123456"))
//                .roles();
//    }

    //创建一个密码加密器放到ioc容器中
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        //必须调用父类的方法，否则就不需要认证即可访问，除非当前方法配置了认证
//        super.configure(http);
        //允许iframe嵌套显示
        http.headers().frameOptions().sameOrigin();
        //允许iframe显示

        //配置可以匿名访问的请求，也就是配置访问以下资源可以不用进行认证，否则其他的都需要认证
        http.authorizeRequests()    //授权请求
                .mvcMatchers("/static/**","/login").permitAll() //以下路径的资源不用认证
                .anyRequest().authenticated(); // 其它页面全部需要验证

        //自定义登录页面
        http.formLogin().loginPage("/login")     //配置去登录页面的路径
                .defaultSuccessUrl("/");    //配置登录认证成功去的地址

        http.logout().logoutUrl("/logout")   //退出登陆的路径，指定spring security拦截的注销url,退出功能是security提供的
                .logoutSuccessUrl("/login");//用户退出后要被重定向的url

        //关闭跨域请求伪造
        http.csrf().disable();
        //添加自定义异常入口，其实也就是当没有权限时，用这个new的CustomAccessDeineHandler对象处理，
//        这个对象是自己定义的无权限的处理器
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeineHandler());
    }
}
