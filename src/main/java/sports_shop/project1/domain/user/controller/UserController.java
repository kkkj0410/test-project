package sports_shop.project1.domain.user.controller;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sports_shop.project1.domain.user.dto.UserDto;
import sports_shop.project1.domain.user.service.UserService;

import java.util.Arrays;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;
    private final HttpHeaders header;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.header = new HttpHeaders();
    }

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userDto", new UserDto());
        return "login";
    }

    @GetMapping("/login/success")
    public ResponseEntity<String> loginSuccess(HttpServletRequest request) {
        String jwtToken = findJwtToken(request);
        header.set("Authorization","Bearer "+ jwtToken);
        return ResponseEntity.ok().headers(header).body(jwtToken);
    }

    private String findJwtToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> "JwtToken".equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }


    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupRequest(@ModelAttribute UserDto userDto){
        log.info("userDto : {}", userDto);
        userService.save(userDto);
        return "success";
    }

    @GetMapping("/security/user")
    public String securityUser(){
        return "securityUser";
    }

    @GetMapping("/security/admin")
    public String securityAdmin(){
        return "securityAdmin";
    }

}
