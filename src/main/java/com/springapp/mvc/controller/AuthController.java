package com.springapp.mvc.controller;

import com.springapp.mvc.authentication.LdapUserPwd;
import com.springapp.mvc.authentication.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by adrianh on 08.02.15.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserLoginService userLoginService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value= "/login", method = RequestMethod.POST)
    public @ResponseBody void login(@RequestBody LdapUserPwd ldapUserPwd) {
        logger.debug("Login try with : {}", ldapUserPwd);
        userLoginService.login(ldapUserPwd);
    }

    @RequestMapping("/logout")
    public @ResponseBody void logout() {
        userLoginService.logout();
    }
}
