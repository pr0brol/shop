package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springmarket.shop.entities.User;
import springmarket.shop.services.UserService;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login_page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal){
        if(principal == null){
            return "redirect:/";
        }
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());
        return "profile";
    }

    @PostMapping("/registration/create")
    public String createUser(Model model,
                             @ModelAttribute(name = "user") User user,
                             @RequestParam(name = "password_1", required = false) String pass1,
                             @RequestParam(name = "password_2", required = false) String pass2){
        if(pass1.equals(pass2)){
            user.setPassword(pass1);
            boolean result = userService.addUser(user);
            if(result){
                model.addAttribute(user);
                return "registration_success";
            }
            else {
                return "redirect:/registration";
            }
        }
        return "redirect:/registration";
    }

    @GetMapping("/registration")
    public String addUser(){
        return "registration";
    }
}
