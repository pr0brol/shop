package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springmarket.shop.entities.Category;
import springmarket.shop.entities.Product;
import springmarket.shop.services.CategoryService;
import springmarket.shop.services.ProductService;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;

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
}
