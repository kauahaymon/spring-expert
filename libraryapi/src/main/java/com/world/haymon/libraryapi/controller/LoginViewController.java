package com.world.haymon.libraryapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // to pages web
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }
}
