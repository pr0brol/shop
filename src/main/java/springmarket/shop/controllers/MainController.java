package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmarket.shop.entities.*;
import springmarket.shop.services.*;
import springmarket.shop.utils.ProductFilter;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class MainController {
    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private OrderService orderService;
    private FeedbackService feedbackService;

    @GetMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        int pageIndex = 0;
        if (params.containsKey("p")) {
            pageIndex = Integer.parseInt(params.get("p")) - 1;
        }
        Pageable pageRequest = PageRequest.of(pageIndex, 3);
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> page = productService.findAll(productFilter.getSpec(), pageRequest);

        List<Category> categories = categoryService.getAll();

        model.addAttribute("filtersDef", productFilter.getFilterDefinition());
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);
        return "index";
    }

    @PostMapping("/")
    public String pageAfterFeedback(Model model,
                                    @RequestParam(name = "feedback", required = false) String feedback,
                                    @RequestParam(name = "evaluation", required = false) String evaluation){
        Feedback feedBack = new Feedback(1L, feedback, Integer.parseInt(evaluation));
        feedbackService.save(feedBack);
        return "redirect:/";
    }

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
        List<Order> orders = orderService.findOrdersByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("orders", orders);
        return "profile";
    }

    @PostMapping("/registration/create")
    public String createUser(Model model,
                             @ModelAttribute(name = "user") User user,
                             @RequestParam(name = "password_1", required = false) String pass1,             // не будет исключения если не введён пароль
                             @RequestParam(name = "password_2", required = false) String pass2){
        if(pass1.equals(pass2)){
            user.setPassword(pass1);
            boolean result = userService.addUser(user);
            if(result){
                model.addAttribute(user);
                return "registration_success";
            }
            else {
                return "redirect:/registration";  //заменить на страницу с ошибкой регистрации
            }
        }
        return "redirect:/registration";
    }

    @GetMapping("/registration")
    public String addUser(){
        return "registration";
    }

}
