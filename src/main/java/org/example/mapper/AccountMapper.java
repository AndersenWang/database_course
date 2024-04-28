package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.dto.Account;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    @Select("select * from user where username = #{username}")
    Account getAccountByUsername(String username);
}
