package org.example.controller;

import jakarta.annotation.Resource;
import org.example.entity.RestBean;
import org.example.entity.dto.Account;
import org.example.entity.vo.response.AccountVO;
import org.example.service.AccountService;
import org.example.utils.ConstUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AccountController {
    @Resource
    AccountService accountService;
    @GetMapping("/info")
    public RestBean<AccountVO> info(@RequestAttribute(ConstUtil.ATTR_USERNAME) String username){
        AccountVO accountVO = new AccountVO();
        Account account = accountService.getAccountByUsername(username);
        accountVO.setRole(account.getRole());
        accountVO.setUsername(account.getUsername());
        return RestBean.success(accountVO);
    }
}
