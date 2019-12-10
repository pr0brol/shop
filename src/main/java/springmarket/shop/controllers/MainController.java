package springmarket.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmarket.shop.entities.Category;
import springmarket.shop.entities.Product;
import springmarket.shop.services.CategoryService;
import springmarket.shop.services.ProductService;
import springmarket.shop.utils.Cart;
import springmarket.shop.utils.ProductFilter;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private ProductService productService;
    private CategoryService categoryService;
    private Cart cart;

    @Autowired
    public MainController(ProductService productService, CategoryService categoryService, Cart cart) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cart = cart;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        int pageIndex = 0;
        if (params.containsKey("p")) {
            pageIndex = Integer.parseInt(params.get("p")) - 1;
        }
        if(params.containsKey("id") && !params.get("id").isEmpty()){
            Long id = Long.parseLong(params.get("id"));
            cart.add(id);
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

    @GetMapping("/edit/{id}")
    public String editProductForm(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "edit_item";
    }

    @PostMapping("/edit")
    public String saveItem(@ModelAttribute(name = "product") Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String cartForm(Model model) {
        Map<Product, Integer> products = cart.allProductsInCart();
        model.addAttribute("products", products);
        return "cart";
    }
}
