package kr.co.velnova.securityjwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 로그인
    @GetMapping("/user/test")
    public String user(Authentication authentication) {

        logger.debug("authentication = " + authentication.getPrincipal().toString());

        return "user test";
    }

    // 로그인
    @GetMapping("/admin/test")
    public String admin(Authentication authentication) {

        logger.debug("authentication = " + authentication.getPrincipal());

        return "admin test";
    }

    // 로그인
    @GetMapping("/super/test")
    public String supers(Authentication authentication) {

        logger.debug("authentication = " + authentication.getPrincipal());

        return "super test";
    }
}