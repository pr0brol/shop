package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmarket.shop.entities.Category;
import springmarket.shop.entities.Feedback;
import springmarket.shop.entities.Product;
import springmarket.shop.entities.User;
import springmarket.shop.services.CategoryService;
import springmarket.shop.services.FeedbackService;
import springmarket.shop.services.ProductService;
import springmarket.shop.services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private FeedbackService feedbackService;

    @GetMapping("/edit/{id}")
    public String editProductForm(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "edit_product";
    }

    @PostMapping("/edit")
    public String saveItem(@ModelAttribute(name = "product") Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/edit/delete/{id}")
    public String deleteFeedbackById(Model model, @PathVariable Long id) {
        Long prod_id = feedbackService.findById(id).getProd_id();
        feedbackService.deleteById(id);     // TODO найти почему не работает
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", productService.findById(prod_id));
        model.addAttribute("categories", categories);
        return "redirect:/edit/" + prod_id.toString();
    }

    @GetMapping("/product/{id}")
    public String showProductPage(Model model, Principal principal, @PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
//        for (Cookie c: request.getCookies()) {
//            if(c.getName().equals("product1")) {
//
//            }
//        }
        String result;
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        if (principal != null) {
            User user = userService.findByPhone(principal.getName());
            List<Feedback> feedbacks = user.getFeedbacks();
            model.addAttribute("user", user);
            List<Product> products = userService.findBuyedProductsByUser(user);
            if (products.size() == 0) {
                result = "no";
                model.addAttribute("result", result);
                return "product_page";
            }
            for (Product prod : products) {
                if (prod.getId() == product.getId()) {
                    if (feedbacks.size() == 0) {
                        result = "ok";
                        model.addAttribute("result", result);
                        break;
                    }
                    for (Feedback fb: feedbacks) {
                        if (fb.getProd_id() == product.getId()) {
                            result = "already";
                            model.addAttribute("result", result);
                            return "product_page";
                        } else {
                            result = "ok";
                            model.addAttribute("result", result);
                        }
                    }
                    return "product_page";
                } else {
                    result = "no";
                    model.addAttribute("result", result);
                }
            }
        }
        return "product_page";
    }
}
