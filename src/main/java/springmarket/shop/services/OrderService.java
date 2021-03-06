package springmarket.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springmarket.shop.entities.Order;
import springmarket.shop.repositories.OrderRepository;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public Order save(Order order){
        return orderRepository.save(order);
    }

    public Order findById(Long id) {
        return orderRepository.findOrderById(id);
    }

    public void updateOrder(Long id, String status) {
        orderRepository.update(id, status);
    }

    public void delete(Long id) {orderRepository.deleteById(id);}

}
