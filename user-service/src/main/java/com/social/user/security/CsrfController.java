package com.social.user.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {

    @GetMapping("csrf")
    public CsrfToken csrfToken(CsrfToken csrfToken){
        return csrfToken;
    }
}
