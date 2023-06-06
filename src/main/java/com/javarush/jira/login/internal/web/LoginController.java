package com.javarush.jira.login.internal.web;

import com.javarush.jira.login.internal.UserRepository;
/*import com.javarush.jira.login.internal.jwt.JwtRequest;
import com.javarush.jira.login.internal.jwt.JwtResponse;
import com.javarush.jira.login.internal.jwt.JwtUtils;*/
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;*/
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view/login")
@Slf4j
public class LoginController {

    @Autowired
    protected UserRepository repository;

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

    @GetMapping()
    public String login(Model model) {
        model.addAttribute("request", request);
        model.addAttribute("response", response);
        model.addAttribute("servletContext", servletContext);
        return "login";
    }
    /*@Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;*/


    /*@PostMapping()
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtUtils.generateJwtToken();
            JwtResponse jwtResponse = new JwtResponse(jwtToken);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + jwtToken)
                    .body(jwtResponse);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }*/
}
