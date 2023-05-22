package com.javarush.jira.login.internal.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class LoginController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ServletContext servletContext;
    @ModelAttribute("errorParam")
    public String errorParam(HttpServletRequest request) {
        return request.getParameter("error");
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", servletContext);
        return "login";
    }
}

