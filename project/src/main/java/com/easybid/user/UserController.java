package com.easybid.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;

    @GetMapping("/login")
    public String request_login(Model model) {
        model.addAttribute("user", new User());
        return "user/login";
    }

    /**로그아웃 처리 및 세션 삭제 */ 
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 전체 세션을 무효화하여 로그아웃 처리
        session.invalidate();        
        return "home";
    }
    
    /**
     * 로그인 폼 처리
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/login-check")
    public String login_handler(@ModelAttribute User user, HttpSession session) {
        if(userService.login_check(user)) {
            User tuser = userService.getUser(user.getUserEmail()).orElseThrow(null);
            session.setAttribute("userId", tuser.getId());
            return "home";
        } else {
            return "user/login";
        }
    }

    @GetMapping("/register")
    public String request_register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }
    /**
     * 회원가입 폼 처리
     * @param user
     * @param redirectAttributes
     * @param session
     * @return
     */
    @PostMapping("/register-check")
    public String register_handler(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        System.out.println("해당 이메일로 회원가입 시도 : " + user.getUserEmail());  // 디버깅용 로그

        if(userService.register(user)) {
            User tuser = userService.getUser(user.getUserEmail()).orElseThrow(null);
            session.setAttribute("userId", tuser.getId());

            System.out.println("성공");  // 디버깅용 로그

            return "home";
        } else {
            // 회원가입 실패 시 다시 login.html로 이동

            return "user/login";
        }
    }

    @GetMapping("/profile")
    public String get_user_profile(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        //유저의 데이터를 DB에서 가져와 model을 통해 뷰에 전달
        User user = userService.getUser((Long)session.getAttribute("userId")).orElseThrow(null);

        model.addAttribute("user", user);
        return "user/profile";
    }


    
}
