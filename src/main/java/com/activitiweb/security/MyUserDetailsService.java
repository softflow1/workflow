package com.activitiweb.security;


import com.activitiweb.mapper.UserInfoBeanMapper;
import com.activitiweb.pojo.UserInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserInfoBeanMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//-------------------------读取数据库判断登录-----------------------
/*        SysUser sysUser = sysUserService.queryByUsername(username);

        if (Objects.nonNull(sysUser)) {
            return User.withUsername(username).password(sysUser.getEncodePassword())
                    .authorities(AuthorityUtils.NO_AUTHORITIES)
                    .build();
        }
        throw new UsernameNotFoundException("username: " + username + " notfound");*/

        //-------------------根据自定义用户属性登录-----------------------------

        UserInfoBean userInfoBean = mapper.selectByUsername(username);
        if (userInfoBean == null) {
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        return userInfoBean;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}