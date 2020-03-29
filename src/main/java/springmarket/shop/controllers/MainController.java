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
    public String pageAfterFeedback(Model model, Principal principal,
                                    @RequestParam(name = "feedback", required = false) String feedback,
                                    @RequestParam(name = "evaluation", required = false) String evaluation,
                                    @RequestParam(name = "id") String id){
        Long ID = Long.parseLong(id);
        User user = userService.findByPhone(principal.getName());
        feedbackService.save(new Feedback(user.getId(), ID, feedback, Integer.parseInt(evaluation), productService.findById(ID), user));
        return "redirect:/";
    }

}
