package org.example.controller;

import jakarta.annotation.Resource;
import org.example.service.AccountService;
import org.example.utils.MethodUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthorizedController {
    @Resource
    AccountService accountService;
    @Resource
    MethodUtil methodUtil;
}
