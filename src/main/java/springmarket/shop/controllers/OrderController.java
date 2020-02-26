package springmarket.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springmarket.shop.entities.Order;
import springmarket.shop.entities.User;
import springmarket.shop.rabbit.ClientController;
import springmarket.shop.rabbit.RabbitmqApplication;
import springmarket.shop.services.FeedbackService;
import springmarket.shop.services.OrderService;
import springmarket.shop.services.UserService;
import springmarket.shop.utils.Cart;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class OrderController {
    private UserService userService;
    private OrderService orderService;
    private Cart cart;
    private ClientController clientController;

    @GetMapping("/orders/control")
    public String showOrder(Model model, Principal principal){
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("cart", cart);
        return "order_save";
    }

    @GetMapping("/one_click")
    public String showSimpleOrder(Model model){
        model.addAttribute("cart", cart);
        return "simple_order_save";
    }

    @PostMapping("/orders/create")
    public String createOrder(Model model, Principal principal, @RequestParam(name = "address") String address, @RequestParam(name = "phone_number") String phone){
        User user = userService.findByPhone(principal.getName());
        Order order = new Order(user, cart, address, phone, "create");
        order = orderService.save(order);
        clientController.sendMessage("order id: " + order.getId());
        model.addAttribute("order_id", String.format("%05d ", order.getId()));
        return "order_result";
    }

    @PostMapping("/simple/create")
    public String createSimpleOrder(Model model, @RequestParam(name = "address") String address, @RequestParam(name = "phone_number") String phone){
        if(userService.isUserExist(phone)){
            User user = userService.findByPhone(phone);
            Order order = new Order(user, cart, address, phone, "create");
            order = orderService.save(order);
            clientController.sendMessage("order id: " + order.getId());
            model.addAttribute("order_id", String.format("%05d ", order.getId()));
            return "order_result";
        }
        User user = new User();
        user.setPhone(phone);
        userService.addUser(user);
        Order order = new Order(user, cart, address, phone, "create");
        order = orderService.save(order);
        clientController.sendMessage("order id: " + order.getId());
        model.addAttribute("order_id", String.format("%05d ", order.getId()));
        return "order_result";
    }

    @GetMapping("/orders/control/delete/{id}")
    public String deleteProductFromOrder(@PathVariable Long id){
        cart.removeById(id);
        return "redirect:/orders/control";
    }

    @GetMapping("/history")
    public String showHistory(Model model, Principal principal) {
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());
        return "history";
    }

    public void setStatus(String status) {
        if(status != null) {
            String[] statusWithId = status.split(" ");
            Long id = Long.parseLong(statusWithId[0]);
            String stat = statusWithId[1];
            Order order = orderService.findById(id);
            order.setStatus(stat);
            orderService.updateOrder(order);
        }
    }
}
