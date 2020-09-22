package com.bgtech.oauthserver.config;

import com.bgtech.oauthserver.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author HuangJiefeng
 * @date 2020/9/16 0016 16:28
 *
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyUserDetailsService myUserDetailsService;

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 允许所有用户访问
                .antMatchers("/oauth/**","/login/**", "/logout").permitAll()
                // 其他地址的访问均需验证权限
                .anyRequest().authenticated()
                .and()
                .formLogin()
                // 登录页面
//                .loginPage("/login.html")
                .and()
                // 退登录页面
                .logout().logoutSuccessUrl("/");
    }*/

/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 允许所有用户访问
                .antMatchers("/oauth/**","/login/**", "/logout").permitAll()
                // 其他地址的访问均需验证权限
                .anyRequest().authenticated()
                *//*绕过CSRF防御，方便测试*//*
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/plugins/**", "/favicon.ico");
    }

    /**
     * 使用自定义的认证，可以在数据库中找
     *   问题：出现没有ADMIN角色的用户也访问到了ADMIN资源，换成内存中存放
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 内存中存放用户名密码角色信息
     */
/*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("zhangsan")
                .password("$2a$10$3nVkoLTDPUVLBHybUgsUtenrxDboDHgWtqQzKeLZjhjL1dE5sqmxy")
                .roles("USER")
                .and()
                .withUser("lisi")
                .password("$2a$10$3nVkoLTDPUVLBHybUgsUtenrxDboDHgWtqQzKeLZjhjL1dE5sqmxy")
                .roles("USER", "ADMIN")
                .and()
                .withUser("wangwu")
                .password("$2a$10$3nVkoLTDPUVLBHybUgsUtenrxDboDHgWtqQzKeLZjhjL1dE5sqmxy")
                .roles("NO");
    }*/

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
