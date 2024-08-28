package com.project.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
	
	@GetMapping("/login")
    public String home(Model model) {
        return "oauth_login";
    }
	
	@GetMapping("/")
    public String login(Model model) {
        return "oauth_login";
    }
	
	@GetMapping("/main")
    public String main(Model model) {
		System.out.println("main 호출!!!");
        return "main";
    }
	
	@GetMapping("/sign-up-page")
    public String signUpPage(Model model) {
		System.out.println("sign 호출!!!");
        return "sign_up";
	}
}
