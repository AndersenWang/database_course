package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.entity.dto.Account;
import org.example.mapper.AccountMapper;
import org.example.service.AccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Resource
    AccountMapper userMapper;
    @Override
    public Account getAccountByUsername(String username) {
        return userMapper.getAccountByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = this.query().eq("username", username).one();
        if (user == null)
            throw new UsernameNotFoundException("用户不存在或密码错误");
        return org.springframework.security.core.userdetails.User.withUsername(username)
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
