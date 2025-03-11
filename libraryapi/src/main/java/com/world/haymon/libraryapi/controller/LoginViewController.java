package com.world.haymon.libraryapi.controller;

import com.world.haymon.libraryapi.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // to pages web
public class LoginViewController {

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication) {
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUsuario());
        }

        return "Olá " + authentication.getName();
    }

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code") String code) {
        return "Seu Authorization Code: " + code;
    }
}
