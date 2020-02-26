package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmarket.shop.entities.User;
import springmarket.shop.services.MailService;
import springmarket.shop.services.UserService;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.Random;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class UserController {

    private final String SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final int SIZE = 40;

    private final String SHOP_URL = "http://localhost:8189/shop/";

    private UserService userService;
    private MailService mailService;

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

    @GetMapping("/registration/confirm/{token}")
    public String confirmRegistration(Model model, @PathVariable String token) {
        User user = userService.findByToken(token);
        if(user != null) {
            user.setToken(null);
            userService.save(user);
            model.addAttribute(user);
            return "registration_confirmed";
        }
        return "registration_failed";
    }

    @PostMapping("/registration/create")
    public String createUser(Model model,
                             @ModelAttribute(name = "user") User user,
                             @RequestParam(name = "password_1", required = false) String pass1,
                             @RequestParam(name = "password_2", required = false) String pass2){
        if(pass1.equals(pass2)){
            user.setPassword(pass1);
            String token = new Random().ints(SIZE, 0, SYMBOLS.length())
                    .mapToObj(SYMBOLS::charAt)
                    .map(Object::toString)
                    .collect(Collectors.joining());
            String text = SHOP_URL + "registration/confirm/" + token;
            user.setToken(token);
            boolean result = userService.addUser(user);
            if(result){
                model.addAttribute(user);
                try {
                    mailService.sendMail(user.getEmail(), "Confirm your account", text);
                } catch (MessagingException e) {
                    System.out.println("confirm ex " + e);
                }
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
