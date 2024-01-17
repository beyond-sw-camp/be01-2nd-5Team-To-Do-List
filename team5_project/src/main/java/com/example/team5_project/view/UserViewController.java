package com.example.team5_project.view;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.team5_project.Service.AuthService;
import com.example.team5_project.Service.ProjectService;
import com.example.team5_project.Service.UserService;
import com.example.team5_project.model.LoginDto;
import com.example.team5_project.model.ProjectUser;
import com.example.team5_project.model.User;
import com.example.team5_project.model.UserDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class UserViewController {
	private final UserService userService;
	private final AuthService authService;
	private final ProjectService projectService;

    @GetMapping("/login")
    public String login() {    	
        return "login_form";
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response, @Valid LoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
       
        String jwtToken = authService.authorize(loginDto);
        // 서버에서 쿠키 설정
        Cookie cookie = new Cookie("jwtToken", jwtToken.substring(7));
        cookie.setMaxAge(60 * 60 * 24); // 쿠키의 유효기간 설정
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/main";
    }
    
    @GetMapping("/user/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwtToken", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // 쿠키의 경로를 애플리케이션의 루트로 설정 (필요에 따라 조절)

        // 응답 헤더에 Set-Cookie 헤더를 추가하여 클라이언트에게 쿠키를 삭제하도록 지시
        response.addCookie(cookie);
        return "redirect:/login";
    }
    

  
    @GetMapping("/user")
    public String userPage(Model model) {
    	
    	Optional<User> user = userService.getMyUserWithAuthorities();
    	Set<ProjectUser> projectUser = projectService.readProjectUser(user);
    	 
    	model.addAttribute("userDTO", new UserDto());
        model.addAttribute("user", user.get());   
        model.addAttribute("invite", projectUser);
    	return "mypage";
    }

    @PostMapping("/user/update")
    public String updateUser(HttpServletResponse response, @Valid UserDto userDto, BindingResult bindingResult) {
    	Optional<User> user = userService.getMyUserWithAuthorities();
    	userService.updateUser(userDto, user.get());

    	LoginDto loginDto = new LoginDto(userDto.getUsername(), userDto.getPassword());
        
        
        login(response, loginDto, bindingResult);
        
        return "redirect:/main";
    }
    
    @PostMapping("/user/delete")
    public String deleteUser() {
    	Optional<User> user = userService.getMyUserWithAuthorities();
    	userService.deleteUser(user.get());
        
        return "redirect:/main";
    }
	
    @GetMapping("/signup")
    public String signup(UserDto userDto) {    	
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(HttpServletResponse response, @Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        
        userService.signup(userDto);
        LoginDto loginDto = new LoginDto(userDto.getUsername(), userDto.getPassword());
        
        
        login(response, loginDto, bindingResult);
        
        return "redirect:/main";
    }

}
