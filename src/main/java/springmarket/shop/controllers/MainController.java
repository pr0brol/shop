package springmarket.shop.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springmarket.shop.entities.Category;
import springmarket.shop.entities.Order;
import springmarket.shop.entities.Product;
import springmarket.shop.entities.User;
import springmarket.shop.services.CategoryService;
import springmarket.shop.services.OrderService;
import springmarket.shop.services.ProductService;
import springmarket.shop.services.UserService;
import springmarket.shop.utils.Cart;
import springmarket.shop.utils.ProductFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private OrderService orderService;
    private Cart cart;

    public MainController(ProductService productService, CategoryService categoryService, UserService userService, OrderService orderService, Cart cart) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.orderService = orderService;
        this.cart = cart;
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
        model.addAttribute("user", user);
        return "profile";
    }

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

    @GetMapping("/cart")
    public String cartForm(Model model) {
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/cart/add/{id}")
    public void addProductToCart(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.add(productService.findById(id));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/orders/control")
    public String showOrder(Model model, Principal principal){
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        return "save_order";
    }

    @GetMapping("/orders/create")
    public String createOrder(Principal principal){
        User user = userService.findByPhone(principal.getName());
        Order order = new Order(user, cart);
        orderService.save(order);
        return "redirect:/";
    }

    @GetMapping("/cart/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        cart.removeById(id);
        return "redirect:/cart";
    }

    @GetMapping("/orders/control/delete/{id}")
    public String deleteProductFromOrder(@PathVariable Long id){
        cart.removeById(id);
        return "redirect:/orders/control";
    }
}
