package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springmarket.shop.services.ProductService;
import springmarket.shop.utils.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class CartController {
    private ProductService productService;
    private Cart cart;

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

    @GetMapping("/cart/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        cart.removeById(id);
        return "redirect:/cart";
    }

}
