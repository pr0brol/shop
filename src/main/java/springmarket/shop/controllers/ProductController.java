package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmarket.shop.entities.Category;
import springmarket.shop.entities.Product;
import springmarket.shop.entities.User;
import springmarket.shop.services.*;

import java.security.Principal;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private FeedbackService feedbackService;
    private OrderItemService orderItemService;

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
        feedbackService.deleteById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", productService.findById(prod_id));
        model.addAttribute("categories", categories);
        return "redirect:/edit/" + prod_id.toString();
    }

    @GetMapping("/product/{id}")
    public String showProductPage(Model model, Principal principal, @PathVariable Long id) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        if (principal != null) {
            User user = userService.findByPhone(principal.getName());
            if(orderItemService.findByProductIdAndUser(id, user).size() != 0 && feedbackService.findFeedbackByProductId(id) != null) {
                model.addAttribute("result", "already");
                return "product_page";
            } else if(orderItemService.findByProductIdAndUser(id, user).size() != 0 && feedbackService.findFeedbackByProductId(id) == null) {
                model.addAttribute("result", "ok");
                return "product_page";
            } else {
                model.addAttribute("result", "no");
                return "product_page";
            }
        }
        return "product_page";
    }
}
